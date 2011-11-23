package pizzaProgram.utils;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.gui.ReceiptWindow;

/**
 * Generates a receipt from a given Order instance, and shows it inside a window
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class ReceiptGenerator {
	/**
	 * Stores the amount rows in the last receipt generated. Used when showing
	 * the receipt in the GUI.
	 */
	private static int lastrows = 0;

	/**
	 * This method takes an order and creates an html-receipt from its data
	 * 
	 * @param order
	 */
	public static void generateReceiptAndWindow(Order order) {
		new ReceiptWindow(generateReceipt(order), lastrows);
	}

	/**
	 * Generates a receipt String from an order, formatted with HTML
	 * 
	 * @param order
	 *            The order to generate the receipt of
	 * @return A String representing the receipt of the order
	 */
	public static String generateReceipt(Order order) {
		lastrows = 6;
		StringBuilder receiptString = new StringBuilder();

		receiptString.append("<html>");
		receiptString.append("<table width=\"" + GUIConstants.RECIPT_WIDTH + "\" border=\"0\">");
		receiptString.append("<tr><td align=\"center\"colspan=\"2\">-" + PriceCalculators.getRestaurantName()
				+ "-</td></tr>");

		for (OrderDish d : order.orderedDishes) {
			lastrows++;
			String dishprice = order.deliveryMethod.equals(Order.DELIVER_AT_HOME) ? PriceCalculators
					.getPriceForDishDeliveryAtHome(d.dish) : PriceCalculators
					.getPriceForDishPickupAtRestaurant(d.dish);
			receiptString.append(createHeaderRow(d.dish.name, dishprice));
			if (d.dish.name.length() > 16) {
				lastrows++;
			}

			for (Extra e : d.getExtras()) {
				String extraprice = order.deliveryMethod.equals(Order.DELIVER_AT_HOME) ? PriceCalculators
						.getPriceForExtraOnDishDeliver(d.dish, e) : PriceCalculators
						.getPriceForExtraOnDishPickup(d.dish, e);
				receiptString.append(createRow("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + e.name, extraprice));
				lastrows++;
				if (e.name.length() > 22) {
					lastrows++;
				}
			}
		}

		receiptString.append(createRow("", "")
				+ createRow("Alle retter", PriceCalculators.getPriceForOrderWithVAT(order))
				+ createRow("Herav MVA", PriceCalculators.getVATForOrder(order))
				+ createRow("Levering", PriceCalculators.getDeliveryCostForOrder(order))
				+ createHeaderRow("Totalt", PriceCalculators.getPriceForOrderWithVATAndDelivery(order))
				+ "</table></html>");

		return receiptString.toString();
	}

	/**
	 * Creates an htmlrow with two columns from two strings
	 * 
	 * @param col1
	 *            the string that should be added to the first column
	 * @param col2
	 *            the string that should be added to the second column
	 * @return an htmlrow as a string
	 */
	private static String createRow(String col1, String col2) {
		return "<tr><td align=\"left\">" + col1 + "</td><td align=\"right\">" + col2 + "</td></tr>";
	}

	/**
	 * Creates an htmlrow with two columns from two strings where the elements are
	 * bold
	 * 
	 * @param col1
	 *            the string that should be added to the firstcolumn
	 * @param col2
	 *            the string that should be added to the secondcolumn
	 * @return an htmlrow as a string
	 */
	private static String createHeaderRow(String col1, String col2) {
		return "<tr><th align=\"left\">" + col1 + "</th><td align=\"right\">" + col2 + "</td></tr>";
	}
}