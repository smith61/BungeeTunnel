package me.smith_61.tunnel.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This packet represents a unit of data sent over
 * 	a plugin channel
 * 
 * The data format for this message is:
 * 		channelLength: int
 * 		channel: char[channelLength]
 * 		dataLength: int
 * 		data: byte[dataLength]
 */
public class ChannelMessagePacket extends Packet {

	private char[] channel;
	private byte[] data;
	
	ChannelMessagePacket() {
		super(Packet.MESSAGEPACKET_ID);
		
		channel = null;
		data = null;
	}
	
	public ChannelMessagePacket(String channel, byte[] data) {
		super(Packet.MESSAGEPACKET_ID);
		
		this.channel = channel.toCharArray();
		this.data = data;
	}
	
	public String getChannel() {
		return new String(this.channel);
	}
	
	public byte[] getData() {
		return this.data;
	}
	
	@Override
	protected boolean canWrite() {
		return super.canWrite() && this.channel != null && this.data != null;
	}

	@Override
	protected void writePacketData(DataOutput out) throws IOException {
		out.writeInt(this.channel.length);
		for(int i=0; i<this.channel.length; i++) {
			out.writeChar(this.channel[i]);
		}
		
		out.writeInt(this.data.length);
		out.write(this.data);
	}

	@Override
	protected void readPacketData(DataInput in) throws InvalidPacketException, IOException {
		int length = in.readInt();
		
		this.channel = new char[length];
		for(int i=0; i<length; i++) {
			this.channel[i] = in.readChar();
		}
		
		length = in.readInt();
		this.data = new byte[length];
		
		in.readFully(this.data);
	}

	@Override
	protected int getSize() {
		//Two ints + Char array + Byte array
		return 8 + (this.channel.length * 2) + this.data.length;
	}
}
