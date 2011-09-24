package pizzaProgram.dataObjects;


public class Dish {

	public final int dishID;
	public final int price;
	public final String name;
	public final boolean containsGluten;
	public final boolean containsNuts;
	public final boolean containsDiary;
	public final boolean isVegetarian;
	public final boolean isSpicy;
	public final String description;

	public Dish(int dishID, int price, String name, boolean containsGluten, boolean containsNuts,
			boolean containsDiary, boolean isVegetarian, boolean isSpicy,
			String description) {
		this.dishID = dishID;
		this.price = price;
		this.name = name;
		this.containsGluten = containsGluten;
		this.containsNuts = containsNuts;
		this.containsDiary = containsDiary;
		this.isVegetarian = isVegetarian;
		this.isSpicy = isSpicy;
		this.description = description;
	}
	
	public String toString(){
		return dishID + " " + price + " " + name + " " + containsGluten + " " + containsNuts + " " + containsDiary + " " + isVegetarian + " " + isSpicy + " " + description;
	}

}// END

