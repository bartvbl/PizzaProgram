package desktopapplication1.pizzaProgram.gui;

import desktopapplication1.CookView;
import javax.swing.JPanel;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.ProgramWindow;
import pizzaProgram.modules.GUIModule;

public class CookGUI extends GUIModule implements EventHandler{
	
        private JPanel cookView;
        private ProgramWindow programWindow;
	
	public CookGUI(ProgramWindow mainWindow, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
                this.cookView = new CookView();
                mainWindow.addJPanel(this.cookView);
                this.programWindow = mainWindow;
                this.hide();
	}
	
	
	@Override
	public void handleEvent(Event<?> event){
            if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			show();
		}else if(event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)){
			hide();
		}else if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			hide();
		}
	}

	@Override
	public void show() {
           this.programWindow.refreshFrame();
            this.cookView.setVisible(true);
            

	}
	@Override
	public void hide() {
           this.programWindow.refreshFrame();
            this.cookView.setVisible(false);
            
	}

    @Override
    public void initialize() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
