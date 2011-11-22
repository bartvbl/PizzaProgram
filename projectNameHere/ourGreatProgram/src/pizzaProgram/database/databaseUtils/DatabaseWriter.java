package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

import pizzaProgram.core.DatabaseMessages;
import pizzaProgram.core.DatabaseQueryConstants;
import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.dataObjects.Setting;
import pizzaProgram.dataObjects.UnaddedCustomer;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.database.DatabaseConnection;

/**
 * A class that writes or updates the database
 * 
 * @author Bart
 * 
 */
public class DatabaseWriter {
	/**
	 * Updates the status of the order entered from "registered" to
	 * "being cooked", if and only if the status of the order is still
	 * "registered".
	 * 
	 * @param order
	 *            The Order instance representing the order that should be
	 *            updated (by order ID)
	 */
	public static void markOrderAsInProgress(Order order) {
		updateOrderStatusIfStatusMatchesCurrentStatus(order, Order.REGISTERED, Order.BEING_COOKED);
	}

	/**
	 * Updates the status of the order entered from "being cooked" to
	 * "has been cooked", if and only if the status of the order is still
	 * "being cooked".
	 * 
	 * @param order
	 *            The Order instance representing the order that should be
	 *            updated (by order ID)
	 */
	public static void markOrderAsFinishedCooking(Order order) {
		updateOrderStatusIfStatusMatchesCurrentStatus(order, Order.BEING_COOKED, Order.HAS_BEEN_COOKED);
	}

	/**
	 * Updates the status of the order entered from "has been cooked" to
	 * "being delivered", if and only if the status of the order is still
	 * "has been cooked" and the order's delivery method is "deliver at home".
	 * 
	 * @param order
	 *            The Order instance representing the order that should be
	 *            updated (by order ID)
	 */
	public static void markOrderAsBeingDelivered(Order order) {
		if (order.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			updateOrderStatusIfStatusMatchesCurrentStatus(order, Order.HAS_BEEN_COOKED, Order.BEING_DELIVERED);
		} else {
			GUIConstants.showErrorMessage(DatabaseMessages.ILLEGAL_STATUS_ON_ORDER);
		}
	}

	/**
	 * Updates the status of the order entered from "being delivered" to
	 * "delivered", if the delivery method is "deliver at home", or from
	 * "has been cooked" to "delivered" when the delivery method is "pickup at
	 * restaurant. In both cases it will verify first that the order status is
	 * still valid before applying the update.
	 * 
	 * @param order
	 *            The Order instance representing the order that should be
	 *            updated (by order ID)
	 */
	public static void markOrderAsDelivered(Order order) {
		if (order.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			updateOrderStatusIfStatusMatchesCurrentStatus(order, Order.BEING_DELIVERED, Order.DELIVERED);
		} else if (order.deliveryMethod.equals(Order.PICKUP_AT_RESTAURANT)) {
			updateOrderStatusIfStatusMatchesCurrentStatus(order, Order.HAS_BEEN_COOKED, Order.DELIVERED);
		}
	}

