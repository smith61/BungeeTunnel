package me.smith_61.tunnel.server;

import me.smith_61.tunnel.events.EventManager;
import me.smith_61.tunnel.exceptions.ChannelRegisteredException;


/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This is the base point for looking up servers
 * 	that have been registered
 */
public interface ServerManager {

	/**
	 * Looks up the server with the given name. Is not case
	 * 	sensitive.
	 * 
	 * @param name The name of the server
	 * 
	 * @return The Server with the given name or null if one does not exist
	 */
	Server lookupServer(String name);
	
	/**
	 * Gets all servers that have been registered.
	 * 
	 * @return All servers that have been registered
	 */
	Server[] getServers();
	
	/**
	 * Registers a ChannelDataListener to listen on the given channel.
	 * 
	 * Channels are not case sensitive.
	 * 
	 * ChannelDataListeners are not unregistered when a plugin is
	 * 	disabled and must be manually unregistered
	 * 
	 * @param channel The channel to listen on
	 * @param listener The listener to call when data is received on the channel
	 * 
	 * @throws ChannelRegisteredException If the channel is already registered to another listener
	 */
	void registerIncomingChannelListener(String channel, ChannelDataListener listener) throws ChannelRegisteredException;
	
	/**
	 * Unregisters a ChannelDataListener from the given channel.
	 * 
	 * Channels are no case sensitive.
	 * 
	 * @param channel The channel to unregister
	 */
	void unregisterIncomingChannelListener(String channel);
	
	/**
	 * Gets the EventManager for this instance. This EventManager will
	 *  be used to fire any events that are sent from any server registered
	 *  with this manager
	 * 
	 * @return The EventManager for this instance
	 */
	EventManager getEventManager();
}
