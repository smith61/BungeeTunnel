package me.smith_61.tunnel.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This class is used to handle the registration,
 *  creation, reading, and writing of Packets
 *  
 * PacketManagers have two states. Initialization and Read-Only
 * 
 * In the Initialization state the PacketManager is not asynchronous
 * 	and Packets can only be registered to the PacketManager. Packets
 * 	can not be read, written, or created using this PacketManager while
 * 	it is in this state
 * 
 * In the Read-Only state the PacketManager is fully asynchronous. Packets
 * 	can not longer be registered with this PacketManager but can be read,
 * 	written, and created without the need for external synchronization. This
 * 	does not include any external resources such as streams and possibly Packets
 * 	needing to be synchronized
 * 
 */
public class PacketManager {

	private boolean isReadOnly;
	
	private Class<? extends Packet>[] packetClasses;
	
	@SuppressWarnings("unchecked")
	public PacketManager() {
		this.isReadOnly = false;
		
		this.packetClasses = new Class[Packet.MAX_PACKET_ID];
	}
	
	/**
	 * Sets a PacketManager to read-only mode
	 */
	public void setReadOnly() {
		this.isReadOnly = true;
	}
	
	/**
	 * Gets if this PacketManager is in read-only mode
	 * 
	 * @return If this PacketManager is read-only
	 */
	public boolean isReadOnly() {
		return this.isReadOnly;
	}
	
	/**
	 * Registers a Packet to the given id
	 * 
	 * IDs must be within the acceptable Packet ID range
	 * 
	 * @param id The id of the packet
	 * @param packetClass The packet class
	 * 
	 * @throws IllegalStateException If this PacketManager is read-only
	 * @throws IllegalArgumentException If id is not a valid Packet ID, the id is already registered, or packetClass is not a valid Packet class
	 */
	public void registerPacket(int id, Class<? extends Packet> packetClass) throws IllegalStateException, IllegalArgumentException {
		if(this.isReadOnly()) {
			throw new IllegalStateException("PacketManager is read-only");
		}
		
		if(id < Packet.MIN_PACKET_ID || id > Packet.MAX_PACKET_ID) {
			throw new IllegalArgumentException("Invalid Packet ID: " + id);
		}
		
		if(this.packetClasses[id] != null) {
			throw new IllegalArgumentException("Packet ID: " + id + " is already registered.");
		}
		
		//Check to see if packetClass has a public no-arg constructor
		try {
			packetClass.newInstance();
			//packetClass has a public no-arg constructor
		}
		catch(Throwable t) {
			//packetClass does not have a public no-arg constructor
			throw new IllegalArgumentException("Invalid packet class: " + packetClass, t);
		}
		
		this.packetClasses[id] = packetClass;
	}
	
	/**
	 * Reads a packet from the given stream
	 * 
	 * @param input The stream to read from
	 * 
	 * @return The read in packet
	 * 
	 * @throws IllegalStateException If this PacketManager is not in read-only mode
	 */
	public Packet readPacket(DataInput input) throws IOException, IllegalStateException {
		if(!this.isReadOnly()) {
			throw new IllegalStateException("PacketManager is not in read-only mode");
		}
		
		int id = input.readUnsignedByte();
		Class<? extends Packet> packetClass = this.packetClasses[id];
		
		if(packetClass == null) {
			throw new IOException("Invalid packet id: " + id);
		}
		
		Packet packet = null;
		try {
			packet = packetClass.newInstance();
		}
		catch (InstantiationException e) {
			throw new IOException("Error creating packet.", e);
		} 
		catch (IllegalAccessException e) {
			//Ignore. This should never happen as we verified access to the class when it was registered
		}
		
		packet.readPacketData(input);
		
		return packet;
	}
	
	/**
	 * Writes a packet to the given stream
	 * 
	 * @param output The stream to write to
	 * @param packet The packet to write
	 * 
	 * @throws IllegalArgumentException If the packet is not registered to this PacketManager
	 * @throws IllegalStateException If this PacketManager is not in read-only mode
	 */
	public void writePacket(DataOutput output, Packet packet) throws IOException, IllegalArgumentException, IllegalStateException {
		if(!this.isReadOnly()) {
			throw new IllegalStateException("PacketManager is not in read-only mode");
		}
		
		int id = packet.getID();
		if(this.packetClasses[id] != packet.getClass()) {
			throw new IllegalArgumentException("Packet: " + packet + " is not registered with this PacketManager");
		}
		
		output.writeByte(id);
		packet.writePacketData(output);
	}
}
