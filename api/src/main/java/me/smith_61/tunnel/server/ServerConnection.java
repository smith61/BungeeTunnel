package me.smith_61.tunnel.server;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This represents a connection to a server that
 *  data can be sent over. The actual path the data
 *  takes to get to the server is not specified
 */
public interface ServerConnection {

	/**
	 * Gets if this connection is closed. If the connection
	 *  is closed no further data may be sent over it and
	 *  any attempts to send data over this connection will
	 *  error
	 * 
	 * @return If the connection is closed
	 */
	boolean isClosed();
	
	/**
	 * Gets the destination server of this connection
	 * 
	 * @return The destination server of this connection
	 */
	Server getServer();
	
	/**
	 * Sends data to the server over the given channel using
	 *  any means neccasary.
	 * 
	 * @param channel The channel to send the data over
	 * @param data The data to send over the channel
	 * 
	 * @return The future representing this task
	 */
	ServerFuture sendData(String channel, byte[] data);
}
