package me.smith_61.tunnel.packet;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import me.smith_61.tunnel.exceptions.InvalidPacketException;

/**
 * 
 * @author Jacob
 * @since
 * 
 * This packet is used to inform a Server that another
 * 	server has been added. The source field of this
 * 	packet is the name of the server that was added
 */
public class ServerConnectedPacket extends Packet {
	
	ServerConnectedPacket() {
		super(Packet.SERVERREGISTEREDPACKET_ID);
	}
	
	public ServerConnectedPacket(String source) {
		super(Packet.SERVERREGISTEREDPACKET_ID, source);
	}
	
	@Override
	public void handle(PacketHandler handler) {
	}

	@Override
	protected void writePacketData(DataOutput out) throws IOException {
	}

	@Override
	protected void readPacketData(DataInput in) throws InvalidPacketException, IOException {
	}

}
