package me.smith_61.tunnel;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This interface represents a data listener. It is called
 * 	when data is received on a channel. The channels this
 * 	will recieve data on are the ones that this listener
 * 	has been registered to on the BungeeServer
 */
public interface TunnelDataListener {
	
	/**
	 * Called when data is received on a channel. This may be called from
	 * 	multiple threads at the same time. You must make sure that this
	 * 	method and any called methods are multithreaded.
	 * 
	 * @param data The data received
	 * @param server The server it was sent from
	 * @param channel The channel it was sent over
	 */
	public void onDataRecieved(byte[] data, Server server, String channel);
}
