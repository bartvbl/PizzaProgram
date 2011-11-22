package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.core.DatabaseQueryConstants;
import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.Setting;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.database.DatabaseConnection;

/**
 * A class that performs all read queries to the database
 * 
 * @author Bart
 * 
 */
public class DatabaseReader {
	/**
	 * Retrieves a list of all customers from the database
	 * 
	 * @return An arrayList containing Customer instances, ech representing a
	 *         row in the database
	 */
	public static ArrayList<Customer> getAllCustomers() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM "
				+ DatabaseQueryConstants.CUSTOMER_TABLE_NAME + ";");
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
	 * Retrieves the setting from the database given by the key entered
	 * 
	 * @param key
	 *            The key representing the desired setting
	 * @return A Setting data object, representing the setting associated with
	 *         the entered key
	 */
	public static Setting getSettingByKey(String key) {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM "
				+ DatabaseQueryConstants.CONFIG_TABLE_NAME + " WHERE " + DatabaseQueryConstants.CONFIG_KEY + "='" + key
				+ "';");
		try {
			results.next();
			Setting setting = DatabaseDataObjectGenerator.createSetting(results);
			return setting;
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente innstillinger fra databasen!");
		}
		return null;
	}

	/**
	 * Returns a list of all settings in the database
	 * 
	 * @return An ARrayList containing Setting instances, each representing a
	 *         setting in the database
	 */
	public static ArrayList<Setting> getAllSettings() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM "
				+ DatabaseQueryConstants.CONFIG_TABLE_NAME + ";");
		try {
			ArrayList<Setting> settings = DatabaseDataObjectGenerator.generateSettingsList(results);
			return settings;
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente innstillinger fra databasen!");
		}
		return null;
	}

	/**
	 * Returns a list of all the dishes that are currently in the assortiment of
	 * the restaurant
	 * 
	 * @return An ArrayList containing Dish instances, each representing an
	 *         active dish.
	 */
	public static ArrayList<Dish> getAllActiveDishes() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM " + DatabaseQueryConstants.DISH_TABLE_NAME
				+ " WHERE (" + DatabaseQueryConstants.DISH_IS_ACTIVE + "=1);");
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		try {
			dishList = DatabaseDataObjectGenerator.generateDishList(results);
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente retter fra databasen!");
		}
		return dishList;
	}

	/**
	 * Retrieves a list of all dishes from the database, no matter if they are
	 * active or not
	 * 
	 * @return An ArrayList containing DIsh instances, where every Dish object
	 *         is a Dish in the Dishes table in the database
	 */
	public static ArrayList<Dish> getAllDishes() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM " + DatabaseQueryConstants.DISH_TABLE_NAME
				+ ";");
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		try {
			dishList = DatabaseDataObjectGenerator.generateDishList(results);
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente retter fra databasen!");
		}
		return dishList;
	}

	/**
	 * Retrieves a list of all extras that are currently in the restaurant's
	 * assortiment.
	 * 
	 * @return An ArrayList of Extra instances, where every Extra instance is an
	 *         active Extra.
	 */
	public static ArrayList<Extra> getAllActiveExtras() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM "
				+ DatabaseQueryConstants.EXTRAS_TABLE_NAME + " WHERE (" + DatabaseQueryConstants.EXTRAS_IS_ACTIVE
				+ "=1);");
		ArrayList<Extra> dishList = new ArrayList<Extra>();
		try {
			dishList = DatabaseDataObjectGenerator.generateExtrasList(results);
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente tilbehør fra databasen!");
		}
		return dishList;
	}

	/**
	 * Retrieves a list of all Extras in the database. It does not distinguish
	 * whether extras are in the restaurant's assortiment or not
	 * 
	 * @return An ArrayList containing Extra instances, where each Extra
	 *         instance represents an Extra in the database
	 */
	public static ArrayList<Extra> getAllExtras() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM "
				+ DatabaseQueryConstants.EXTRAS_TABLE_NAME + ";");
		ArrayList<Extra> dishList = new ArrayList<Extra>();
		try {
			dishList = DatabaseDataObjectGenerator.generateExtrasList(results);
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente tilbehør fra databasen!");
		}
		return dishList;
	}

	/**
	 * Method to check wether or not the customer the provided order is being
	 * issued to already has an uncooked order
	 * 
	 * @return The orderID of the existing order if such an order exists, -1 if
	 *         not.
	 */
	public static int customerHasUncookedOrder(UnaddedOrder order) {
		ResultSet result = DatabaseConnection.fetchData(getOrderSelectionQuery(
				DatabaseQueryConstants.ORDERS_STATUS + " = '" + Order.REGISTERED + "' AND "
						+ DatabaseQueryConstants.ORDERS_TO_CUSTOMER_ID + " = " + order.customer.customerID, ""));
		try {
			if (!result.next()) {
				return -1;
			} else {
				return result.getInt(result.findColumn(DatabaseQueryConstants.ORDERS_ID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente ordre fra databasen!");
			return -1;
		}
	}

	/**
	 * Retrieves a list of all orders that have either been registered or are
	 * being cooked from the database
	 * 
	 * @return An ArrayList containing Order instances, where every Order
	 *         instance is an order that has been registered, or is being
	 *         cooked.
	 */
	public static ArrayList<Order> getAllUncookedOrders() {
		ResultSet result = DatabaseConnection.fetchData(getOrderSelectionQuery(
				DatabaseQueryConstants.ORDERS_STATUS + " = '" + Order.REGISTERED + "' OR "
						+ DatabaseQueryConstants.ORDERS_STATUS + " = '" + Order.BEING_COOKED + "'", ""));
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			orderList = DatabaseDataObjectGenerator.generateOrderListFromResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente ordre fra databasen!");
		}
		return orderList;
	}

	/**
	 * Retrieves a list of all orders that have been delivered from the database
	 * 
	 * @return An ArrayList containing Order instances, where every Order
	 *         instance is an order that has been delivered.
	 */
	public static ArrayList<Order> getAllDeliveredOrders() {
		ResultSet result = DatabaseConnection.fetchData(getOrderSelectionQuery(
				DatabaseQueryConstants.ORDERS_STATUS + " = '" + Order.DELIVERED + "'", ""));
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			orderList = DatabaseDataObjectGenerator.generateOrderListFromResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente ordre fra databasen!");
		}
		return orderList;
	}

	/**
	 * Generates a query to select orders from the database, with a given where
	 * clause, and possible extra parameters.
	 * 
	 * @param whereClause
	 *            A string representing the where clause of the query. Usage:
	 *            replace <String> by the where clause in: "WHERE (<String>)".
	 * @param extraOptions
	 *            Any extra options to be placed at the end of the query. These
	 *            chould be things like "LIMIT 20", or
	 *            "ORDER BY Orders.OrderID", etc.
	 * @return A String representing a query that can be sent to the database to
	 *         read and ocnstruct orders
	 */
	public static String getOrderSelectionQuery(String whereClause, String extraOptions) {
		String query = "SELECT " + DatabaseQueryConstants.CUSTOMER_ALL_COLS + ", "
				+ DatabaseQueryConstants.DISHES_ALL_COLS + ", " + DatabaseQueryConstants.EXTRAS_ALL_COLS + ", "
				+ DatabaseQueryConstants.ORDERS_COMMENT_ALL_COLS + ", " + DatabaseQueryConstants.ORDERS_ALL_COLS + ", "
				+ DatabaseQueryConstants.ORDERS_CONTENTS_ALL_COLS + ", "
				+ DatabaseQueryConstants.DISH_EXTRAS_CHOSEN_ALL_COLS + " FROM "
				+ DatabaseQueryConstants.ORDERS_TABLE_NAME + " LEFT JOIN "
				+ DatabaseQueryConstants.ORDERS_COMMENT_TABLE_NAME + " ON ( "
				+ DatabaseQueryConstants.ORDERS_TO_ORDERCOMMENT_ID + " = "
				+ DatabaseQueryConstants.ORDERS_COMMENT_TO_ORDER_ID + " ) " + "INNER JOIN "
				+ DatabaseQueryConstants.ORDERS_CONTENTS_TABLE_NAME + " ON ( " + DatabaseQueryConstants.ORDERS_ID
				+ " = " + DatabaseQueryConstants.ORDERS_CONTENTS_TO_ORDER_ID + " ) " + "LEFT JOIN "
				+ DatabaseQueryConstants.DISH_EXTRAS_CHOSEN_TABLE_NAME + " ON ( "
				+ DatabaseQueryConstants.ORDERS_CONTENTS_ID + " = "
				+ DatabaseQueryConstants.DISH_EXTRAS_TO_ORDERCONTENTS + " ) " + "LEFT JOIN "
				+ DatabaseQueryConstants.CUSTOMER_TABLE_NAME + " ON ( " + DatabaseQueryConstants.ORDERS_TO_CUSTOMER_ID
				+ " = " + DatabaseQueryConstants.CUSTOMER_ID + " ) " + "LEFT JOIN "
				+ DatabaseQueryConstants.DISH_TABLE_NAME + " ON ( " + DatabaseQueryConstants.ORDERS_CONTENTS_TO_DISH_ID
				+ " = " + DatabaseQueryConstants.DISH_ID + " ) " + "LEFT JOIN Extras ON ( "
				+ DatabaseQueryConstants.EXTRAS_ID + " = " + DatabaseQueryConstants.DISH_EXTRAS_CHOSEN_TO_EXTRAS_ID
				+ " ) " + "WHERE (" + whereClause + ") " + extraOptions + " ;";
		return query;
	}

	/**
	 * Returns a list of all orders that have been cooked, or are being
	 * delivered.
	 * 
	 * @return An ArrayList containing Order instances, where each of the Order
	 *         instances either has been cooked, or is being delivered.
	 */
	public static ArrayList<Order> getAllUndeliveredOrders() {
		ResultSet result = DatabaseConnection.fetchData(getOrderSelectionQuery("Orders.OrdersStatus = '"
				+ Order.HAS_BEEN_COOKED + "' OR Orders.OrdersStatus = '" + Order.BEING_DELIVERED + "'", ""));
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			orderList = DatabaseDataObjectGenerator.generateOrderListFromResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente ordre fra databasen!");
		}
		return orderList;
	}

}// END
