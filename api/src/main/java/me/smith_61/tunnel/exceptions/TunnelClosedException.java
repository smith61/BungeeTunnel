package me.smith_61.tunnel.exceptions;

import me.smith_61.tunnel.ServerTunnel;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This exception is thrown when data is attempted to be sent over a closed
 * 	connection
 */
public class TunnelClosedException extends TunnelException {

	private static final long serialVersionUID = 8282978571578525302L;
	
	public TunnelClosedException(ServerTunnel tunnel) {
		super("Tunnel has been closed: " + tunnel.getName(), tunnel);
	}
}
