package pizzaProgram.dataObjects;

import java.util.ArrayList;

public class OrderDish {
	private int orderID;
	private Dish dish;
	private ArrayList<Extra> extras;

	public OrderDish(int orderID, Dish dish) {
		this.orderID = orderID;
		this.dish = dish;
		extras = new ArrayList<Extra>();
	}

	public int getOrderID() {
		return orderID;
	}

	public Dish getDish() {
		return dish;
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
		String tempString = "Dish Ordered = " + dish.getName() + ", Extras ordered = ";
		for (Extra e : extras){
			tempString += e.getName() + " ";
		}
		tempString += "\n";
		return tempString;
	}
}
