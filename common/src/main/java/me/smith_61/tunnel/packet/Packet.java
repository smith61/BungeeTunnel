package me.smith_61.tunnel.packet;

import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This class represents the base class for all network packets
 * 
 * The network packet format is:
 * 		id: byte
 * 		length: int
 * 		data: byte[length]
 */
public abstract class Packet {

	private final int id;
	
	protected Packet(int id) {
		this.id = id;
	}
	
	protected abstract void writePacketData(DataOutput out) throws IOException;
	
	protected abstract void readPacketData(DataInput in) throws InvalidPacketException, IOException;
	
	protected boolean canWrite() {
		return true;
	}
	
	protected abstract int getSize();
	
	public static void ensurePacketLength(ByteBuf buf) {
		buf.skipBytes(1); //Skip ID field
		int length = buf.readInt();
		
		buf.skipBytes(length);
	}
	
	public static Packet readPacket(byte[] data) throws InvalidPacketException {
		try {
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
			
			int id = in.readUnsignedByte();
			int length = in.readInt();
			if(id >= PACKET_CLASSES.length || PACKET_CLASSES[id] == null) {
				throw new InvalidPacketException(id);
			}
			
			Constructor<? extends Packet> con = PACKET_CONS[id];
			if(con == null) {
				try {
					con = PACKET_CONS[id] = PACKET_CLASSES[id].getDeclaredConstructor();
				}
				catch (NoSuchMethodException e) {
					in.skipBytes(length);
					
					throw new InvalidPacketException(PACKET_CLASSES[id]);
				}
			}
			
			Packet p = null;
			try {
				p = con.newInstance();
			}
			catch(Throwable t) {
				throw new InvalidPacketException("Exception while creating packet.", t);
			}
			
			p.readPacketData(in);
			
			
			return p;
		}
		catch(IOException ioe) {
			//Should never reach this
			throw new InvalidPacketException("IOException while reading packet.", ioe);
		}
	}
	
	public static byte[] writePacket(Packet p) {
		try {
			if(!p.canWrite()) {
				throw new RuntimeException("Can not write packet: " + p);
			}
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(byteOut);
			
			dataOut.writeByte(p.id);
			dataOut.writeInt(p.getSize());
			p.writePacketData(dataOut);
			
			return byteOut.toByteArray();
		}
		catch(IOException ioe) {
			//Should never reach this
			throw new RuntimeException(ioe);
		}
	}
	
	public static Class<? extends Packet> getPacketClass(int id) {
		return PACKET_CLASSES[id];
	}
	
	public static final int TOTAL_PACKETS = 3;
	public static final int PACKET_HEADER_LENGTH = 1 + 4;
	
	@SuppressWarnings("unchecked")
	private static final Class<? extends Packet>[] PACKET_CLASSES = new Class[TOTAL_PACKETS];
	
	@SuppressWarnings("unchecked")
	private static final Constructor<? extends Packet>[] PACKET_CONS = new Constructor[PACKET_CLASSES.length];
	
	
	public static final int TUNNELEDPACKET_ID = 0x01;
	public static final int MESSAGEPACKET_ID = 0x02;
	
	static {
		PACKET_CLASSES[TUNNELEDPACKET_ID] = TunneledPacket.class;
		PACKET_CLASSES[MESSAGEPACKET_ID] = ChannelMessagePacket.class;
	}
}
