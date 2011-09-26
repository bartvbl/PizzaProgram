import java.sql.SQLException;

import javax.swing.JFrame;

import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.gui.CookGUI;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.ProgramWindow;

/**
 * The Main class acts as the root of the system. Its main task is to hold references and initialize various parts/modules
 * @author Bart
 *
 */
public class Main {
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
	public void initialize()
	{
		this.eventDispatcher = new EventDispatcher();
		this.connectToDatabase();
		this.createMainWindow();
		this.createGUIModules();
	}
	
	private void createMainWindow()
	{
		this.programWindow = new ProgramWindow(this.eventDispatcher);
		this.programWindow.createMenuBar();
		this.programWindow.createMainWindow(640, 480);
	}
	
	private void connectToDatabase()
	{
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
	private void createGUIModules()
	{
		//TODO: remove database connection parameter when database events are operational
		
		OrderGUI orderGUI = new OrderGUI(this.databaseConnection, this.eventDispatcher);
		this.programWindow.setWindowFrame(orderGUI);
		orderGUI.clear();
		CookGUI cookGUI  = new CookGUI(this.databaseConnection, this.eventDispatcher);
		this.programWindow.setWindowFrame(cookGUI);
		cookGUI.clear();
		DeliverGUI deliverGUI  = new DeliverGUI(this.databaseConnection, this.eventDispatcher);
		this.programWindow.setWindowFrame(deliverGUI);
		deliverGUI.draw();
	}
	//this function will be available soon
}
