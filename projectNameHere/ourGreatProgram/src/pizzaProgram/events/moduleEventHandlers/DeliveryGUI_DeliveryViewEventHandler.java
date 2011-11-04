package pizzaProgram.events.moduleEventHandlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.utils.DeliveryGUIUpdater;
import pizzaProgram.gui.views.DeliveryView;
import pizzaProgram.gui.views.OrderView;
import pizzaProgram.gui.views.ReceiptWindow;
import pizzaProgram.modules.Module;
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
		
		DeliveryView.orderSearchTextField.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {
				if(DeliveryView.orderSearchTextField.getText().equals(""))
				{
					showAllOrders();
				} else {
					searchOrders();
				}
			}
			public void keyTyped(KeyEvent arg0) {}
		});
		
		DeliveryView.showReceiptButton.addActionListener(this);
		this.registerEventType(DeliveryView.showReceiptButton, "showReceipt");
	}

	public void actionPerformed(ActionEvent event)
	{
		if (this.getEventNameByComponent((Component) event.getSource()).equals("showReceipt")) {
			this.showReceipt();
		}
	}
	
	private void searchOrders()
	{
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEARCH_ORDERS, DeliveryView.orderSearchTextField.getText()));
	}
	
	private void showAllOrders()
	{
		System.out.println("showing all orders");
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS));
	}
	
	private void showReceipt() {
		Order order = this.getCurrentSelectedOrder();
		if(order == null)
		{
			return;
		}
		String receipt = ReceiptGenerator.generateReceipt(order);
		new ReceiptWindow(receipt);
	}

	private void handleOrderSelection()
	{
		Order order = this.getCurrentSelectedOrder();
		if(order == null)
		{
			return;
		}
		this.guiUpdater.showOrder(order);
	}
	
	private Order getCurrentSelectedOrder()
	{
		int selectedIndex = DeliveryView.activeOrdersTable.getSelectionModel().getLeadSelectionIndex();
		if(selectedIndex == -1)
		{
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

}
