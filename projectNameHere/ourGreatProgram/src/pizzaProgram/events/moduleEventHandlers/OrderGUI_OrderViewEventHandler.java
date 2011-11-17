/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.events.moduleEventHandlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.core.Constants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.database.databaseUtils.DatabaseResultsFeedbackProvider;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.NewCustomerWindow;
import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.views.OrderView;

/**
 * 
 * @author Bart
 */
public class OrderGUI_OrderViewEventHandler extends ComponentEventHandler implements ActionListener {
	
	private OrderGUI_TemporaryOrderDataStorage temporaryOrderData;
	private OrderGUI orderGUI;
	private Customer currentSelecetedCustomer;

	public OrderGUI_OrderViewEventHandler(OrderView orderView, OrderGUI orderGUI) {
		super(orderGUI);
		this.orderGUI = orderGUI;
		this.addEventListeners();
		this.temporaryOrderData = new OrderGUI_TemporaryOrderDataStorage();
		this.resetUI();
	}

	private void addEventListeners() {
		OrderView.changeCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currentSelecetedCustomer == null){
					DatabaseResultsFeedbackProvider.showEditCustomerFailedNoCustomerSelectedMessage();
					return;
				}
				new NewCustomerWindow(orderGUI, NewCustomerWindow.UPDATE_CUSTOMER, currentSelecetedCustomer);
			}
		});
		
		
		OrderView.customerList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleCustomerSelection(e);
			}});
		OrderView.dishSelectionList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleDishSelection(e);
			}});
		OrderView.searchCustomerTextArea.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {}//not needed
			public void keyTyped(KeyEvent e) {}//not needed
			public void keyReleased(KeyEvent e) {
				if(OrderView.searchCustomerTextArea.getText().equals("")){
					showAllCustomers();
				}
				else{
					searchCustomers();
				}
			}
		});

		OrderView.selectCustomerButton.addActionListener(this);
		this.registerEventType(OrderView.selectCustomerButton, "selectCustomer");

		OrderView.addDishButton.addActionListener(this);
		this.registerEventType(OrderView.addDishButton, "addDish");

		OrderView.confirmOrderButton.addActionListener(this);
		this.registerEventType(OrderView.confirmOrderButton, "confirmOrder");

		OrderView.resetOrderButton.addActionListener(this);
		this.registerEventType(OrderView.resetOrderButton, "resetOrder");

		OrderView.deleteSelectedOrderDishButton.addActionListener(this);
		this.registerEventType(OrderView.deleteSelectedOrderDishButton, "deleteSelected");

		OrderView.duplicateSelectedOrderDishButton.addActionListener(this);
		this.registerEventType(OrderView.duplicateSelectedOrderDishButton, "duplicateSelected");

		OrderView.newCustomerButton.addActionListener(this);
		this.registerEventType(OrderView.newCustomerButton, "newCustomer");

	}

	public void actionPerformed(ActionEvent event) {
		if (this.getEventNameByComponent((Component) event.getSource()).equals("selectCustomer")) {
			this.selectCustomer(event);
		} else if (this.getEventNameByComponent((Component) event.getSource()).equals("addDish")) {
			this.addDish(event);
		} else if (this.getEventNameByComponent((Component) event.getSource()).equals("confirmOrder")) {
			this.confirmOrder(event);
		} else if (this.getEventNameByComponent((Component) event.getSource()).equals("resetOrder")) {
			this.resetOrder(event);
		} else if (this.getEventNameByComponent((Component) event.getSource()).equals("deleteSelected")) {
			this.deleteSelectedDishesFromOrder(event);
		} else if (this.getEventNameByComponent((Component) event.getSource()).equals("duplicateSelected")) {
			this.duplicateOrderDishes(event);
		}
		else if (this.getEventNameByComponent((Component) event.getSource()).equals("newCustomer")) {
			new NewCustomerWindow(orderGUI, NewCustomerWindow.NEW_CUSTOMER, null);
		}
		else if (this.getEventNameByComponent((Component) event.getSource()).equals("searchCustomers")) {
			this.searchCustomers();
		}
	}

	private void showAllCustomers(){
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS));
	}

	private void searchCustomers() {
		String searchQuery = OrderView.searchCustomerTextArea.getText();
		if(!searchQuery.equals("")){
			this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_ORDER_GUI_SEARCH_CUSTOMERS_BY_KEYWORDS, searchQuery));
		} 
		else {
			this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ORDER_GUI_SEND_ALL_CUSTOMERS));
		}
	}

	private void duplicateOrderDishes(ActionEvent event) {
		int[] selectedIndices = OrderView.orderContentsTable.getSelectedRows();
		DefaultTableModel tableModel = (DefaultTableModel) OrderView.orderContentsTable.getModel();
		for(int index : selectedIndices){
			this.temporaryOrderData.duplicateDishInOrder(index);
			tableModel.addRow(new Object[]{	tableModel.getValueAt(index, 0), tableModel.getValueAt(index, 1)});
		}
	}

	private void deleteSelectedDishesFromOrder(ActionEvent event) {
		int[] selectedIndices = OrderView.orderContentsTable.getSelectedRows();
		DefaultTableModel tableModel = (DefaultTableModel) OrderView.orderContentsTable.getModel();
		for(int i = selectedIndices.length - 1; i >= 0; i--){
			this.temporaryOrderData.removeDishFromOrder(i);
			tableModel.removeRow(i);
		}
	}

	private void resetOrder(ActionEvent event) {
		System.out.println("resetting UI");
		this.temporaryOrderData.reset();
		this.resetUI();
	}

	private void confirmOrder(ActionEvent event) {
		this.temporaryOrderData.setOrderComments(OrderView.orderCommentsTextArea.getText());
		
		String selectedDeliveryMethod = (String)OrderView.deliveryMethodComboBox.getSelectedItem();
		String deliveryMethod = selectedDeliveryMethod.equals(Constants.GUI_PICKUP) ? Order.PICKUP_AT_RESTAURANT :  Order.DELIVER_AT_HOME;
		this.temporaryOrderData.setDeliveryMethod(deliveryMethod);
		
		UnaddedOrder orderToConfirm = this.temporaryOrderData.convertToOrderObjectAndReset();
		if(orderToConfirm.orderedDishes.isEmpty()){
			JOptionPane.showMessageDialog(null, "Orderen m� innholde en eller flere retter!", "Feil", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		this.dispatchEvent(new Event<UnaddedOrder>(EventType.DATABASE_ADD_NEW_ORDER, orderToConfirm));
		this.resetUI();
	}

	private void resetUI() {
		DefaultTableModel model = (DefaultTableModel)OrderView.orderContentsTable.getModel();
		model.setNumRows(0);
		((DefaultListModel)OrderView.dishSelectionList.getModel()).clear();
		((DefaultListModel)OrderView.extrasSelectionList.getModel()).clear();
		this.setCustomerSelectionAreaEnabled(true);
		OrderView.orderCommentsTextArea.setText("");
		OrderView.dishDescriptionTextArea.setText("");
		OrderView.dishContainsGlutenText.setText("");
		OrderView.dishContainsNutsText.setText("");
		OrderView.dishContainsDairyText.setText("");
		OrderView.dishIsVegetarianText.setText("");
		OrderView.dishisSpicyText.setText("");
	}

	private void addDish(ActionEvent event) {
		int[] selectedIndices = OrderView.dishSelectionList.getSelectedIndices();
		if(selectedIndices.length == 0){
			DatabaseResultsFeedbackProvider.showAddExtraWithoutDishFailedMessage();
			return;
		}
		Dish selectedDish = this.orderGUI.currentDishList.get(selectedIndices[0]);
		int[] selectedExtrasIndices = OrderView.extrasSelectionList.getSelectedIndices();
		Extra[] selectedExtras = new Extra[selectedExtrasIndices.length];
		int counter = 0;
		String stringForInTable = "";
		Extra currentExtra;
		for(int index : selectedExtrasIndices){
			if(counter != 0){
				stringForInTable += ", ";
			}
			currentExtra = this.orderGUI.currentExtrasList.get(index);
			stringForInTable += currentExtra.name;
			selectedExtras[counter] = currentExtra;
			counter++;
		}
		this.temporaryOrderData.addDish(selectedDish, selectedExtras);
		DefaultTableModel model = (DefaultTableModel) OrderView.orderContentsTable.getModel();
		model.addRow(new Object[]{selectedDish.name, stringForInTable});
		//TODO: find out what everyone thinks about keeping the selection after adding a dish to the order
		//OrderView.extrasSelectionList.clearSelection();
		//OrderView.dishSelectionList.clearSelection();
	}

	private void selectCustomer(ActionEvent event) {
		this.setCustomerSelectionAreaEnabled(false);
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ORDER_GUI_DISH_LIST));
		this.dispatchEvent(new Event<Integer>(EventType.DATABASE_UPDATE_ORDER_GUI_EXTRAS_LIST_BY_DISH_ID));
	}

	private void handleCustomerSelection(ListSelectionEvent e) {
		if (e.getFirstIndex() == -1) {
			currentSelecetedCustomer = null;
			return;
		}
		Customer customer = this.orderGUI.currentCustomerList.get(((JList)e.getSource()).getSelectedIndex());
		currentSelecetedCustomer = customer;
		
		this.temporaryOrderData.setCustomer(customer);
		OrderView.customerInformationTextArea.setText(customer.firstName
				+ " " + customer.lastName + "\n" + customer.address + "\n"
				+ customer.postalCode + " " + customer.city + "\n"
				+ customer.phoneNumber);
	}

	private void handleDishSelection(ListSelectionEvent e) {
		if (e.getFirstIndex() == -1) {
			return;
		}
		JList list = (JList)e.getSource();
		int index = list.getSelectedIndex();
		if(index != -1){
			Dish dish = this.orderGUI.currentDishList.get(index);
			this.setDishDetails(dish);
		}
	}

	private void setDishDetails(Dish dish) {
		OrderView.dishDescriptionTextArea.setText(dish.description);
		OrderView.dishContainsGlutenText.setText(this.convertBooleanToYesOrNoString(dish.containsGluten));
		OrderView.dishContainsNutsText.setText(this.convertBooleanToYesOrNoString(dish.containsNuts));
		OrderView.dishContainsDairyText.setText(this.convertBooleanToYesOrNoString(dish.containsDairy));
		OrderView.dishIsVegetarianText.setText(this.convertBooleanToYesOrNoString(dish.isVegetarian));
		OrderView.dishisSpicyText.setText(this.convertBooleanToYesOrNoString(dish.isSpicy));
	}

	private String convertBooleanToYesOrNoString(boolean b){
		return b ? Constants.GUI_TRUE : Constants.GUI_FALSE;
	}

	private void setCustomerSelectionAreaEnabled(boolean enabled) {
		OrderView.selectCustomerButton.setEnabled(enabled);
		OrderView.customerList.setEnabled(enabled);
		OrderView.searchCustomerTextArea.setEnabled(enabled);
		OrderView.newCustomerButton.setEnabled(enabled);
		OrderView.changeCustomerButton.setEnabled(enabled);
		OrderView.deleteCustomerButton.setEnabled(enabled);
	}

}//END
