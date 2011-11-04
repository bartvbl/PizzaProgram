package pizzaProgram.utils;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Order;

public class ReceiptGenerator {

	public static String generateReceipt(Order order) {
		String receiptString = addHeader("", order);
		
		return receiptString;
	}

	private static String addHeader(String receiptString, Order order) {
		receiptString += "=== Pizza Restaurant 'Piza Di Papa' ===\n";
		receiptString += "Order reference: " + order.orderID + "\n";
		receiptString += "";
		return receiptString;
	}
	
}
