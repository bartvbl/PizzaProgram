package pizzaProgram.dataObjects;

/**
 * A data object that represents a customer that has not yet been added to the
 * database
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class UnaddedCustomer extends Customer {
	/**
	 * Creates a new unadded customer. It takes in all the data required to
	 * create the new customer in the database, and assumes default values
	 * elsewhere
	 * 
	 * @param firstName
	 *            The customer's first name
	 * @param lastName
	 *            The customers last name
	 * @param address
	 *            The customer's address
	 * @param postalCode
	 *            The customer's postal code
	 * @param city
	 *            The customer's city of residence
	 * @param phoneNumber
	 *            The customer's phone number
	 */
	public UnaddedCustomer(String firstName, String lastName, String address, String postalCode, String city,
			int phoneNumber) {
		super(-1, firstName, lastName, address, postalCode, city, phoneNumber);
	}
}