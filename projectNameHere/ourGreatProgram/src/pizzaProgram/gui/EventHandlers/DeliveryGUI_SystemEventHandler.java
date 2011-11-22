package pizzaProgram.gui.EventHandlers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.views.DeliveryView;
/**
 * Handles all events coming from the system and that are directed at the delivery GUI
 * @author Bart
 *
 */
public class DeliveryGUI_SystemEventHandler implements EventHandler{
	/**
	 * A reference to the main delivery GUI module
	 */
	private DeliverGUI deliveryGUI;
	/**
	 * A reference to the system's main event dispatcher
	 */
	private EventDispatcher eventDispatcher;

	/**
	 * Registers all the event listeners for the events that htis class handles at the system's event dispatcher
	 * @param eventDispatcher The system's main event dispatcher
	 * @param deliveryGUI A reference to the delivery GUI module
	 */
	public DeliveryGUI_SystemEventHandler(EventDispatcher eventDispatcher, DeliverGUI deliveryGUI){
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
	 * Handles an incoming event by calling the appropiate event handling function
	 */
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.DELIVERY_GUI_UPDATE_ORDER_LIST)){
			this.updateOrderList(event);
		}
	}

	/**
	 * Updates the order list in the delivery GUI with a new list of orders, which should be attached to the parameter event
	 * @param event An Event instance having a parameter containing an ArrayList of Order instances
	 */
	private void updateOrderList(Event<?> event) {
		if(event.getEventParameterObject() instanceof ArrayList<?>){
			@SuppressWarnings("unchecked")
			ArrayList<Order> orderList = (ArrayList<Order>)event.getEventParameterObject();
			this.deliveryGUI.currentOrderList = orderList;
			DefaultTableModel tableModel = (DefaultTableModel)DeliveryView.activeOrdersTable.getModel();
			tableModel.setRowCount(0);
			for(Order order : orderList){
				tableModel.addRow(new Object[]{order.orderID, GUIConstants.translateOrderStatus(order.status), order.timeRegistered, GUIConstants.translateDeliveryMethod(order.deliveryMethod)});
			}
		}else{
			GUIConstants.showErrorMessage("Kunne ikke hente ordre fra databasen!");
			return;
		}
	}
	
}//END
