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
import pizzaProgram.database.databaseUtils.DataCleaner;
import pizzaProgram.database.databaseUtils.DatabaseReader;
import pizzaProgram.database.databaseUtils.DatabaseResultsFeedbackProvider;
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
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_COOK_GUI_SEND_ALL_ORDERS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_COOK_GUI_SEARCH_ORDERS_BY_KEYWORDS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS);
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
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_COOK_GUI_SEND_ALL_ORDERS))
		{
			this.sendListOfAllUncookedOrders(event);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_COOK_GUI_SEARCH_ORDERS_BY_KEYWORDS))
		{
			this.searchOrders(event);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS))
		{
			this.sendListOfActiveOrdersToDeliveryGUI(event);
		}  
	}


	private void sendListOfActiveOrdersToDeliveryGUI(Event<?> event) {
		ArrayList<Order> orderList = DatabaseReader.getAllUndeliveredOrders();
		this.eventDispatcher.dispatchEvent(new Event<ArrayList<Order>>(EventType.DELIVERY_GUI_UPDATE_ORDER_LIST, orderList));
	}

	private void searchOrders(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof String))
		{
			DatabaseResultsFeedbackProvider.showSearchCustomersFailedMessage();
			return;
		}
		String searchQuery = (String)event.getEventParameterObject();
		searchQuery = DataCleaner.cleanDbData(searchQuery);
		ArrayList<Order> orderList = DatabaseReader.getOrdersByKeywords(searchQuery);
		if(orderList != null)
		{
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Order>>(EventType.COOK_GUI_UPDATE_ORDER_LIST, orderList));
		}
	}

	private void sendListOfAllUncookedOrders(Event<?> event) {
		ArrayList<Order> orderList = DatabaseReader.getAllUncookedOrders();
		this.eventDispatcher.dispatchEvent(new Event<ArrayList<Order>>(EventType.COOK_GUI_UPDATE_ORDER_LIST, orderList));
	}

	private void searchCustomers(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof String))
		{
			DatabaseResultsFeedbackProvider.showSearchCustomersFailedMessage();
			return;
		}
		String searchQuery = (String)event.getEventParameterObject();
		searchQuery = DataCleaner.cleanDbData(searchQuery);
		ArrayList<Customer> customerList = DatabaseReader.searchCustomerByString(searchQuery);
		if(customerList != null)
		{
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Customer>>(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST, customerList));
		}
	}

	private void sendListOfAllExtrasToOrderGUI() {
		ArrayList<Extra> extraList = DatabaseReader.getAllExtras();
		if(extraList != null)
		{
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Extra>>(EventType.ORDER_GUI_UPDATE_EXTRAS_LIST, extraList));
		}
	}

	private void sendListOfAllDishesToOrderGUI() 
	{
		ArrayList<Dish> dishList = DatabaseReader.getAllDishes();
		if(dishList != null)
		{
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Dish>>(EventType.ORDER_GUI_UPDATE_DISH_LIST, dishList));
		}
	}

	private void sendListOfAllCustomersToOrderGUI() 
	{
		ArrayList<Customer> customerList = DatabaseReader.getAllCustomers();
		if(customerList != null)
		{
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Customer>>(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST, customerList));
		}
	}
	
}
