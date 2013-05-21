package me.smith_61.tunnel.connection;

import static org.junit.Assert.*;
import io.netty.channel.ChannelFuture;

import me.smith_61.tunnel.exceptions.TunnelClosedException;
import me.smith_61.tunnel.packet.Packet;

import org.junit.Test;

public class ServerConnectionTest {

	private static final byte[] data = new byte[100];
	
	@Test
	public void testWrite() throws TunnelClosedException {
		ServerConnection connection = new DummyConnection();
		
		connection.sendData("TestChannel", data, null);
	}
	
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testBoundaries() throws TunnelClosedException {
		ServerConnection connection = new DummyConnection();
		
		connection.sendData("TestChannel", data, 0, data.length + 1, null);
	}

	
	private static class DummyConnection extends ServerConnection {
		
		private DummyConnection() {
			super(null);
		}

		@Override
		public boolean isClosed() {
			return false;
		}

		@Override
		public void close() {
		}

		@Override
		public ChannelFuture sendPacket(Packet packet) {
			return null;
		}
		
		
	}
}
