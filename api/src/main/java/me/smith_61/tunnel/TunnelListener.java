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
	 * @param server The server that has been opened
	 */
	public void onServerOpened(Server server);
	
	/**
	 * Called when a tunnel is closed. This is send after
	 * 	the connection to the server has been closed. No
	 * 	further data can be sent to the server
	 * 
	 * This may be called from multiple threads at the
	 *  same time. You must ensure that this method and 
	 *  any it calls are multithreaded.
	 *  
	 * @param server The server that has closed or is being closed
	 */
	public void onServerClosed(Server server);
}
