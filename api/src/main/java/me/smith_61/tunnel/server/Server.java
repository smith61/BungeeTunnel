package me.smith_61.tunnel.server;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This interface represents info about a server
 */
public interface Server {


	/**
	 * Gets the registry this Server is registered with
	 * 
	 * @return The registry that this Server is registered with
	 */
	ServerManager getRegistry();
	
	/**
	 * Gets the name of this server
	 * 
	 * @return The name of the server
	 */
	String getName();
	
	/**
	 * Gets the connection to this server
	 * 
	 * @return The connection to this server
	 */
	ServerConnection getConnection();
}
