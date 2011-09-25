package pizzaProgram.gui;

import javax.swing.JPanel;

import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

@SuppressWarnings("serial")
public class CommonGUI extends JPanel implements EventHandler, GUIModule{
	
	private EventDispatcher eventDispatcher;
	
	private CookGUI cookGUI;
	private OrderGUI orderGUI;
	private DeliverGUI deliveryGUI;
	
	public CommonGUI(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		this.eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		this.eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		this.eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		
		
		
		
	}
	
	public void handleEvent(Event event){
		System.out.println("fikk en event");
		if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			System.out.println("fikk beskjed om å vide order");
		}
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

}
