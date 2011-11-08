package pizzaProgram.gui;

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.events.moduleEventHandlers.CookGUI_CookViewEventHandler;
import pizzaProgram.events.moduleEventHandlers.CookGUI_SystemEventHandler;
import pizzaProgram.gui.views.CookView;
import pizzaProgram.modules.GUIModule;

public class CookGUI extends GUIModule implements EventHandler{

	private JPanel cookView;
	private ProgramWindow programWindow;

	private CookGUI_CookViewEventHandler cookViewEventHandler;
	private CookGUI_SystemEventHandler systemEventhandler;

	public ArrayList<Order> currentOrderList;
	public Order currentSelectedOrder;

	public CookGUI(ProgramWindow mainWindow, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		this.cookView = new CookView();
		mainWindow.addJPanel(this.cookView);
		this.programWindow = mainWindow;
		this.hide();
		this.cookViewEventHandler = new CookGUI_CookViewEventHandler(this);
		this.systemEventhandler = new CookGUI_SystemEventHandler(eventDispatcher, this);
		this.setupComponents();
	}

	private void setupComponents() {
		DefaultTableModel tableModel = (DefaultTableModel) CookView.orderDetailsTable.getModel();
		tableModel.addColumn("Order ID");
		tableModel.addColumn("Status");
		tableModel.addColumn("Time registered");
		tableModel = (DefaultTableModel) CookView.currentOrderTable.getModel();
		tableModel.addColumn("Dish");
		tableModel.addColumn("Extras");
		CookView.orderDetailsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		CookView.currentOrderTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}


	@Override
	public void handleEvent(Event<?> event){
		if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			show();
			this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_COOK_GUI_SEND_ALL_ORDERS));
		}
	}

	@Override
	public void show() {
		this.programWindow.showPanel(this.cookView);
	}
	@Override
	public void hide() {
		this.programWindow.hidePanel(this.cookView);
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
	}
}
