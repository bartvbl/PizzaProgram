package pizzaProgram.dataObjects;
/**
 * A data object representing a dish that has not yet been added to the database
 * @author Bart
 *
 */
public class UnaddedOrderDish extends OrderDish {
	/**
	 * Creates a new unadded order dish
	 * @param dish The dish that is going to be inserted into the database
	 */
	public UnaddedOrderDish(Dish dish) {
		super(-1, dish);
	}

}
