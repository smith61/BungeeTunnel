package me.smith_61.tunnel;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This interface represents a tunnel listener. It is used
 * 	to inform plugins about events that happen by the implementation
 * 	of this api.
 */
public interface TunnelListener {

	/**
	 * Called when a tunnel is opened. 
	 * 
	 * This may be called from multiple threads at the
	 *  same time. You must ensure that this method and 
	 *  any it calls are multithreaded.
	 * 
	 * @param tunnel The tunnel that has been opened
	 */
	public void onTunnelOpened(ServerTunnel tunnel);
	
	/**
	 * Called when a tunnel is closed. The tunnel may still
	 * 	be open at this time and data could still be sent
	 * 	over it. This is not a guarantee and the status of
	 * 	the tunnel should be checked before any data is sent.
	 * 
	 * This may be called from multiple threads at the
	 *  same time. You must ensure that this method and 
	 *  any it calls are multithreaded.
	 *  
	 * @param tunnel The tunnel that has closed or is being closed
	 */
	public void onTunnelClosed(ServerTunnel tunnel);
}
