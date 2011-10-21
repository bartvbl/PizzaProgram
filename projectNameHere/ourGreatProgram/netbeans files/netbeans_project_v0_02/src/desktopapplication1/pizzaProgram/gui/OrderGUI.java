package desktopApplication1.pizzaProgram.gui;

import desktopapplication1.OrderView;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.ProgramWindow;
import pizzaProgram.modules.GUIModule;

public class OrderGUI extends GUIModule implements EventHandler {

	private OrderView orderView;
    private ProgramWindow programWindow;
        
	public OrderGUI(ProgramWindow mainWindow, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		
                //this.jFrame = mainWindow.getWindowFrame();
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		this.orderView = new OrderView();
                mainWindow.addJPanel(orderView);
		this.orderView.addPropertyChangeListener(null);
                this.programWindow = mainWindow;
                hide();
	}
	
	@Override
	public void initialize() {
            
	}
	

	
	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			hide();
		}else if(event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)){
			hide();
		}else if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			show();
		}
	}
	@Override
	public void show() {
            this.programWindow.refreshFrame();
            this.orderView.setVisible(true);
            

	}
	@Override
	public void hide() {
           this.programWindow.refreshFrame();
            this.orderView.setVisible(false);
            
	}
}//END
