package pizzaProgram.gui.EventHandlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import pizzaProgram.events.Event;
import pizzaProgram.modules.Module;

/**
 * An class that manages the event dispatcher and provides a lookup table for Components and what events they produce
 * @author Bart
 *
 */
public abstract class ComponentEventHandler implements ActionListener {
	private Hashtable<Component, String> eventTypeTable;
	private Module eventDispatchingModule;
	/**
	 * Creates the component event handler class
	 * @param eventDispatchingModule A reference to a module that can send events to the event dispatcher
	 */
	public ComponentEventHandler(Module eventDispatchingModule){
		this.eventDispatchingModule = eventDispatchingModule;
		this.eventTypeTable = new Hashtable<Component, String>();
	}

	/**
	 * Links a component to a string representing the component, so that it can be used to determine what component produced an event invoked
	 * @param eventSourceComponent The component that sent the ActionEvent
	 * @param eventName The String that should represent the event
	 */
	protected void registerEventType(Component eventSourceComponent, String eventName){
		this.eventTypeTable.put(eventSourceComponent, eventName);
	}

	/**
	 * Returns the String that has been associated with the the component
	 * @param component The component that has sent out an event
	 * @return The string that represents its event
	 */
	protected String getEventNameByComponent(Component component){
		return this.eventTypeTable.get(component);
	}

	/**
	 * Dispatches an event to the module event dispatcher
	 * @param event The event to be sent out
	 */
	protected void dispatchEvent(Event<?> event){
		this.eventDispatchingModule.dispatchEvent(event);
	}

	/**
	 * The event handler for swing components
	 */
	public abstract void actionPerformed(ActionEvent e);
}
