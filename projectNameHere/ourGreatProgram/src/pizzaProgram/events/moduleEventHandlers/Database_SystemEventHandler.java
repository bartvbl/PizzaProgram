/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.events.moduleEventHandlers;

import pizzaProgram.events.EventDispatcher;
import pizzaProgram.database.DatabaseModule;

/**
 * A class that manages the database event handler classes
 * @author Bart
 */
public class Database_SystemEventHandler{

	/**
	 * The constructor creates new instances of the database read and write event handlers
	 * @param databaseModule
	 * @param eventDispatcher
	 */
	public Database_SystemEventHandler(DatabaseModule databaseModule, EventDispatcher eventDispatcher){
		new Database_ReadEventHandler(eventDispatcher);
		new Database_WriteEventHandler(databaseModule, eventDispatcher);
	}
}
