package pizzaProgram.gui;

import java.awt.GridBagConstraints;
import java.awt.List;

import javax.swing.JFrame;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

public class DeliverGUI extends GUIModule implements EventHandler{

	
	private DatabaseConnection database;
	private JFrame jFrame;
	
	
	public DeliverGUI(DatabaseConnection dbc, JFrame jFrame, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		this.database = dbc;
		this.jFrame = jFrame;
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		initialize();
		hide();
	}
	/**
	 * Her skal koden for å lage og legge til komponenter ligger
	 */
	@Override
	public void initialize() {
		
		
	}
	/**
	 * Her skal koden for å vise komponentene ligge
	 */
	@Override
	public void show() {
		//komponent.setVisible(true)
	}
	/**
	 * Her skal koden for å skjule komponentene ligge
	 */
	@Override
	public void hide() {
		//komponent.setVisible(false)
	}
	
	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			hide();
		}else if(event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)){
			show();
		}else if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			hide();
		}
	}
	
}
