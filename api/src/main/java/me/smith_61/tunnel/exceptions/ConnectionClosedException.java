package me.smith_61.tunnel.exceptions;

import me.smith_61.tunnel.server.ServerConnection;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This is exception is thrown when data is attempted
 * 	to be written to a closed ServerConnection
 */
public class ConnectionClosedException extends Exception {

	private static final long serialVersionUID = -1405579722740820485L;

	private final ServerConnection connection;
	
	public ConnectionClosedException(ServerConnection connection) {
		this.connection = connection;
	}
	
	public final ServerConnection getConnection() {
		return this.connection;
	}
}
