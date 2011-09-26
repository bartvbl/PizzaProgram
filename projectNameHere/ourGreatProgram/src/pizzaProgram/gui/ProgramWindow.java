package pizzaProgram.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;

import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.controls.WindowMenuBar;

/**
 * The ProgramWindow class creates the application's main window, with several features attached to it
 * @author Bart
 *
 */
public class ProgramWindow implements EventHandler{
	
	//håvard prøvert stuff
	private OrderGUI orderGUI;
	private CookGUI cookGUI;
	private DeliverGUI deliverGUI;
	//håvard prøvert stuff
	
	/**
	 * The window's canvas could potantionally be used to draw custom drawings that the swing library does not support (if we would ever need this)
	 */
	private final Canvas canvas;
	/**
	 * The instance of JFrame representing the main window
	 */
	private final JFrame jframe;
	/**
	 * A reference to the instance of WindowMenuBar, which is used by this class to draw the window's menu bar
	 */
	private WindowMenuBar menuBar;
	/**
	 * A reference to the application's main event dispatcher, which is used to dispatch events that are generated from parts of the main window
	 */
	private final EventDispatcher eventDispatcher;
	
	/**
	 * A constant holding the name as it will appear inside the window
	 */
	public static final String MAIN_WINDOW_NAME = "Pizza Manager";
	
	//AtomicReference is used because the AWT event system runs in a separate thread
	/**
	 * An atomic variable to communicate the canvas size between java's AWT event handling thread and the program's main thread
	 */
	private AtomicReference<Dimension> canvasSize = new AtomicReference<Dimension>();
	
	/**
	 * The constructor of the program takes in the event dispatcher, and creates t
	 * @param eventDispatcher
	 */
	public ProgramWindow(EventDispatcher eventDispatcher){
		this.eventDispatcher = eventDispatcher;
		
		Canvas canvas = new Canvas();
		JFrame frame = new JFrame(ProgramWindow.MAIN_WINDOW_NAME);
		frame.setLayout(new GridBagLayout());
		this.canvas = canvas;
		this.jframe = frame;
		
		this.eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		this.eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		this.eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
	}
	
	/**
	 * creates the different views(but does not display them)
	 * the databaseparameter is just for testing
	 */
	public void createGUIViews(DatabaseConnection dbc){
		orderGUI = new OrderGUI(jframe, dbc);
		orderGUI.clear();
		cookGUI  = new CookGUI(jframe, dbc);
		cookGUI.clear();
		deliverGUI  = new DeliverGUI(jframe, dbc);
		deliverGUI.draw();
	}
	
	/**
	 * Creates the main window
	 * @param width The width of the window
	 * @param height The height of the window
	 */
	public void createMainWindow(int width, int height){
		ComponentAdapter adapter = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resize();
			}
		};
		this.canvas.addComponentListener(adapter);
		this.jframe.setSize(width, height);
		this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.jframe.getContentPane().add(canvas);
		this.jframe.setVisible(true);
	}
	/**
	 * Creates the menu bar of the window, and adds it to the JFrame
	 */
	public void createMenuBar(){
		this.menuBar = new WindowMenuBar(eventDispatcher, this.jframe);
		this.createMenuBar(eventDispatcher);
	}
	
	/**
	 * Is called when the window is being resized. Updates the canvas size
	 */
	public void resize(){
		Dimension dim = this.canvas.getSize();
		canvasSize.set(dim);
		dim = null;
	}
	
	
	/**
	 * Generates the contents of the menu bar
	 */
	private void createMenuBar(EventDispatcher eventDispatcher){
		this.menuBar.addMenu("File");
		this.menuBar.addMenuItem("Exit", EventType.PROGRAM_EXIT_REQUESTED);
		this.menuBar.addMenu("Edit");
		this.menuBar.addMenuItem("Settings", EventType.OPEN_SETTINGS_WINDOW_REQUESTED);
		this.menuBar.addMenu("View");
		this.menuBar.createButtonGroup();
		this.menuBar.addRadioMenuItem("Orders", EventType.ORDER_GUI_REQUESTED , true);
		this.menuBar.addRadioMenuItem("Cook", EventType.COOK_GUI_REQUESTED, false);
		this.menuBar.addRadioMenuItem("Delivery", EventType.DELIVERY_GUI_REQUESTED, false);
		this.menuBar.pack();
	}
	@Override
	public void handleEvent(Event<Object> event) {
		
		if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			orderGUI.clear();
			deliverGUI.clear();
			cookGUI.draw();
		}else if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			cookGUI.clear();
			deliverGUI.clear();
			orderGUI.draw();
		}else if(event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)){
			cookGUI.clear();
			orderGUI.clear();
			deliverGUI.draw();
		}
	}
	
}//END
