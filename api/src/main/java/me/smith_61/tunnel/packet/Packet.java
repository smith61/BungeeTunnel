package me.smith_61.tunnel.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * Base class for Packets. All packets must
 * 	provide a public no-arg constructor that
 * 	is used to create a new packet for reading
 * 
 * Packet ids can only be in the range of 0-255
 */
public abstract class Packet {

	/**
	 * The min id a packet can use
	 */
	public static final int MIN_PACKET_ID = 0;
	
	/**
	 * The max is a packet can use
	 */
	public static final int MAX_PACKET_ID = 255;
	
	
	private final byte id;
	
	protected Packet(byte id) {
		this.id = id;
	}
	
	/**
	 * Gets the id of this packet
	 * 
	 * @return The id of this packet
	 */
	public final int getID() {
		return this.id & 0xFF;
	}
	protected abstract void readPacketData(DataInput input) throws IOException;
	
	protected abstract void writePacketData(DataOutput output) throws IOException;
}
