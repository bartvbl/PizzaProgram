package pizzaProgram.database.eventHandlers;

import java.util.ArrayList;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.database.databaseUtils.DataCleaner;
import pizzaProgram.database.databaseUtils.DatabaseReader;
import pizzaProgram.database.databaseUtils.DatabaseSearcher;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
/**
 * The Database_ReadEventHandler handles all database events that require reading from the database
 * @author Bart
 *
 */
public class Database_ReadEventHandler implements EventHandler {
	/**
	 * A reference to the main event dispatcher
	 */
	private EventDispatcher eventDispatcher;
	
	/**
	 * The constructor of the readEventHandler. Stores the veent dispatcher and adds all event listeners it can handle
	 * @param eventDispatcher
	 */
	public Database_ReadEventHandler(EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		this.addEventListeners();
	}
	/**
	 * Adds all event listeners that this class handles
	 */
	private void addEventListeners() {
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_SEARCH_CUSTOMERS_BY_KEYWORDS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_DISH_LIST);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_EXTRAS_LIST);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_COOK_GUI_SEND_ALL_ORDERS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_COOK_GUI_SEARCH_ORDERS_BY_KEYWORDS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_DELIVERY_GUI_SEARCH_ORDERS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_DISHES);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_EXTRAS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_ORDERS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_SEARCH_DISHES);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ORDER_GUI_SEARCH_EXTRAS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_DISHES);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_EXTRAS);
	}

	
	/**
	 * This function is called when any of the events that this class registered for is dispatched, and proceeds to handles them.
	 * It determines what the called event is, then calls the appropiate internal event handling function
	 */
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS)){
			this.sendListOfAllCustomersToOrderGUI();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_DISH_LIST)){
			this.sendListOfAllDishesToOrderGUI();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_EXTRAS_LIST)){
			this.sendListOfAllExtrasToOrderGUI();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_SEARCH_CUSTOMERS_BY_KEYWORDS)){
			this.searchCustomers(event);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_COOK_GUI_SEND_ALL_ORDERS)){
			this.sendListOfAllUncookedOrders();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_COOK_GUI_SEARCH_ORDERS_BY_KEYWORDS)){
			this.searchUncookedOrders(event);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS)){
			this.sendListOfActiveOrdersToDeliveryGUI();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEARCH_ORDERS)){
			this.searchUndeliveredOrders(event);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_DISHES)){
			this.sendListOfAllDishesToAdminGUI();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_EXTRAS)){
			this.sendListOfAllExtrasToAdminGUI();
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_SEARCH_DISHES)){
			this.searchDishesByKeywords(event, EventType.ORDER_GUI_UPDATE_DISH_LIST);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ORDER_GUI_SEARCH_EXTRAS)){
			this.searchExtrasByKeywords(event, EventType.ORDER_GUI_UPDATE_EXTRAS_LIST);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_DISHES)){
			this.searchDishesByKeywords(event, EventType.ADMIN_GUI_UPDATE_DISH_LIST);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_EXTRAS)){
			this.searchExtrasByKeywords(event, EventType.ADMIN_GUI_UPDATE_EXTRA_LIST);
		}else if(event.eventType.equals(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_ORDERS)){
			this.sendListOfAllDeliverdOrdersToAdminGUI();
		}
	}

	
	private void sendListOfAllDeliverdOrdersToAdminGUI() {
		ArrayList<Order> orderList = DatabaseReader.getAllDeliveredOrders();
		if(orderList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Order>>(EventType.ADMIN_GUI_UPDATE_ORDER_LIST, orderList));
		}
	}
	
	
	/**
	 * Handles the event when the GUI requests to search for dishes matching a keyword String
	 * @param event The event containing a keyword String
	 * @param sendBackEventOfEventType The event type to dispatch with the resulting list of Dishes
	 */
	private void searchDishesByKeywords(Event<?> event, String sendBackEventOfEventType) {
		if(!(event.getEventParameterObject() instanceof String)){
			GUIConstants.showErrorMessage("Kunne ikke hente retter fra databasen!");
			return;
		}
		String searchQuery = (String)event.getEventParameterObject();
		searchQuery = DataCleaner.cleanDbData(searchQuery);
		ArrayList<Dish> dishList = DatabaseSearcher.searchDishByString(searchQuery);
		if(dishList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Dish>>(sendBackEventOfEventType, dishList));
		}
	}

	/**
	 * searches for extras matching an arbitrary keyword String
	 * @param event The event containing the keyword string
	 * @param sendBackEventOfEventType The Event type the event with the results attached to it should be dispatched as
	 */
	private void searchExtrasByKeywords(Event<?> event, String sendBackEventOfEventType) {
		if(!(event.getEventParameterObject() instanceof String)){
			GUIConstants.showErrorMessage("Kunne ikke hente tilbehør fra databasen!");
			return;
		}
		String searchQuery = (String)event.getEventParameterObject();
		searchQuery = DataCleaner.cleanDbData(searchQuery);
		ArrayList<Extra> extrasList = DatabaseSearcher.searchExtraByString(searchQuery);
		if(extrasList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Extra>>(sendBackEventOfEventType, extrasList));
		}
	}

	/**
	 * Searches for undelivered orders that match a String of keywords
	 * @param event The event that contains a keyword String
	 */
	private void searchUndeliveredOrders(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof String)){
			GUIConstants.showErrorMessage("Kunne ikke hente ordre fra databasen!");
			return;
		}
		String searchQuery = (String)event.getEventParameterObject();
		searchQuery = DataCleaner.cleanDbData(searchQuery);
		ArrayList<Order> orderList = DatabaseSearcher.getOrdersByKeywords(searchQuery, new String[]{Order.HAS_BEEN_COOKED, Order.BEING_DELIVERED});
		if(orderList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Order>>(EventType.DELIVERY_GUI_UPDATE_ORDER_LIST, orderList));
		}
	}

	/**
	 * Handles a request from the delivery GUI to send a list of all active orders
	 */
	private void sendListOfActiveOrdersToDeliveryGUI() {
		ArrayList<Order> orderList = DatabaseReader.getAllUndeliveredOrders();
		this.eventDispatcher.dispatchEvent(new Event<ArrayList<Order>>(EventType.DELIVERY_GUI_UPDATE_ORDER_LIST, orderList));
	}

	private void searchUncookedOrders(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof String)){
			GUIConstants.showErrorMessage("Kunne ikke hente ordre fra databasen!");
			return;
		}
		String searchQuery = (String)event.getEventParameterObject();
		searchQuery = DataCleaner.cleanDbData(searchQuery);
		ArrayList<Order> orderList = DatabaseSearcher.getOrdersByKeywords(searchQuery, new String[]{Order.BEING_COOKED, Order.REGISTERED});
		if(orderList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Order>>(EventType.COOK_GUI_UPDATE_ORDER_LIST, orderList));
		}
	}

	/**
	 * Sends a list of all uncooked orders to the cook GUI
	 */
	private void sendListOfAllUncookedOrders() {
		ArrayList<Order> orderList = DatabaseReader.getAllUncookedOrders();
		this.eventDispatcher.dispatchEvent(new Event<ArrayList<Order>>(EventType.COOK_GUI_UPDATE_ORDER_LIST, orderList));
	}

	/**
	 * Searches for customers by a keyword String attached to the event entered, and sends them to the order GUI
	 * @param event An event that should have a String attached to it
	 */
	private void searchCustomers(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof String)){
			GUIConstants.showErrorMessage("Kunne ikke hente kunder fra databasen!");
			return;
		}
		String searchQuery = (String)event.getEventParameterObject();
		searchQuery = DataCleaner.cleanDbData(searchQuery);
		ArrayList<Customer> customerList = DatabaseSearcher.searchCustomerByString(searchQuery);
		if(customerList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Customer>>(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST, customerList));
		}
	}

	/**
	 * Sends a list of all Extras to the order GUI
	 */
	private void sendListOfAllExtrasToOrderGUI() {
		ArrayList<Extra> extraList = DatabaseReader.getAllActiveExtras();
		if(extraList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Extra>>(EventType.ORDER_GUI_UPDATE_EXTRAS_LIST, extraList));
		}
	}

	/**
	 * Fetches a list of all dishes from the database, and sends them to the admin GUI
	 */
	private void sendListOfAllDishesToAdminGUI() {
		ArrayList<Dish> dishList = DatabaseReader.getAllDishes();
		if(dishList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Dish>>(EventType.ADMIN_GUI_UPDATE_DISH_LIST, dishList));
		}
	}
	
	/**
	 * Retrieves a list of all extras from the database, and sends them to the admin GUI
	 */
	private void sendListOfAllExtrasToAdminGUI() {
		ArrayList<Extra> extraList = DatabaseReader.getAllExtras();
		if(extraList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Extra>>(EventType.ADMIN_GUI_UPDATE_EXTRA_LIST, extraList));
		}
	}
	
	/**
	 * Sends a list of all active dishes to the Order GUI
	 */
	private void sendListOfAllDishesToOrderGUI() {
		ArrayList<Dish> dishList = DatabaseReader.getAllActiveDishes();
		if(dishList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Dish>>(EventType.ORDER_GUI_UPDATE_DISH_LIST, dishList));
		}
	}

	/**
	 * Sends a list of all customers to the order GUI
	 */
	private void sendListOfAllCustomersToOrderGUI() {
		ArrayList<Customer> customerList = DatabaseReader.getAllCustomers();
		if(customerList != null){
			this.eventDispatcher.dispatchEvent(new Event<ArrayList<Customer>>(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST, customerList));
		}
	}
	
}//END
