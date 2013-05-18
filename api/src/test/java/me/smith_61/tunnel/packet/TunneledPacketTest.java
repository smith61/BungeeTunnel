package me.smith_61.tunnel.packet;

import static org.junit.Assert.*;

import org.junit.Test;

public class TunneledPacketTest {

	@Test
	public void testGetSize() {
		TestPacket tp = new TestPacket();
		TunneledPacket p = new TunneledPacket("T", tp);
		
		//int + int + char + PACKET_HEADER + byte[100]
		assertEquals(4 + 4 + 2 + Packet.PACKET_HEADER_LENGTH + tp.getSize(), p.getSize());
	}

}
