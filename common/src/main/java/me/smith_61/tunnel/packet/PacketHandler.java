package me.smith_61.tunnel.packet;

import me.smith_61.tunnel.Server;
import me.smith_61.tunnel.ServerManagerImpl;
import me.smith_61.tunnel.connection.ServerConnection;

public class PacketHandler {

	private final ServerManagerImpl manager;
	
	protected PacketHandler(ServerManagerImpl manager) {
		this.manager = manager;
	}
	
	public void handleChannelMessage(ChannelMessagePacket packet) {
	}
	
	public void handleDisconnectPacket(DisconnectPacket packet) {
	}
}
