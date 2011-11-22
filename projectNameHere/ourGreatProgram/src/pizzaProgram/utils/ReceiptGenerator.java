package pizzaProgram.utils;

import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.gui.views.ReceiptWindow;
/**
 * Generates a receipt from a given Order instance, and shows it inside a window
 * @author Bart
 *
 */
public class ReceiptGenerator {
	
	private static int lastrows = 0;
	/**
	 * This method takes an order and creates an html-receipt form its data
	 * 
	 * @param order
	 */
	public static void generateReceiptAndWindow(Order order) {
		new ReceiptWindow(generateReceipt(order), lastrows);
	}
	public static String generateReceipt(Order order) {
		lastrows = 6;
		StringBuilder receiptString = new StringBuilder();

		receiptString.append("<html>");
		receiptString.append("<table width=\"" + GUIConstants.RECIPT_WIDTH + "\" border=\"0\">");
		receiptString.append("<tr><td align=\"center\"colspan=\"2\">-" + PriceCalculators.getRestaurantName() + "-</td></tr>");
		for (OrderDish d : order.orderedDishes) {
			lastrows++;
			String dishprice = order.deliveryMethod.equals(Order.DELIVER_AT_HOME) ? PriceCalculators.getPriceForDishDeliver(d.dish) : PriceCalculators.getPriceForDishPickup(d.dish);
			receiptString.append(createHeaderRow(d.dish.name, dishprice));
			
			for (Extra e : d.getExtras()) {
				String extraprice = order.deliveryMethod.equals(Order.DELIVER_AT_HOME) ? PriceCalculators.getPriceForExtraOnDishDeliver(d.dish, e) : PriceCalculators.getPriceForExtraOnDishPickup(d.dish, e);
				receiptString.append(createRow("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + e.name, extraprice));
				lastrows++;
			}
		}

		receiptString.append(createRow("", ""));
		receiptString.append(createRow("Alle retter", PriceCalculators.getPriceForOrderWithVAT(order)));
		receiptString.append(createRow("Herav MVA", PriceCalculators.getVATForOrder(order)));
		receiptString.append(createRow("Levering",PriceCalculators.getDeliveryCostForOrder(order)));
		receiptString.append(createHeaderRow("Totalt",PriceCalculators.getPriceForOrderWithVATAndDelivery(order)));
		receiptString.append("</table>");
		receiptString.append("</html>");
		return receiptString.toString();
	}

	/**
	 * creates an htmlrow with 2 columns from two strings
	 * 
	 * @param the
	 *            string that should be added to the firstcolumn
	 * @param the
	 *            string that should be added to the secondcolumn
	 * @return an htmlrow as a string
	 */
	private static StringBuilder createRow(String col1, String col2) {
		StringBuilder sb = new StringBuilder("<tr><td align=\"left\">" + col1
				+ "</td><td align=\"right\">" + col2 + "</td></tr>");
		return sb;
	}

	/**
	 * creates an htmlrow with 2 columns from two strings where the elemets are
	 * bold
	 * 
	 * @param the
	 *            string that should be added to the firstcolumn
	 * @param the
	 *            string that should be added to the secondcolumn
	 * @return an htmlrow as a string
	 */
	private static StringBuilder createHeaderRow(String col1, String col2) {
		StringBuilder sb = new StringBuilder("<tr><th align=\"left\">" + col1
				+ "</th><td align=\"right\">" + col2 + "</td></tr>");
		return sb;
	}

}// END
