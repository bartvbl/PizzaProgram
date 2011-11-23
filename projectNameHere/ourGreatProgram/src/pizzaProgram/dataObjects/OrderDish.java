package pizzaProgram.dataObjects;

import java.util.ArrayList;


/**
 * Represents a dish from the database that is a part of an order
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class OrderDish {
	public final int orderID;
	public final Dish dish;
	private final ArrayList<Extra> extras;

	/**
	 * Creates a new OrderDish
	 * 
	 * @param orderID
	 *            The Id of the order this dish is linked to
	 * @param dish
	 *            The dish that is a part of the specified order
	 */
	public OrderDish(int orderID, Dish dish) {
		this.orderID = orderID;
		this.dish = dish;
		extras = new ArrayList<Extra>();
	}

	/**
	 * Returns a list of all extras that are a part of this order dish
	 * 
	 * @return A new ArrayList that contains instances of Extra, representing
	 *         Extras in this order dish
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<Extra> getExtras() {
		return (ArrayList<Extra>) extras.clone();
	}

	/**
	 * Adds an extra to this order dish
	 * 
	 * @param extra
	 *            The extra to be added
	 */
	public void addExtra(Extra extra) {
		if (extra != null) {
			extras.add(extra);
		}
	}

	public String toString() {
		String tempString = "Dish Ordered = " + dish.name + ", Extras ordered = ";
		for (Extra e : extras) {
			tempString += e.name + " ";
		}
		tempString += "\n";
		return tempString;
	}
	
	/**
	 * Compares this instance to another
	 * @param orderDish The OrderDish to compare this instance to
	 * @return true if the ID's of both objects match.
	 */
	public boolean equals(OrderDish orderDish)
	{
		if(orderDish == null)
		{
			return false;
		}
		return (this.dish.dishID == orderDish.dish.dishID) && (this.orderID == orderDish.orderID);
	}
}