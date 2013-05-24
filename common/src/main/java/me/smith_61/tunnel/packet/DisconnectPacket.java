package me.smith_61.tunnel.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import me.smith_61.tunnel.Server;
import me.smith_61.tunnel.exceptions.InvalidPacketException;


public class DisconnectPacket extends Packet {

	private char[] reason;
	
	DisconnectPacket() {
		super(Packet.DISCONNECTPACKET_ID);
		
		this.reason = null;
	}
	
	public DisconnectPacket(String source, String destination, String reason) {
		super(Packet.DISCONNECTPACKET_ID, source, destination);
		
		this.reason = reason.toCharArray();
	}
	
	@Override
	public boolean canWrite() {
		return super.canWrite() && this.reason != null;
	}

	@Override
	protected void writePacketData(DataOutput out) throws IOException {
		Packet.writeCharArray(out, this.reason);
	}

	@Override
	protected void readPacketData(DataInput in) throws InvalidPacketException, IOException {
		this.reason = Packet.readCharArray(in);

	}
	
	@Override
	public void handle(PacketHandler handler) {
		handler.handleDisconnectPacket(this);
	}

}
