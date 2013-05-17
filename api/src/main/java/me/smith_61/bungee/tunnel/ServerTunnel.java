package me.smith_61.bungee.tunnel;

import me.smith_61.bungee.tunnel.exceptions.TunnelClosedException;

public interface ServerTunnel {

	/**
	 * Sends data over a given channel to the destination server by
	 * 	any means. If the channel is not registered to listen on the
	 * 	destination server the data will not be sent.
	 * 
	 * The data will be sent asynchronously. If an exception occurs
	 * 	and there is an error handler provided it will be called. If
	 * 	there is no provided error handler the error will be suppressed.
	 * 
	 * @param channel The channel to send the data over
	 * @param data The data to send
	 * @param handler The error handler to pass errors to
	 * 
	 * @throws TunnelClosedException If the tunnel is closed
	 */
	public void sendData(String channel, byte[] data, ErrorHandler handler) throws TunnelClosedException;
	
	/**
	 * Sends data over a given channel to the destination server by
	 * 	any means. If the channel is not registered to listen on the
	 * 	destination server the data will not be sent.
	 * 
	 * The data will be sent asynchronously. If an exception occurs
	 * 	and there is an error handler provided it will be called. If
	 * 	there is no provided error handler the error will be suppressed.
	 * 
	 * @param channel The channel to send the data over
	 * @param data The data to send
	 * @param start The offset into the data array to start sending
	 * @param length The length of the data to send
	 * @param handler The error handler to pass errors to
	 * 
	 * @throws TunnelClosedException If the tunnel is closed
	 * @throws ArrayIndexOutOfBoundsException If start + length is greater than the data array length
	 */
	public void sendData(String channel, byte[] data, int start, int length, ErrorHandler handler) throws TunnelClosedException, ArrayIndexOutOfBoundsException;
	
	/**
	 * Gets whether this tunnel has been closed. Once a tunnel is closed
	 * 	the tunnel will not be reopened. A new tunnel may be created if
	 * 	the server reconnects.
	 * 
	 * @return If this tunnel is closed
	 */
	public boolean isClosed();
	
	/**
	 * Gets the name of the destination server.
	 * 
	 * There are two predefined server names
	 * 	- BUNGEECORD - This represents the connection to the BungeeCord server
	 *  - SELF - This represents the connection to this server
	 *  
	 *  In the case that this is being run on the BungeeCord server this will return
	 *  	SELF and will never return BUNGEECORD
	 *  
	 * @return The name of the destination server
	 */
	public String getName();
}
