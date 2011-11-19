package pizzaProgram.gui.guiEventHandelers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.core.Constants;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.database.databaseUtils.DatabaseResultsFeedbackProvider;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.views.DeliveryView;

public class DeliveryGUI_SystemEventHandler implements EventHandler{
	private DeliverGUI deliveryGUI;
	private EventDispatcher eventDispatcher;

	public DeliveryGUI_SystemEventHandler(EventDispatcher eventDispatcher, DeliverGUI deliveryGUI){
		this.deliveryGUI = deliveryGUI;
		this.eventDispatcher = eventDispatcher;
		this.addEventListeners();
	}

	private void addEventListeners() {
		this.eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_UPDATE_ORDER_LIST);
	}

	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.DELIVERY_GUI_UPDATE_ORDER_LIST)){
			this.updateOrderList(event);
		}
	}

	private void updateOrderList(Event<?> event) {
		if(event.getEventParameterObject() instanceof ArrayList<?>){
			@SuppressWarnings("unchecked")
			ArrayList<Order> orderList = (ArrayList<Order>)event.getEventParameterObject();
			this.deliveryGUI.currentOrderList = orderList;
			DefaultTableModel tableModel = (DefaultTableModel)DeliveryView.activeOrdersTable.getModel();
			tableModel.setRowCount(0);
			for(Order order : orderList){
				tableModel.addRow(new Object[]{order.orderID, Constants.translateOrderStatus(order.status), order.timeRegistered, Constants.translateDeliveryMethod(order.deliveryMethod)});
			}
		}else{
			DatabaseResultsFeedbackProvider.showGetAllUndeliveredOrdersFailedMessage();
			return;
		}
	}
	
}//END
