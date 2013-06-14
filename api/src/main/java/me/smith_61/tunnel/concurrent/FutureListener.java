package me.smith_61.tunnel.concurrent;

public interface FutureListener<F extends Future<?>> {

	/**
	 * Callback to notify when the future has been completed.
	 *  This is called when the future has finished from any
	 *  cause including errors
	 *  
	 * @param future The future that caused this callback
	 */
	void futureComplete(F future);
}
