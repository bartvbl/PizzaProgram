package pizzaProgram.gui.EventHandlers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.constants.GUIMessages;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.views.DeliveryView;

/**
 * Handles all events coming from the system and that are directed at the
 * delivery GUI
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class DeliveryGUI_SystemEventHandler implements EventHandler {
	/**
	 * A reference to the main delivery GUI module
	 */
	private DeliverGUI deliveryGUI;
	/**
	 * A reference to the system's main event dispatcher
	 */
	private EventDispatcher eventDispatcher;

	/**
	 * Registers all the event listeners for the events that this class handles
	 * at the system's event dispatcher
	 * 
	 * @param eventDispatcher
	 *            The system's main event dispatcher
	 * @param deliveryGUI
	 *            A reference to the delivery GUI module
	 */
	public DeliveryGUI_SystemEventHandler(EventDispatcher eventDispatcher, DeliverGUI deliveryGUI) {
		this.deliveryGUI = deliveryGUI;
		this.eventDispatcher = eventDispatcher;
		this.addEventListeners();
	}

	/**
	 * Registers all event listeners that this class handles
	 */
	private void addEventListeners() {
		this.eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_UPDATE_ORDER_LIST);
	}

	/**
	 * Handles an incoming event by calling the appropiate event handling
	 * function
	 * 
	 * @param event
	 *            The event to be handled
	 */
	public void handleEvent(Event<?> event) {
		if (event.eventType.equals(EventType.DELIVERY_GUI_UPDATE_ORDER_LIST)) {
			this.updateOrderList(event);
		}
	}

	/**
	 * Updates the order list in the delivery GUI with a new list of orders,
	 * which should be attached to the parameter event
	 * 
	 * @param event
	 *            An Event instance having a parameter containing an ArrayList
	 *            of Order instances
	 */
	private void updateOrderList(Event<?> event) {
		if (event.getEventParameterObject() instanceof ArrayList<?>) {
			@SuppressWarnings("unchecked")
			ArrayList<Order> orderList = (ArrayList<Order>) event.getEventParameterObject();
			this.deliveryGUI.currentOrderList = orderList;
			Order currentOrder = this.deliveryGUI.currentOrder;
			DefaultTableModel tableModel = (DefaultTableModel) DeliveryView.activeOrdersTable.getModel();
			tableModel.setRowCount(0);
			int selectedIndex = -1;
			for (int i = 0; i < orderList.size(); i++) {
				Order order = orderList.get(i);
				tableModel.addRow(new Object[] { order.orderID,
						GUIConstants.translateOrderStatus(order.status), order.timeRegistered,
						GUIConstants.translateDeliveryMethod(order.deliveryMethod) });
				if(order.equals(currentOrder))
				{
					selectedIndex = i;
				}
			}
			DeliveryView.activeOrdersTable.getSelectionModel().setSelectionInterval(selectedIndex, selectedIndex);
		} else {
			GUIConstants.showErrorMessage(GUIMessages.UNABLE_TO_GET_ORDERS_FROM_DATABASE);
			return;
		}
	}
}