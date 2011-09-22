package pizzaProgram.dataObjects;

import java.util.ArrayList;

public class OrderDish {
	private Dish dish;
	private ArrayList<Extra> extras;

	public OrderDish(Dish dish, ArrayList<Extra> extras) {
		this.dish = dish;
		this.extras = extras;
	}

	public Dish getDish() {
		return dish;
	}

	public ArrayList<Extra> getExtras() {
		return extras;
	}
}
