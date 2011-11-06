package pizzaProgram.gui;

import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.views.AdminView;
import pizzaProgram.modules.GUIModule;

public class AdminGUI extends GUIModule implements EventHandler{
	private ProgramWindow programWindow;
	private AdminView adminView;
	
	public AdminGUI(ProgramWindow programWindow, EventDispatcher eventDispatcher)
	{
		super(eventDispatcher);
		this.programWindow = programWindow;
		this.adminView = new AdminView();
		programWindow.addJPanel(this.adminView);
		
		eventDispatcher.addEventListener(this, EventType.OPEN_SETTINGS_WINDOW_REQUESTED);
		this.hide();
	}

	public void show() {
		this.programWindow.showPanel(this.adminView);
	}

	public void hide() {
		this.programWindow.hidePanel(this.adminView);
	}
	
	public void handleEvent(Event<?> event)
	{
		if(event.eventType.equals(EventType.OPEN_SETTINGS_WINDOW_REQUESTED))
		{
			this.show();
		}
	}
	
	public void initialize() {
		
	}
}
