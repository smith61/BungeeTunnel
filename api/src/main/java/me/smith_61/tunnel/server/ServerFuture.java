package me.smith_61.tunnel.server;

import me.smith_61.tunnel.concurrent.Future;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This represents a Future task involving a server
 */
public interface ServerFuture extends Future<Void> {

	/**
	 * Gets the server that created this task
	 * 
	 * @return The server that created this task
	 */
	Server getServer();
}
