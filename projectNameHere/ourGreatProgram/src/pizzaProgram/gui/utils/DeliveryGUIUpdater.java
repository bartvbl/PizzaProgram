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
/**
 * A class that populates the Delivery GUI.
 * @author Bart
 *
 */
public class DeliveryGUIUpdater {
	/**
	 * Shows the order passed in the argument in the delivery UI.
	 * @param order The Order instance to be displayed
	 */
	public static void showOrder(Order order){
		fillDishTable(order);
		fillOrderPriceLabels(order);
		showComments(order);
		enableMarkButtons(order);
		showMapImage(order);
	}
	
	/**
	 * Downloads a map image showing the location of the restaurant and the location of the destination of the delivery in the map area of the delivery UI.
	 * @param order The order to use to display the map
	 */
	private static void showMapImage(Order order) {
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

	/**
	 * Shows the dishes the order contains in the dish table in the delivery GUI
	 * @param order The order to be displayed
	 */
	private static void fillDishTable(Order order) {
//		this.orderPriceCalculator.reset();
		DefaultTableModel tableModel = (DefaultTableModel)DeliveryView.orderContentsTable.getModel();
		tableModel.setRowCount(0);
		ArrayList<OrderDish> dishList = order.getOrderedDishes();
//		DishPrice currentDishPrice;
		for(OrderDish dish : dishList){
//			currentDishPrice = DishPriceCalculator.getPrice(dish);
//			this.orderPriceCalculator.addDishToTotalPrice(currentDishPrice);
			tableModel.addRow(new Object[]{dish.dish.name, generateExtrasString(dish.getExtras()), PriceCalculators.getPriceForOrderDish(dish)});
		}
	}

	/**
	 * Calculates the various prices related to the order, and displays them in the delivery GUI
	 * @param order The order to be displayed
	 */
	private static void fillOrderPriceLabels(Order order) {
//		OrderPrice orderPrice = this.orderPriceCalculator.getTotalOrderPrice();
		DeliveryView.orderCostDeliveryCost.setText(PriceCalculators.getDeliveryCostForOrder(order));
		DeliveryView.orderCostOrderPrice.setText(PriceCalculators.getPriceForOrderWithVAT(order));
		DeliveryView.orderCostTotalCost.setText(PriceCalculators.getPriceForOrderWithVATAndDelivery(order));
	}

	/**
	 * Shows the order comments and customer details in the delivery GUI
	 * @param order The order to be displayed
	 */
	private static void showComments(Order order) {
		DeliveryView.orderCommentsTextArea.setText(order.comment);
		String customerAddress = new String();
		Customer customer = order.customer;
		customerAddress += customer.firstName + " " + customer.lastName + "\n";
		customerAddress += customer.phoneNumber + "\n";
		customerAddress += customer.address + "\n";
		customerAddress += customer.postalCode + " " + customer.city;
		DeliveryView.orderAddressTextArea.setText(customerAddress);
	}
	
	/**
	 * Enables the "mark order as being delivered" and "mark order as delivered" buttons, depending on the order's state and delivery mthod.
	 * @param order The order to vbe displayed
	 */
	private static void enableMarkButtons(Order order){
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

	/**
	 * Generates a string for displaying in the order details table.
	 * @param extras The array of dishes to generate the string from
	 * @return The resulting string representing the list of extras.
	 */
	private static String generateExtrasString(ArrayList<Extra> extras) {
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
