package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import pizzaProgram.constants.DatabaseQueryConstants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.dataObjects.Setting;

/**
 * Class to generate dataObjects based on {@link java.sql.ResultSet ResultSets}
 * sent to the methods of the class. All methods in the class are static.
 * 
 * @author IT1901 Group 3, Fall 2011
 */
public class DatabaseDataObjectGenerator {
	/**
	 * Method to populate an ArrayList with
	 * {@link pizzaProgram.dataObjects.Dish dishes} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param results
	 *            a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Dishes table of the database
	 * @return The populated ArrayList of {@link pizzaProgram.dataObjects.Dish
	 *         dishes}
	 * @throws SQLException
	 *             if a database access error occurs or this method is called on
	 *             a closed result set
	 */
	public static ArrayList<Dish> generateDishList(ResultSet results) throws SQLException {
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		Dish currentDish;
		while (results.next()) {
			currentDish = DatabaseDataObjectGenerator.createDish(results);
			dishList.add(currentDish);
		}
		return dishList;
	}

	/**
	 * Method to populate an ArrayList with
	 * {@link pizzaProgram.dataObjects.Customer customers} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param results
	 *            a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Customers table of the database
	 * @return The populated ArrayList of
	 *         {@link pizzaProgram.dataObjects.Customer customers}
	 * @throws SQLException
	 *             if a database access error occurs or this method is called on
	 *             a closed result set
	 */
	public static ArrayList<Customer> generateCustomerList(ResultSet results) throws SQLException {
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		Customer currentCustomer;
		while (results.next()) {
			currentCustomer = DatabaseDataObjectGenerator.createCustomer(results);
			customerList.add(currentCustomer);
		}
		return customerList;
	}

	/**
	 * Method to populate an ArrayList with
	 * {@link pizzaProgram.dataObjects.Extra extras} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param results
	 *            a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Extras table of the database
	 * @return The populated ArrayList of {@link pizzaProgram.dataObjects.Extra
	 *         extras}
	 * @throws SQLException
	 *             if a database access error occurs or this method is called on
	 *             a closed result set
	 */
	public static ArrayList<Extra> generateExtrasList(ResultSet results) throws SQLException {
		ArrayList<Extra> extrasList = new ArrayList<Extra>();
		Extra currentExtra;
		while (results.next()) {
			currentExtra = DatabaseDataObjectGenerator.createExtra(results);
			extrasList.add(currentExtra);
		}
		return extrasList;
	}

	/**
	 * Method to populate an ArrayList with
	 * {@link pizzaProgram.dataObjects.Setting settings} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param results
	 *            a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Config table of the database
	 * @return The populated ArrayList of
	 *         {@link pizzaProgram.dataObjects.Setting settings}
	 * @throws SQLException
	 *             if a database access error occurs or this method is called on
	 *             a closed result set
	 */
	public static ArrayList<Setting> generateSettingsList(ResultSet results) throws SQLException {
		ArrayList<Setting> settingsList = new ArrayList<Setting>();
		Setting currentSetting;
		while (results.next()) {
			currentSetting = DatabaseDataObjectGenerator.createSetting(results);
			settingsList.add(currentSetting);
		}
		return settingsList;
	}

