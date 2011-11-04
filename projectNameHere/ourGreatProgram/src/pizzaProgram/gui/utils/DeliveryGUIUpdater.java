package pizzaProgram.gui.utils;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.gui.views.DeliveryView;
import pizzaProgram.utils.DishPrice;
import pizzaProgram.utils.DishPriceCalculator;
import pizzaProgram.utils.OrderPrice;
import pizzaProgram.utils.OrderPriceCalculator;

public class DeliveryGUIUpdater {
	private OrderPriceCalculator orderPriceCalculator;
	
	public void showOrder(Order order)
	{
		this.orderPriceCalculator = new OrderPriceCalculator();
		this.fillDishTable(order);
		this.fillOrderPriceLabels();
		this.showComments(order);
		this.enableMarkButtons(order);
	}
	
	private void fillDishTable(Order order) {
		this.orderPriceCalculator.reset();
		DefaultTableModel tableModel = (DefaultTableModel)DeliveryView.orderContentsTable.getModel();
		tableModel.setRowCount(0);
		ArrayList<OrderDish> dishList = order.getOrderedDishes();
		DishPrice currentDishPrice;
		for(OrderDish dish : dishList)
		{
			currentDishPrice = DishPriceCalculator.getPrice(dish);
			this.orderPriceCalculator.addDishToTotalPrice(currentDishPrice);
			tableModel.addRow(new Object[]{dish.dish.name, this.generateExtrasString(dish.getExtras()), currentDishPrice.getPriceAsString()});
		}
	}

	private void fillOrderPriceLabels() {
		OrderPrice orderPrice = this.orderPriceCalculator.getTotalOrderPrice();
		DeliveryView.orderCostDeliveryCost.setText(orderPrice.getDeliveryCostString());
		DeliveryView.orderCostOrderPrice.setText(orderPrice.getOrderPriceString());
		DeliveryView.orderCostTotalCost.setText(orderPrice.getTotalOverallOrderCostString());
	}

	private void showComments(Order order) {
		DeliveryView.orderCommentsTextArea.setText(order.comment);
		String customerAddress = new String();
		Customer customer = order.customer;
		customerAddress += customer.firstName + " " + customer.lastName + "\n";
		customerAddress += customer.address + "\n";
		customerAddress += customer.postalCode + " " + customer.city;
		DeliveryView.orderAddressTextArea.setText(customerAddress);
	}
	
	private void enableMarkButtons(Order order)
	{
		if(order.status.equals(Order.HAS_BEEN_COOKED))
		{
			DeliveryView.markOrderBeingDeliveredButton.setEnabled(true);
			DeliveryView.markOrderDeliveredButton.setEnabled(false);
		} else if(order.status.equals(Order.BEING_DELIVERED))
		{
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
		for(Extra extra : extras)
		{
			if(counter != 0)
			{
				output += ", ";
			}
			counter++;
			output += extra.name;
		}
		return output;
	}
}
