package me.smith_61.bungee.tunnel;

import me.smith_61.bungee.tunnel.exceptions.TunnelException;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This interface represents an error handler that handles
 * 	and error that has happened asynchronously
 */
public interface ErrorHandler {

	/**
	 * Called when an error happens asynchronously
	 * 
	 * @param t The exception that has occurred
	 */
	public void onError(TunnelException t);
}
