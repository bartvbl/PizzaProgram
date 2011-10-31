package pizzaProgram.dataObjects;

import java.sql.Date;

public class UnaddedOrder extends Order {

	public UnaddedOrder(Customer customer, String deliveryMethod, String comment) {
		super(-1, customer, null, null, deliveryMethod, comment);
	}

}
