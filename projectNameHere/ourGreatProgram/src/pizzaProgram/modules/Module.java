package pizzaProgram.modules;

import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;

/**
 * The Module is a basic part of the system. It communicates to other modules
 * through the event system.
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public abstract class Module implements EventHandler {
	/**
	 * A reference to the system's main event dispatcher
	 */
	private EventDispatcher eventDispatcher;

	/**
	 * Creates a new module
	 * 
	 * @param eventDispatcher
	 *            A reference to the system's main event dispatcher
	 */
	public Module(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
	}

	/**
	 * Should be implemented by a child class. Handles an incoming event from
	 * the event dispatcher, for which this module has registered.
	 */
	public abstract void handleEvent(Event<?> event);

	/**
	 * Dispatches a new event to the main event dispatcher
	 * 
	 * @param event
	 *            The Event instance to dispatch
	 */
	public void dispatchEvent(Event<?> event) {
		this.eventDispatcher.dispatchEvent(event);
	}
}