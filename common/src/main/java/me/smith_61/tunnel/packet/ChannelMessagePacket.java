package me.smith_61.tunnel.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.google.common.base.Preconditions;

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
 * 		channel: String(UTF-8)
 * 		dataLength: int
 * 		data: byte[dataLength]
 */
public class ChannelMessagePacket extends Packet {

	private String channel;
	private byte[] data;
	
	ChannelMessagePacket() {
		super(Packet.MESSAGEPACKET_ID);
		
		channel = null;
		data = null;
	}
	
	public ChannelMessagePacket(String source, String channel, byte[] data) {
		super(Packet.MESSAGEPACKET_ID, source);
		String format = "%s can not be null.";
		
		Preconditions.checkNotNull(channel, format, "Channel");
		Preconditions.checkNotNull(data, format, "Data");
		
		this.channel = channel;
		this.data = data;
	}
	
	public String getChannel() {
		return this.channel;
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
		Packet.writeString(out, this.channel);
		
		out.writeInt(this.data.length);
		out.write(this.data);
	}

	@Override
	protected void readPacketData(DataInput in) throws InvalidPacketException, IOException {
		this.channel = Packet.readString(in);
		
		int length = in.readInt();
		this.data = new byte[length];
		
		in.readFully(this.data);
	}

	@Override
	public void handle(PacketHandler handler) {
		handler.handleChannelMessage(this);
	}
}
