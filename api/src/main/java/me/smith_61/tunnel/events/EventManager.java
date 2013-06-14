package me.smith_61.tunnel.events;

/**
 * 
 * @author Jacob
 * @since 1.0.0
 * 
 * This is the base point for registering
 *  listeners for events and firing events
 */
public interface EventManager {

	/**
	 * Registers all event listeners found for
	 *  the given object. Event listener methods
	 *  must be tagged with an EventListener annotation
	 *  for them to be be registered.
	 *  
	 *  Be warned this will register for any sub-events
	 *   of the registered event. So while it is possible
	 *   to register to the Event event you will receive
	 *   every event that is fired through this instance
	 *  
	 * Listeners are not auto-removed when a plugin is
	 * 	disabled and must be removed manually
	 *  
	 * @param listener The listener to register
	 */
	void registerListeners(Object listener);
	
	/**
	 * Unregisters all event listeners that were
	 *  registered for the given object
	 *  
	 * @param listener The listener to unregister
	 */
	void unregisterListener(Object listener);
	
	/**
	 * Fires an event to any registered listeners. The event
	 *  may be executed on a separate thread and may return before
	 *  the event is actually fired
	 * 
	 * @param event The event to fire
	 */
	<E extends Event> EventFuture<E> fireEvent(E event);
}
