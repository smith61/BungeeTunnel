package me.smith_61.tunnel.server.events;

import me.smith_61.tunnel.server.Server;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * Event fired when a Server is registered with a
 *  ServerRegistry
 */
public class ServerRegisteredEvent extends ServerEvent {

	public ServerRegisteredEvent(Server server) {
		super(server);
	}
}
