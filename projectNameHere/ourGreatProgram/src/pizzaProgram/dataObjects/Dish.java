package pizzaProgram.dataObjects;

/**
 * DERP
 */
public class Dish {

	public final int dishID;
	public final int price;
	public final String name;
	public final boolean containsGluten;
	public final boolean containsNuts;
	public final boolean containsDairy;
	public final boolean isVegetarian;
	public final boolean isSpicy;
	public final String description;
	public final boolean isActive;

	// TODO: add a list of all extras the dish can have to the dish object
	public Dish(int dishID, int price, String name, boolean containsGluten,
			boolean containsNuts, boolean containsDiary, boolean isVegetarian,
			boolean isSpicy, String description, boolean isActive) {
		this.dishID = dishID;
		this.price = price;
		this.name = name;
		this.containsGluten = containsGluten;
		this.containsNuts = containsNuts;
		this.containsDairy = containsDiary;
		this.isVegetarian = isVegetarian;
		this.isSpicy = isSpicy;
		this.description = description;
		this.isActive = isActive;
	}

	public String toString() {
		return dishID + " " + price + " " + name + " " + containsGluten + " "
				+ containsNuts + " " + containsDairy + " " + isVegetarian + " "
				+ isSpicy + " " + description;
	}

}// END

