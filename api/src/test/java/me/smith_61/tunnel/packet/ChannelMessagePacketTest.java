package me.smith_61.tunnel.packet;

import static org.junit.Assert.*;

import org.junit.Test;

public class ChannelMessagePacketTest {

	@Test
	public void testGetSize() {
		ChannelMessagePacket packet = new ChannelMessagePacket("T", new byte[1]);
		
		//int + int + char + byte
		assertEquals(4 + 4 + 2 + 1, packet.getSize());
	}
}
