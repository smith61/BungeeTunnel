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
 * This packet is not used in the protocol but rather during
 * 	testing. The Packet ID of 0 is not used and won't be used
 * 	for any actual packet
 */
public class TestPacket extends Packet {

	private static final byte[] data = new byte[100];
	
	TestPacket() {
		super(0);
	}
	
	@Override
	protected void writePacketData(DataOutput out) throws IOException {
		out.write(data);
	}

	@Override
	protected void readPacketData(DataInput in) throws InvalidPacketException, IOException {
		in.skipBytes(data.length);
	}

	@Override
	protected int getSize() {
		return data.length;
	}

}
