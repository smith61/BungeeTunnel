package me.smith_61.tunnel.packet;

import static org.junit.Assert.*;

import java.io.IOException;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

import org.junit.Test;

public class PacketTest {

	@Test
	public void testWrite() throws IOException {
		assertNotNull(Packet.writePacket(new TestPacket()));
	}

	@Test
	public void testRead() throws IOException, InvalidPacketException {
		byte[] packet = Packet.writePacket(new TestPacket());
		Packet p = Packet.readPacket(packet);
		
		assertNotNull(p);
		if(!(p instanceof TestPacket)) {
			fail("Wrong packet type returned. Expected: TestPacket. Got: " + p.getClass());
		}
	}
}
