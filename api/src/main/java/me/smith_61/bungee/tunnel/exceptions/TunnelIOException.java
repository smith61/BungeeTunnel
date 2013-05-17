package me.smith_61.bungee.tunnel.exceptions;

import java.io.IOException;

import me.smith_61.bungee.tunnel.ServerTunnel;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This exception is thrown when there is an IOException
 * 	has occurred while writing data to a tunnel.
 * 
 */
public class TunnelIOException extends TunnelException {

	private static final long serialVersionUID = -7274389261834712689L;
	
	private String channel;
	private IOException cause;
	
	public TunnelIOException(ServerTunnel tunnel, String channel, IOException cause) {
		super("Exception while writing data to the tunnel: " + tunnel.getName(), tunnel);
		
		this.channel = channel;
		this.cause = cause;
	}
	
	/**
	 * Gets the channel this exception occurred on
	 * 
	 * @return The tunnel the exception occurred on
	 */
	public String getChannel() {
		return this.channel;
	}
	
	/**
	 * Gets the IOException that caused this exception
	 * 
	 * @return The IOException that cause this exception
	 */
	public IOException getException() {
		return this.cause;
	}
}
