package pizzaProgram.dataObjects;

import java.util.ArrayList;

public class OrderDish {
	public final int orderID;
	public final Dish dish;
	private final ArrayList<Extra> extras;

	public OrderDish(int orderID, Dish dish) {
		this.orderID = orderID;
		this.dish = dish;
		extras = new ArrayList<Extra>();
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Extra> getExtras() {
		return (ArrayList<Extra>)extras.clone();
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
