package pizzaProgram.events.moduleEventHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.database.DatabaseModule;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;

public class Database_ReadEventHandler implements EventHandler {
	private DatabaseConnection databaseConnection;
	private EventDispatcher eventDispatcher;
	
	
	public Database_ReadEventHandler(DatabaseConnection databaseConnection, EventDispatcher eventDispatcher) 
	{
		this.databaseConnection = databaseConnection;
		this.eventDispatcher = eventDispatcher;
		this.addEventListeners();
	}
	
	private void addEventListeners() {
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS);
		
	}

	public void handleEvent(Event<?> event)
	{
		
	}
	
	private void createNewOrder(Event<?> event)
	{


	}
	
	private String selectDeliveryMethod(String deliveryMethod)
	{
		return null;
	}
}
