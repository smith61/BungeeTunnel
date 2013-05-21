package me.smith_61.tunnel;

import java.net.InetAddress;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This interface represents info about a server
 */
public interface Server {

	/**
	 * Gets the server manager that handles
	 * 	this server
	 * 
	 * @return The server manager
	 */
	public ServerManager getManager();
	/**
	 * Gets the name of this server
	 * 
	 * @return The name of the server
	 */
	public String getName();
	
	/**
	 * Gets the address of this server
	 * 
	 * @return The address of this server
	 */
	public InetAddress getAddress();
	
	/**
	 * Gets the tunnel to this server
	 * 
	 * @return The tunnel to this server
	 */
	public ServerTunnel getTunnel();
}
