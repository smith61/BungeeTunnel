package me.smith_61.tunnel.packet;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This packet is used to inform a server that
 * 	channels are now registered and being listened on
 */
public class ChannelRegisteredPacket extends ChannelInfoPacket {

	ChannelRegisteredPacket() {
		super(Packet.CHANNELREGISTEREDPACKET_ID);
	}
	
	public ChannelRegisteredPacket(String source, String... channels) {
		super(Packet.CHANNELREGISTEREDPACKET_ID, source, channels);
	}

	@Override
	public void handle(PacketHandler handler) {
		// TODO Auto-generated method stub

	}

}