	/**
	 * Method to populate an ArrayList with
	 * {@link pizzaProgram.dataObjects.Order orders} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param result
	 *            a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Customer, Dishes, Extras, OrdersContents, OrderComments
	 *            and Orders table of the database
	 * @return The populated ArrayList of {@link pizzaProgram.dataObjects.Order
	 *         orders}
	 * @throws SQLException
	 *             if a database access error occurs or this method is called on
	 *             a closed result set
	 */
	public static ArrayList<Order> generateOrderListFromResultSet(ResultSet result) throws SQLException {
		ArrayList<Order> orderList = new ArrayList<Order>();
		HashMap<Integer, Dish> dishMap = new HashMap<Integer, Dish>();
		HashMap<Integer, Customer> customerMap = new HashMap<Integer, Customer>();
		HashMap<Integer, Extra> extraMap = new HashMap<Integer, Extra>();
		HashMap<Integer, Order> orderMap = new HashMap<Integer, Order>();
		HashMap<Integer, OrderDish> orderDishMap = new HashMap<Integer, OrderDish>();
		Order currentOrder = null;
		OrderDish currentOrderDish = null;
		Customer currentCustomer = null;
		Dish currentDish = null;
		Extra currentExtra = null;
		int currentOrderID = -1;
		int currentOrderContentID = -1;
		int currentCustomerID = -1;
		int currentDishID = -1;
		int currentExtraID = -1;
		while (result.next()) {
			currentOrderID = result.getInt(result.findColumn(DatabaseQueryConstants.ORDERS_ID));
			if (orderMap.get(currentOrderID) == null) {
				currentCustomerID = result.getInt(result.findColumn(DatabaseQueryConstants.CUSTOMER_ID));
				if (customerMap.get(currentCustomerID) == null) {
					customerMap.put(currentCustomerID, DatabaseDataObjectGenerator.createCustomer(result));
				}
				currentCustomer = customerMap.get(currentCustomerID);
				currentOrder = DatabaseDataObjectGenerator.createOrder(result, currentCustomer);
				orderList.add(currentOrder);
				orderMap.put(currentOrderID, currentOrder);
			}
			currentOrder = orderMap.get(currentOrderID);
			currentOrderContentID = result.getInt(result
					.findColumn(DatabaseQueryConstants.ORDERS_CONTENTS_ID));
			if (currentOrder != null && orderDishMap.get(currentOrderContentID) == null) {
				currentDishID = result.getInt(result.findColumn(DatabaseQueryConstants.DISH_ID));
				if (dishMap.get(currentDishID) == null) {
					dishMap.put(currentDishID, DatabaseDataObjectGenerator.createDish(result));
				}
				currentDish = dishMap.get(currentDishID);
				currentOrderDish = new OrderDish(currentOrder.orderID, currentDish);
				currentOrder.addOrderDish(currentOrderDish);
				orderDishMap.put(currentOrderContentID, currentOrderDish);
			}
			currentOrderDish = orderDishMap.get(currentOrderContentID);
			currentExtraID = result.getInt(result.findColumn(DatabaseQueryConstants.EXTRAS_ID));
			if (currentExtraID != 0) {
				if (extraMap.get(currentExtraID) == null) {
					extraMap.put(currentExtraID, DatabaseDataObjectGenerator.createExtra(result));
				}
				currentExtra = extraMap.get(currentExtraID);
				if (currentOrderDish != null) {
					currentOrderDish.addExtra(currentExtra);
				}
			}
		}
		return orderList;
	}

	/**
	 * Creates a Customer from the ResultSet inserted, given that the ResultSet
	 * contains a row from the Customers table.
	 * 
	 * @param resultSet
	 *            The ResultSet containing columns from the Customer table
	 * @return a Customer data object
	 * @throws SQLException
	 *             any error raised while reading data from the database
	 */
	private static Customer createCustomer(ResultSet resultSet) throws SQLException {
		int customerID = resultSet.getInt(resultSet.findColumn(DatabaseQueryConstants.CUSTOMER_ID));
		String firstName = resultSet.getString(resultSet
				.findColumn(DatabaseQueryConstants.CUSTOMER_FIRST_NAME));
		String lastName = resultSet
				.getString(resultSet.findColumn(DatabaseQueryConstants.CUSTOMER_LAST_NAME));
		String address = resultSet.getString(resultSet.findColumn(DatabaseQueryConstants.CUSTOMER_ADDRESS));
		String postalCode = resultSet.getString(resultSet
				.findColumn(DatabaseQueryConstants.CUSTOMER_POSTAL_CODE));
		String city = resultSet.getString(resultSet.findColumn(DatabaseQueryConstants.CUSTOMER_CITY));
		int phoneNumber = resultSet
				.getInt(resultSet.findColumn(DatabaseQueryConstants.CUSTOMER_PHONE_NUMBER));
		Customer customer = new Customer(customerID, firstName, lastName, address, postalCode, city,
				phoneNumber);
		return customer;
	}

