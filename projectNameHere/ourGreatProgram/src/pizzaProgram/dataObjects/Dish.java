package pizzaProgram.dataObjects;


/**
 * Class encapsulating all information about any given dish on the restaurants
 * menu. All fields in the object are final, and so cannot be changed after
 * construction. Since the fields are final, they are publically available.
 * 
 * @author IT1901 Group 3, Fall 2011
 */
public class Dish{

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

	/**
	 * The constructor requires all fields to be defined. It takes in all the
	 * field's values, and stores them as read-only variables.
	 * 
	 * @param dishID
	 *            The databases's internal ID for this Dish.
	 * @param price
	 *            The price of this Dish in norwegian øre (1/100 NOK)
	 * @param name
	 *            The name of this Dish
	 * @param containsGluten
	 *            Wether or not this Dish contains Gluten
	 * @param containsNuts
	 *            Wether or not this Dish contains Nuts
	 * @param containsDiary
	 *            Wether or not this Dish contains Dairy products
	 * @param isVegetarian
	 *            Wether or not this Dish is Vegetarian in nature
	 * @param isSpicy
	 *            Wether or not this Dish is Spicy
	 * @param description
	 *            A description of the Dish, for example its ingredients.
	 * @param isActive
	 *            Wether or not this Dish is on the restaurants current menu.
	 */

	public Dish(int dishID, int price, String name, boolean containsGluten, boolean containsNuts,
			boolean containsDiary, boolean isVegetarian, boolean isSpicy, String description, boolean isActive) {
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
	
	public boolean equals(Dish dish)
	{
		if(dish == null)
		{
			return false;
		}
		return this.dishID == dish.dishID;
	}
}