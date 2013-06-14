package me.smith_61.tunnel.server.events;

import me.smith_61.tunnel.server.Server;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * Event fired when a server is unregistered from
 *  a ServerRegistry
 */
public abstract class ServerUnregisteredEvent extends ServerEvent {

	public ServerUnregisteredEvent(Server server) {
		super(server);
	}
}
