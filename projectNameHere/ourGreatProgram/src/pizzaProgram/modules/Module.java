package pizzaProgram.modules;

import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;

public abstract class Module implements EventHandler{
	private EventDispatcher eventDispatcher;
 
	public Module(EventDispatcher eventDispatcher){
		this.eventDispatcher = eventDispatcher;
	}
	
	public abstract void handleEvent(Event<?> event);
	
	public void dispatchEvent(Event<?> event){
		this.eventDispatcher.dispatchEvent(event);
	}
	
}//END
