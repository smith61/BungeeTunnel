package me.smith_61.tunnel.netty;

import me.smith_61.tunnel.packet.Packet;


//This packet is never read only written.
public class TunneledPacket {

	private final String destination;
	private final byte[] packet;
	
	public TunneledPacket(String destination, Packet packet) {
		this.destination = destination;
		this.packet = Packet.writePacket(packet);
	}
	
	public byte[] getRawPacket() {
		return this.packet;
	}
	
	public String getDestination() {
		return this.destination;
	}
}
