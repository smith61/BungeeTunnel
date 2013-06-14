package me.smith_61.tunnel.concurrent;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * Callback used when a Future has been completed
 * 
 * @param <F> Future subclass type
 */
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
