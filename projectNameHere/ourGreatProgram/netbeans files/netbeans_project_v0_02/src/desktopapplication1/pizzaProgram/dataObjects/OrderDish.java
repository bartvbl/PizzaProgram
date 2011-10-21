package pizzaProgram.dataObjects;

import java.util.ArrayList;

public class OrderDish {
	public final int orderID;
	public final Dish dish;
	
	//TODO: this field needs a more proper way to operate. Returning the object upon request is improper publication.
	//ideally, you should pass in the finished ArrayList from the function that creates it, the only expose methods that allow the iterator and get() method to be retrieved.
	private final ArrayList<Extra> extras;

	public OrderDish(int orderID, Dish dish) {
		this.orderID = orderID;
		this.dish = dish;
		extras = new ArrayList<Extra>();
	}

	public ArrayList<Extra> getExtras() {
		return extras;
	}

	public void addExtra(Extra extra) {
		if (extra != null) {
			extras.add(extra);
		}
	}
	public String toString() {
		String tempString = "Dish Ordered = " + dish.name + ", Extras ordered = ";
		for (Extra e : extras){
			tempString += e.name + " ";
		}
		tempString += "\n";
		return tempString;
	}
}
