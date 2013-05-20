package me.smith_61.tunnel.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

/**
 * 
 * @author Jacob
 * @see 1.0.0
 * 
 * This packet represents another packet that is tunneled
 * 	over a connection.
 * 
 * The data format for this packet is:
 * 		destinationLength: int
 * 		destination: char[destinationLength]
 * 		packetLength: int
 * 		tunneledPacket: Packet
 */
public class TunneledPacket extends Packet {

	private char[] destination;
	private Packet tunneledPacket;
	
	TunneledPacket() {
		super(Packet.TUNNELEDPACKET_ID);
		
		tunneledPacket = null;
		destination = null;
	}
	
	public TunneledPacket(String destination, Packet packet) {
		super(Packet.TUNNELEDPACKET_ID);
		
		this.destination = destination.toCharArray();
		this.tunneledPacket = packet;
	}
	
	public String getDestination() {
		return new String(this.destination);
	}
	
	public Packet getPacket() {
		return this.tunneledPacket;
	}
	
	@Override
	protected boolean canWrite() {
		return super.canWrite() && this.destination != null && this.tunneledPacket != null;
	}
	
	@Override
	protected void writePacketData(DataOutput out) throws IOException {
		out.writeInt(this.destination.length);
		for(int i=0; i<this.destination.length; i++) {
			out.writeChar(this.destination[i]);
		}
		
		byte[] packet = Packet.writePacket(this.tunneledPacket);
		out.writeInt(packet.length);
		out.write(packet);
	}

	@Override
	protected void readPacketData(DataInput in) throws InvalidPacketException, IOException {
		int length = in.readInt();
		this.destination = new char[length];
		for(int i=0; i<length; i++) {
			this.destination[i] = in.readChar();
		}
		
		length = in.readInt();
		byte[] packet = new byte[length];
		in.readFully(packet);
		
		this.tunneledPacket = Packet.readPacket(packet);
	}

	@Override
	protected int getSize() {
		//2 Ints + Char array + TunneledPacket length
		return 8 + (this.destination.length * 2) + Packet.PACKET_HEADER_LENGTH + this.tunneledPacket.getSize();
	}

}
