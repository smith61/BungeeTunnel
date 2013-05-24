package me.smith_61.tunnel.packet;

import me.smith_61.tunnel.Server;
import me.smith_61.tunnel.ServerManagerImpl;
import me.smith_61.tunnel.connection.ServerConnection;

public class PacketHandler {

	private final ServerManagerImpl manager;
	
	protected PacketHandler(ServerManagerImpl manager) {
		this.manager = manager;
	}
	
	//This routes a packet to its given destination
	// If this server is the packet's end destination
	// this method will return false. Otherwise it will
	// return true
	protected boolean routePacket(Packet packet) {
		String thisServerName = this.manager.getThisServer().getName();
		if(packet.getDestination().equals(thisServerName)) {
			return false;
		}
		
		Server destination = this.manager.getServer(packet.getDestination());
		if(destination == null) {
			System.err.println("Invalid destination: " + packet.getDestination());
			System.err.println("Discarding packet.");
			return true;
		}
		
		((ServerConnection)destination.getTunnel()).sendPacket(packet);
		return true;
	}
	
	public void handleChannelMessage(ChannelMessagePacket packet) {
	}
	
	public void handleDisconnectPacket(DisconnectPacket packet) {
	}
}
