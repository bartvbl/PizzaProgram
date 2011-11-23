package pizzaProgram.dataObjects;

/**
 * A class that represents an order that has not yet been added to the database
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class UnaddedOrder extends Order {
	/**
	 * Creates a new unadded order
	 * 
	 * @param customer
	 *            The customer that has ordered the order
	 * @param deliveryMethod
	 *            The delivery method of the order
	 * @param comment
	 *            Any comments regarding the order
	 */
	public UnaddedOrder(Customer customer, String deliveryMethod, String comment) {
		super(-1, customer, null, null, deliveryMethod, comment);
	}

}
