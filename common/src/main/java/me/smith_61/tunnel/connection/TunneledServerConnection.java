package me.smith_61.tunnel.connection;

import io.netty.channel.ChannelFuture;
import me.smith_61.tunnel.Server;
import me.smith_61.tunnel.ServerManager;
import me.smith_61.tunnel.exceptions.TunnelClosedException;
import me.smith_61.tunnel.packet.Packet;
import me.smith_61.tunnel.packet.TunneledPacket;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This class represents a connection to a server that is tunneled
 * 	through another connection
 */
public class TunneledServerConnection extends ServerConnection {

	private final ServerConnection tunnel;
	
	private boolean isClosed;
	
	public TunneledServerConnection(Server server, ServerConnection tunnel) {
		super(server);
		
		this.tunnel = tunnel;
		this.isClosed = false;
	}

	@Override
	public boolean isClosed() {
		//If the tunnel is closed we can't send packets anyways
		return this.tunnel.isClosed() || this.isClosed;
	}
	
	@Override
	public void close() {
		this.isClosed = true;
	}

	@Override
	public ChannelFuture sendPacket(Packet packet) throws TunnelClosedException {
		if(!(packet instanceof TunneledPacket)) {
			//If the packet is not already being tunneled we need to wrap
			// it in a tunneled packet before sending.
			//If the packet is already a TunneledPacket we should not wrap it
			// again.
			String source = this.getServer().getManager().getServer(ServerManager.SELF).getName();
			
			packet = new TunneledPacket(source, this.getServer().getName(), packet);
		}
		return this.tunnel.sendPacket(packet);
	}

}
