package pizzaProgram.gui.guiEventHandelers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventType;
import pizzaProgram.events.moduleEventHandlers.ComponentEventHandler;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.utils.DeliveryGUIUpdater;
import pizzaProgram.gui.views.CookView;
import pizzaProgram.gui.views.DeliveryView;
import pizzaProgram.utils.ReceiptGenerator;

public class DeliveryGUI_DeliveryViewEventHandler extends ComponentEventHandler implements ActionListener  {

	private DeliverGUI deliveryGUI;
	private DeliveryGUIUpdater guiUpdater;

	public DeliveryGUI_DeliveryViewEventHandler(DeliverGUI deliveryGUI) {
		super(deliveryGUI);
		this.deliveryGUI = deliveryGUI;
		this.resetUI();
		this.addEventListeners();
		this.guiUpdater = new DeliveryGUIUpdater();
	}

	private void addEventListeners() {
		DeliveryView.activeOrdersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleOrderSelection();
			}});
		
		DeliveryView.orderSearchTextField.addFocusListener(new FocusListener(){public void focusLost(FocusEvent arg0) {}
		public void focusGained(FocusEvent arg0) {
			handleSearchBoxSelection();}});
		
		DeliveryView.orderSearchTextField.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {
				showOrdersBasedOnSearchBox();
			}
			public void keyTyped(KeyEvent arg0) {}
		});

		DeliveryView.showReceiptButton.addActionListener(this);
		this.registerEventType(DeliveryView.showReceiptButton, "showReceipt");

		DeliveryView.markOrderDeliveredButton.addActionListener(this);
		this.registerEventType(DeliveryView.markOrderDeliveredButton, "markOrderDelivered");

		DeliveryView.markOrderBeingDeliveredButton.addActionListener(this);
		this.registerEventType(DeliveryView.markOrderBeingDeliveredButton, "markOrderBeingDelivered");
	}

	public void actionPerformed(ActionEvent event){
		if (this.getEventNameByComponent((Component) event.getSource()).equals("showReceipt")) {
			this.showReceipt();
		}
		else if (this.getEventNameByComponent((Component) event.getSource()).equals("markOrderDelivered")) {
			this.markOrderAsDelivered();
		}
		else if (this.getEventNameByComponent((Component) event.getSource()).equals("markOrderBeingDelivered")) {
			this.markOrderAsBeingDelivered();
		}
	}
	
	protected void handleSearchBoxSelection() {
		DeliveryView.orderSearchTextField.setSelectionStart(0);
		DeliveryView.orderSearchTextField.setSelectionEnd(DeliveryView.orderSearchTextField.getText().length());
	}

	private void searchOrders(){
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEARCH_ORDERS, DeliveryView.orderSearchTextField.getText()));
	}

	private void showAllOrders(){
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS));
	}

	private void showOrdersBasedOnSearchBox(){
		if(DeliveryView.orderSearchTextField.getText().equals("")){
			showAllOrders();
		}
		else {
			searchOrders();
		}
	}

	private void markOrderAsBeingDelivered(){
		int selectedIndex = DeliveryView.activeOrdersTable.getSelectionModel().getMinSelectionIndex();
		this.dispatchEvent(new Event<Order>(EventType.DATABASE_MARK_ORDER_BEING_DELIVERED, this.deliveryGUI.currentOrder));
		if(selectedIndex == -1){
			return;
		}
		showOrdersBasedOnSearchBox();
		DeliveryView.activeOrdersTable.getSelectionModel().setLeadSelectionIndex(selectedIndex);
		Order order = this.deliveryGUI.currentOrderList.get(selectedIndex);
		this.deliveryGUI.currentOrder = order;
		this.guiUpdater.showOrder(order);
	}

	private void markOrderAsDelivered(){
		this.dispatchEvent(new Event<Order>(EventType.DATABASE_MARK_ORDER_DELIVERED, this.deliveryGUI.currentOrder));
		this.resetUI();
		this.showAllOrders();
	}

	private void showReceipt() {
		Order order = this.getCurrentSelectedOrder();
		if(order == null){
			return;
		}
		ReceiptGenerator.generateReceiptAndWindow(order);
	}

	public void handleOrderSelection(){
		Order order = this.getCurrentSelectedOrder();
		if(order == null){
			return;
		}
		this.guiUpdater.showOrder(order);
		this.deliveryGUI.currentOrder = order;
	}

	private Order getCurrentSelectedOrder(){
		int selectedIndex = DeliveryView.activeOrdersTable.getSelectionModel().getLeadSelectionIndex();
		if((selectedIndex == -1) || (selectedIndex >= this.deliveryGUI.currentOrderList.size())){
			return null;
		}
		Order order = this.deliveryGUI.currentOrderList.get(selectedIndex);
		return order;
	}

	private void resetUI() {
		DeliveryView.orderCostDeliveryCost.setText("");
		DeliveryView.orderCostOrderPrice.setText("");
		DeliveryView.orderCostTotalCost.setText("");
		DeliveryView.orderCommentsTextArea.setText("");
		DeliveryView.orderAddressTextArea.setText("");
		DeliveryView.markOrderBeingDeliveredButton.setEnabled(false);
		DeliveryView.markOrderDeliveredButton.setEnabled(false);
	}

}//END
