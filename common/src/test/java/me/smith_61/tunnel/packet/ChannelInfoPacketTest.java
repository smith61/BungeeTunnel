package me.smith_61.tunnel.packet;

import static org.junit.Assert.*;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

import org.junit.Assert;
import org.junit.Test;

public class ChannelInfoPacketTest {

	@Test
	public void testReadWrite() throws InvalidPacketException {
		String[] oldChannels = new String[] { "Channel1", "Channel2" };
		
		ChannelInfoPacket packet = new ChannelRegisteredPacket("TestServer", oldChannels);
		
		packet = (ChannelInfoPacket)Packet.readPacket(Packet.writePacket(packet));
		
		String[] newChannels = packet.getChannels();
		Assert.assertArrayEquals(oldChannels, newChannels);
	}

}
