package me.smith_61.tunnel.exceptions;

import me.smith_61.tunnel.ServerTunnel;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This exception is thrown when there is an exception
 * 	has occurred while writing data to a tunnel.
 * 
 */
public class TunnelWriteException extends TunnelException {

	private static final long serialVersionUID = -7274389261834712689L;
	
	private String channel;
	private Throwable cause;
	
	public TunnelWriteException(ServerTunnel tunnel, String channel, Throwable cause) {
		super("Exception while writing data to the tunnel: " + tunnel.getServer().getName(), tunnel);
		
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
	 * Gets the exception that caused this exception
	 * 
	 * @return The exception that cause this exception
	 */
	public Throwable getException() {
		return this.cause;
	}
}