	/**
	 * Creates a Dish data object from a given resultSet
	 * 
	 * @param resultSet
	 *            The ResultSet, containing all columns from the Dishes table,
	 *            from which the Dish object should be created
	 * @return The resulting Dish instance from the current row in the ResultSet
	 * @throws SQLException
	 *             Any error raised while reading the data from the ResultSet
	 */
	private static Dish createDish(ResultSet resultSet) throws SQLException {
		int dishID = resultSet.getInt(resultSet.findColumn(DatabaseQueryConstants.DISH_ID));
		int price = resultSet.getInt(resultSet.findColumn(DatabaseQueryConstants.DISH_PRICE));
		String name = resultSet.getString(resultSet.findColumn(DatabaseQueryConstants.DISH_NAME));
		boolean containsGluten = resultSet.getBoolean(resultSet
				.findColumn(DatabaseQueryConstants.DISH_CONTAINS_GLUTEN));
		boolean containsNuts = resultSet.getBoolean(resultSet
				.findColumn(DatabaseQueryConstants.DISH_CONTAINS_NUTS));
		boolean containsDairy = resultSet.getBoolean(resultSet
				.findColumn(DatabaseQueryConstants.DISH_CONTAINS_DAIRY));
		boolean isVegetarian = resultSet.getBoolean(resultSet
				.findColumn(DatabaseQueryConstants.DISH_IS_VEGETARIAN));
		boolean isSpicy = resultSet.getBoolean(resultSet.findColumn(DatabaseQueryConstants.DISH_IS_SPICY));
		String description = resultSet.getString(resultSet
				.findColumn(DatabaseQueryConstants.DISH_DESCRIPTION));
		boolean isActive = resultSet.getBoolean(resultSet.findColumn(DatabaseQueryConstants.DISH_IS_ACTIVE));
		Dish dish = new Dish(dishID, price, name, containsGluten, containsNuts, containsDairy, isVegetarian,
				isSpicy, description, isActive);
		return dish;
	}

	/**
	 * Creates an Extra object from a given ResultSet
	 * 
	 * @param resultSet
	 *            A ResultSet instance containing the Extras table
	 * @return An Extra instance containing the Extra that was at the current
	 *         row of the database
	 * @throws SQLException
	 *             Any error raised while reading the data from the ResultSet
	 */
	private static Extra createExtra(ResultSet resultSet) throws SQLException {
		int extrasID = resultSet.getInt(resultSet.findColumn(DatabaseQueryConstants.EXTRAS_ID));
		String name = resultSet.getString(resultSet.findColumn(DatabaseQueryConstants.EXTRAS_NAME));
		String price = resultSet.getString(resultSet.findColumn(DatabaseQueryConstants.EXTRAS_PRICE));
		boolean isActive = resultSet
				.getBoolean(resultSet.findColumn(DatabaseQueryConstants.EXTRAS_IS_ACTIVE));
		Extra extra = new Extra(extrasID, name, price, isActive);
		return extra;
	}

	/**
	 * Creates an Order data object from a given ResultSet and Customer instance
	 * 
	 * @param resultSet
	 *            The resultSet to get the data from. The ResultSet must contain
	 *            a join of the Orders and OrdersComments tables.
	 * @param customer
	 *            The customer that has ordered the order in the resultSet.
	 * @return An instance of Order, containing the data at the current row of
	 *         the resultSet
	 * @throws SQLException
	 *             Any error raised while reading the data from the resultSet
	 */
	private static Order createOrder(ResultSet resultSet, Customer customer) throws SQLException {
		int orderID = resultSet.getInt(resultSet.findColumn(DatabaseQueryConstants.ORDERS_ID));
		String timeRegistered = resultSet.getString(resultSet
				.findColumn(DatabaseQueryConstants.ORDERS_TIME_REGISTERED));
		String orderStatus = resultSet.getString(resultSet.findColumn(DatabaseQueryConstants.ORDERS_STATUS));
		String deliveryMethod = resultSet.getString(resultSet
				.findColumn(DatabaseQueryConstants.ORDERS_DELIVERY_METHOD));
		String comment = resultSet.getString(resultSet.findColumn(DatabaseQueryConstants.ORDERS_COMMENT));
		Order order = new Order(orderID, customer, timeRegistered, orderStatus, deliveryMethod, comment);
		return order;
	}

	/**
	 * Creates a Setting instance from the entered ResultSet.
	 * 
	 * @param results
	 *            The resultSet that should be converted, which must contain all
	 *            columns of the Config table
	 * @return A Setting instance representing the setting of the current row of
	 *         the ResultSet
	 * @throws SQLException
	 *             throws an exception if an error is raised while reading the
	 *             data
	 */
	public static Setting createSetting(ResultSet results) throws SQLException {
		String configKey = results.getString(results.findColumn(DatabaseQueryConstants.CONFIG_KEY));
		String configValue = results.getString(results.findColumn(DatabaseQueryConstants.CONFIG_VALUE));
		Setting setting = new Setting(configKey, configValue);
		return setting;
	}
}