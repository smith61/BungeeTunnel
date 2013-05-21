package me.smith_61.tunnel.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.google.common.base.Preconditions;

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
 * 		sourceLength: int
 * 		source: char[sourceLength]
 * 		destinationLength: int
 * 		destination: char[destinationLength]
 * 		packetLength: int
 * 		tunneledPacket: Packet
 */
public class TunneledPacket extends Packet {

	private char[] source;
	private char[] destination;
	
	private Packet tunneledPacket;
	private byte[] rawPacket;
	
	TunneledPacket() {
		super(Packet.TUNNELEDPACKET_ID);
		
		this.source = null;
		this.tunneledPacket = null;
		this.destination = null;
		this.rawPacket = null;
	}
	
	public TunneledPacket(String source, String destination, Packet packet) {
		super(Packet.TUNNELEDPACKET_ID);
		String format = "%s can not be null.";
		
		Preconditions.checkNotNull(source, format, "Source");
		Preconditions.checkNotNull(destination, format, "Detination");
		Preconditions.checkNotNull(packet, format, "Packet");
		
		this.source = source.toCharArray();
		this.destination = destination.toCharArray();
		this.tunneledPacket = packet;
		this.rawPacket = null;
		
	}
	
	public String getSource() {
		return new String(this.source);
	}
	
	public String getDestination() {
		return new String(this.destination);
	}
	
	public Packet getPacket() throws InvalidPacketException {
		if(this.tunneledPacket == null) {
			this.tunneledPacket = Packet.readPacket(this.rawPacket);
		}
		return this.tunneledPacket;
	}
	
	private byte[] getRawPacket() {
		if(this.rawPacket == null) {
			this.rawPacket = Packet.writePacket(this.tunneledPacket);
		}
		
		return this.rawPacket;
	}
	
	@Override
	protected boolean canWrite() {
		return super.canWrite() && this.source != null && this.destination != null && (this.tunneledPacket != null || this.rawPacket != null);
	}
	
	@Override
	protected void writePacketData(DataOutput out) throws IOException {
		out.writeInt(this.source.length);
		for(int i=0; i<this.source.length; i++) {
			out.writeChar(this.source[i]);
		}
		
		out.writeInt(this.destination.length);
		for(int i=0; i<this.destination.length; i++) {
			out.writeChar(this.destination[i]);
		}
		
		byte[] packet = this.getRawPacket();
		out.writeInt(packet.length);
		out.write(packet);
	}

	@Override
	protected void readPacketData(DataInput in) throws InvalidPacketException, IOException {
		int length = in.readInt();
		this.source = new char[length];
		for(int i=0; i<length; i++) {
			this.source[i] = in.readChar();
		}
		
		length = in.readInt();
		this.destination = new char[length];
		for(int i=0; i<length; i++) {
			this.destination[i] = in.readChar();
		}
		
		length = in.readInt();
		this.rawPacket = new byte[length];
		in.readFully(this.rawPacket);
	}

	@Override
	protected int getSize() {
		//3 Ints + Char array + Char array +  TunneledPacket length
		return (4 * 3) + (this.source.length * 2) + (this.destination.length * 2) + this.getRawPacket().length;
	}

}
