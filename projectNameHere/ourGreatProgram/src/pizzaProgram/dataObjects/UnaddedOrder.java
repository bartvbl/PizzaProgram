package pizzaProgram.dataObjects;

public class UnaddedOrder extends Order {

	public UnaddedOrder(Customer customer, String deliveryMethod, String comment) {
		super(-1, customer, null, null, deliveryMethod, comment);
	}

}
