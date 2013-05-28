package me.smith_61.tunnel.packet;

import static org.junit.Assert.*;

import java.io.IOException;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

import org.junit.Test;

public class PacketTest {

	@Test
	public void testWrite() throws IOException {
		assertNotNull(Packet.writePacket(new ChannelMessagePacket("", "", new byte[0])));
	}

	@Test
	public void testRead() throws InvalidPacketException {
		byte[] packet = Packet.writePacket(new ChannelMessagePacket("", "", new byte[0]));
		Packet p = Packet.readPacket(packet);
		
		assertNotNull(p);
		if(!(p instanceof ChannelMessagePacket)) {
			fail("Wrong packet type returned. Expected: TestPacket. Got: " + p.getClass());
		}
	}
	
	@Test(expected = InvalidPacketException.class)
	public void testInvalidPacket() throws InvalidPacketException {
		byte[] packet = new byte[1];
		packet[0] = Packet.TOTAL_PACKETS + 2;
		Packet.readPacket(packet);
	}
	
	@Test
	public void testGetPacket() {
		for(int i=1; i<Packet.TOTAL_PACKETS + 1; i++) {
			assertNotNull(Packet.getPacketClass(i));
		}
	}
}
