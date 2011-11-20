package pizzaProgram.utils;

import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.gui.views.ReceiptWindow;

public class ReceiptGenerator {

	/**
	 * This method takes an order and creates an html-receipt form its data
	 * 
	 * @param order
	 */
	public static void generateReceiptAndWindow(Order order) {
		int rows = 6;
		String receiptString = "";

		receiptString += "<html>";
		receiptString += "<table width=\"" + GUIConstants.RECIPT_WIDTH
				+ "\" border=\"0\">";
		receiptString += "<tr><td align=\"center\"colspan=\"2\">====="
				+ PriceCalculators.getRestaurantName() + "=====</td></tr>";
		for (OrderDish d : order.orderedDishes) {
			receiptString += createHeaderRow(d.dish.name,
					PriceCalculators.getPriceForDish(d.dish));
			rows++;
			for (Extra e : d.getExtras()) {
				receiptString += createRow("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + e.name,
						PriceCalculators.getPriceForExtraOnDish(d.dish, e));
				rows++;
			}
		}

		receiptString += createRow("", "");
		receiptString += createRow("Alle retter",
				PriceCalculators.getPriceForOrderWithoutVAT(order));
		receiptString += createRow("MVA",
				PriceCalculators.getVATForOrder(order));
		receiptString += createRow("Levering",
				PriceCalculators.getDeliveryCostForOrder(order));
		receiptString += createHeaderRow("Totalt",
				PriceCalculators.getPriceForOrderWithVATAndDelivery(order));
		receiptString += "</table>";
		receiptString += "</html>";

		new ReceiptWindow(receiptString, rows);
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
