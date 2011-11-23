package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.constants.DatabaseMessages;
import pizzaProgram.constants.DatabaseQueryConstants;
import pizzaProgram.constants.GUIConstants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.database.DatabaseConnection;

/**
 * A class that is used to search for various data objects in the database, by
 * sending a specific String containing a search query
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class DatabaseSearcher {

	/**
	 * Searches the database and creates a list of Customer instances that match
	 * with the search query. It searches by first name, last name and phone
	 * number.
	 * 
	 * @param searchQuery
	 *            A search query consiting of one or more keywords separated by
	 *            spaces.
	 * @return An ArrayList of Customer instances that may be related/match the
	 *         entered keywords.
	 */
	public static ArrayList<Customer> searchCustomerByString(String searchQuery) {
		String query = DatabaseSearcher.generateCustomerSearchQuery(searchQuery);
		ResultSet results = DatabaseConnection.fetchData(query);
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		try {
			customerList = DatabaseDataObjectGenerator.generateCustomerList(results);
			return customerList;
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_FETCH_CUSTOMERS);
		}
		return null;
	}

	/**
	 * Returns a list of dishes in the database that are related to the keywords
	 * in the search query string, separated by spaces. It searches by price,
	 * name and description
	 * 
	 * @param searchQuery
	 *            A search query consiting of one or more keywords separated by
	 *            spaces.
	 * @return An ArrayList of Dish instances that may be related/match the
	 *         entered keywords.
	 */
	public static ArrayList<Dish> searchDishByString(String searchQuery) {
		String query = DatabaseSearcher.generateDishSearchQuery(searchQuery);
		ResultSet results = DatabaseConnection.fetchData(query);
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		try {
			dishList = DatabaseDataObjectGenerator.generateDishList(results);
			return dishList;
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_FETCH_DISHES);
		}
		return null;
	}

	/**
	 * Searches for extras that may be related to the entered search query in
	 * the database. Searches by price and name.
	 * 
	 * @param searchQuery
	 *            A search query consiting of one or more keywords separated by
	 *            spaces.
	 * @return An ArrayList of Extra instances that may be related/match the
	 *         entered keywords.
	 */
	public static ArrayList<Extra> searchExtraByString(String searchQuery) {
		String query = DatabaseSearcher.generateExtraSearchQuery(searchQuery);
		ResultSet results = DatabaseConnection.fetchData(query);
		ArrayList<Extra> extrasList = new ArrayList<Extra>();
		try {
			extrasList = DatabaseDataObjectGenerator.generateExtrasList(results);
			return extrasList;
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_FETCH_EXTRAS);
		}
		return null;
	}

	/**
	 * Searches in the database for orders that may be related to the entered
	 * keywords. Searches by delivery method, order ID and Order status
	 * 
	 * @param keywordString
	 *            A search query consiting of one or more keywords separated by
	 *            spaces.
	 * @param orderStatusStringList
	 *            The required order status('s) that the orders should have.
	 * @return An ArrayList of Order instances that may be related/match the
	 *         entered keywords.
	 */
	public static ArrayList<Order> getOrdersByKeywords(String keywordString, String[] orderStatusStringList) {
		String whereClause = DatabaseSearcher.generateOrderSearchWhereClause(keywordString,
				orderStatusStringList);
		String query = DatabaseReader.getOrderSelectionQuery(whereClause, "LIMIT 30");
		ResultSet results = DatabaseConnection.fetchData(query);
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			orderList = DatabaseDataObjectGenerator.generateOrderListFromResultSet(results);
			return orderList;
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_FETCH_ORDERS);
		}
		return null;
	}

	/**
	 * Generates a database query that can search for customers
	 * 
	 * @param searchQuery
	 *            A String containing keywords to search for, separated by
	 *            spaces.
	 * @return The search query that can be used to search the Customer table of
	 *         the database.
	 */
	private static String generateCustomerSearchQuery(String searchQuery) {
		String query = "SELECT * FROM Customer WHERE (";
		String[] keywords = searchQuery.split(" ");
		int counter = 0;
		for (String keyword : keywords) {
			if (counter != 0) {
				query += "OR";
			}
			query += "(" + DatabaseQueryConstants.CUSTOMER_FIRST_NAME + " LIKE '%" + keyword + "%') OR ";
			query += "(" + DatabaseQueryConstants.CUSTOMER_LAST_NAME + " LIKE '%" + keyword + "%') OR ";
			query += "(" + DatabaseQueryConstants.CUSTOMER_PHONE_NUMBER + " LIKE '%" + keyword + "%')";
			counter++;
		}
		query += ") LIMIT 30;";
		return query;
	}

	/**
	 * Generates a dish search query from the entered keywords, that can be used
	 * to search the database for dishes
	 * 
	 * @param searchQuery
	 *            A String containing keywords to search for, separated by
	 *            spaces.
	 * @return The search query that can be used to search the Dishes table of
	 *         the database.
	 */
	private static String generateDishSearchQuery(String searchQuery) {
		String query = "SELECT * FROM " + DatabaseQueryConstants.DISH_TABLE_NAME + " WHERE (";
		String[] keywords = searchQuery.split(" ");
		int counter = 0;
		for (String keyword : keywords) {
			if (counter != 0) {
				query += "OR";
			}
			query += "(" + DatabaseQueryConstants.DISH_PRICE + " LIKE '%" + keyword + "%') OR ";
			query += "(" + DatabaseQueryConstants.DISH_NAME + " LIKE '%" + keyword + "%') OR ";
			query += "(" + DatabaseQueryConstants.DISH_DESCRIPTION + " LIKE '%" + keyword + "%')";
			counter++;
		}
		query += ") LIMIT 30;";
		return query;
	}

	/**
	 * Generates a database query that will search for extras in the database
	 * from the given keyword input string
	 * 
	 * @param searchQuery
	 *            A String containing keywords to search for, separated by
	 *            spaces.
	 * @return The search query that can be used to search the Extras table of
	 *         the database.
	 */
	private static String generateExtraSearchQuery(String searchQuery) {
		String query = "SELECT * FROM " + DatabaseQueryConstants.EXTRAS_TABLE_NAME + " WHERE (";
		String[] keywords = searchQuery.split(" ");
		int counter = 0;
		for (String keyword : keywords) {
			if (counter != 0) {
				query += "OR";
			}
			query += "(" + DatabaseQueryConstants.EXTRAS_PRICE + " LIKE '%" + keyword + "%') OR ";
			query += "(" + DatabaseQueryConstants.EXTRAS_NAME + " LIKE '%" + keyword + "%')";
			counter++;
		}
		query += ") LIMIT 30;";
		return query;
	}

	/**
	 * Generates a String query that can be used to search the database for
	 * orders, specified by a search query, and a list of order status's that
	 * the orders should have
	 * 
	 * @param keywordString
	 *            A String containing keywords to search for, separated by
	 *            spaces.
	 * @param orderStatus
	 *            the status that the orders should have.
	 * @return The search query that can be used to search the Orders table of
	 *         the database.
	 */
	private static String generateOrderSearchWhereClause(String keywordString, String[] orderStatus) {
		String[] keywords = keywordString.split(" ");
		String whereClause = "(";
		int counter = 0;
		for (String status : orderStatus) {
			if (counter != 0) {
				whereClause += " OR ";
			}

			counter++;
			whereClause += "(" + DatabaseQueryConstants.ORDERS_STATUS + " = '" + status + "')";
		}

		whereClause += ") AND (";
		counter = 0;
		for (String keyword : keywords) {
			if (counter != 0) {
				whereClause += "OR ";
			}
			whereClause += "(" + DatabaseQueryConstants.ORDERS_STATUS + " LIKE '%" + keyword + "%') OR ";
			whereClause += "(" + DatabaseQueryConstants.ORDERS_DELIVERY_METHOD + " LIKE '%" + keyword
					+ "%') OR ";
			whereClause += "(" + DatabaseQueryConstants.ORDERS_ID + " LIKE '%" + keyword + "%') ";
			counter++;
		}
		whereClause += ")";
		return whereClause;
	}
}