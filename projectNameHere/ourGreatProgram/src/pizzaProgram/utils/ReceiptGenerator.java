package pizzaProgram.utils;

import java.text.DecimalFormat;

import pizzaProgram.core.Constants;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.gui.views.ReceiptWindow;

public class ReceiptGenerator {

	/**
	 * This method takes an order and creates an html-receipt form its data
	 * @param order
	 */
	public static void generateReceiptAndWindow(Order order) {
		int rows = 6;
		String receiptString = "";
		double totalpris = 0;

		receiptString += "<html>";
		receiptString += "<table width=\"" + Constants.RECIPT_WIDTH + "\" border=\"0\">";
		receiptString += "<tr><td align=\"center\"colspan=\"2\">=====" + Constants.RESTAURANT_NAME + "=====</td></tr>";
		for (OrderDish d : order.orderedDishes) {
			receiptString += createHeaderRow(d.dish.name, formatPrice(d.dish.price));
			totalpris += d.dish.price;
			rows++;
			for (Extra e : d.getExtras()) {
				receiptString += createRow( "   +   " + e.name, formatPrice(calculateExtraCost(e, d.dish)));
				totalpris += calculateExtraCost(e, d.dish);
				rows++;
			}
		}

		receiptString += createRow("", "");
		receiptString += createRow("Alle retter", formatPrice(totalpris));
		receiptString += createRow("Herav MVA", formatPrice(totalpris*  (order.deliveryMethod == Order.DELIVER_AT_HOME ? Constants.DELIVER_MOMS : Constants.PICKUP_MOMS) ));
		int leveringskostnad = (((int)totalpris > Constants.FREE_DELIVERY_TRESHOLD) ? 0 : Constants.DELIVERY_COST);
		receiptString += createRow("Levering", formatPrice(leveringskostnad));
		receiptString += createHeaderRow("Totalt", formatPrice(totalpris + leveringskostnad));
		receiptString += "</table>";
		receiptString += "</html>";


		new ReceiptWindow(receiptString, rows);
	}
	private static String createRow(String col1, String col2){
		String s ="<tr><td align=\"left\">" + col1 + "</td><td align=\"right\">" + col2 + "</td></tr>";
		return s;
	}
	private static String createHeaderRow(String col1, String col2){
		String s ="<tr><th align=\"left\">" + col1 + "</th><td align=\"right\">" + col2 + "</td></tr>";
		return s;
	}

	private static String formatPrice(double price){
		DecimalFormat formatter = new DecimalFormat("##0.00");
		return formatter.format(price);
	}

	private static double calculateExtraCost(Extra e, Dish d) {
		double price;
		if(e.priceFuncPart == '*'){
			price = d.price * (e.priceValPart - 1);
		}else if(e.priceFuncPart == '-'){
			price = -e.priceValPart;
		}else{
			price = e.priceValPart;
		}
		return price;
	}

}