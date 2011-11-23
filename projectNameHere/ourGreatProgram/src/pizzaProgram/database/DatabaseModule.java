package pizzaProgram.database;

import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.database.eventHandlers.Database_SystemEventHandler;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.modules.Module;

/**
 * The main database module. Its main task is to hold references to the various
 * database event handlers, and the connection to the database. It also
 * initializes all of these at the start of the program.
 * 
 * @author IT1901 Group 3, Fall 2011
 */
public class DatabaseModule extends Module {
	/**
	 * The constructor of the database module initializes all the event
	 * handlers.
	 * 
	 * @param eventDispatcher
	 *            the systems main event dispatcher
	 */
	public DatabaseModule(EventDispatcher eventDispatcher) {
		super(eventDispatcher);

		new DatabaseConnection();
		new Database_SystemEventHandler(this, eventDispatcher);
	}

	/**
	 * Implementation of a function required by the Module class. Does not do
	 * anything
	 */
	public void handleEvent(Event<?> event) {
	}

	/**
	 * Creates a connection to the database
	 */
	public void connect() {
		DatabaseConnection.connect();
	}

	/**
	 * Breaks the connection to the database
	 */
	public void disconnect() {
		DatabaseConnection.disconnect();
	}
}