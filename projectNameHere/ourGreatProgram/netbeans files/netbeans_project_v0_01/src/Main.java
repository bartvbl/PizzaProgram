import java.sql.SQLException;

import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.CookGUI;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.ProgramWindow;

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
	
	
	private DatabaseConnection databaseConnection;
	
	/**
	 * creates every part of the program, and sets it up correctly
	 */
	public void initialize(){
		this.eventDispatcher = new EventDispatcher();
		this.connectToDatabase();
		this.createMainWindow();
		this.createGUIModules();
		this.eventDispatcher.addEventListener(this, EventType.PROGRAM_EXIT_REQUESTED);
	}
	
	private void createMainWindow(){
		this.programWindow = new ProgramWindow(this.eventDispatcher);
		this.programWindow.createMenuBar();
		this.programWindow.createMainWindow(640, 480);
	}
	
	private void connectToDatabase(){
		try {
			this.databaseConnection = new DatabaseConnection();
			this.databaseConnection.connect();
			this.databaseConnection.buildContents();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	/**
	 * This function instantiates all the GUI modules of the program. 
	 * After they are operational, they will dispatch events to the event dispatcher when the user interacts with the program
	 */
	private void createGUIModules(){
		//TODO: remove database connection parameter when database events are operational
		//TODO: remove jframe parameter
		
		OrderGUI orderGUI = new OrderGUI(this.databaseConnection, this.programWindow.getWindowFrame(), this.eventDispatcher);
		CookGUI cookGUI  = new CookGUI(this.databaseConnection, this.programWindow.getWindowFrame(), this.eventDispatcher);
		DeliverGUI deliverGUI  = new DeliverGUI(this.databaseConnection, this.programWindow.getWindowFrame(), this.eventDispatcher);
		cookGUI.show();
	}
	
	public void handleEvent(Event<?> event) {
		this.databaseConnection.disconnect();
		System.exit(0);
	}
}
