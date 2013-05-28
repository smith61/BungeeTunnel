package me.smith_61.tunnel.packet;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This packet is used to inform another server that channels have
 * 	been unregistered and are no longer listened on.
 */
public class ChannelUnregisteredPacket extends ChannelInfoPacket {

	ChannelUnregisteredPacket() {
		super(Packet.CHANNELUNREGISTEREDPACKET_ID);
	}
	
	public ChannelUnregisteredPacket(String source, String... channels) {
		super(Packet.CHANNELUNREGISTEREDPACKET_ID, source, channels);
	}
	
	@Override
	public void handle(PacketHandler handler) {
		// TODO Auto-generated method stub

	}

}
