package pizzaProgram.gui.EventHandlers;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.views.OrderView;
import pizzaProgram.utils.PriceCalculators;

/**
 * Handles all the events coming from the system directed at the Order GUI.
 * 
 * @author IT1901 Group 3, Fall 2011
 */
public class OrderGUI_SystemEventHandler implements EventHandler {
	/**
	 * A reference to the order GUI main module class
	 */
	private OrderGUI orderGUI;

	/**
	 * Creates a new instance and registers all event listeners at the main
	 * event dispatcher.
	 * 
	 * @param eventDispatcher
	 *            The system's main event dispatcher
	 * @param orderGUI
	 *            A reference to the order GUI module
	 */
	public OrderGUI_SystemEventHandler(EventDispatcher eventDispatcher, OrderGUI orderGUI) {
		this.orderGUI = orderGUI;
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_DISH_LIST);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_EXTRAS_LIST);
	}

	/**
	 * Handles an incoming event, and calls the appropiate internal handling
	 * function
	 */
	public void handleEvent(Event<?> event) {
		if (event.eventType.equals(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST)) {
			this.updateCustomerList(event);
		} else if (event.eventType.equals(EventType.ORDER_GUI_UPDATE_DISH_LIST)) {
			this.updateDishList(event);
		} else if (event.eventType.equals(EventType.ORDER_GUI_UPDATE_EXTRAS_LIST)) {
			this.updateExtrasList(event);
		}
	}

	/**
	 * Shows the list of Extra instances attached to the event in the Order GUI
	 * 
	 * @param event
	 *            The Event instance
	 */
	private void updateExtrasList(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof ArrayList<?>)) {
			System.out
					.println("ERROR: received event containing a wrong data type [order GUI update extras list]");
			return;
		}
		if(!OrderView.extrasSelectionList.isEnabled())
		{
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Extra> extrasList = (ArrayList<Extra>) event.getEventParameterObject();
		this.orderGUI.currentExtrasList = extrasList;
		
		
		int currentSelectedRow = OrderView.extrasSelectionList.getSelectedIndex();
		
		
		
		ArrayList<Extra> selectedExtras = new ArrayList<Extra>();
		int[] indexes = OrderView.extrasSelectionList.getSelectedIndices();
		if(currentSelectedRow != -1){
			for (int i : indexes) {
				selectedExtras.add(this.orderGUI.currentExtrasList.get(i));
			}
		}
		
		DefaultListModel model = (DefaultListModel) OrderView.extrasSelectionList.getModel();
		model.clear();
		ArrayList<Integer> selectedRows = new ArrayList<Integer>();
		
		for (int i = 0; i < extrasList.size(); i++) {
			Extra extra = extrasList.get(i);
			if (orderGUI.currentSelectedDish != null) {
				model.addElement(extra.name + " (Levert hjem: "
						+ PriceCalculators.getPriceForExtraOnDishDeliver(orderGUI.currentSelectedDish, extra)
						+ "     Hent selv:  "
						+ PriceCalculators.getPriceForExtraOnDishPickup(orderGUI.currentSelectedDish, extra)
						+ ")");
			} else {
				model.addElement(extra.name + " ( " + PriceCalculators.getPriceForExtra(extra) + ")");
			}
			if(selectedExtras.contains(extra)){
				selectedRows.add(i);
			}
		}
		
		int[] selectTheese = new int[selectedRows.size()];
		for (int i = 0; i < selectTheese.length; i++) {
			selectTheese[i] = selectedRows.get(i);
		}
		
		OrderView.extrasSelectionList.setSelectedIndices(selectTheese);
		
	}

	/**
	 * Updates the dish list in the Order GUI, according to the ArrayList of
	 * Dish instances sent with the event
	 * 
	 * @param event
	 *            The event containing an ArrayList with Dish instances
	 */
	private void updateDishList(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof ArrayList<?>)) {
			System.out
					.println("ERROR: received event containing a wrong data type [order GUI update dish list]");
			return;
		}
		if(!OrderView.dishSelectionList.isEnabled())
		{
			return;
		}
		
		Dish selectedDish = this.orderGUI.currentSelectedDish;
		@SuppressWarnings("unchecked")
		ArrayList<Dish> dishList = (ArrayList<Dish>) event.getEventParameterObject();
		this.orderGUI.currentDishList = dishList;

		DefaultListModel model = (DefaultListModel) OrderView.dishSelectionList.getModel();
		model.clear();
		int selectedRow = -1;
		for (int i = 0; i < dishList.size(); i++) {
			Dish dish = dishList.get(i);
			model.addElement(dish.name + " (Levert hjem: "
					+ PriceCalculators.getPriceForDishDeliveryAtHome(dish) + "     Hent selv:  "
					+ PriceCalculators.getPriceForDishPickupAtRestaurant(dish) + ")");
			if(dish.equals(selectedDish))
			{
				selectedRow = i;
			}
		}
		 OrderView.dishSelectionList.setSelectedIndex(selectedRow);
		
	}

	/**
	 * Updates the list of customers in the Order UI.
	 * 
	 * @param event
	 *            An Event instance with an ArrayList of Customer objects in it.
	 */
	private void updateCustomerList(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof ArrayList<?>)) {
			System.out
					.println("ERROR: received event containing a wrong data type [order GUI update customer list]");
			return;
		}
		int currentSelectedRow = OrderView.customerList.getSelectedIndex();
		Customer selectedCustomer = null;
		if(currentSelectedRow != -1)
		{
			selectedCustomer = this.orderGUI.currentCustomerList.get(currentSelectedRow);
		}
		@SuppressWarnings("unchecked")
		ArrayList<Customer> customerList = (ArrayList<Customer>) event.getEventParameterObject();
		this.orderGUI.currentCustomerList = customerList;
		DefaultListModel model = (DefaultListModel) OrderView.customerList.getModel();
		model.clear();
		int selectedIndex = -1;
		for (int i = 0; i < customerList.size(); i++) {
			Customer customer = customerList.get(i);
			
			if(customer.equals(selectedCustomer))
			{
				selectedIndex = i;
			}
			model.addElement(customer.firstName + " " + customer.lastName);
		}
		OrderView.customerList.setSelectedIndex(selectedIndex);
	}
}