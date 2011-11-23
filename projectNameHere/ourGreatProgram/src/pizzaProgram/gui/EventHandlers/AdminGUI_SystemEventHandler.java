package pizzaProgram.gui.EventHandlers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.AdminGUI;
import pizzaProgram.gui.views.AdminView;
import pizzaProgram.utils.PriceCalculators;

/**
 * The AdminGUI System event handler handles all events coming from the system,
 * and are directed at the admin GUI.
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class AdminGUI_SystemEventHandler implements EventHandler {
	/**
	 * A reference to the system's main event dispatcher
	 */
	private EventDispatcher eventDispatcher;
	/**
	 * A reference to the admin GUI module
	 */
	private AdminGUI adminGUI;

	/**
	 * Registers all the event listeners at the main event dispatcher that this
	 * class handles
	 * 
	 * @param eventDispatcher
	 *            A reference to the system's main event dispatcher
	 * @param adminGUI
	 *            A reference to the main admin GUI module
	 */
	public AdminGUI_SystemEventHandler(EventDispatcher eventDispatcher, AdminGUI adminGUI) {
		this.eventDispatcher = eventDispatcher;
		this.adminGUI = adminGUI;
		this.addEventListeners();
	}

	/**
	 * Add events listeners, chooses wich events to listen for
	 */
	private void addEventListeners() {
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_UPDATE_DISH_LIST);
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_UPDATE_EXTRA_LIST);
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_UPDATE_ORDER_LIST);
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_REQUESTED);
	}

	/**
	 * Handles the incoming events, calls the apropriate method for changing the
	 * gui
	 * 
	 * @param event
	 *            The event to be handled
	 */
	@Override
	public void handleEvent(Event<?> event) {
		if (event.eventType.equals(EventType.ADMIN_GUI_UPDATE_DISH_LIST)) {
			this.updateDishList(event);
		} else if (event.eventType.equals(EventType.ADMIN_GUI_UPDATE_EXTRA_LIST)) {
			this.updateExtraList(event);
		} else if (event.eventType.equals(EventType.ADMIN_GUI_UPDATE_ORDER_LIST)) {
			this.updateOrderList(event);
		} else if (event.eventType.equals(EventType.ADMIN_GUI_REQUESTED)) {
			this.adminGUI.show();
		}
	}

	/**
	 * This method updates the list of extras in the adminview
	 * 
	 * @param event
	 *            An event with an added payload of an ArrayList containing all
	 *            the Extras in the database
	 */
	private void updateExtraList(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof ArrayList<?>)) {
			System.err.println("ERROR: got a list that was not a list of Extra "
					+ "instances when trying to update the extra list in the admin GUI.");
			return;
		}
		Extra selectedExtra = this.adminGUI.currentSelectedExtra;
		ArrayList<Extra> extraList = (ArrayList<Extra>) event.getEventParameterObject();
		adminGUI.currentExtraList = extraList;
		DefaultTableModel tableModel = (DefaultTableModel) AdminView.allRegisteredExtrasTable.getModel();
		tableModel.setRowCount(0);
		int selectedIndex = -1;
		for (int i = 0; i < extraList.size(); i++) {
			Extra extra = extraList.get(i);
			tableModel.addRow(new Object[] { extra.name, PriceCalculators.getPriceForExtra(extra),
					extra.isActive ? GUIConstants.GUI_TRUE : GUIConstants.GUI_FALSE });
			if(extra.equals(selectedExtra))
			{
				selectedIndex = i;
			}
		}
		AdminView.allRegisteredExtrasTable.getSelectionModel().setSelectionInterval(selectedIndex, selectedIndex);
	}

	/**
	 * This method updates the list of dishes in the adminview
	 * 
	 * @param event
	 *            An event with an added payload of an ArrayList containing all
	 *            the Dishes in the database
	 */
	private void updateDishList(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof ArrayList<?>)) {
			System.err.println("ERROR: got a list that was not a list of Dish "
					+ "instances when trying to update the dish list in the admin GUI.");
			return;
		}
		@SuppressWarnings("unchecked")
		Dish selectedDish = this.adminGUI.currentSelectedDish;
		ArrayList<Dish> dishList = (ArrayList<Dish>) event.getEventParameterObject();
		adminGUI.currentDishList = dishList;
		DefaultTableModel tableModel = (DefaultTableModel) AdminView.allActiveDishesTable.getModel();
		tableModel.setRowCount(0);
		int selectedIndex = -1;
		for (int i = 0; i < dishList.size(); i++) {
			Dish d = dishList.get(i);
			tableModel.addRow(new Object[] { d.name, PriceCalculators.getPriceForDish(d) + " kr",
					d.isActive ? GUIConstants.GUI_TRUE : GUIConstants.GUI_FALSE });
			if(d.equals(selectedDish))
			{
				selectedIndex = i;
			}
		}
		AdminView.allActiveDishesTable.getSelectionModel().setSelectionInterval(selectedIndex, selectedIndex);
	}

	/**
	 * Updates the list of Orders in the orderhistory
	 * 
	 * @param event
	 *            An event with an added payload of an ArrayList containing all
	 *            the deliverer Orders in the database
	 */
	private void updateOrderList(Event<?> event) {
		if (!(event.getEventParameterObject() instanceof ArrayList<?>)) {
			System.err.println("ERROR: got a list that was not a list of Order "
					+ "instances when trying to update the order list in the admin GUI.");
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Order> orderList = (ArrayList<Order>) event.getEventParameterObject();
		adminGUI.currentOrderList = orderList;
		DefaultTableModel tableModel = (DefaultTableModel) AdminView.ordersTable.getModel();
		tableModel.setRowCount(0);
		for (Order o : orderList) {
			tableModel.addRow(new Object[] { o.orderID, o.customer.firstName + " " + o.customer.lastName,
					GUIConstants.translateDeliveryMethod(o.deliveryMethod) });
		}
		System.out.println("udapting orders in admingui");
	}
}