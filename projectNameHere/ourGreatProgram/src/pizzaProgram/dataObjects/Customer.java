package pizzaProgram.dataObjects;
/**
 * TODO: Insert Class Javadoc here [is that REALLY needed? what idiot would NOT understand what Customer.address means?]
 * @author IT1903 Gruppe 03
 *
 */
public class Customer {
	
	/**
	 * The customer ID (created by the database)
	 */
	public final int customerID;
	/**
	 * The first name of the customer
	 */
	public final String firstName;
	/**
	 * The last name of the customer
	 */
	public final String lastName;
	/**
	 * The customer's address
	 */
	public final String address;
	/**
	 * The customer's phone number
	 */
	public final int phoneNumber;
	/**
	 * Any additional comments about this customer stored in the database
	 */
	public final String comment;
	
	/**
	 * The constructor of Customer requires all fields to be defined. It takes in all the field's values, and stores them as read-only variables.
	 * @param customerID The database's internal ID for this customer.
	 * @param firstName The customer's first name
	 * @param lastName The customer's last name
	 * @param address The customer's address
	 * @param phoneNumber The customer's phone number
	 * @param comment Any comments about this customer
	 */
	public Customer(int customerID, String firstName, String lastName, String address, int phoneNumber, String comment) {
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.comment = comment;
	}
	
	/**
	 * Creates a readable string from the data stored in the object
	 */
	public String toString() {
		return customerID + " " + firstName + " " + lastName + " " + address + " " + phoneNumber + " " + comment;
	}
	
	
	
}//END