package me.smith_61.tunnel.concurrent;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This represents the state of a Future task
 */
public enum FutureState {

	/**
	 * A future that has not been finished
	 */
	UNCOMPLETED,
	/**
	 * A future that has been completed successfully
	 */
	COMPLETED,
	/**
	 * A future that has completed with errors
	 */
	ERROR;
}
