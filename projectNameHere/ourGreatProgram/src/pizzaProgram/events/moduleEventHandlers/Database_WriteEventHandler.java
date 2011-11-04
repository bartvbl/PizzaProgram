package pizzaProgram.events.moduleEventHandlers;

import pizzaProgram.dataObjects.Order;
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
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_IN_PROGRESS);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_FINISHED_COOKING);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_BEING_DELIVERED);
		this.eventDispatcher.addEventListener(this, EventType.DATABASE_MARK_ORDER_DELIVERED);
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
		} 
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
