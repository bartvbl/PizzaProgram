/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.events.moduleEventHandlers;

import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.database.DatabaseModule;

/**
 *
 * @author Bart
 */
public class Database_SystemEventHandler implements EventHandler{
    private DatabaseModule systemEventDispatcher;
    private DatabaseConnection databaseConnection;
    private EventDispatcher eventDispatcher;
    
    private Database_ReadEventHandler readEventHandler;
    private Database_WriteEventHandler writeEventHandler;
    private Database_UpdateEventHandler updateEventHandler;
    
    
    public Database_SystemEventHandler(DatabaseModule databaseModule, DatabaseConnection databaseConnection, EventDispatcher eventDispatcher)
    {
        this.databaseConnection = databaseConnection;
        this.systemEventDispatcher = databaseModule;
        this.eventDispatcher = eventDispatcher;
        this.addEventListeners();
        
        this.readEventHandler = new Database_ReadEventHandler(databaseConnection, eventDispatcher);
        this.writeEventHandler = new Database_WriteEventHandler(databaseModule, databaseConnection, eventDispatcher);
        this.updateEventHandler = new Database_UpdateEventHandler(databaseModule, databaseConnection, eventDispatcher);
    }
    
    private void addEventListeners()
    {
        this.eventDispatcher.addEventListener(this.systemEventDispatcher, EventType.DATABASE_SEARCH_CUSTOMER_INFO_BY_NAME);
        System.out.println("listener added");
    }
    
    public void handleEvent(Event<?> event) {
    	if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
			System.err.println("No active database connection: please try again!");
		}
    	
    	if(event.eventType.equals(EventType.DATABASE_SEARCH_CUSTOMER_INFO_BY_NAME))
        {
            this.handleCustomerSearchRequest(event);
        }
    }
    
    private void handleCustomerSearchRequest(Event<?> event)
    {
        System.out.println("searching for customer data");
        if((event.getEventParameterObject() instanceof String))
        {
            
        }
    }
}