	/**
	 * Writes a new order to the database, specified by the parameter
	 * 
	 * @param order
	 *            The order to be created.
	 */
	public static void writeNewOrder(UnaddedOrder order) {
		try {
			int commentID = -1;
			if (order.comment != null && !order.comment.equals("")) {
				commentID = createOrderComment(order.comment);
			}
			int orderID = createNewOrder(order, commentID);
			if (orderID != -1) {
				addDishesToOrder(order, orderID);
			}
			GUIConstants.showConfirmMessage(DatabaseMessages.ORDER_ADDED_SUCCESSFULLY);
		} catch (SQLException e) {
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_ADD_ORDER);
			e.printStackTrace();
		}
	}

	/**
	 * Adds a new customer to the database, specified by the parameter
	 * 
	 * @param customer
	 *            The new customer to be added to the database
	 */
	public static void writeNewCustomer(UnaddedCustomer customer) {
		try {
			DatabaseConnection.executeWriteQuery("INSERT INTO " + DatabaseQueryConstants.CUSTOMER_TABLE_NAME
					+ " VALUES (NULL, '" + customer.firstName + "', '" + customer.lastName + "', '"
					+ customer.address + "', " + customer.postalCode + ", '" + customer.city + "', "
					+ customer.phoneNumber + ", '" + createCustomerIdentifier(customer) + "');");
			GUIConstants.showConfirmMessage(DatabaseMessages.CUSTOMER_ADDED_SUCCESSFULLY);
		} catch (SQLException e) {
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_ADD_CUSTOMER);
			e.printStackTrace();
		}
	}

	/**
	 * Updates a customer with the values of the Customer object, specified by
	 * the customer ID of the input object
	 * 
	 * @param customer
	 *            The Customer instance representing the customer to be updated
	 *            in the database
	 */
	public static void updateCustomerById(Customer customer) {
		try {
			DatabaseConnection.executeWriteQuery("REPLACE INTO " + DatabaseQueryConstants.CUSTOMER_TABLE_NAME
					+ " VALUES (" + customer.customerID + ", '" + customer.firstName + "', '"
					+ customer.lastName + "', '" + customer.address + "', " + customer.postalCode + ", '"
					+ customer.city + "', " + customer.phoneNumber + ", '"
					+ createCustomerIdentifier(customer) + "');");
			GUIConstants.showConfirmMessage(DatabaseMessages.CUSTOMER_EDITED_SUCCESSFULLY);
		} catch (SQLException e) {
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_EDIT_CUSTOMER);
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a new dish into the database
	 * 
	 * @param dish
	 *            The Dish to be created
	 */
	public static void writeNewDish(Dish dish) {
		try {
			DatabaseConnection.executeWriteQuery("INSERT INTO " + DatabaseQueryConstants.DISH_TABLE_NAME
					+ " VALUES (NULL, " + dish.price + ", '" + dish.name + "', " + dish.containsGluten + ", "
					+ dish.containsNuts + ", " + dish.containsDairy + ", " + dish.isSpicy + ", "
					+ dish.isVegetarian + ", '" + dish.description + "', " + dish.isActive + ");");
			GUIConstants.showConfirmMessage(DatabaseMessages.DISH_ADDED_SUCCESSFULLY);
		} catch (SQLException e) {
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_ADD_DISH);
			e.printStackTrace();
		}
	}

	/**
	 * Updates the dish in the database, specified by the ID of the dish object,
	 * and whose updated values are represented by the fields in the dish
	 * object.
	 * 
	 * @param dish
	 *            The dish to be updated
	 */
	public static void updateDishByDishID(Dish dish) {
		try {
			DatabaseConnection.executeWriteQuery("UPDATE " + DatabaseQueryConstants.DISH_TABLE_NAME + " SET "
					+ DatabaseQueryConstants.DISH_PRICE + "=" + dish.price + ", " + DatabaseQueryConstants.DISH_NAME
					+ "='" + dish.name + "', " + DatabaseQueryConstants.DISH_CONTAINS_GLUTEN + "="
					+ dish.containsGluten + ", " + DatabaseQueryConstants.DISH_CONTAINS_NUTS + "="
					+ dish.containsNuts + ", " + DatabaseQueryConstants.DISH_CONTAINS_DAIRY + "="
					+ dish.containsDairy + ", " + DatabaseQueryConstants.DISH_IS_SPICY + "=" + dish.isSpicy + ", "
					+ DatabaseQueryConstants.DISH_IS_VEGETARIAN + "=" + dish.isVegetarian + ", "
					+ DatabaseQueryConstants.DISH_DESCRIPTION + "='" + dish.description + "', "
					+ DatabaseQueryConstants.DISH_IS_ACTIVE + "=" + dish.isActive + " WHERE "
					+ DatabaseQueryConstants.DISH_ID + "=" + dish.dishID + ";");
			GUIConstants.showConfirmMessage(DatabaseMessages.DISH_EDITED_SUCCESSFULLY);
		} catch (SQLException e) {
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_EDIT_DISH);
			e.printStackTrace();
		}
	}

	/**
	 * Updates the extra in the database specified by the Extra object's ID
	 * 
	 * @param extra
	 *            the extra to update in the database
	 */
	public static void updateExtraByExtraID(Extra extra) {
		try {
			String price = extra.priceFuncPart + "" + extra.priceValPart;
			DatabaseConnection.executeWriteQuery("UPDATE " + DatabaseQueryConstants.EXTRAS_TABLE_NAME + " SET "
					+ DatabaseQueryConstants.EXTRAS_NAME + "='" + extra.name + "', "
					+ DatabaseQueryConstants.EXTRAS_PRICE + "='" + price + "', "
					+ DatabaseQueryConstants.EXTRAS_IS_ACTIVE + "=" + extra.isActive + " WHERE "
					+ DatabaseQueryConstants.EXTRAS_ID + "=" + extra.id + ";");
			GUIConstants.showConfirmMessage(DatabaseMessages.EXTRA_EDITED_SUCCESSFULLY);
		} catch (SQLException e) {
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_EDIT_EXTRA);
			e.printStackTrace();
		}
	}

	/**
	 * Inserts a new extra into the database, whose values correspond to the
	 * fields in the inserted Extra instance
	 * 
	 * @param extra
	 *            the extra to be created
	 */
	public static void writeNewExtra(Extra extra) {
		String price = extra.priceFuncPart + "" + extra.priceValPart;
		try {
			DatabaseConnection.executeWriteQuery("INSERT INTO " + DatabaseQueryConstants.EXTRAS_TABLE_NAME
					+ " VALUES (NULL, '" + extra.name + "', '" + price + "', " + extra.isActive + ");");
			GUIConstants.showConfirmMessage(DatabaseMessages.EXTRA_ADDED_SUCCESSFULLY);
		} catch (SQLException e) {
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_ADD_EXTRA);
			e.printStackTrace();
		}
	}

	/**
	 * Updates the config value in the database specified by the Setting
	 * instance's key, and sets it to the Setting instance's value.
	 * 
	 * @param setting
	 *            The setting to be updated
	 */
	public static void updateConfigValue(Setting setting) {
		try {
			DatabaseConnection.executeWriteQuery("UPDATE " + DatabaseQueryConstants.CONFIG_TABLE_NAME + " SET "
					+ DatabaseQueryConstants.CONFIG_VALUE + "='" + setting.value + "' WHERE "
					+ DatabaseQueryConstants.CONFIG_KEY + "='" + setting.key + "'");
		} catch (SQLException e) {
			GUIConstants.showErrorMessage(DatabaseMessages.UNABLE_TO_CHANGE_BLANK + setting.key);
			e.printStackTrace();
		}
	}

	/**
	 * A general function that updates the order status of an order. It will
	 * only update the order if and only if the current order status matches the
	 * currentStatus parameter, and will then set it to the newStatus status. It
	 * meanwhile locks all the tables in the database to make sure that the
	 * order is not updated while the process is still going on.
	 * 
	 * @param order
	 *            The order to be updated
	 * @param currentStatus
	 *            The order status that the order should currently have
	 * @param newStatus
	 *            The order status that the order should be updated to
	 */
	private static void updateOrderStatusIfStatusMatchesCurrentStatus(Order order, String currentStatus,
			String newStatus) {
		try {
			lockTablesForUpdatingOrderStatus();
			String currentOrderStatus = getCurrentStatusOfOrder(order.orderID);
			if (currentOrderStatus.equals(currentStatus)) {
				updateOrderStatus(newStatus, order.orderID);
				unlockTables();
			} else {
				unlockTables();
				GUIConstants.showConfirmMessage(DatabaseMessages.UNEXPECTED_ORDER_STATUS);
			}
		} catch (SQLException e) {
			GUIConstants.showConfirmMessage(DatabaseMessages.UNABLE_TO_CHANGE_ORDER_STATUS);
			e.printStackTrace();
		} finally {
			unlockTables();
		}
	}

	/**
	 * Returns a String representing the current status of an order, specified
	 * by the order's order ID
	 * 
	 * @param orderID
	 *            the ID of the order that should be retrieved
	 * @return A String representing an enum value of the status of the order
	 * @throws SQLException
	 *             Any error raised while retrieving data from the database
	 */
	private static String getCurrentStatusOfOrder(int orderID) throws SQLException {
		String query = "SELECT " + DatabaseQueryConstants.ORDERS_LOCKED_STATUS + " FROM "
				+ DatabaseQueryConstants.ORDERS_TABLE_NAME + " AS " + DatabaseQueryConstants.ORDERS_LOCKED_TABLE_NAME
				+ " WHERE " + DatabaseQueryConstants.ORDERS_LOCKED_ID + "=" + orderID + ";";
		ResultSet results = DatabaseConnection.fetchData(query);
		results.next();
		String currentStatus = results.getString(results.findColumn(DatabaseQueryConstants.ORDERS_LOCKED_STATUS));
		return currentStatus;
	}

	/**
	 * Updates the status of the order specified by the order ID to a new status
	 * specified by the new status string parameter
	 * 
	 * @param status
	 *            The new status of the of the order
	 * @param orderID
	 *            the order to be updated
	 * @throws SQLException
	 *             Any error raised while retrieving the data from the database
	 */
	private static void updateOrderStatus(String status, int orderID) throws SQLException {
		DatabaseConnection.executeWriteQuery("UPDATE " + DatabaseQueryConstants.ORDERS_TABLE_NAME + " SET "
				+ DatabaseQueryConstants.ORDERS_STATUS + "='" + status + "' WHERE " + DatabaseQueryConstants.ORDERS_ID
				+ "=" + orderID + ";");
	}

	/**
	 * Locks the Orders table for READ and WRITE, so that the order status can
	 * be safely updated without any concurrency conflicts
	 * 
	 * @throws SQLException
	 *             Any error raised while locking the tables
	 */
	private static void lockTablesForUpdatingOrderStatus() throws SQLException {
		DatabaseConnection.executeWriteQuery("LOCK TABLES " + DatabaseQueryConstants.ORDERS_TABLE_NAME
				+ " WRITE, " + DatabaseQueryConstants.ORDERS_TABLE_NAME + " AS "
				+ DatabaseQueryConstants.ORDERS_LOCKED_TABLE_NAME + " READ;");
	}

	/**
	 * Unlocks the locked tables for the order status updating process
	 */
	private static void unlockTables() {
		DatabaseConnection.insertIntoDB("UNLOCK TABLES;");
	}

	/**
	 * Writes all the dishes that the order contains to the database
	 * 
	 * @param order
	 *            The unadded order containing the dishes to be added
	 * @param orderID
	 *            The ID of the new order created in the database
	 * @throws SQLException
	 *             Any error raised in the process of adding dishes to the
	 *             database
	 */
	private static void addDishesToOrder(UnaddedOrder order, int orderID) throws SQLException {
		for (OrderDish dish : order.orderedDishes) {
			addDishToOrder(dish, orderID);
		}
	}

	/**
	 * Inserts a link in the database between the new order being added and the
	 * specified dish
	 * 
	 * @param dish
	 *            The dish to be linked to the specified order in the database
	 * @param orderID
	 *            The order ID to be linked to the specified fish
	 * @throws SQLException
	 *             Any error raised while inserting data into the database
	 */
	private static void addDishToOrder(OrderDish dish, int orderID) throws SQLException {
		int orderContentsID = DatabaseConnection.insertIntoDBAndReturnID("INSERT INTO "
				+ DatabaseQueryConstants.ORDERS_CONTENTS_TABLE_NAME + " VALUES (NULL, " + orderID + ", "
				+ dish.dish.dishID + ");");
		if (orderContentsID == -1) {
			return;
		}
		for (Extra extra : dish.getExtras()) {
			addExtraToOrder(orderContentsID, extra);
		}
	}

	/**
	 * Adds an extra to the current order
	 * 
	 * @param orderContentsID
	 *            The order contents ID of the new order
	 * @param extra
	 *            the extra to be linked to the current new order
	 */
	private static void addExtraToOrder(int orderContentsID, Extra extra) {
		DatabaseConnection.insertIntoDB("INSERT INTO " + DatabaseQueryConstants.DISH_EXTRAS_CHOSEN_TABLE_NAME
				+ " VALUES (" + orderContentsID + ", " + extra.id + ")");
	}

	/**
	 * Inserts a comment about the new order into the database
	 * 
	 * @param comment
	 *            the comment to be insterted
	 * @return The ID representing the comment in the database
	 * @throws SQLException
	 *             Any error raised while inserting the comment into the
	 *             database
	 */
	private static int createOrderComment(String comment) throws SQLException {
		int commentID = DatabaseConnection.insertIntoDBAndReturnID("INSERT INTO "
				+ DatabaseQueryConstants.ORDERS_COMMENT_TABLE_NAME + " VALUES (NULL, '" + comment + "');");
		return commentID;
	}

	/**
	 * Creates a new order in the orders table in the database
	 * 
	 * @param order
	 *            the unadded order that should be inserted into the database
	 * @param commentID
	 *            the ID of a comment about the order
	 * @return the ID of the created order
	 * @throws SQLException
	 *             Any error raised while adding the new order.
	 */
	private static int createNewOrder(UnaddedOrder order, int commentID) throws SQLException {
		int orderID = DatabaseReader.customerHasUncookedOrder(order);
		System.out.println(orderID);
		if (orderID > 0) {
			return orderID;
		}
		orderID = DatabaseConnection.insertIntoDBAndReturnID("INSERT INTO "
				+ DatabaseQueryConstants.ORDERS_TABLE_NAME + " VALUES (NULL, " + order.customer.customerID
				+ ", NOW(), '" + Order.REGISTERED + "', '" + order.deliveryMethod + "', " + commentID + ");");
		return orderID;
	}

	/**
	 * Generates a string representing the customer in the database
	 * 
	 * @param customer
	 *            The customer that the identifier should be generated for
	 * @return A string representing the inserted customer
	 */
	public static String createCustomerIdentifier(Customer customer) {
		return customer.firstName.toLowerCase() + customer.lastName.toLowerCase() + customer.phoneNumber;
	}

	/**
	 * Deletes a Customer from the database, defined by the passed Customer
	 * instance
	 * 
	 * @param customer
	 *            the customer to be deleted
	 */
	public static void deleteCustomer(Customer customer) {
		DatabaseConnection.insertIntoDB("DELETE FROM " + DatabaseQueryConstants.CUSTOMER_TABLE_NAME + " WHERE "
				+ DatabaseQueryConstants.CUSTOMER_ID + "=" + customer.customerID + " LIMIT 1");
	}

}// END