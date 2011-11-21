package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import pizzaProgram.core.DatabaseConstants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.dataObjects.Setting;

/**
 * Class to generate dataObjects based on {@link java.sql.ResultSet ResultSets}
 * sent to the methods of the class. All methods in the class are static.
 */
public class DatabaseDataObjectGenerator {
	/**
	 * Method to populate an {@link java.util.ArrayList ArrayList} with
	 * {@link pizzaProgram.dataObjects.Dish dishes} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param results
	 *            - a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Dishes table of the database
	 * @return The populated {@link java.util.ArrayList ArrayList} of
	 *         {@link pizzaProgram.dataObjects.Dish dishes}
	 * @throws SQLException
	 */
	public static ArrayList<Dish> generateDishList(ResultSet results)
			throws SQLException {
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		Dish currentDish;
		while (results.next()) {
			currentDish = DatabaseDataObjectGenerator.createDish(results);
			dishList.add(currentDish);
		}
		return dishList;
	}

	/**
	 * Method to populate an {@link java.util.ArrayList ArrayList} with
	 * {@link pizzaProgram.dataObjects.Customer customers} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param results
	 *            - a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Customers table of the database
	 * @return The populated {@link java.util.ArrayList ArrayList} of
	 *         {@link pizzaProgram.dataObjects.Customer customers}
	 * @throws SQLException
	 */
	public static ArrayList<Customer> generateCustomerList(ResultSet results)
			throws SQLException {
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		Customer currentCustomer;
		while (results.next()) {
			currentCustomer = DatabaseDataObjectGenerator
					.createCustomer(results);
			customerList.add(currentCustomer);
		}
		return customerList;
	}

	/**
	 * Method to populate an {@link java.util.ArrayList ArrayList} with
	 * {@link pizzaProgram.dataObjects.Extra extras} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param results
	 *            - a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Extras table of the database
	 * @return The populated {@link java.util.ArrayList ArrayList} of
	 *         {@link pizzaProgram.dataObjects.Extra extras}
	 * @throws SQLException
	 */
	public static ArrayList<Extra> generateExtrasList(ResultSet results)
			throws SQLException {
		ArrayList<Extra> extrasList = new ArrayList<Extra>();
		Extra currentExtra;
		while (results.next()) {
			currentExtra = DatabaseDataObjectGenerator.createExtra(results);
			extrasList.add(currentExtra);
		}
		return extrasList;
	}

	/**
	 * Method to populate an {@link java.util.ArrayList ArrayList} with
	 * {@link pizzaProgram.dataObjects.Setting settings} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param results
	 *            - a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Config table of the database
	 * @return The populated {@link java.util.ArrayList ArrayList} of
	 *         {@link pizzaProgram.dataObjects.Setting settings}
	 * @throws SQLException
	 */
	public static ArrayList<Setting> generateSettingsList(ResultSet results)
			throws SQLException {
		ArrayList<Setting> settingsList = new ArrayList<Setting>();
		Setting currentSetting;
		while (results.next()) {
			currentSetting = DatabaseDataObjectGenerator.createSetting(results);
			settingsList.add(currentSetting);
		}
		return settingsList;
	}

