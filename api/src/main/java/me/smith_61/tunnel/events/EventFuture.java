package me.smith_61.tunnel.events;

import me.smith_61.tunnel.concurrent.Future;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * Represents a future task involving an event
 */
public interface EventFuture<E extends Event> extends Future<Void> {

	/**
	 * Gets the event involved in this future
	 * 
	 * @return The event
	 */
	E getEvent();
	
	/**
	 * Gets the EventRegistry that created this future
	 * 
	 * @return The EventRegistry that created this future
	 */
	EventManager getRegistry();
}
