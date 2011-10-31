package pizzaProgram.events.moduleEventHandlers;

import pizzaProgram.dataObjects.UnaddedCustomer;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.database.DatabaseModule;
import pizzaProgram.database.databaseUtils.DatabaseWriter;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;

public class Database_WriteEventHandler implements EventHandler {
	private DatabaseModule databaseModule;
	private DatabaseConnection databaseConnection;
	private EventDispatcher eventDispatcher;
	
	public Database_WriteEventHandler(DatabaseModule databaseModule, DatabaseConnection databaseConnection, EventDispatcher eventDispatcher) 
	{
		this.databaseModule = databaseModule;
		this.databaseConnection = databaseConnection;
		this.eventDispatcher = eventDispatcher;
		this.addListeners();
	}

	private void addListeners() {
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_ADD_NEW_ORDER);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_ADD_NEW_CUSTOMER);
	}

	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.DATABASE_ADD_NEW_ORDER))
		{
			this.addNewOrder(event);
		} else if(event.eventType.equals(EventType.DATABASE_ADD_NEW_CUSTOMER))
		{
			this.addNewCustomer(event);
		} 
	}

	private void addNewCustomer(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof UnaddedCustomer))
		{
			System.out.println("The database received an event that did not contain an UnaddedCustomer instance when trying to add a new customer!");
			return;
		}
		DatabaseWriter.writeNewCustomer((UnaddedCustomer)event.getEventParameterObject());
		this.eventDispatcher.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS));
	}

	private void addNewOrder(Event<?> event) 
	{
		if(!(event.getEventParameterObject() instanceof UnaddedOrder))
		{
			System.out.println("To add an order, please attach an instance of UnaddedOrder. ");
			return;
		}
		DatabaseWriter.writeNewOrder((UnaddedOrder)event.getEventParameterObject());
	}
}
