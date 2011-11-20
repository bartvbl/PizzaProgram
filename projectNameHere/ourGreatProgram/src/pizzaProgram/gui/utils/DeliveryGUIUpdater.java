package pizzaProgram.gui.utils;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.gui.DeliveryMap;
import pizzaProgram.gui.views.DeliveryView;
//import pizzaProgram.utils.DishPrice;
//import pizzaProgram.utils.DishPriceCalculator;
//import pizzaProgram.utils.OrderPrice;
//import pizzaProgram.utils.OrderPriceCalculator;
import pizzaProgram.utils.PriceCalculators;

public class DeliveryGUIUpdater {
//	private OrderPriceCalculator orderPriceCalculator;
	
	public void showOrder(Order order){
//		this.orderPriceCalculator = new OrderPriceCalculator();
		this.fillDishTable(order);
		this.fillOrderPriceLabels(order);
		this.showComments(order);
		this.enableMarkButtons(order);
		this.showMapImage(order);
	}
	
	private void showMapImage(Order order) {
		DeliveryMap map = new DeliveryMap();
		map.loadImage(order.customer, DeliveryView.mapImagePanel.getWidth(), DeliveryView.mapImagePanel.getHeight());
		DeliveryView.mapImagePanel.setLayout(new BorderLayout());
		DeliveryView.mapImagePanel.removeAll();
		DeliveryView.mapImagePanel.add(map, BorderLayout.CENTER);
		DeliveryView.mapImagePanel.setVisible(true);
		map.setVisible(true);
		map.validate();
		map.repaint();
		DeliveryView.mapImagePanel.validate();
		DeliveryView.mapImagePanel.repaint();
	}

	private void fillDishTable(Order order) {
//		this.orderPriceCalculator.reset();
		DefaultTableModel tableModel = (DefaultTableModel)DeliveryView.orderContentsTable.getModel();
		tableModel.setRowCount(0);
		ArrayList<OrderDish> dishList = order.getOrderedDishes();
//		DishPrice currentDishPrice;
		for(OrderDish dish : dishList){
//			currentDishPrice = DishPriceCalculator.getPrice(dish);
//			this.orderPriceCalculator.addDishToTotalPrice(currentDishPrice);
			tableModel.addRow(new Object[]{dish.dish.name, this.generateExtrasString(dish.getExtras()), PriceCalculators.getPriceForOrderDish(dish)});
		}
	}

	private void fillOrderPriceLabels(Order o) {
//		OrderPrice orderPrice = this.orderPriceCalculator.getTotalOrderPrice();
		DeliveryView.orderCostDeliveryCost.setText(PriceCalculators.getDeliveryCostForOrder(o));
		DeliveryView.orderCostOrderPrice.setText(PriceCalculators.getPriceForOrderWithVAT(o));
		DeliveryView.orderCostTotalCost.setText(PriceCalculators.getPriceForOrderWithVATAndDelivery(o));
	}

	private void showComments(Order order) {
		DeliveryView.orderCommentsTextArea.setText(order.comment);
		String customerAddress = new String();
		Customer customer = order.customer;
		customerAddress += customer.firstName + " " + customer.lastName + "\n";
		customerAddress += customer.phoneNumber + "\n";
		customerAddress += customer.address + "\n";
		customerAddress += customer.postalCode + " " + customer.city;
		DeliveryView.orderAddressTextArea.setText(customerAddress);
	}
	
	private void enableMarkButtons(Order order){
		DeliveryView.showReceiptButton.setEnabled(true);
		
		if(order.status.equals(Order.HAS_BEEN_COOKED) && order.deliveryMethod.equals(Order.DELIVER_AT_HOME)){
			DeliveryView.markOrderBeingDeliveredButton.setEnabled(true);
			DeliveryView.markOrderDeliveredButton.setEnabled(false);
		} else if(order.status.equals(Order.HAS_BEEN_COOKED) && order.deliveryMethod.equals(Order.PICKUP_AT_RESTAURANT)){
			DeliveryView.markOrderBeingDeliveredButton.setEnabled(false);
			DeliveryView.markOrderDeliveredButton.setEnabled(true);
		} else if(order.status.equals(Order.BEING_DELIVERED) && order.deliveryMethod.equals(Order.DELIVER_AT_HOME)){
			DeliveryView.markOrderBeingDeliveredButton.setEnabled(false);
			DeliveryView.markOrderDeliveredButton.setEnabled(true);
		} else {
			DeliveryView.markOrderBeingDeliveredButton.setEnabled(false);
			DeliveryView.markOrderDeliveredButton.setEnabled(false);
		}
	}

	private String generateExtrasString(ArrayList<Extra> extras) {
		String output = "";
		int counter = 0;
		for(Extra extra : extras){
			if(counter != 0){
				output += ", ";
			}
			counter++;
			output += extra.name;
		}
		return output;
	}
}
