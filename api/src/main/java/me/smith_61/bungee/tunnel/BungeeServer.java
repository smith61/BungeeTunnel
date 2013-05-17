package me.smith_61.bungee.tunnel;

import me.smith_61.bungee.tunnel.exceptions.ChannelRegisteredException;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This interface represents a BungeeCord Server hub. It
 * 	manages all server tunnels to all servers
 * 	including a connection to the BungeeCord server
 * 	and the server this is running on
 */
public interface BungeeServer {

	/**
	 * Gets all open ServerTunnels
	 * 
	 * @return All open ServerTunnels
	 */
	public ServerTunnel[] getTunnels();
	
	/**
	 * Gets the open ServerTunnel with the given name
	 * 	or null if no tunnel exists.
	 * 
	 * There are two predefined ServerTunnel names
	 * 	- BUNGEECORD - This represents the connection to the BungeeCord Server
	 * 	- SELF - This represents the connection to this server
	 * 
	 * In the case that this is running on the BungeeCord Server
	 * 	they will both return the same connection
	 * 
	 * @param name
	 * @return
	 */
	public ServerTunnel getTunnel(String name);
	
	/**
	 * Registers a data listener for the given channel. These are not unregistered
	 * 	when the plugin is disabled. You must unregister the data listener yourself 
	 * 	when your plugin is disabled or use a more specific method that will handle
	 * 	it for you.
	 * 
	 * @param channel The channel to listen on
	 * 
	 * @param listener The data listener
	 * 
	 * @throws ChannelRegisteredException If the channel is already registered
	 */
	public void registerTunnelDataListener(String channel, TunnelDataListener listener) throws ChannelRegisteredException;
	
	/**
	 * Unregisters the data listener on the given channel
	 * 
	 * @param channel The channel to unregister
	 */
	public void unregisterTunnelDataListener(String channel);
	
	/**
	 * Registers a tunnel event listener. These are not unregistered when the plugin
	 * 	is disabled. You must unregister the event listener yourself when your plugin
	 * 	is disabled or use a more specific method that will handle it for you.
	 * 
	 * @param listener The listener to register
	 */
	public void registerTunnelListener(TunnelListener listener);
	
	/**
	 * Unregisters a tunnel event listener.
	 * 
	 * @param listener The listener to unregister
	 */
	public void unregisterTunnelListener(TunnelListener listener);
}
