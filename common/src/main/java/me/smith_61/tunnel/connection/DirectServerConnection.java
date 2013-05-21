package me.smith_61.tunnel.connection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import me.smith_61.tunnel.Server;
import me.smith_61.tunnel.exceptions.TunnelClosedException;
import me.smith_61.tunnel.packet.Packet;

/**
 * 
 * @author Jacob
 * @since
 * 
 * This represents a server connection that is direct to a server.
 */
public class DirectServerConnection extends ServerConnection {

	private final Channel channel;
	private boolean isClosed;
	
	public DirectServerConnection(Server server, Channel channel) {
		super(server);
		
		this.channel = channel;
		this.isClosed = false;
		
		this.channel.closeFuture().addListener(new GenericFutureListener<ChannelFuture>() {

			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				DirectServerConnection.this.close();
			}
			
		});
	}

	@Override
	public boolean isClosed() {
		return this.isClosed;
	}
	
	@Override
	public void close() {
		this.isClosed = true;
		if(this.channel.isActive()) {
			this.channel.close();
		}
	}

	@Override
	public ChannelFuture sendPacket(Packet packet) throws TunnelClosedException {
		if(this.isClosed()) {
			throw new TunnelClosedException(this);
		}
		return this.channel.write(packet);
	}

}
