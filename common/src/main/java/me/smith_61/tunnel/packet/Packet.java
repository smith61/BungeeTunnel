package me.smith_61.tunnel.packet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.charset.Charset;

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
	
	private String source = null;
	
	Packet(int id) {
		this.id = id;
	}
	
	Packet(int id, String source) {
		this(id);
		
		this.source = source;
	}
	
	public String getSource() {
		return this.source;
	}
	
	public abstract void handle(PacketHandler handler);
	
	protected abstract void writePacketData(DataOutput out) throws IOException;
	
	protected abstract void readPacketData(DataInput in) throws InvalidPacketException, IOException;
	
	protected boolean canWrite() {
		return this.source != null;
	}
	
	
	
	public static String readString(DataInput input) throws IOException {
		int length = input.readInt();
		
		byte[] chars = new byte[length];
		input.readFully(chars);
		
		return new String(chars, Charset.forName("UTF-8"));
	}
	
	public static void writeString(DataOutput output, String string) throws IOException {
		byte[] chars = string.getBytes(Charset.forName("UTF-8"));
		
		output.writeInt(chars.length);
		output.write(chars);
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
			
			p.source = Packet.readString(in);
			
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
			Packet.writeString(dataOut, p.source);
			
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
	
	public static final int TOTAL_PACKETS = 5;
	
	
	@SuppressWarnings("unchecked")
	private static final Class<? extends Packet>[] PACKET_CLASSES = new Class[TOTAL_PACKETS + 1];
	
	@SuppressWarnings("unchecked")
	private static final Constructor<? extends Packet>[] PACKET_CONS = new Constructor[PACKET_CLASSES.length];
	
	
	public static final int MESSAGEPACKET_ID = 1;
	public static final int DISCONNECTPACKET_ID = 2;
	public static final int SERVERREGISTEREDPACKET_ID = 3;
	public static final int CHANNELREGISTEREDPACKET_ID = 4;
	public static final int CHANNELUNREGISTEREDPACKET_ID = 5;
	
	
	static {
		PACKET_CLASSES[MESSAGEPACKET_ID] = ChannelMessagePacket.class;
		PACKET_CLASSES[DISCONNECTPACKET_ID] = DisconnectPacket.class;
		PACKET_CLASSES[SERVERREGISTEREDPACKET_ID] = ServerConnectedPacket.class;
		PACKET_CLASSES[CHANNELREGISTEREDPACKET_ID] = ChannelRegisteredPacket.class;
		PACKET_CLASSES[CHANNELUNREGISTEREDPACKET_ID] = ChannelUnregisteredPacket.class;
	}
}
