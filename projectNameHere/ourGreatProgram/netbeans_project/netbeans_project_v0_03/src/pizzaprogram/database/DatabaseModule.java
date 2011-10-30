/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaprogram.database;

import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.modules.Module;
import pizzaprogram.events.moduleEventHandlers.Database_SystemEventHandler;

/**
 *
 * @author Bart
 */
public class DatabaseModule extends Module {
    private Database_SystemEventHandler eventHandler;
    private DatabaseConnection databaseConnection;
    
    public DatabaseModule(EventDispatcher eventDispatcher)
    {
        super(eventDispatcher);
        
        this.databaseConnection = new DatabaseConnection();
        this.eventHandler = new Database_SystemEventHandler(this, this.databaseConnection, eventDispatcher);
    }
    
    public void handleEvent(Event<?> event)
    {
        this.eventHandler.handleEvent(event);
    }
    
    public void connect()
    {
        this.databaseConnection.connect();
    }
    
    public void disconnect()
    {
        this.databaseConnection.disconnect();
    }
}
