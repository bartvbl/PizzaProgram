package pizzaProgram.core;

import org.jdesktop.application.SingleFrameApplication;

import pizzaProgram.database.DatabaseModule;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.AdminGUI;
import pizzaProgram.gui.CookGUI;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.ProgramWindow;
import pizzaProgram.modules.TimeredDataUpdater;
import pizzaProgram.utils.PriceCalculators;

/**
 * The Main class acts as the root of the system. Its main task is to hold
 * references and initialize various parts/modules
 * 
 * @author Bart
 * 
 */
public class Main implements EventHandler {
	/**
	 * A reference to the main event dispatcher, which is the communication
	 * backbone of the program
	 */
	public EventDispatcher eventDispatcher;

	/**
	 * A reference to the class that manages the main window of the program
	 */
	private ProgramWindow programWindow;
	/**
	 * A reference to the program's database module, so that it can be used to disconnect from the database when a program shutdown is requested.
	 */
	private DatabaseModule databaseModule;

	/**
	 * Initializes every part of the program, and sets the parts up correctly
	 */
	public void initialize(SingleFrameApplication mainApplication) {
		this.eventDispatcher = new EventDispatcher();
		this.connectToDatabase();
		PriceCalculators.getConstantsFromDataBase();
		this.createMainWindow(mainApplication);
		this.createGUIModules();
		this.createDataUpdater();
		this.eventDispatcher.addEventListener(this,
				EventType.PROGRAM_EXIT_REQUESTED);

		this.eventDispatcher.dispatchEvent(new Event<Object>(
				EventType.ORDER_GUI_REQUESTED));
	}
	
	/**
	 * Creates the timered data updater module that periodically updates the data in the GUI
	 */
	private void createDataUpdater() {
		new TimeredDataUpdater(this.eventDispatcher);
	}

	/**
	 * Creates the program's main window
	 * @param mainApplication The SingleFrameApplication instance, which the program window requires to start
	 */
	private void createMainWindow(SingleFrameApplication mainApplication) {
		this.programWindow = new ProgramWindow(this.eventDispatcher,
				mainApplication);
	}
	
	/**
	 * Creates the database module, and then connects to the database. The database module registers the database event handlers.
	 */
	private void connectToDatabase() {
		this.databaseModule = new DatabaseModule(this.eventDispatcher);
		this.databaseModule.connect();
	}

	/**
	 * This function instantiates all the GUI modules of the program. After they
	 * are operational, they will dispatch events to the event dispatcher when
	 * the user interacts with the program
	 */
	private void createGUIModules() {
		new AdminGUI(this.programWindow, this.eventDispatcher);
		new DeliverGUI(this.programWindow, this.eventDispatcher);
		new OrderGUI(this.programWindow, this.eventDispatcher);
		new CookGUI(this.programWindow, this.eventDispatcher);
	}
	
	/**
	 * An event handler for a program exit request
	 */
	public void handleEvent(Event<?> event) {
		if (event.eventType.equals(EventType.PROGRAM_EXIT_REQUESTED)) {
			this.databaseModule.disconnect();
			System.exit(0);
		}
	}
}
