package me.smith_61.tunnel.packet;

import static org.junit.Assert.*;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

import org.junit.Test;

public class TunneledPacketTest {

	@Test
	public void testGetSize() {
		ChannelMessagePacket tp = new ChannelMessagePacket("", new byte[0]);
		TunneledPacket p = new TunneledPacket("T", "T", tp);
		
		//int + int + int + char[] + char[] + PACKET_HEADER + Packet length
		assertEquals(4 + 4 + 4 + 2 + 2 + Packet.PACKET_HEADER_LENGTH + tp.getSize(), p.getSize());
	}

	@Test
	public void testReadWrite() throws InvalidPacketException {
		byte[] data = new byte[100];
		for(int i=0; i<data.length; i++) {
			data[i] = (byte) i;
		}
		
		ChannelMessagePacket tp = new ChannelMessagePacket("", data);
		TunneledPacket write = new TunneledPacket("T1", "T2", tp);
		
		byte[] packet = Packet.writePacket(write);
		TunneledPacket read = (TunneledPacket) Packet.readPacket(packet);
		
		assertArrayEquals(data, ((ChannelMessagePacket)read.getPacket()).getData());
		assertEquals(write.getDestination(), read.getDestination());
		assertEquals(write.getSource(), read.getSource());
		assertEquals(write.getSize(), read.getSize());
	}
}
