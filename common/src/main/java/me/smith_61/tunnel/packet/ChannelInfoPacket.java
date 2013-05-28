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
 * This class provides the base class for ChannelInfo packets
 * 	such as ChannelRegisteredPacket and ChannelUnregisteredPacket
 * 
 * The packet format is:
 * 		channelsLength: int
 * 		channels: String[channelsLength](UTF-8)
 */
public abstract class ChannelInfoPacket extends Packet {

	private String[] channels = null;
	
	ChannelInfoPacket(int id) {
		super(id);
	}
	
	ChannelInfoPacket(int id, String source, String[] channels) {
		super(id, source);
		
		Preconditions.checkNotNull(channels, "channels");
		Preconditions.checkArgument(channels.length != 0, "Can not send 0 channels");
		for(int i=0; i<channels.length; i++) {
			Preconditions.checkNotNull(channels[i], "channel[%d]", i);
		}
		this.channels = channels.clone();
	}
	
	public String[] getChannels() {
		return this.channels.clone();
	}
	
	@Override
	protected boolean canWrite() {
		return super.canWrite() && this.channels != null;
	}

	@Override
	protected void writePacketData(DataOutput out) throws IOException {
		out.writeInt(this.channels.length);
		for(String channel : this.channels) {
			Packet.writeString(out, channel);
		}
	}

	@Override
	protected void readPacketData(DataInput in) throws InvalidPacketException, IOException {
		int length = in.readInt();
		this.channels = new String[length];
		
		for(int i=0; i<length; i++) {
			this.channels[i] = Packet.readString(in);
		}
	}

}
