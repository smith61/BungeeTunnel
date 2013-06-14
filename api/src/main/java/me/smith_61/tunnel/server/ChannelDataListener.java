package me.smith_61.tunnel.server;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * Listener interface to listen for data sent
 *  over a channel
 */
public interface ChannelDataListener {

	/**
	 * Called when data is received on a channel this is
	 * 	listening to. 
	 * 
	 * This method may be called from multiple
	 * 	threads at the same time if data is received from multiple
	 *  servers. This will not be called at the same time if another
	 *  message from the same server is being handled.
	 * 
	 * @param server The server the data was sent from
	 * @param channel The channel the data was received on
	 * @param data The data received
	 */
	void dataReceived(Server server, String channel, byte[] data);
}
