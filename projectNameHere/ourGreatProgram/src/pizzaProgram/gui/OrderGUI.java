package pizzaProgram.gui;

import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.UnaddedCustomer;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.EventHandlers.OrderGUI_OrderViewEventHandler;
import pizzaProgram.gui.EventHandlers.OrderGUI_SystemEventHandler;
import pizzaProgram.gui.views.OrderView;
import pizzaProgram.modules.GUIModule;
/**
 * The OrderGUI module handles everything that has to do with the Orders view of the program. Its main task is to manage customers and registering new orders
 * @author Bart
 *
 */
public class OrderGUI extends GUIModule implements EventHandler {
	/**
	 * A reference to the OrderView instance so that it can be used to show or hide it in the main window
	 */
	private OrderView orderView;
	/**
	 * A reference to the program's main window
	 */
	private ProgramWindow programWindow;
	/**
	 * A reference to the list of customers currently being displayed in the Order GUI
	 */
	public ArrayList<Customer> currentCustomerList;
	/**
	 * A list of dishes that is currently being displayed in the order GUI
	 */
	public ArrayList<Dish> currentDishList;
	/**
	 * A list of extras that is currently being displayed in the order GUI
	 */
	public ArrayList<Extra> currentExtrasList;
	/**
	 * The dish that is currently selected by the user in the order GUI
	 */
	public Dish currentSelectedDish;
	
	/**
	 * Holds a reference to the orderViewEventHandler for internal use
	 */
	OrderGUI_OrderViewEventHandler orderViewEventHandler;
	/**
	 * Keeps a reference to the orderGUISystemEventHandler for internal reference
	 */
	OrderGUI_SystemEventHandler orderSystemEventHandler;

	/**
	 * The constructor initializes the order view and the associated evenr handling classes
	 * @param mainWindow The program's main window
	 * @param eventDispatcher The system's main event dispatcher
	 */
	public OrderGUI(ProgramWindow mainWindow, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		this.orderView = new OrderView();
		mainWindow.addJPanel(orderView);
		this.orderView.addPropertyChangeListener(null);
		this.programWindow = mainWindow;
		orderViewEventHandler = new OrderGUI_OrderViewEventHandler(this);
		orderSystemEventHandler = new OrderGUI_SystemEventHandler(eventDispatcher, this);
		this.setupComponents();
		hide();
		this.addEventListeners(eventDispatcher);
	}
	
	/**
	 * Adds event listeners for all events that this class listens for
	 * @param eventDispatcher The system's main event dispatcher
	 */
	private void addEventListeners(EventDispatcher eventDispatcher)
	{
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DATA_REFRESH_REQUESTED);
	}
	
	/**
	 * Sets up a series of components in the order GUi
	 */
	private void setupComponents(){
		OrderView.deliveryMethodComboBox.removeAllItems();
		OrderView.deliveryMethodComboBox.addItem(GUIConstants.GUI_PICKUP);
		OrderView.deliveryMethodComboBox.addItem(GUIConstants.GUI_DELIVER);
		OrderView.deliveryMethodComboBox.selectWithKeyChar(GUIConstants.GUI_DELIVER.charAt(0));
		OrderView.extrasSelectionList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		OrderView.dishSelectionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		OrderView.customerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		DefaultTableModel tableModel = (DefaultTableModel)OrderView.orderContentsTable.getModel();
		tableModel.addColumn("Rett");
		tableModel.addColumn("Tilbehør");
		OrderView.orderContentsTable.removeEditor();
		OrderView.selectCustomerButton.setEnabled(false);
		OrderView.deleteCustomerButton.setEnabled(false);
		OrderView.changeCustomerButton.setEnabled(false);
	}

	/**
	 * Creates a new customer in the database
	 * @param customer The customer to create
	 */
	public void createNewCustomer(UnaddedCustomer customer) {
		this.dispatchEvent(new Event<UnaddedCustomer>(EventType.DATABASE_ADD_NEW_CUSTOMER, customer));
	}
	
	/**
	 * Updates a customer in the database
	 * @param customer The customer whose data should be updated
	 */
	public void updateCustomer(Customer customer) {
		this.dispatchEvent(new Event<Customer>(EventType.DATABASE_UPDATE_CUSTOMER_BY_CUSTOMER_ID, customer));
	}

	/**
	 * Handles incoming events directed at the order GUI module and calls the appropiate event handling function
	 */
	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			show();
			this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS));
		} else if(event.eventType.equals(EventType.DATA_REFRESH_REQUESTED)){
			if(this.programWindow.panelIsCurrentlyVisible(this.orderView))
			{
				//woo! broke the record for longest function name! :D
				this.orderViewEventHandler.handleCustomerSearchTypingBasedOnSearchBoxContents();
				this.orderViewEventHandler.handleDishSearchTyping();
				this.orderViewEventHandler.handleExtrasSearchTyping();
			}
		}
	}
	
	/**
	 * Shows the order view in the main window
	 */
	@Override
	public void show() {
		this.orderViewEventHandler.resetOrder();
		this.programWindow.showPanel(this.orderView);
	}
	
	/**
	 * Hides the order view in the main window
	 */
	@Override
	public void hide() {
		this.programWindow.hidePanel(this.orderView);
	}
}// END
