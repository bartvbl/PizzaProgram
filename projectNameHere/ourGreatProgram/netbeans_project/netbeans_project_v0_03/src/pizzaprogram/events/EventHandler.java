package pizzaProgram.events;

/**
 * Any class that would like to handle events needs to implement this interface in order to be able to register for events.
 * The interface requires the creation of a function called "handleEvent", which is called by the EventDispatcher when an event occurs.
 * @author Bart
 *
 */
public interface EventHandler {
	/**
	 * This function is called by the event dispatcher when the event the implementing class registered for occurs
	 * @param event The event to be handled by the object
	 */
	public void handleEvent(Event<?> event);
}
