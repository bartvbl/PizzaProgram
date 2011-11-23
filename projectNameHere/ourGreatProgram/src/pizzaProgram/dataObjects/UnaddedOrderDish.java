package pizzaProgram.dataObjects;

/**
 * A data object representing a dish that has not yet been added to the database
 * 
 * @author @author IT1901 Group 3, Fall 2011
 * 
 */
public class UnaddedOrderDish extends OrderDish {
	/**
	 * Creates a new unadded order dish
	 * 
	 * @param dish
	 *            The dish that is going to be inserted into the database
	 */
	public UnaddedOrderDish(Dish dish) {
		super(-1, dish);
	}
}