package me.smith_61.tunnel.packet;

import static org.junit.Assert.*;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

import org.junit.Test;

public class ChannelMessagePacketTest {

	@Test
	public void testGetSize() {
		ChannelMessagePacket packet = new ChannelMessagePacket("T", new byte[1]);
		
		//int + int + char + byte
		assertEquals(4 + 4 + 2 + 1, packet.getSize());
	}
	
	@Test
	public void testReadWrite() throws InvalidPacketException {
		byte[] data = new byte[100];
		for(int i=0; i<data.length; i++) {
			data[i] = (byte)i;
		}
		ChannelMessagePacket write = new ChannelMessagePacket("T", data);
		
		ChannelMessagePacket read = (ChannelMessagePacket)Packet.readPacket(Packet.writePacket(write));
		
		assertArrayEquals(write.getData(), read.getData());
		assertEquals(write.getChannel(), read.getChannel());
	}
}
