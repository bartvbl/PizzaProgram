package pizzaProgram.gui.EventHandlers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.CookGUI;
import pizzaProgram.gui.views.CookView;

/**
 * Handles all events from the system directed at the cook GUI
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class CookGUI_SystemEventHandler implements EventHandler {
	/**
	 * A reference to the system's main event dispatcher
	 */
	private EventDispatcher eventDispatcher;
	/**
	 * A reference to the cook GUI module
	 */
	private CookGUI cookGUI;

	/**
	 * Registers all the event listeners at the main event dispatcher that this
	 * class handles
	 * 
	 * @param eventDispatcher
	 *            A reference to the system's main event dispatcher
	 * @param cookGUI
	 *            A reference to the main cook GUI module
	 */
	public CookGUI_SystemEventHandler(EventDispatcher eventDispatcher, CookGUI cookGUI) {
		this.eventDispatcher = eventDispatcher;
		this.cookGUI = cookGUI;
		this.addEventListeners();
	}

	/**
	 * Adds all event listeners that this class handles
	 */
	private void addEventListeners() {
		this.eventDispatcher.addEventListener(this, EventType.COOK_GUI_UPDATE_ORDER_LIST);
		this.eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
	}

	/**
	 * Handles incoming events and calls the appropiate event handling functions
	 * 
	 * @param event
	 *            The event to be handled
	 */
	@Override
	public void handleEvent(Event<?> event) {
		if (event.eventType.equals(EventType.COOK_GUI_UPDATE_ORDER_LIST)) {
			this.updateOrderList(event);
		}
	}

	/**
	 * Updates the order list based on the list of orders attached to the
	 * parameter event
	 * 
	 * @param event
	 *            The Event instance, having an ArrayList of Order objects
	 *            attached to it
	 */
	private void updateOrderList(Event<?> event) {
		if (event.getEventParameterObject() instanceof ArrayList<?>) {
			@SuppressWarnings("unchecked")
			ArrayList<Order> orderList = (ArrayList<Order>) event.getEventParameterObject();
			this.cookGUI.currentOrderList = orderList;
			Order currentOrder = this.cookGUI.currentSelectedOrder;
			DefaultTableModel tableModel = (DefaultTableModel) CookView.orderDetailsTable.getModel();
			tableModel.setRowCount(0);
			int selectedIndex = -1;
			for (int i = 0; i < orderList.size(); i++) {
				Order order = orderList.get(i);
				tableModel.addRow(new Object[] { order.orderID,
						GUIConstants.translateOrderStatus(order.status), order.timeRegistered });
				if(order.equals(currentOrder))
				{
					selectedIndex = i;
				}
			}
			CookView.orderDetailsTable.getSelectionModel().setSelectionInterval(selectedIndex, selectedIndex);
		} else {
			System.err
					.println("ERROR: got a list that was not a list of Order instances when trying to update the order list in the cook GUI.");
			return;
		}
	}
}