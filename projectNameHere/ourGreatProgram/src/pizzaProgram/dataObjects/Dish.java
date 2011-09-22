package pizzaProgram.dataObjects;


public class Dish {

	private int dishID;
	private int price;
	private String name;
	private boolean containsGluten;
	private boolean containsNuts;
	private boolean containsDiary;
	private boolean isVegetarian;
	private boolean isSpicy;
	private String description;

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

	public int getDishID() {
		return dishID;
	}

	public boolean isContainsNuts() {
		return containsNuts;
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
	
	public String toString(){
		return dishID + " " + price + " " + name + " " + containsGluten + " " + containsNuts + " " + containsDiary + " " + isVegetarian + " " + isSpicy + " " + description;
	}

}// END

