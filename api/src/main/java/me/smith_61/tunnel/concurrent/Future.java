package me.smith_61.tunnel.concurrent;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * Represents an uncompleted action that may be completed
 *  sometime in the future
 *  
 * @param <V> The result of the action
 */
public interface Future<V> extends java.util.concurrent.Future<V> {

	
	/**
	 * Gets the state of this task
	 * 
	 * @return The state of this task
	 */
	FutureState getState();
	
	/**
	 * Gets the error of this task. This will return
	 *  a non-null value only if the state of this task
	 *  is of type FutureState.ERROR
	 *  
	 * @return The error that this task ended with
	 */
	Throwable getError();
	
	/**
	 * Waits for this task to be completed
	 * 
	 * @return This Future
	 * 
	 * @throws InterruptedException If the current thread was interrupted
	 */
	Future<V> await() throws InterruptedException;
	
	/**
	 * Registers a listener to listen for the completion of this task.
	 * 	If the task is already completed the listener is notified immediately
	 *  
	 * @param listener
	 * 
	 * @return This future
	 */
	Future<V> addListener(FutureListener<? extends Future<? extends V>> listener);
	
	/**
	 * Unregisters a listener from this future.
	 * 
	 * @param listener The listener to unregister
	 * 
	 * @return This future
	 */
	Future<V> removeListener(FutureListener<? extends Future<? extends V>> listener);
}
