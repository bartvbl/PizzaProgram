package pizzaProgram.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import pizzaProgram.database.DatabaseConnection;
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
	
	public CommonGUI(EventDispatcher eventDispatcher, DatabaseConnection dbc) {
		this.eventDispatcher = eventDispatcher;
		this.eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		this.eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		this.eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		
		
		/*JUKS
		orderGUI = new OrderGUI(dbc);
		add(orderGUI, BorderLayout.CENTER);
		*/
		this.setVisible(true);
		
		System.out.println("listeners added");
	}
	
	public void handleEvent(Event<Object> event){
		System.out.println("fikk en event");
		if(event.eventType.equalsIgnoreCase(EventType.ORDER_GUI_REQUESTED)){
			System.out.println("fikk beskjed om å vise order");
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
