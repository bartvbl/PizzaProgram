package pizzaProgram.database.eventHandlers;

import pizzaProgram.events.EventDispatcher;
import pizzaProgram.database.DatabaseModule;

/**
 * A class that manages the database event handler classes
 * 
 * @author IT1901 Group 3, Fall 2011
 */
public class Database_SystemEventHandler {

	/**
	 * The constructor creates new instances of the database read and write
	 * event handlers
	 * 
	 * @param databaseModule
	 *            the database module of the program
	 * @param eventDispatcher
	 *            the main event dispatcher of the program
	 */
	public Database_SystemEventHandler(DatabaseModule databaseModule, EventDispatcher eventDispatcher) {
		new Database_ReadEventHandler(eventDispatcher);
		new Database_WriteEventHandler(databaseModule, eventDispatcher);
	}
}
