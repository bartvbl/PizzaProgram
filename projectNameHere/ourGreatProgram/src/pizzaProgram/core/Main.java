package pizzaProgram.core;

import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.CookGUI;
import java.sql.SQLException;

import org.jdesktop.application.SingleFrameApplication;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.ProgramWindow;
import pizzaProgram.database.DatabaseModule;

/**
 * The Main class acts as the root of the system. Its main task is to hold references and initialize various parts/modules
 * @author Bart
 *
 */
public class Main implements EventHandler {
	//
	/**
	 * A reference to the main event dispatcher, which represents the communication backbone of the program
	 */
	public EventDispatcher eventDispatcher;
	
	/**
	 * A reference to the class that managed the program's main window
	 */
	private ProgramWindow programWindow;
	
	
	private DatabaseModule databaseModule;
	
	/**
	 * creates every part of the program, and sets it up correctly
	 */
	public void initialize(SingleFrameApplication mainApplication){
		this.eventDispatcher = new EventDispatcher();
		this.connectToDatabase();
		this.createMainWindow(mainApplication);
		this.createGUIModules();
		this.eventDispatcher.addEventListener(this, EventType.PROGRAM_EXIT_REQUESTED);
	}
	
	private void createMainWindow(SingleFrameApplication mainApplication){
		this.programWindow = new ProgramWindow(this.eventDispatcher, mainApplication);
	}
	
	private void connectToDatabase()
        {
            this.databaseModule = new DatabaseModule(this.eventDispatcher);
            this.databaseModule.connect();
	}
	
	/**
	 * This function instantiates all the GUI modules of the program. 
	 * After they are operational, they will dispatch events to the event dispatcher when the user interacts with the program
	 */
	private void createGUIModules(){
		//TODO: remove database connection parameter when database events are operational
		//TODO: remove jframe parameter
		DeliverGUI deliverGUI  = new DeliverGUI(this.programWindow, this.eventDispatcher);
		OrderGUI orderGUI = new OrderGUI(this.programWindow, this.eventDispatcher);
		CookGUI cookGUI  = new CookGUI(this.programWindow, this.eventDispatcher);
		
		orderGUI.show();
	}
	
	public void handleEvent(Event<?> event) {
		this.databaseModule.disconnect();
		System.exit(0);
	}
}
