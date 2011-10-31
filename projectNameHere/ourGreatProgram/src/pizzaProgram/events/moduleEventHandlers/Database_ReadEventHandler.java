package pizzaProgram.events.moduleEventHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.database.DatabaseModule;
import pizzaProgram.database.databaseUtils.DatabaseReader;
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
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_SEARCH_CUSTOMERS_BY_KEYWORDS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_DISH_LIST);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_EXTRAS_LIST_BY_DISH_ID);
	}

	
	private void createNewOrder(Event<?> event)
	{
		
	}

	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS))
		{
			this.sendListOfAllCustomersToOrderGUI();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_DISH_LIST))
		{
			this.sendListOfAllDishesToOrderGUI();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_EXTRAS_LIST_BY_DISH_ID))
		{
			this.sendListOfAllExtrasToOrderGUI();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_SEARCH_CUSTOMERS_BY_KEYWORDS))
		{
			this.searchCustomers(event);
		}  
	}


	private void searchCustomers(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof String))
		{
			System.err.println("ERROR: search query for finding customers is not a string!");
			return;
		}
		String searchQuery = (String)event.getEventParameterObject();
		ArrayList<Customer> customerList = DatabaseReader.searchCustomerByString(searchQuery);
		this.eventDispatcher.dispatchEvent(new Event<ArrayList<Customer>>(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST, customerList));
	}

	private void sendListOfAllExtrasToOrderGUI() {
		ArrayList<Extra> extraList = DatabaseReader.getAllExtras();
		this.eventDispatcher.dispatchEvent(new Event<ArrayList<Extra>>(EventType.ORDER_GUI_UPDATE_EXTRAS_LIST, extraList));
	}

	private void sendListOfAllDishesToOrderGUI() 
	{
		ArrayList<Dish> dishList = DatabaseReader.getAllDishes();
		this.eventDispatcher.dispatchEvent(new Event<ArrayList<Dish>>(EventType.ORDER_GUI_UPDATE_DISH_LIST, dishList));
	}

	private void sendListOfAllCustomersToOrderGUI() 
	{
		ArrayList<Customer> customerList = DatabaseReader.getAllCustomers();
		this.eventDispatcher.dispatchEvent(new Event<ArrayList<Customer>>(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST, customerList));
	}
	
}
