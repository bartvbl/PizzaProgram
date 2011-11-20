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
 * The main database module. Its main task is to hold references to the various database event handlers, and the connection to the database. It also initializes all of these at the start of the program.
 * @author Bart
 */
public class DatabaseModule extends Module {
	/**
	 * A reference to the main database event handler class.
	 */
	private Database_SystemEventHandler eventHandler;

	/**
	 * The constructor of the database module initializes all the event handlers.
	 * @param eventDispatcher
	 */
	public DatabaseModule(EventDispatcher eventDispatcher){
		super(eventDispatcher);

		new DatabaseConnection();
		this.eventHandler = new Database_SystemEventHandler(this, eventDispatcher);
	}

	/**
	 * Implementation of a function required by the Module class. Forwards all incoming events to the main database event handler.
	 */
	public void handleEvent(Event<?> event){
		this.eventHandler.handleEvent(event);
	}

	/**
	 * Creates a connection to the database
	 */
	public void connect(){
		DatabaseConnection.connect();
	}

	/**
	 * Destroys the connection to the database
	 */
	public void disconnect(){
		DatabaseConnection.disconnect();
	}
}
