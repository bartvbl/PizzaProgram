/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.events.moduleEventHandlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.views.OrderView;

/**
 * 
 * @author Bart
 */
public class OrderGUI_OrderViewEventHandler extends ComponentEventHandler
		implements ActionListener {
	private OrderView orderView;
	private OrderGUI_TemporaryOrderDataStorage temporaryOrderData;
	private OrderGUI orderGUI;

	public OrderGUI_OrderViewEventHandler(OrderView orderView, OrderGUI orderGUI) {
		super(orderGUI);
		this.orderGUI = orderGUI;
		this.orderView = orderView;
		this.addEventListeners();
		this.temporaryOrderData = new OrderGUI_TemporaryOrderDataStorage();
		this.resetUI();
	}

	private void addEventListeners() {
		this.orderView.customerList.addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						handleCustomerSelection(e);
					}});
		this.orderView.dishSelectionList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleDishSelection(e);
			}});
		
		OrderView.selectCustomerButton.addActionListener(this);
		this.registerEventType(OrderView.selectCustomerButton, "selectCustomer");
		
		OrderView.searchCustomerSearchButton.addActionListener(this);
		this.registerEventType(OrderView.searchCustomerSearchButton, "searchCustomer");
		
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
	}
	
	private void duplicateOrderDishes(ActionEvent event) {
		int[] selectedIndices = OrderView.orderContentsTable.getSelectedRows();
		DefaultTableModel tableModel = (DefaultTableModel) OrderView.orderContentsTable.getModel();
		for(int index : selectedIndices)
		{
			this.temporaryOrderData.duplicateDishInOrder(index);
			tableModel.addRow(new Object[]{	tableModel.getValueAt(index, 0),
											tableModel.getValueAt(index, 1)});
		}
	}

	private void deleteSelectedDishesFromOrder(ActionEvent event) {
		int[] selectedIndices = OrderView.orderContentsTable.getSelectedRows();
		DefaultTableModel tableModel = (DefaultTableModel) OrderView.orderContentsTable.getModel();
		for(int i = selectedIndices.length - 1; i >= 0; i--)
		{
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
		this.temporaryOrderData.setDeliveryMethod((String)OrderView.deliveryMethodComboBox.getSelectedItem());
		this.dispatchEvent(new Event<UnaddedOrder>(EventType.DATABASE_ADD_NEW_ORDER, this.temporaryOrderData.convertToOrderObjectAndReset()));
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
		Dish selectedDish = this.orderGUI.currentDishList.get(OrderView.dishSelectionList.getSelectedIndices()[0]);
		int[] selectedExtrasIndices = OrderView.extrasSelectionList.getSelectedIndices();
		Extra[] selectedExtras = new Extra[selectedExtrasIndices.length];
		int counter = 0;
		String stringForInTable = "";
		Extra currentExtra;
		for(int index : selectedExtrasIndices)
		{
			if(counter != 0)
			{
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
		OrderView.extrasSelectionList.clearSelection();
		OrderView.dishSelectionList.clearSelection();
	}

	private void selectCustomer(ActionEvent event) {
		System.out.println("selecting customer");
		this.setCustomerSelectionAreaEnabled(false);
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ORDER_GUI_DISH_LIST));
		this.dispatchEvent(new Event<Integer>(EventType.DATABASE_UPDATE_ORDER_GUI_EXTRAS_LIST_BY_DISH_ID));
	}

	private void handleCustomerSelection(ListSelectionEvent e) {
		if (e.getFirstIndex() == -1) {
			return;
		}
		Customer customer = this.orderGUI.currentCustomerList.get(e.getFirstIndex());
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
		Dish dish = this.orderGUI.currentDishList.get(list.getSelectedIndex());
		this.setDishDetails(dish);
	}
	
	private void setDishDetails(Dish dish) 
	{
		OrderView.dishDescriptionTextArea.setText(dish.description);
		OrderView.dishContainsGlutenText.setText(this.convertBooleanToYesOrNoString(dish.containsGluten));
		OrderView.dishContainsNutsText.setText(this.convertBooleanToYesOrNoString(dish.containsNuts));
		OrderView.dishContainsDairyText.setText(this.convertBooleanToYesOrNoString(dish.containsDiary));
		OrderView.dishIsVegetarianText.setText(this.convertBooleanToYesOrNoString(dish.isVegetarian));
		OrderView.dishisSpicyText.setText(this.convertBooleanToYesOrNoString(dish.isSpicy));
	}
	
	private String convertBooleanToYesOrNoString(boolean bool)
	{
		if(bool)
		{
			return "yes";
		} else 
		{
			return "no";
		}
	}
	

	private void setCustomerSelectionAreaEnabled(boolean enabled) {
		OrderView.selectCustomerButton.setEnabled(enabled);
		OrderView.customerList.setEnabled(enabled);
		OrderView.searchCustomerSearchButton.setEnabled(enabled);
		OrderView.searchCustomerTextArea.setEnabled(enabled);
		OrderView.newCustomerButton.setEnabled(enabled);
	}
}
