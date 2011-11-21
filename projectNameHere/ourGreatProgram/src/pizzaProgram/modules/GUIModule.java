package pizzaProgram.modules;

import pizzaProgram.events.EventDispatcher;
/**
 * The base class for a GUI module. As GUI views can be hidden or shown it adds those methods to the Module class.
 * @author Bart
 *
 */
public abstract class GUIModule extends Module {
	/**
	 * The constructor creates a Module instance
	 * @param eventDispatcher The system's main event dispatcher
	 */
	public GUIModule(EventDispatcher eventDispatcher){
		super(eventDispatcher);
	}
	
	/**
	 * Should be implemented by the child class. This function shows the component
	 */
	public abstract void show();
	/**
	 * Should be implemented by the child class. This function hides the component.
	 */
	public abstract void hide();
}
