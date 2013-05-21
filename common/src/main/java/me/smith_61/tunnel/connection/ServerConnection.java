package me.smith_61.tunnel.connection;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import me.smith_61.tunnel.ErrorHandler;
import me.smith_61.tunnel.Server;
import me.smith_61.tunnel.ServerTunnel;
import me.smith_61.tunnel.exceptions.TunnelClosedException;
import me.smith_61.tunnel.exceptions.TunnelWriteException;
import me.smith_61.tunnel.packet.ChannelMessagePacket;
import me.smith_61.tunnel.packet.Packet;

public abstract class ServerConnection implements ServerTunnel {

	private final Server server;
	
	protected ServerConnection(Server server) {
		this.server = server;
	}
	
	@Override
	public void sendData(String channel, byte[] data, ErrorHandler handler) throws TunnelClosedException {
		this.sendData(channel, data, 0, data.length, handler);
	}

	@Override
	public void sendData(final String channel, byte[] data, int start, int length, final ErrorHandler handler) throws TunnelClosedException, ArrayIndexOutOfBoundsException {
		if(this.isClosed()) {
			throw new TunnelClosedException(this);
		}
		if(start + length > data.length) {
			throw new ArrayIndexOutOfBoundsException((start + length) + " is greater than " + data.length);
		}
		
		
		byte[] newData = new byte[length];
		System.arraycopy(data, start, newData, 0, length);
		
		ChannelFuture future = this.sendPacket(new ChannelMessagePacket(channel, newData));
		if(handler != null) {
			future.addListener(new GenericFutureListener<ChannelFuture>() {

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if(!future.isSuccess()) {
						if(ServerConnection.this.isClosed()) {
							handler.onError(new TunnelClosedException(ServerConnection.this));
						}
						else {
							handler.onError(new TunnelWriteException(ServerConnection.this, channel, future.cause()));
						}
					}
				}
				
			});
		}
	}
	
	@Override
	public Server getServer() {
		return this.server;
	}
	
	public abstract void close();
	
	public abstract ChannelFuture sendPacket(Packet packet) throws TunnelClosedException;
}
