package pizzaProgram.events.moduleEventHandlers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.CookGUI;
import pizzaProgram.gui.views.CookView;

public class CookGUI_SystemEventHandler implements EventHandler {
	private EventDispatcher eventDispatcher;
	private CookGUI cookGUI;
	
	public CookGUI_SystemEventHandler(EventDispatcher eventDispatcher, CookGUI cookGUI)
	{
		this.eventDispatcher = eventDispatcher;
		this.cookGUI = cookGUI;
		this.addEventListeners();
	}

	private void addEventListeners() {
		this.eventDispatcher.addEventListener(this, EventType.COOK_GUI_UPDATE_ORDER_LIST);
		this.eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
	}

	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.COOK_GUI_UPDATE_ORDER_LIST))
		{
			this.updateOrderList(event);
		}
	}

	private void updateOrderList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>))
		{
			System.err.println("ERROR: got a list that was not a list of Order instances when trying to update the order list in the cook GUI.");
			return;
		}
		ArrayList<Order> orderList = (ArrayList<Order>)event.getEventParameterObject();
		this.cookGUI.currentOrderList = orderList;
		DefaultTableModel tableModel = (DefaultTableModel)CookView.orderDetailsTable.getModel();
		tableModel.setRowCount(0);
		for(Order order : orderList)
		{
			tableModel.addRow(new Object[]{order.orderID, order.status, order.timeRegistered});
		}
	}
}
