package pizzaProgram.dataObjects;

/**
 * A data object that holds data about an Extra in the database
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class Extra {

	public final int id;
	public final int priceValPart;
	public final char priceFuncPart;
	public final String name;
	public final boolean isActive;

	/**
	 * Creates a new Extra instance
	 * 
	 * @param id
	 *            The ID of the extra
	 * @param name
	 *            The extra's name
	 * @param priceFunc
	 *            A char representing the function that should be applied on the
	 *            dish price (*, /, + or -)
	 * @param isActive
	 *            determines whether the Extra is in the restaurant's current
	 *            assortiment
	 */
	public Extra(int id, String name, String priceFunc, boolean isActive) {
		this.id = id;
		this.name = name;
		this.priceFuncPart = priceFunc.charAt(0);
		this.priceValPart = Integer.parseInt(priceFunc.substring(1));
		this.isActive = isActive;
	}

	/**
	 * Creates a String from the data in the object
	 */
	public String toString() {
		return id + " " + priceFuncPart + " " + priceValPart + " " + name;
	}
}