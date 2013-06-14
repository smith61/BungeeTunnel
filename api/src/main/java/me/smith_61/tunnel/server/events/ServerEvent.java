package me.smith_61.tunnel.server.events;

import me.smith_61.tunnel.events.Event;
import me.smith_61.tunnel.server.Server;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * Base class for Server events
 */
public abstract class ServerEvent extends Event {

	private final Server server;
	
	protected ServerEvent(Server server) {
		this.server = server;
	}
	
	public final Server getServer() {
		return this.server;
	}
}
