/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.database;

import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.modules.Module;
import pizzaProgram.events.moduleEventHandlers.Database_SystemEventHandler;

/**
 *
 * @author Bart
 */
public class DatabaseModule extends Module {
	private Database_SystemEventHandler eventHandler;

	public DatabaseModule(EventDispatcher eventDispatcher){
		super(eventDispatcher);

		new DatabaseConnection();
		this.eventHandler = new Database_SystemEventHandler(this, eventDispatcher);
	}

	public void handleEvent(Event<?> event){
		this.eventHandler.handleEvent(event);
	}

	public void connect(){
		DatabaseConnection.connect();
	}

	public void disconnect(){
		DatabaseConnection.disconnect();
	}
}
