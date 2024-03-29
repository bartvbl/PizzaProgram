package pizzaProgram.gui.EventHandlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
import pizzaProgram.utils.ReceiptGenerator;

/**
 * Handles all events generated by user interaction with the delivery GUI
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class DeliveryGUI_DeliveryViewEventHandler extends ComponentEventHandler implements ActionListener {
	/**
	 * A reference to the delivery GUI module
	 */
	private DeliverGUI deliveryGUI;

	/**
	 * Initializes the class, and registers all event listeners that the class
	 * will handle
	 * 
	 * @param deliveryGUI
	 *            a reference to the main deliveryGUI module
	 */
	public DeliveryGUI_DeliveryViewEventHandler(DeliverGUI deliveryGUI) {
		super(deliveryGUI);
		this.deliveryGUI = deliveryGUI;
		this.resetUI();
		this.addEventListeners();
	}

	/**
	 * Adds all event listeners at the various components in the delivery view
	 */
	private void addEventListeners() {
		DeliveryView.activeOrdersTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						handleOrderSelection();
					}
				});

		DeliveryView.orderSearchTextField.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
			}

			public void focusGained(FocusEvent arg0) {
				handleSearchBoxSelection();
			}
		});

		DeliveryView.orderSearchTextField.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent arg0) {
			}

			public void keyReleased(KeyEvent arg0) {
				showOrdersBasedOnSearchBox();
			}

			public void keyTyped(KeyEvent arg0) {
			}
		});

		DeliveryView.showReceiptButton.addActionListener(this);
		this.registerEventType(DeliveryView.showReceiptButton, "showReceipt");

		DeliveryView.markOrderDeliveredButton.addActionListener(this);
		this.registerEventType(DeliveryView.markOrderDeliveredButton, "markOrderDelivered");

		DeliveryView.markOrderBeingDeliveredButton.addActionListener(this);
		this.registerEventType(DeliveryView.markOrderBeingDeliveredButton, "markOrderBeingDelivered");
	}

	/**
	 * Calls the appropiate event handling function depending on the source of
	 * the ActionEvent
	 * 
	 * @param event
	 *            The event to be handled
	 */
	public void actionPerformed(ActionEvent event) {
		if (this.getEventNameByComponent((Component) event.getSource()).equals("showReceipt")) {
			this.showReceipt();
		} else if (this.getEventNameByComponent((Component) event.getSource()).equals("markOrderDelivered")) {
			this.markOrderAsDelivered();
		} else if (this.getEventNameByComponent((Component) event.getSource()).equals(
				"markOrderBeingDelivered")) {
			this.markOrderAsBeingDelivered();
		}
	}

	/**
	 * Selects all text in the order search box when the user selects it
	 */
	protected void handleSearchBoxSelection() {
		DeliveryView.orderSearchTextField.setSelectionStart(0);
		DeliveryView.orderSearchTextField.setSelectionEnd(DeliveryView.orderSearchTextField.getText()
				.length());
	}

	/**
	 * Searches the database for the keywords written in the order search box,
	 * and displays the result in the order list
	 */
	private void searchOrders() {
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEARCH_ORDERS,
				DeliveryView.orderSearchTextField.getText()));
	}

	/**
	 * Fetches all undelivered orders from the database and shows them in the
	 * order list
	 */
	private void showAllOrders() {
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_DELIVERY_GUI_SEND_ALL_ORDERS));
	}

	/**
	 * Searches for orders in the database if the search box is not empty, or
	 * shows all orders when it is
	 */
	public void showOrdersBasedOnSearchBox() {
		if (DeliveryView.orderSearchTextField.getText().equals("")) {
			showAllOrders();
		} else {
			searchOrders();
		}
	}

	/**
	 * Marks an order as being delivered and updates the delivery UI
	 */
	private void markOrderAsBeingDelivered() {
		int selectedIndex = DeliveryView.activeOrdersTable.getSelectionModel().getMinSelectionIndex();
		this.dispatchEvent(new Event<Order>(EventType.DATABASE_MARK_ORDER_BEING_DELIVERED,
				this.deliveryGUI.currentOrder));
		if (selectedIndex == -1) {
			return;
		}
		showOrdersBasedOnSearchBox();
		DeliveryView.activeOrdersTable.getSelectionModel().setLeadSelectionIndex(selectedIndex);
		Order order = this.deliveryGUI.currentOrderList.get(selectedIndex);
		this.deliveryGUI.currentOrder = order;
		DeliveryGUIUpdater.showOrder(order);
	}

	/**
	 * Marks an order as being delivered and updates/resets the delivery UI
	 */
	private void markOrderAsDelivered() {
		this.dispatchEvent(new Event<Order>(EventType.DATABASE_MARK_ORDER_DELIVERED,
				this.deliveryGUI.currentOrder));
		this.resetUI();
		showOrdersBasedOnSearchBox();
	}

	/**
	 * Shows a receipt of the currently selected order in a separate window
	 */
	private void showReceipt() {
		Order order = this.getCurrentSelectedOrder();
		if (order == null) {
			return;
		}
		ReceiptGenerator.generateReceiptAndWindow(order);
	}

	/**
	 * Displays an order in the delivery UI when a new one is selected
	 */
	public void handleOrderSelection() {
		Order order = this.getCurrentSelectedOrder();
		if (order == null) {
			return;
		}
		DeliveryGUIUpdater.showOrder(order);
		this.deliveryGUI.currentOrder = order;
	}

	/**
	 * Returns the order that is currently selected by the user
	 * 
	 * @return The currently selected order
	 */
	private Order getCurrentSelectedOrder() {
		int selectedIndex = DeliveryView.activeOrdersTable.getSelectionModel().getLeadSelectionIndex();
		if ((selectedIndex == -1) || (selectedIndex >= this.deliveryGUI.currentOrderList.size())) {
			return null;
		}
		Order order = this.deliveryGUI.currentOrderList.get(selectedIndex);
		return order;
	}

	/**
	 * Resets the UI to its initial state
	 */
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