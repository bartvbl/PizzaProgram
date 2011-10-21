package pizzaProgram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import pizzaProgram.dataObjects.Customer;

/**
 * Object for handling customers in the database. At construction the object
 * creates an {@link java.util.ArrayList ArrayList} and a
 * {@link java.util.HashMap HashMap} of all the different
 * {@link pizzaProgram.dataObject.Customer customers} based on a fetch from the
 * database; these lists are publically available through the getter methods.
 * For now it is suggested to discard this object any time a change
 * occurs to a customer in the database, and reconstruct it by a call to the 
 * constructor. The methods of the class handles removal of existing customers 
 * from the database, as well as adding new customers to the database.
 * 
 * @author IT1901 Group 03, Fall 2011
 */

//TODO: Dispatch an event whenever the lists are updated

public class CustomerList {
	private ArrayList<Customer> customerList;
	private HashMap<Integer, Customer> customerMap;
	
	/**
	 * Constructor that creates the list objects as specified in the class javadoc
	 * 
	 * @param dbCon - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @throws SQLException
	 */
	
	public CustomerList (DatabaseConnection dbCon) throws SQLException {
		customerList = new ArrayList<Customer>();
		customerMap = new HashMap<Integer, Customer>();
		
		if (!(dbCon != null && dbCon.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err.println("No active database connection: please try again!");
		}
		else {
			HashMap<Integer, String> customerCommentsMap = createCustomerCommentMap(dbCon);
			ResultSet results = dbCon.fetchData("SELECT * FROM Customer;");
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
		}	
	}
	
	/**
	 * Creates and returns the customer commentlist, which is needed when creating
	 * the lists
	 * @param dbCon - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @return a {@link java.util.HashMap HashMap} from commentID to comment
	 * @throws SQLException
	 */
	
	private HashMap<Integer, String> createCustomerCommentMap(DatabaseConnection dbCon) throws SQLException {
		HashMap<Integer, String> customerComments = new HashMap<Integer, String>();
		ResultSet results = dbCon.fetchData("SELECT * FROM CustomerNotes;");
		customerComments.put(-1, "");
		while (results.next()) {
			customerComments.put(results.getInt(1), results.getString(2));
		}
		results.close();
		return customerComments;
	}

	public ArrayList<Customer> getCustomerList() {
		return customerList;
	}

	public HashMap<Integer, Customer> getCustomerMap() {
		return customerMap;
	}
	
	/**
	 * Method for adding a new customer to the database. The method checks if
	 * the exact combination of firstName+lastName+phoneNumber already exists in
	 * the database by creating a unique identifier, and does not add any new rows 
	 * if such a combination already exists in the database.
	 * 
	 * @param dbCon - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database 
	 * @param firstName - The first, and middle, name of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param lastName - The last name of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param address - The address of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_LONG
	 *            VARCHAR_MAX_LENGTH_LONG}
	 * @param postalCode - The postal code of the customer as an integer
	 * @param city - The city of the customer, as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param phoneNumber - The phone number of the customer as an integer
	 * @param comment - Comments to this customer as a String
	 * @return returns true if the Customer was added successfully to the
	 *         database, returns false in all other cases.
	 * @throws SQLException
	 */
	
	public boolean addCustomer(DatabaseConnection dbCon, String firstName, String lastName,
		String address, int postalCode, String city, int phoneNumber,
		String comment) throws SQLException {
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
		String identifier = firstName.toLowerCase() + lastName.toLowerCase() + phoneNumber;
		if (!dbCon.fetchData("SELECT Identifier FROM Customer WHERE Identifier='" + identifier + "';").next()) {
			int commentID = -1;
			if (!(comment == null || comment.equals(""))) {
				dbCon.insertIntoDB("INSERT INTO CustomerNotes (Note) VALUES ('"	+ comment + "');");
				ResultSet commentIDset = dbCon.fetchData("SELECT NoteID FROM CustomerNotes WHERE Note='" + comment + "';");
				if (commentIDset.next()) {
					commentID = commentIDset.getInt(1);
				}
			}
			return dbCon.insertIntoDB(
				"INSERT INTO Customer (FirstName, LastName, Address, PostalCode, City, TelephoneNumber, CommentID, Identifier) VALUES ('"
					+ firstName	+ "', '" + lastName	+ "', '" + address + "', " + postalCode + ", '" + city + "', " + phoneNumber
						+ ", " + commentID + ", '" + identifier + "');");
		}
		return false;
	}
	
	/**
	 * Method for adding a new customer to the database if no comment is present.
	 * The method calls the full version of the method with the comment parameter
	 * set to null
	 * 
	 * @param dbCon - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database 
	 * @param firstName - The first, and middle, name of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param lastName - The last name of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param address - The address of the customer as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_LONG
	 *            VARCHAR_MAX_LENGTH_LONG}
	 * @param postalCode - The postal code of the customer as an integer
	 * @param city - The city of the customer, as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_SHORT
	 *            VARCHAR_MAX_LENGTH_SHORT}
	 * @param phoneNumber - The phone number of the customer as an integer
	 * @return returns true if the Customer was added successfully to the
	 *         database, returns false in all other cases.
	 * @throws SQLException
	 */
	
	public boolean addCustomer(DatabaseConnection dbCon, String firstName, String lastName,
			String address, int postalCode, String city, int phoneNumber)
			throws SQLException {
		return addCustomer(dbCon, firstName, lastName, address, postalCode, city,
				phoneNumber, null);
	}
	
	/**
	 * Method to remove a dish from the database
	 * 
	 * @param dbCon
	 *            the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @param customer
	 *            the {@link pizzaProgram.dataObjects.Customer customer} to be removed
	 *            from the database
	 * @return returns true if the removal of the customer was a success, returns
	 *         false in all other cases.
	 */
	
	//TODO: Find out if we want to remove the comment as well, and if so remove it in the method
	
	public boolean removeCustomer(DatabaseConnection dbCon, Customer customer) {
		if (!(dbCon != null && dbCon.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err.println("No valid database connection specified; no customer removed from the database.");
			return false;
		}
		return dbCon.insertIntoDB("DELETE FROM Customer WHERE CustomerID=" + customer.customerID + ");");
	}
}