package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.core.DatabaseConstants;
import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.database.DatabaseConnection;

/**
 * A class that is used to search for various data objects in the database, by
 * sending a specific String containing a search query
 * 
 * @author Bart
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
	 * @return An ArrayList containing customers that may be related to the
	 *         search query
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
			GUIConstants.showErrorMessage("Kunne ikke hente kunder fra databasen!");
		}
		return null;
	}

	/**
	 * Returns a list of dishes in the database that are related to the keywords
	 * in the search query string, separated by spaces. It searches by price,
	 * name and description
	 * 
	 * @param searchQuery
	 *            A search query of keywords to look for, separated by spaces
	 * @return A list of Dish instances that may be related/match the entered
	 *         keywords
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
			GUIConstants.showErrorMessage("Kunne ikke hente retter fra databasen!");
		}
		return null;
	}

	/**
	 * Searches for extras that may be related to the entered search query in
	 * the database. Searches by price and name.
	 * 
	 * @param searchQuery
	 *            the search query to search for. The keywords should be
	 *            separated by spaces
	 * @return An ArrayList that may match the search query entered.
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
			GUIConstants.showErrorMessage("Kunne ikke hente tilbehør fra databasen!");
		}
		return null;
	}

	/**
	 * Searches in the database for orders that may be related to the entered
	 * keywords. Searches by delivery method, order ID and Order status
	 * 
	 * @param keywordString
	 *            A string containing keywords to search for, separated by
	 *            spaces
	 * @param orderStatusStringList
	 *            The required order status('s) that the orders should have.
	 * @return An ArrayList of Order instances that match the keywords entered
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
			GUIConstants.showErrorMessage("Kunne ikke hente ordre fra databasen!");
		}
		return null;
	}

	/**
	 * Generates a database query that can search for customers
	 * 
	 * @param searchQuery
	 *            A String containing keywords to search for, separated by
	 *            spaces
	 * @return The search query that can be used to search the database
	 */
	private static String generateCustomerSearchQuery(String searchQuery) {
		String query = "SELECT * FROM Customer WHERE (";
		String[] keywords = searchQuery.split(" ");
		int counter = 0;
		for (String keyword : keywords) {
			if (counter != 0) {
				query += "OR";
			}
			query += "(" + DatabaseConstants.CUSTOMER_FIRST_NAME + " LIKE '%" + keyword + "%') OR ";
			query += "(" + DatabaseConstants.CUSTOMER_LAST_NAME + " LIKE '%" + keyword + "%') OR ";
			query += "(" + DatabaseConstants.CUSTOMER_PHONE_NUMBER + " LIKE '%" + keyword + "%')";
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
	 *            A search query containing keywords separated by spaces
	 * @return A query that will result in dishes that match the entered
	 *         keywords
	 */
	private static String generateDishSearchQuery(String searchQuery) {
		String query = "SELECT * FROM Dishes WHERE (";
		String[] keywords = searchQuery.split(" ");
		int counter = 0;
		for (String keyword : keywords) {
			if (counter != 0) {
				query += "OR";
			}
			query += "(Dishes.Price LIKE '%" + keyword + "%') OR ";
			query += "(Dishes.Name LIKE '%" + keyword + "%') OR ";
			query += "(Dishes.Description LIKE '%" + keyword + "%')";
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
	 *            A search query string, containing keywords separated by
	 *            spaces.
	 * @return An query that can be used to search for extras in the database
	 */
	private static String generateExtraSearchQuery(String searchQuery) {
		String query = "SELECT * FROM Extras WHERE (";
		String[] keywords = searchQuery.split(" ");
		int counter = 0;
		for (String keyword : keywords) {
			if (counter != 0) {
				query += "OR";
			}
			query += "(Extras.Price LIKE '%" + keyword + "%') OR ";
			query += "(Extras.Name LIKE '%" + keyword + "%')";
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
	 *            A string of keywords to search for, separated by spaces
	 * @param orderStatus
	 *            the status that the orders should have
	 * @return A String the contains a search query that can be used to search
	 *         for orders
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
			whereClause += "(Orders.OrdersStatus = '" + status + "')";
		}

		whereClause += ") AND (";
		counter = 0;
		for (String keyword : keywords) {
			if (counter != 0) {
				whereClause += "OR ";
			}
			whereClause += "(Orders.OrdersStatus LIKE '%" + keyword + "%') OR ";
			whereClause += "(Orders.DeliveryMethod LIKE '%" + keyword + "%') OR ";
			whereClause += "(Orders.OrdersID LIKE '%" + keyword + "%') ";
			counter++;
		}
		whereClause += ")";
		return whereClause;
	}

}
