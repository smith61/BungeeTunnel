package me.smith_61.tunnel.exceptions;

import me.smith_61.tunnel.ServerTunnel;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This class represents the base class for all tunnel exceptions
 * 	thrown by this api.
 */
public abstract class TunnelException extends Exception {

	private static final long serialVersionUID = -1725122221550944868L;


	private ServerTunnel tunnel;
	
	protected TunnelException(String error, ServerTunnel tunnel) {
		super(error);
		
		this.tunnel = tunnel;
	}
	
	/**
	 * Gets the tunnel this exception occurred on
	 * 
	 * @return The tunnel this exception occurred on
	 */
	public ServerTunnel getTunnel() {
		return this.tunnel;
	}
}
