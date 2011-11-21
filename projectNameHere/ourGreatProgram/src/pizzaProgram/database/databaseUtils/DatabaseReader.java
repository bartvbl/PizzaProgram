package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.Setting;
import pizzaProgram.database.DatabaseConnection;

/**
 * A class that performs all read queries to the database
 * @author Bart
 *
 */
public class DatabaseReader {
	/**
	 * Retrieves a list of all customers from the database
	 * @return An arrayList containing Customer instances, ech representing a row in the database
	 */
	public static ArrayList<Customer> getAllCustomers(){
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Customer;");
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
	 * @param key The key representing the desired setting
	 * @return A Setting data object, representing the setting associated with the entered key
	 */
	public static Setting getSettingByKey(String key){
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Config WHERE ConfigKey='"+key+"';");
		try {
			results.next();
			Setting setting = DatabaseDataObjectGenerator.createSetting(results);
			return setting;
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente data fra databasen!");
		}
		return null;
	}
	/**
	 * Returns a list of all settings in the database
	 * @return An ARrayList containing Setting instances, each representing a setting in the database
	 */
	public static ArrayList<Setting> getAllSettings(){
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Config;");
		try {
			ArrayList<Setting> settings = DatabaseDataObjectGenerator.generateSettingsList(results);
			return settings;
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente data fra databasen!");
		}
		return null;
	}
	/**
	 * Returns a list of all the dishes that are currently in the assortiment of the restaurant
	 * @return An ArrayList containing Dish instances, each representing an active dish.
	 */
	public static ArrayList<Dish> getAllActiveDishes() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Dishes WHERE (isactive=1);");
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
	 * Retrieves a list of all dishes from the database, no matter if they are active or not
	 * @return An ArrayList containing DIsh instances, where every Dish object is a DIsh in the Dishes table in the database
	 */
	public static ArrayList<Dish> getAllDishes() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Dishes;");
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
	 * Retrieves a list of all extras that are currently in the restaurant's assortiment.
	 * @return An ArrayList of Extra instances, where every Extra instance is an active Extra.
	 */
	public static ArrayList<Extra> getAllActiveExtras() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Extras WHERE (isactive=1);");
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
	 * Retrieves a list of all Extras in the database. It does not distinguish whether extras are in the restaurant's assortiment or not
	 * @return An ArrayList containing Extra instances, where each Extra instance represents an Extra in the database
	 */
	public static ArrayList<Extra> getAllExtras() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Extras;");
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
	 * Retrieves a list of all orders that have either been registered or are being cooked from the database
	 * @return An ArrayList containing Order instances, where every Order instance is an order that has been registered, or is being cooked.
	 */
	public static ArrayList<Order> getAllUncookedOrders() {
		ResultSet result = DatabaseConnection.fetchData(getOrderSelectionQuery("Orders.OrdersStatus = '"+Order.REGISTERED+"' OR Orders.OrdersStatus = '"+Order.BEING_COOKED+"'", ""));
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
	 * Generates a query to select orders from the database, with a given where clause, and possible extra parameters.
	 * @param whereClause A string representing the where clause of the query. Usage: replace <String> by the where clause in: "WHERE (<String>)".
	 * @param extraOptions Any extra options to be placed at the end of the query. These chould be things like "LIMIT 20", or "ORDER BY Orders.OrderID", etc.
	 * @return A String representing a query that can be sent to the database to read and ocnstruct orders
	 */
	public static String getOrderSelectionQuery(String whereClause, String extraOptions){
		String query = "SELECT Customer.* , Dishes.* , Extras.* , OrderComments.* , Orders.*, OrdersContents.* FROM Orders " +
		"LEFT JOIN OrderComments ON ( Orders.CommentID = OrderComments.CommentID ) " +
		"INNER JOIN OrdersContents ON ( Orders.OrdersID = OrdersContents.OrdersID ) " +
		"INNER JOIN DishExtrasChosen ON ( OrdersContents.OrdersContentsID = DishExtrasChosen.OrdersContentsID ) " +
		"LEFT JOIN Customer ON ( Orders.CustomerID = Customer.CustomerID ) " +
		"LEFT JOIN Dishes ON ( OrdersContents.DishID = Dishes.DishID ) " +
		"LEFT JOIN Extras ON ( Extras.ExtrasID = DishExtrasChosen.DishExtraID ) " +
		"WHERE (" + whereClause + ") "+extraOptions+" ;"; 
		return query;
	}
	
	/**
	 * Returns a list of all orders that have been cooked, or are being delivered.
	 * @return An ArrayList containing Order instances, where each of the Order instances either has been cooked, or is being delivered.
	 */
	public static ArrayList<Order> getAllUndeliveredOrders() {
		ResultSet result = DatabaseConnection.fetchData(getOrderSelectionQuery("Orders.OrdersStatus = '"+Order.HAS_BEEN_COOKED+"' OR Orders.OrdersStatus = '"+Order.BEING_DELIVERED+"'", ""));
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			orderList = DatabaseDataObjectGenerator.generateOrderListFromResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
			GUIConstants.showErrorMessage("Kunne ikke hente ordre fra databasen!");
		}
		return orderList;
	}

}//END
