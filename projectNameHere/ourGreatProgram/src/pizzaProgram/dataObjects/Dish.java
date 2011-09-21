package pizzaProgram.dataObjects;

import java.util.ArrayList;

public class Dish {

	private int price;
	private String name;
	private boolean containsGluten;
	private boolean containsDiary;
	private boolean isVegetarian;
	private boolean isSpicy;
	private String description;
	private ArrayList<Extra> extras;
	
	public Dish(int price, String name, boolean containsGluten, boolean containsDiary, boolean isVegetarian, boolean isSpicy, String description, ArrayList<Extra> extras) {
		this.price = price;
		this.name = name;
		this.containsGluten = containsGluten;
		this.containsDiary = containsDiary;
		this.isVegetarian = isVegetarian;
		this.isSpicy = isSpicy;
		this.description = description;
		this.extras = extras;
	}

	public int getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public boolean isContainsGluten() {
		return containsGluten;
	}

	public boolean isContainsDiary() {
		return containsDiary;
	}

	public boolean isVegetarian() {
		return isVegetarian;
	}

	public boolean isSpicy() {
		return isSpicy;
	}

	public String getDescription() {
		return description;
	}

	public ArrayList<Extra> getExtras() {
		return extras;
	}
	
}//END

