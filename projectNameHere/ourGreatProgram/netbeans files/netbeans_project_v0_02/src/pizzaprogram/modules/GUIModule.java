package pizzaProgram.modules;

import pizzaProgram.events.EventDispatcher;

public abstract class GUIModule extends Module {
	
	public GUIModule(EventDispatcher eventDispatcher){
		super(eventDispatcher);
	}
	
	public abstract void show();
	public abstract void hide();
	public abstract void initialize();
}
