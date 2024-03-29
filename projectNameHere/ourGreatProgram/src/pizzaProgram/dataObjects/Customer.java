package pizzaProgram.dataObjects;

/**
 * A data object that holds information about a customer in the database
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class Customer{

	public final int customerID;
	public final String firstName;
	public final String lastName;
	public final String address;
	public final String postalCode;
	public final String city;
	public final int phoneNumber;

	/**
	 * The constructor of Customer requires all fields to be defined. It takes
	 * in all the field's values, and stores them as read-only variables.
	 * 
	 * @param customerID
	 *            The database's internal ID for this customer.
	 * @param firstName
	 *            The customer's first name
	 * @param lastName
	 *            The customer's last name
	 * @param address
	 *            The customer's address
	 * @param postalCode
	 *            The customer's postal code
	 * @param city
	 *            The customer's city
	 * @param phoneNumber
	 *            The customer's phone number
	 */
	public Customer(int customerID, String firstName, String lastName, String address, String postalCode,
			String city, int phoneNumber) {
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.postalCode = postalCode;
		this.city = city;
		this.phoneNumber = phoneNumber;
	}

	/**
	 * Compares this instance to another
	 * @param customer The customer to compare this instance to
	 * @return true if the ID's of both objects match.
	 */
	public boolean equals(Customer customer)
	{
		if(customer == null)
		{
			return false;
		}
		return this.customerID == customer.customerID;
	}
}