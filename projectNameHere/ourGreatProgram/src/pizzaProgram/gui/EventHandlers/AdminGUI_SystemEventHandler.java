package pizzaProgram.gui.EventHandlers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.core.GUIConstants;
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
	 * this class recives events from the database and other places and updates the admingui accordingly
	 * @param eventDispatcher
	 * @param adminGUI
	 */
	public AdminGUI_SystemEventHandler(EventDispatcher eventDispatcher, AdminGUI adminGUI){
		this.eventDispatcher = eventDispatcher;
		this.adminGUI = adminGUI;
		this.addEventListeners();
	}

	/**
	 * add events listeners, chooses wich events to listen for
	 */
	private void addEventListeners() {
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_UPDATE_DISH_LIST);
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_UPDATE_EXTRA_LIST);
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_UPDATE_ORDER_LIST);
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_REQUESTED);
	}

	/**
	 * handles the incoming events, calls the apropriate method for changing the gui
	 */
	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.ADMIN_GUI_UPDATE_DISH_LIST)){
			this.updateDishList(event);
		}
		else if(event.eventType.equals(EventType.ADMIN_GUI_UPDATE_EXTRA_LIST)){
			this.updateExtraList(event);
		}
		else if(event.eventType.equals(EventType.ADMIN_GUI_UPDATE_ORDER_LIST)){
			this.updateOrderList(event);
		}
		else if(event.eventType.equals(EventType.ADMIN_GUI_REQUESTED)){
			this.adminGUI.show();
		}
	}
	
	/**
	 * this method updates the list of extras in the adminview
	 * @param event
	 */
	private void updateExtraList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.err.println("ERROR: got a list that was not a list of Order instances when trying to update the order list in the cook GUI.");
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Extra> extraList = (ArrayList<Extra>)event.getEventParameterObject();
		adminGUI.currentExtraList = extraList;
		DefaultTableModel tableModel = (DefaultTableModel)AdminView.allRegisteredExtrasTable.getModel();
		tableModel.setRowCount(0);
		for(Extra e : extraList){
			tableModel.addRow(new Object[]{e.name, PriceCalculators.getPriceForExtra(e), e.isActive ? GUIConstants.GUI_TRUE : GUIConstants.GUI_FALSE});
		}
	}
	/**
	 * this mentod updates the list of dishes in the adminview
	 * @param event
	 */
	private void updateDishList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.err.println("ERROR: got a list that was not a list of Order instances when trying to update the order list in the cook GUI.");
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Dish> dishList = (ArrayList<Dish>)event.getEventParameterObject();
		adminGUI.currentDishList = dishList;
		DefaultTableModel tableModel = (DefaultTableModel)AdminView.allActiveDishesTable.getModel();
		tableModel.setRowCount(0);
		for(Dish d : dishList){
			tableModel.addRow(new Object[]{d.name, PriceCalculators.getPriceForDish(d) + " kr", d.isActive ? GUIConstants.GUI_TRUE : GUIConstants.GUI_FALSE});
		}
	}
	/**
	 * updates the list of order in the orderhistory
	 * @param event
	 */
	private void updateOrderList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.err.println("ERROR: got a list that was not a list of Order instances when trying to update the order list in the cook GUI.");
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Order> orderList = (ArrayList<Order>)event.getEventParameterObject();
		adminGUI.currentOrderList = orderList;
		DefaultTableModel tableModel = (DefaultTableModel)AdminView.ordersTable.getModel();
		tableModel.setRowCount(0);
		for(Order o : orderList){
			System.out.println("addedorder");
			tableModel.addRow(new Object[]{o.orderID, o.customer.firstName + " " + o.customer.lastName, GUIConstants.translateDeliveryMethod(o.deliveryMethod)});
		}
	}
	
}