	/**
	 * Method to populate an {@link java.util.ArrayList ArrayList} with
	 * {@link pizzaProgram.dataObjects.Order orders} based on the provided
	 * {@link java.sql.ResultSet ResultSet}
	 * 
	 * @param result
	 *            - a {@link java.sql.ResultSet ResultSet} containing data from
	 *            the Customer, Dishes, Extras, OrdersContents and Orders table
	 *            of the database
	 * @return The populated {@link java.util.ArrayList ArrayList} of
	 *         {@link pizzaProgram.dataObjects.Order orders}
	 * @throws SQLException
	 */
	public static ArrayList<Order> generateOrderListFromResultSet(
			ResultSet result) throws SQLException {
		System.out.println("generating order list");
		ArrayList<Order> orderList = new ArrayList<Order>();
		HashMap<Integer, Dish> dishMap = new HashMap<Integer, Dish>();
		HashMap<Integer, Customer> customerMap = new HashMap<Integer, Customer>();
		HashMap<Integer, Extra> extraMap = new HashMap<Integer, Extra>();
		Order currentOrder = null;
		OrderDish currentOrderDish = null;
		Customer currentCustomer = null;
		Dish currentDish = null;
		Extra currentExtra = null;
		int currentOrderID = -1;
		int currentOrderContentID = -1;
		while (result.next()) {
			if (result.getInt(result.findColumn(DatabaseConstants.ORDERS_ID)) != currentOrderID) {
				currentOrderID = result.getInt(result
						.findColumn(DatabaseConstants.ORDERS_ID));
				if (!customerMap.containsKey(result.getInt(result
						.findColumn(DatabaseConstants.CUSTOMER_ID)))) {
					customerMap.put(result.getInt(result
							.findColumn(DatabaseConstants.CUSTOMER_ID)),
							DatabaseDataObjectGenerator.createCustomer(result));
				}
				currentCustomer = customerMap.get(result.getInt(result
						.findColumn(DatabaseConstants.CUSTOMER_ID)));
				currentOrder = DatabaseDataObjectGenerator.createOrder(result,
						currentCustomer);
				orderList.add(currentOrder);
			}
			if (currentOrder != null
					&& result.getInt(result
							.findColumn(DatabaseConstants.ORDERS_CONTENTS_ID)) != currentOrderContentID) {
				currentOrderContentID = result.getInt(result
						.findColumn(DatabaseConstants.ORDERS_CONTENTS_ID));
				if (!dishMap.containsKey(result.getInt(result
						.findColumn(DatabaseConstants.DISH_ID)))) {
					dishMap.put(result.getInt(result
							.findColumn(DatabaseConstants.DISH_ID)),
							DatabaseDataObjectGenerator.createDish(result));
				}
				currentDish = dishMap.get(result.getInt(result
						.findColumn(DatabaseConstants.DISH_ID)));
				currentOrderDish = new OrderDish(currentOrder.orderID,
						currentDish);
				currentOrder.addOrderDish(currentOrderDish);
			}
			if (!extraMap.containsKey(result.getInt(result
					.findColumn(DatabaseConstants.EXTRAS_ID)))) {
				extraMap.put(result.getInt(result
						.findColumn(DatabaseConstants.EXTRAS_ID)),
						DatabaseDataObjectGenerator.createExtra(result));
			}
			currentExtra = extraMap.get(result.getInt(result
					.findColumn(DatabaseConstants.EXTRAS_ID)));
			if (currentOrderDish != null) {
				currentOrderDish.addExtra(currentExtra);
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
	private static Customer createCustomer(ResultSet resultSet)
			throws SQLException {
		int customerID = resultSet.getInt(resultSet
				.findColumn(DatabaseConstants.CUSTOMER_ID));
		String firstName = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.FIRST_NAME));
		String lastName = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.LAST_NAME));
		String address = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.ADDRESS));
		String postalCode = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.POSTAL_CODE));
		String city = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.CITY));
		int phoneNumber = resultSet.getInt(resultSet
				.findColumn(DatabaseConstants.PHONE_NUMBER));
		Customer customer = new Customer(customerID, firstName, lastName,
				address, postalCode, city, phoneNumber);
		return customer;
	}

	/**
	 * Creates a Dish data object from a given resultSet
	 * 
	 * @param resultSet
	 *            The ResultSet, containing all columns from the Dishes table,
	 *            from which the Dish object should be created
	 * @return The resulting DIsh instance from the current row in the ResultSet
	 * @throws SQLException
	 *             Any error raised while reading the data from the ResultSet
	 */
	private static Dish createDish(ResultSet resultSet) throws SQLException {
		int dishID = resultSet.getInt(resultSet
				.findColumn(DatabaseConstants.DISH_ID));
		int price = resultSet.getInt(resultSet
				.findColumn(DatabaseConstants.DISH_PRICE));
		String name = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.DISH_NAME));
		boolean containsGluten = resultSet.getBoolean(resultSet
				.findColumn(DatabaseConstants.DISH_CONTAINS_GLUTEN));
		boolean containsNuts = resultSet.getBoolean(resultSet
				.findColumn(DatabaseConstants.DISH_CONTAINS_NUTS));
		boolean containsDairy = resultSet.getBoolean(resultSet
				.findColumn(DatabaseConstants.DISH_CONTAINS_DAIRY));
		boolean isVegetarian = resultSet.getBoolean(resultSet
				.findColumn(DatabaseConstants.DISH_IS_VEGETARIAN));
		boolean isSpicy = resultSet.getBoolean(resultSet
				.findColumn(DatabaseConstants.DISH_IS_SPICY));
		String description = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.DISH_DESCRIPTION));
		boolean isActive = resultSet.getBoolean(resultSet
				.findColumn(DatabaseConstants.DISH_IS_ACTIVE));
		Dish dish = new Dish(dishID, price, name, containsGluten, containsNuts,
				containsDairy, isVegetarian, isSpicy, description, isActive);
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
		int extrasID = resultSet.getInt(resultSet
				.findColumn(DatabaseConstants.EXTRAS_ID));
		String name = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.EXTRAS_NAME));
		String price = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.EXTRAS_PRICE));
		boolean isActive = resultSet.getBoolean(resultSet
				.findColumn(DatabaseConstants.EXTRAS_IS_ACTIVE));
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
	private static Order createOrder(ResultSet resultSet, Customer customer)
			throws SQLException {
		int orderID = resultSet.getInt(resultSet
				.findColumn(DatabaseConstants.ORDERS_ID));
		String timeRegistered = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.ORDERS_TIME_REGISTERED));
		String orderStatus = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.ORDERS_STATUS));
		String deliveryMethod = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.ORDERS_DELIVERY_METHOD));
		String comment = resultSet.getString(resultSet
				.findColumn(DatabaseConstants.ORDERS_COMMENT));
		Order order = new Order(orderID, customer, timeRegistered, orderStatus,
				deliveryMethod, comment);
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
		String configKey = results.getString(results
				.findColumn(DatabaseConstants.CONFIG_KEY));
		String configValue = results.getString(results
				.findColumn(DatabaseConstants.CONFIG_VALUE));
		Setting setting = new Setting(configKey, configValue);
		return setting;
	}

}