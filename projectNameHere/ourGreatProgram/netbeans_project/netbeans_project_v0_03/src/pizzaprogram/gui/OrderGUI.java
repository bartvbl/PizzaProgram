package pizzaProgram.gui;

import pizzaprogram.gui.views.OrderView;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.ProgramWindow;
import pizzaProgram.modules.GUIModule;
import pizzaprogram.events.moduleEventHandlers.OrderGUI_OrderViewEventHandler;
import pizzaprogram.events.moduleEventHandlers.OrderGUI_SystemEventHandler;

public class OrderGUI extends GUIModule implements EventHandler {

	private OrderView orderView;
    private ProgramWindow programWindow;
    private OrderGUI_OrderViewEventHandler orderViewEventHandler;
    private OrderGUI_SystemEventHandler systemEventHandler;
        
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
                this.orderViewEventHandler = new OrderGUI_OrderViewEventHandler(this.orderView, this);
                this.systemEventHandler = new OrderGUI_SystemEventHandler(this.orderView, eventDispatcher);
                hide();
	}
	
	@Override
	public void initialize() {
            
	}
	

	
	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			show();
		}
	}
	@Override
	public void show() {
            this.programWindow.showPanel(this.orderView);
            
	}
	@Override
	public void hide() {
           this.programWindow.hidePanel(this.orderView);
            
	}
}//END
