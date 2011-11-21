package pizzaProgram.utils;

import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.gui.views.ReceiptWindow;

public class ReceiptGenerator {
	
	private static int lastReciptRows = 0;
	/**
	 * This method takes an order and creates an html-receipt form its data
	 * 
	 * @param order
	 */
	public static void generateReceiptAndWindow(Order order) {
		new ReceiptWindow(generateReceipt(order), lastReciptRows);
		lastReciptRows = 0;
	}
	public static String generateReceipt(Order order) {
		lastReciptRows = 6;
		String receiptString = "";

		receiptString += "<html>";
		receiptString += "<table width=\"" + GUIConstants.RECIPT_WIDTH + "\" border=\"0\">";
		receiptString += "<tr><td align=\"center\"colspan=\"2\">-" + PriceCalculators.getRestaurantName() + "-</td></tr>";
		for (OrderDish d : order.orderedDishes) {
			
			String dishprice = order.deliveryMethod.equals(Order.DELIVER_AT_HOME) ? PriceCalculators.getPriceForDishDeliver(d.dish) : PriceCalculators.getPriceForDishPickup(d.dish);
			receiptString += createHeaderRow(d.dish.name, dishprice);
			
			lastReciptRows++;
			for (Extra e : d.getExtras()) {
				String extraprice = order.deliveryMethod.equals(Order.DELIVER_AT_HOME) ? PriceCalculators.getPriceForExtraOnDishDeliver(d.dish, e) : PriceCalculators.getPriceForExtraOnDishPickup(d.dish, e);
				receiptString += createRow("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + e.name, extraprice);
				lastReciptRows++;
			}
		}

		receiptString += createRow("", "");
		receiptString += createRow("Alle retter", PriceCalculators.getPriceForOrderWithVAT(order));
		receiptString += createRow("Herav MVA", PriceCalculators.getVATForOrder(order));
		receiptString += createRow("Levering",PriceCalculators.getDeliveryCostForOrder(order));
		receiptString += createHeaderRow("Totalt",PriceCalculators.getPriceForOrderWithVATAndDelivery(order));
		receiptString += "</table>";
		receiptString += "</html>";
		return receiptString;
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
	private static String createRow(String col1, String col2) {
		String s = "<tr><td align=\"left\">" + col1
				+ "</td><td align=\"right\">" + col2 + "</td></tr>";
		return s;
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
	private static String createHeaderRow(String col1, String col2) {
		String s = "<tr><th align=\"left\">" + col1
				+ "</th><td align=\"right\">" + col2 + "</td></tr>";
		return s;
	}

}// END
