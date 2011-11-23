package pizzaProgram.database.eventHandlers;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.constants.GUIMessages;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.Setting;
import pizzaProgram.dataObjects.UnaddedCustomer;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.database.DatabaseModule;
import pizzaProgram.database.databaseUtils.DatabaseWriter;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;

/**
 * The database write event handler handles all database events that require
 * writing to the database
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class Database_WriteEventHandler implements EventHandler {
	/**
	 * A private reference to the event dispatcher
	 */
	private EventDispatcher eventDispatcher;

	/**
	 * The constructor registers all the events it handles with the main event
	 * dispatcher
	 * 
	 * @param databaseModule
	 *            The main database module
	 * @param eventDispatcher
	 *            The main event dispatcher
	 */
	public Database_WriteEventHandler(DatabaseModule databaseModule, EventDispatcher eventDispatcher) {
		this.eventDispatcher = eventDispatcher;
		this.addListeners();
	}

	/**
	 * Registers all event listeners this class handles with the main event
	 * dispatcher
	 */
	private void addListeners() {
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_ADD_NEW_ORDER);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_ADD_NEW_CUSTOMER);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_ADD_NEW_EXTRA);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_ADD_NEW_DISH);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_IN_PROGRESS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_FINISHED_COOKING);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_BEING_DELIVERED);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_DELIVERED);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_CONFIG_VALUE);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_DISH_BY_DISH_ID);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_EXTRA_BY_EXTRA_ID);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_CUSTOMER_BY_CUSTOMER_ID);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_DELETE_CUSTOMER);
	}

	/**
	 * Handles all incoming events. Determines the event type, then calls the
	 * appropriate local handling function
	 */
	public void handleEvent(Event<?> event) {
		if (event.eventType.equals(EventType.DATABASE_ADD_NEW_ORDER)) {
			this.addNewOrder(event);
		} else if (event.eventType.equals(EventType.DATABASE_ADD_NEW_CUSTOMER)) {
			this.addNewCustomer(event);
		} else if (event.eventType.equals(EventType.DATABASE_MARK_ORDER_FINISHED_COOKING)) {
			this.markOrderFinishedCooking(event);
		} else if (event.eventType.equals(EventType.DATABASE_MARK_ORDER_IN_PROGRESS)) {
			this.markOrderInProgress(event);
		} else if (event.eventType.equals(EventType.DATABASE_MARK_ORDER_BEING_DELIVERED)) {
			this.markOrderAsBeingDelivered(event);
		} else if (event.eventType.equals(EventType.DATABASE_MARK_ORDER_DELIVERED)) {
			this.markOrderAsDelivered(event);
		} else if (event.eventType.equals(EventType.DATABASE_ADD_NEW_DISH)) {
			this.addNewDish(event);
		} else if (event.eventType.equals(EventType.DATABASE_ADD_NEW_EXTRA)) {
			this.addNewExtra(event);
		} else if (event.eventType.equals(EventType.DATABASE_UPDATE_CONFIG_VALUE)) {
			this.updateConfigValue(event);
		} else if (event.eventType.equals(EventType.DATABASE_UPDATE_DISH_BY_DISH_ID)) {
			this.updateDish(event);
		} else if (event.eventType.equals(EventType.DATABASE_UPDATE_EXTRA_BY_EXTRA_ID)) {
			this.updateExtra(event);
		} else if (event.eventType.equals(EventType.DATABASE_UPDATE_CUSTOMER_BY_CUSTOMER_ID)) {
			this.updateCustomer(event);
		} else if (event.eventType.equals(EventType.DATABASE_DELETE_CUSTOMER)) {
			this.deleteCustomer(event);
		}
	}

	/**
	 * Deletes the customer from the database attached to the event
	 * 
	 * @param event
	 *            An event containing the Customer to be deleted
	 */
	private void deleteCustomer(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Customer)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_DELETE_CUSTOMER_FROM_DATABASE);
			return;
		}
		DatabaseWriter.deleteCustomer((Customer) event.getEventParameterObject());
	}

	/**
	 * Update the extra in the database specified by the Extra instance attached
	 * to the event
	 * 
	 * @param event
	 *            An Event containing the Extra to be updated
	 */
	private void updateExtra(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Extra)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_EDIT_EXTRA);
			return;
		}
		DatabaseWriter.updateExtraByExtraID((Extra) event.getEventParameterObject());
	}

	/**
	 * Updates the dish in the database specified by the Dish instance attached
	 * to the event
	 * 
	 * @param event
	 *            An Event containing the Dish to be updated
	 */
	private void updateDish(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Dish)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_EDIT_DISH);
			return;
		}
		DatabaseWriter.updateDishByDishID((Dish) event.getEventParameterObject());
	}

	/**
	 * Updates the config value in the database according to the Setting
	 * instance's key attached to the event to the Setting's value.
	 * 
	 * @param event
	 *            An Event containing the Setting to be updated
	 */
	private void updateConfigValue(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Setting)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_EDIT_SETTING);
			return;
		}
		DatabaseWriter.updateConfigValue((Setting) event.getEventParameterObject());
	}

	/**
	 * Adds a new extra to the database
	 * 
	 * @param event
	 *            An event containing the Extra to be added
	 */
	private void addNewExtra(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Extra)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_ADD_EXTRA);
			return;
		}
		DatabaseWriter.writeNewExtra((Extra) event.getEventParameterObject());
	}

	/**
	 * Adds a new dish to the database
	 * 
	 * @param event
	 *            An Event containing the Dish to be added
	 */
	private void addNewDish(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Dish)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_ADD_DISH);
			return;
		}
		DatabaseWriter.writeNewDish((Dish) event.getEventParameterObject());
	}

	/**
	 * Marks the order referenced by the Order instance as finished cooking in
	 * the database
	 * 
	 * @param event
	 *            An Event containing the Order to change the status of
	 */
	private void markOrderFinishedCooking(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Order)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_CHANGE_ORDER_STATUS);
			return;
		}
		DatabaseWriter.markOrderAsFinishedCooking((Order) event.getEventParameterObject());
	}

	/**
	 * Marks the order referenced by the Order instance as being delivered in
	 * the database
	 * 
	 * @param event
	 *            An Event containing the Order to change the status of
	 */
	private void markOrderAsBeingDelivered(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Order)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_CHANGE_ORDER_STATUS);
			return;
		}
		DatabaseWriter.markOrderAsBeingDelivered((Order) event.getEventParameterObject());
	}

	/**
	 * Marks the order referenced by the Order instance as delivered in the
	 * database
	 * 
	 * @param event
	 *            An Event containing the Order to change the status of
	 */
	private void markOrderAsDelivered(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Order)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_CHANGE_ORDER_STATUS);
			return;
		}
		DatabaseWriter.markOrderAsDelivered((Order) event.getEventParameterObject());
	}

	/**
	 * Marks the order referenced by the Order instance as being cooked in the
	 * database
	 * 
	 * @param event
	 *            An Event containing the Order to change the status of
	 */
	private void markOrderInProgress(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Order)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_CHANGE_ORDER_STATUS);
			return;
		}
		DatabaseWriter.markOrderAsInProgress((Order) event.getEventParameterObject());
	}

	/**
	 * Adds a new customer to the database
	 * 
	 * @param event
	 *            An Event containing the UnaddedCustomer to be added
	 */
	private void addNewCustomer(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof UnaddedCustomer)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_ADD_CUSTOMER);
			return;
		}
		DatabaseWriter.writeNewCustomer((UnaddedCustomer) event.getEventParameterObject());
		this.eventDispatcher.dispatchEvent(new Event<Object>(
				EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS));
	}

	/**
	 * Updates the customer in the database referenced by the Customer instance
	 * 
	 * @param event
	 *            An Event containing the Customer to be updated
	 */
	private void updateCustomer(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof Customer)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_EDIT_CUSTOMER);
			return;
		}
		DatabaseWriter.updateCustomerById((Customer) event.getEventParameterObject());
		this.eventDispatcher.dispatchEvent(new Event<Object>(
				EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS));
	}

	/**
	 * Adds a new order to the database
	 * 
	 * @param event
	 *            An Event containing the UnaddedOrder to be added
	 */
	private void addNewOrder(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof UnaddedOrder)) {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_ADD_ORDER);
			return;
		}
		DatabaseWriter.writeNewOrder((UnaddedOrder) event.getEventParameterObject());
	}
}