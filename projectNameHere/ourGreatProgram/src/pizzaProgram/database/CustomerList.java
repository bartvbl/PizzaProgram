package pizzaProgram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import pizzaProgram.dataObjects.Customer;

/**
 * Object for handling customers in the database. The methods of the class
 * handles creation of the {@link java.util.ArrayList ArrayList} and the
 * {@link java.util.HashMap HashMap} of all the different
 * {@link pizzaProgram.dataObject.Customer customers} based on a fetch from the
 * database, removal of existing customers from the database, as well as adding
 * new customers to the database. These lists are publically available through
 * the getter methods.
 * 
 * Most methods of this class depend on there being an active connection to the
 * database already (IE the
 * {@link pizzaProgram.database.DatabaseConnection#connect() connect()} method
 * of the {@link pizzaProgram.database.DatabaseConnection DatabaseConnection}
 * class must already have been run).
 * 
 * @author IT1901 Group 03, Fall 2011
 */

// TODO: Dispatch an event whenever the lists are updated

public class CustomerList {
	private final static ArrayList<Customer> customerList = new ArrayList<Customer>();
	private final static HashMap<Integer, Customer> customerMap = new HashMap<Integer, Customer>();

	/**
	 * This method clears the old lists, and repopulates the
	 * {@link java.util.ArrayList ArrayList} and {@link java.util.HashMap
	 * HashMap} of all the different {@link pizzaProgram.dataObject.Customer
	 * customers} based on a fetch from the database. This method must be rerun
	 * each time the Customer table of the database is modified.
	 */
	public static void updateCustomers() {
		if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
			System.err
					.println("No valid database connection; unable to update lists.");
			return;
		}
		customerList.clear();
		customerMap.clear();
		HashMap<Integer, String> customerCommentsMap = createCustomerCommentMap();
		ResultSet results = DatabaseConnection
				.fetchData("SELECT * FROM Customer;");
		try {
			while (results.next()) {
				Customer tempCustomer = new Customer(results.getInt(1),
						results.getString(2), results.getString(3),
						results.getString(4), results.getInt(5),
						results.getString(6), results.getInt(7),
						customerCommentsMap.get(results.getInt(8)));
				customerList.add(tempCustomer);
				customerMap.put(tempCustomer.customerID, tempCustomer);
			}
			results.close();
		} catch (SQLException e) {
			System.err
					.println("An error occured while updating the dish lists: "
							+ e.getMessage());
		}
	}

	/**
	 * Creates and returns the customer commentlist, which is needed when
	 * creating the customerlists.
	 */

	private static HashMap<Integer, String> createCustomerCommentMap() {
		HashMap<Integer, String> customerComments = new HashMap<Integer, String>();
		ResultSet results = DatabaseConnection
				.fetchData("SELECT * FROM CustomerNotes;");
		customerComments.put(-1, "");
		try {
			while (results.next()) {
				customerComments.put(results.getInt(1), results.getString(2));
			}
			results.close();
		} catch (SQLException e) {
			System.err.println("An error occured during your database query: "
					+ e.getMessage());
		}
		return customerComments;
	}

	public static ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public static HashMap<Integer, Customer> getCustomerMap() {
		return customerMap;
	}

	/**
	 * Method for adding a new customer to the database. The method checks if
	 * the exact combination of firstName+lastName+phoneNumber already exists in
	 * the database by creating a unique identifier, and does not add any new
	 * rows if such a combination already exists in the database.
	 * 
	 * @param firstName
	 *            - The first, and middle, name of the customer as a String no
	 *            longer than
	 *            {@link pizzaProgram.database.DatabaseConnection#VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param lastName
	 *            - The last name of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection#VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param address
	 *            - The address of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection#VARCHAR_MAX_LENGTH_LONG
	 *            VARCHAR_MAX_LENGTH_LONG}
	 * @param postalCode
	 *            - The postal code of the customer as an integer
	 * @param city
	 *            - The city of the customer, as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection#VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param phoneNumber
	 *            - The phone number of the customer as an integer
	 * @param comment
	 *            - Comments to this customer as a String
	 * @return returns true if the Customer was added successfully to the
	 *         database, returns false in all other cases.
	 */

	public static boolean addCustomer(String firstName, String lastName,
			String address, int postalCode, String city, int phoneNumber,
			String comment) {
		if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
			System.err
					.println("No valid database connection specified; unable to add customer.");
			return false;
		}
		if (firstName.length() > DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT
				|| lastName.length() > DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT
				|| address.length() > DatabaseConnection.VARCHAR_MAX_LENGTH_LONG
				|| city.length() > DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT) {
			throw new IllegalArgumentException(
					"Names and cities cannot contain more than "
							+ DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT
							+ " characters and addresses cannot contain more than "
							+ DatabaseConnection.VARCHAR_MAX_LENGTH_LONG
							+ " characters.");
		}
		/*
		 * Unique identifier composed from the first name, last name and phone
		 * number of the customer. It is not possible to create a new customer
		 * who has both the same first name, last name and phone number as
		 * another customer.
		 */
		String identifier = firstName.toLowerCase() + lastName.toLowerCase()
				+ phoneNumber;
		try {
			if (!DatabaseConnection.fetchData(
					"SELECT Identifier FROM Customer WHERE Identifier='"
							+ identifier + "';").next()) {
				int commentID = -1;
				if (!(comment == null || comment.equals(""))) {
					DatabaseConnection
							.insertIntoDB("INSERT INTO CustomerNotes (Note) VALUES ('"
									+ comment + "');");
					ResultSet commentIDset = DatabaseConnection
							.fetchData("SELECT NoteID FROM CustomerNotes WHERE Note='"
									+ comment + "';");
					if (commentIDset.next()) {
						commentID = commentIDset.getInt(1);
					}
				}
				DatabaseConnection
						.insertIntoDB("INSERT INTO Customer (FirstName, LastName, Address, PostalCode, City, TelephoneNumber, CommentID, Identifier) VALUES ('"
								+ firstName
								+ "', '"
								+ lastName
								+ "', '"
								+ address
								+ "', "
								+ postalCode
								+ ", '"
								+ city
								+ "', "
								+ phoneNumber
								+ ", "
								+ commentID
								+ ", '" + identifier + "');");
			}
		} catch (SQLException e) {
			System.err
					.println("An error occured when trying to add the customer to the database: "
							+ e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * Method for adding a new customer to the database if no comment is
	 * present. The method calls the full version of the method with the comment
	 * parameter set to null
	 * 
	 * @param firstName
	 *            - The first, and middle, name of the customer as a String no
	 *            longer than
	 *            {@link pizzaProgram.database.DatabaseConnection#VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param lastName
	 *            - The last name of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection#VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param address
	 *            - The address of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection#VARCHAR_MAX_LENGTH_LONG
	 *            VARCHAR_MAX_LENGTH_LONG}
	 * @param postalCode
	 *            - The postal code of the customer as an integer
	 * @param city
	 *            - The city of the customer, as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection#VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param phoneNumber
	 *            - The phone number of the customer as an integer
	 * @return returns true if the Customer was added successfully to the
	 *         database, returns false in all other cases.
	 */

	public static boolean addCustomer(String firstName, String lastName,
			String address, int postalCode, String city, int phoneNumber) {
		return addCustomer(firstName, lastName, address, postalCode, city,
				phoneNumber, null);
	}

	/**
	 * Method to remove a customer from the database
	 * 
	 * @param customer
	 *            the {@link pizzaProgram.dataObjects.Customer customer} to be
	 *            removed from the database
	 * @return returns true if the removal of the customer was a success,
	 *         returns false in all other cases.
	 */

	// TODO: Find out if we want to remove the comment as well, and if so remove
	// it in the method

	public static boolean removeCustomer(Customer customer) {
		if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
			System.err
					.println("No valid database connection specified; no customer removed from the database.");
			return false;
		}
		return DatabaseConnection
				.insertIntoDB("DELETE FROM Customer WHERE CustomerID="
						+ customer.customerID + ");");
	}
}