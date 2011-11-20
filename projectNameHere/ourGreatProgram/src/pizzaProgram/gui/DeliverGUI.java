package pizzaProgram.gui;

import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.EventHandlers.DeliveryGUI_DeliveryViewEventHandler;
import pizzaProgram.gui.EventHandlers.DeliveryGUI_SystemEventHandler;
import pizzaProgram.gui.views.DeliveryView;
import pizzaProgram.modules.GUIModule;

public class DeliverGUI extends GUIModule implements EventHandler {

	private DeliveryView deliverView;
	private ProgramWindow programWindow;

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
		new DeliveryGUI_DeliveryViewEventHandler(this);
		new DeliveryGUI_SystemEventHandler(eventDispatcher, this);
	}

	private void setupComponents() {
		DefaultTableModel tableModel = (DefaultTableModel) DeliveryView.activeOrdersTable
				.getModel();
		tableModel.addColumn("ID");
		tableModel.addColumn("Status");
		tableModel.addColumn("Tid mottatt");
		tableModel.addColumn("Leveringsmetode");
		DeliveryView.activeOrdersTable
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableModel = (DefaultTableModel) DeliveryView.orderContentsTable
				.getModel();
		tableModel.addColumn("Rett");
		tableModel.addColumn("Tilbehør");
		tableModel.addColumn("Pris for retter");
		DeliveryView.showReceiptButton.setEnabled(false);
	}

	/**
	 * Her skal koden for å vise komponentene ligge
	 */
	@Override
	public void show() {
		this.programWindow.showPanel(this.deliverView);
		this.dispatchEvent(new Event<Object>(
				EventType.DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS));
	}

	/**
	 * Her skal koden for å skjule komponentene ligge
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