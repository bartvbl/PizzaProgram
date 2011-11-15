package pizzaProgram.events.moduleEventHandlers;

import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.Setting;
import pizzaProgram.dataObjects.UnaddedCustomer;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.database.DatabaseModule;
import pizzaProgram.database.databaseUtils.DatabaseResultsFeedbackProvider;
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
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_ADD_NEW_EXTRA);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_ADD_NEW_DISH);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_IN_PROGRESS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_FINISHED_COOKING);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_BEING_DELIVERED);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_DELIVERED);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_CONFIG_VALUE);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_DISH_BY_DISH_ID);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_EXTRA_BY_EXTRA_ID);
	}

	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.DATABASE_ADD_NEW_ORDER))
		{
			this.addNewOrder(event);
		} else if(event.eventType.equals(EventType.DATABASE_ADD_NEW_CUSTOMER))
		{
			this.addNewCustomer(event);
		} else if(event.eventType.equals(EventType.DATABASE_MARK_ORDER_FINISHED_COOKING))
		{
			this.markOrderFinishedCooking(event);
		} else if(event.eventType.equals(EventType.DATABASE_MARK_ORDER_IN_PROGRESS))
		{
			this.markOrderInProgress(event);
		} else if(event.eventType.equals(EventType.DATABASE_MARK_ORDER_BEING_DELIVERED))
		{
			this.markOrderAsBeingDelivered(event);
		} else if(event.eventType.equals(EventType.DATABASE_MARK_ORDER_DELIVERED))
		{
			this.markOrderAsDelivered(event);
		} else if(event.eventType.equals(EventType.DATABASE_ADD_NEW_DISH))
		{
			this.addNewDish(event);
		} else if(event.eventType.equals(EventType.DATABASE_ADD_NEW_EXTRA))
		{
			this.addNewExtra(event);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_CONFIG_VALUE))
		{
			this.updateConfigValue(event);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_DISH_BY_DISH_ID))
		{
			this.updateDish(event);
		} else if(event.eventType.equals(EventType.DATABASE_UPDATE_EXTRA_BY_EXTRA_ID))
		{
			this.updateExtra(event);
		}
	}

	private void updateExtra(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof Extra))
		{
			DatabaseResultsFeedbackProvider.showUpdateExtraFailedMessage();
			return;
		}
		DatabaseWriter.updateExtraByExtraID((Extra)event.getEventParameterObject());
	}

	private void updateDish(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof Dish))
		{
			DatabaseResultsFeedbackProvider.showUpdateDishFailedMessage();
			return;
		}
		DatabaseWriter.updateDishByDishID((Dish)event.getEventParameterObject());
	}

	private void updateConfigValue(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof Setting))
		{
			DatabaseResultsFeedbackProvider.showUpdateConfigValueFailedMessage();
			return;
		}
		DatabaseWriter.updateConfigValue((Setting)event.getEventParameterObject());
	}

	private void addNewExtra(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof Extra))
		{
			DatabaseResultsFeedbackProvider.showAddNewExtraFailedMessage();
			return;
		}
		DatabaseWriter.writeNewExtra((Extra)event.getEventParameterObject());
	}

	private void addNewDish(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof Dish))
		{
			DatabaseResultsFeedbackProvider.showAddNewExtraFailedMessage();
			return;
		}
		DatabaseWriter.writeNewDish((Dish)event.getEventParameterObject());
	}

	private void markOrderFinishedCooking(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof Order))
		{
			DatabaseResultsFeedbackProvider.showUpdateOrderStatusFailedMessage();
			return;
		}
		DatabaseWriter.markOrderAsFinishedCooking((Order)event.getEventParameterObject());
	}
	
	private void markOrderAsBeingDelivered(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof Order))
		{
			DatabaseResultsFeedbackProvider.showUpdateOrderStatusFailedMessage();
			return;
		}
		DatabaseWriter.markOrderAsBeingDelivered((Order)event.getEventParameterObject());
	}
	
	private void markOrderAsDelivered(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof Order))
		{
			DatabaseResultsFeedbackProvider.showUpdateOrderStatusFailedMessage();
			return;
		}
		DatabaseWriter.markOrderAsDelivered((Order)event.getEventParameterObject());
	}

	private void markOrderInProgress(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof Order))
		{
			DatabaseResultsFeedbackProvider.showUpdateOrderStatusFailedMessage();
			return;
		}
		DatabaseWriter.markOrderAsInProgress((Order)event.getEventParameterObject());
	}

	private void addNewCustomer(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof UnaddedCustomer))
		{
			DatabaseResultsFeedbackProvider.showAddNewCustomerFailedMessage();
			return;
		}
		DatabaseWriter.writeNewCustomer((UnaddedCustomer)event.getEventParameterObject());
		this.eventDispatcher.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS));
	}

	private void addNewOrder(Event<?> event) 
	{
		if(!(event.getEventParameterObject() instanceof UnaddedOrder))
		{
			DatabaseResultsFeedbackProvider.showAddNewOrderFailedMessage();
			return;
		}
		DatabaseWriter.writeNewOrder((UnaddedOrder)event.getEventParameterObject());
	}
}
