package me.smith_61.tunnel.packet;

import static org.junit.Assert.*;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

import org.junit.Test;

public class ChannelMessagePacketTest {
	
	@Test
	public void testReadWrite() throws InvalidPacketException {
		byte[] data = new byte[100];
		for(int i=0; i<data.length; i++) {
			data[i] = (byte)i;
		}
		ChannelMessagePacket write = new ChannelMessagePacket("TestServer", "TestServer", "TestChannel", data);
		
		ChannelMessagePacket read = (ChannelMessagePacket)Packet.readPacket(Packet.writePacket(write));
		
		assertArrayEquals(write.getData(), read.getData());
		assertEquals(write.getChannel(), read.getChannel());
	}
}
