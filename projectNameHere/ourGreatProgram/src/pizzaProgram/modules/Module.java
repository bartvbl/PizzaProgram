package pizzaProgram.modules;

import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;

public class Module implements EventHandler{
	private EventDispatcher eventDispatcher;
 
	public Module()
	{
	 
	}
	
	//methods to be overridden by child classes
	public void initialize() {}
	public void destroy() {}
	public void handleEvent(Event event){}
	
	protected void dispatchEvent(Event event)
	{
		this.eventDispatcher.dispatchEvent(event);
	}
	
}
