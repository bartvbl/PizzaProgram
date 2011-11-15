package pizzaProgram.gui;

import java.awt.List;
import java.awt.TextArea;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.events.moduleEventHandlers.DeliveryGUI_DeliveryViewEventHandler;
import pizzaProgram.events.moduleEventHandlers.DeliveryGUI_SystemEventHandler;
import pizzaProgram.gui.views.DeliveryView;
import pizzaProgram.modules.GUIModule;

public class DeliverGUI extends GUIModule implements EventHandler {


	private DeliveryView deliverView;
	private ProgramWindow programWindow;

	private DeliveryGUI_DeliveryViewEventHandler deliveryViewEventHandler;
	private DeliveryGUI_SystemEventHandler systemEventHandler;
	
	public ArrayList<Order> currentOrderList;
	public Order currentOrder;

	public DeliverGUI(ProgramWindow mainWindow, EventDispatcher eventDispatcher) {
		super(eventDispatcher);

		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher
				.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		this.deliverView = new DeliveryView();
		mainWindow.addJPanel(this.deliverView);
		this.programWindow = mainWindow;
		hide();
		this.setupComponents();
		this.deliveryViewEventHandler = new DeliveryGUI_DeliveryViewEventHandler(this);
		this.systemEventHandler = new DeliveryGUI_SystemEventHandler(eventDispatcher, this);
	}
	
	private void setupComponents(){
		DefaultTableModel tableModel = (DefaultTableModel)DeliveryView.activeOrdersTable.getModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Status");
		tableModel.addColumn("Time Registered");
		tableModel.addColumn("Delivery method");
		DeliveryView.activeOrdersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel = (DefaultTableModel)DeliveryView.orderContentsTable.getModel();
		tableModel.addColumn("Dish");
		tableModel.addColumn("Extras");
		tableModel.addColumn("Total dish price");
		DeliveryView.showReceiptButton.setEnabled(false);
	}
	

	/**
	 * Her skal koden for � vise komponentene ligge
	 */
	@Override
	public void show() {
		this.programWindow.showPanel(this.deliverView);
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS));
	}

	/**
	 * Her skal koden for � skjule komponentene ligge
	 */
	@Override
	public void hide() {
		this.programWindow.hidePanel(this.deliverView);

	}

	@Override
	public void handleEvent(Event<?> event) {
		if (event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)) {
			show();
		}
	}
}