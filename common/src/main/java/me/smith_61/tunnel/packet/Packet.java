package me.smith_61.tunnel.packet;

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
 * The network packet header format is:
 * 		id: byte
 * 		sourceLength: int
 * 		source: String(UTF-8)
 * 		
 */
public abstract class Packet {

	private final int id;
	
	private char[] source = null;
	
	protected Packet(int id) {
		this.id = id;
	}
	
	protected Packet(int id, String source) {
		this(id);
		
		this.source = source.toCharArray();
	}
	
	public String getSource() {
		return new String(this.source);
	}
	
	public abstract void handle(PacketHandler handler);
	
	protected abstract void writePacketData(DataOutput out) throws IOException;
	
	protected abstract void readPacketData(DataInput in) throws InvalidPacketException, IOException;
	
	protected boolean canWrite() {
		return this.source != null && this.destination != null;
	}
	
	
	
	
	
	public static char[] readCharArray(DataInput input) throws IOException {
		int length = input.readInt();
		if(length < 0) {
			throw new IOException("Invalid length: " + length);
		}
		char[] chars = new char[length];
		for(int i=0; i<length; i++) {
			chars[i] = input.readChar();
		}
		
		return chars;
	}
	
	public static void writeCharArray(DataOutput output, char[] chars) throws IOException {
		output.writeInt(chars.length);
		for(int i=0; i<chars.length; i++) {
			output.writeChar(chars[i]);
		}
	}
	
	public static Packet readPacket(byte[] data) throws InvalidPacketException {
		try {
			DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
			
			int id = in.readUnsignedByte();
			if(id >= PACKET_CLASSES.length || PACKET_CLASSES[id] == null) {
				throw new InvalidPacketException(id);
			}
			
			Constructor<? extends Packet> con = PACKET_CONS[id];
			if(con == null) {
				try {
					con = PACKET_CONS[id] = PACKET_CLASSES[id].getDeclaredConstructor();
				}
				catch (NoSuchMethodException e) {
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
			
			p.source = Packet.readCharArray(in);
			p.destination = Packet.readCharArray(in);
			
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
			Packet.writeCharArray(dataOut, p.source);
			Packet.writeCharArray(dataOut, p.destination);
			
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
	
	public static final int PROTOCOL_VERSION = 1;
	
	public static final int TOTAL_PACKETS = 2;
	
	
	@SuppressWarnings("unchecked")
	private static final Class<? extends Packet>[] PACKET_CLASSES = new Class[TOTAL_PACKETS + 1];
	
	@SuppressWarnings("unchecked")
	private static final Constructor<? extends Packet>[] PACKET_CONS = new Constructor[PACKET_CLASSES.length];
	
	
	public static final int MESSAGEPACKET_ID = 0x01;
	public static final int DISCONNECTPACKET_ID = 0x02;
	
	static {
		PACKET_CLASSES[MESSAGEPACKET_ID] = ChannelMessagePacket.class;
		PACKET_CLASSES[DISCONNECTPACKET_ID] = DisconnectPacket.class;
	}
}
