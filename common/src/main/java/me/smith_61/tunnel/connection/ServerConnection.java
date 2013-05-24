package me.smith_61.tunnel.connection;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import me.smith_61.tunnel.ErrorHandler;
import me.smith_61.tunnel.Server;
import me.smith_61.tunnel.ServerManager;
import me.smith_61.tunnel.ServerTunnel;
import me.smith_61.tunnel.exceptions.TunnelClosedException;
import me.smith_61.tunnel.exceptions.TunnelWriteException;
import me.smith_61.tunnel.packet.ChannelMessagePacket;
import me.smith_61.tunnel.packet.DisconnectPacket;
import me.smith_61.tunnel.packet.Packet;

public class ServerConnection implements ServerTunnel {

	private final Server server;
	private final Channel channel;
	
	private boolean isClosed;
	//This lock is used to keep multiple threads from closing this
	// connection at once. This keeps the amount of disconnect packets
	// to a minimum
	private Lock closeLock;
	
	protected ServerConnection(Server server, Channel channel) {
		this.server = server;
		this.channel = channel;
		
		this.isClosed = false;
		this.closeLock = new ReentrantLock();
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
		
		ChannelFuture future = this.sendPacket(new ChannelMessagePacket(this.getThisServerName(), this.getServer().getName(), channel, newData));
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

	@Override
	public boolean isClosed() {
		return this.isClosed;
	}
	
	public void disconnection(String reason) {
		if(!this.isClosed) {
			//If the lock is already locked then another thread is already
			// closing this connection.
			if(this.closeLock.tryLock()) {
				this.isClosed = true;
				if(this.channel.isActive()) {
					this.sendPacket(new DisconnectPacket(this.getThisServerName(), this.getServer().getName(), reason));
				}
			}
			
			
		}
	}
	
	public ChannelFuture sendPacket(Packet packet) {
		return this.channel.write(packet);
	}
	
	private String getThisServerName() {
		return this.getServer().getManager().getThisServer().getName();
	}
}
