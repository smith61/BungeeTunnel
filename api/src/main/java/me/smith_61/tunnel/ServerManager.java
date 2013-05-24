package me.smith_61.tunnel;

import me.smith_61.tunnel.exceptions.ChannelRegisteredException;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This interface represents a server hub. It manages
 * 	all servers and their connections.
 */
public interface ServerManager {
	
	/**
	 * String constant that represents the name given to
	 * 	the bungee server.
	 */
	public static final String BUNGEE = "bungeecord";
	
	/**
	 * Gets all available ServerInfos
	 * 
	 * @return All available ServerInfos
	 */
	public Server[] getServers();
	
	/**
	 * Gets the open ServerTunnel with the given name
	 * 	or null if no tunnel exists.
	 * 
	 * There are two predefined ServerTunnel names
	 * 	- bungeecord - This represents the BungeeCord Server.
	 * 						This may return null if a connection
	 * 						has no been established to the bungee
	 * 						server.
	 * 	- self - This represents this server. This will never
	 * 				return null when used.
	 * 
	 * In the case that this is running on the BungeeCord Server
	 * 	they will both return the same ServerInfo instance
	 * 
	 * @param name
	 * @return
	 */
	public Server getServer(String name);
	
	/**
	 * Gets the Server that represents this server
	 * 
	 * @return This server
	 */
	public Server getThisServer();
	
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
