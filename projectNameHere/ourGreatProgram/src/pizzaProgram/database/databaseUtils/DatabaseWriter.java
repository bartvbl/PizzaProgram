package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

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
 * @author Bart
 *
 */
public class DatabaseWriter {
	/**
	 * Updates the status of the order entered from "registered" to "being cooked", if and only if the status of the order is still "registered". 
	 * @param order The Order instance representing the order that should be updated (by order ID)
	 */
	public static void markOrderAsInProgress(Order order) {
		updateOrderStatusIfStatusMatchesCurrentStatus(order, Order.REGISTERED,
				Order.BEING_COOKED);
	}
	/**
	 * Updates the status of the order entered from "being cooked" to "has been cooked", if and only if the status of the order is still "being cooked". 
	 * @param order The Order instance representing the order that should be updated (by order ID)
	 */
	public static void markOrderAsFinishedCooking(Order order) {
		updateOrderStatusIfStatusMatchesCurrentStatus(order,
				Order.BEING_COOKED, Order.HAS_BEEN_COOKED);
	}
	/**
	 * Updates the status of the order entered from "has been cooked" to "being delivered", if and only if the status of the order is still "has been cooked" and the order's delivery method is "deliver at home". 
	 * @param order The Order instance representing the order that should be updated (by order ID)
	 */
	public static void markOrderAsBeingDelivered(Order order) {
		if (order.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			updateOrderStatusIfStatusMatchesCurrentStatus(order,
					Order.HAS_BEEN_COOKED, Order.BEING_DELIVERED);
		} else {
			DatabaseResultsFeedbackProvider
					.showUpdateOrderStatusFailedInvalidDeliveryMethodMessage();
		}
	}
	/**
	 * Updates the status of the order entered from "being delivered" to "delivered", if the delivery method is "deliver at home", or from "has been cooked" to "delivered" when the delivery method is "pickup at restaurant. In both cases it will verify first that the order status is still valid before applying the update. 
	 * @param order The Order instance representing the order that should be updated (by order ID)
	 */
	public static void markOrderAsDelivered(Order order) {
		if (order.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			updateOrderStatusIfStatusMatchesCurrentStatus(order,
					Order.BEING_DELIVERED, Order.DELIVERED);
		} else if (order.deliveryMethod.equals(Order.PICKUP_AT_RESTAURANT)) {
			updateOrderStatusIfStatusMatchesCurrentStatus(order,
					Order.HAS_BEEN_COOKED, Order.DELIVERED);
		}
	}

	/**
	 * Writes a new order to the database, specified by the parameter
	 * @param order The order to be created.
	 */
	public static void writeNewOrder(UnaddedOrder order) {
		try {
			int commentID = -1;
			if (order.comment != "") {
				commentID = createOrderComment(order.comment);
			}
			int orderID = createNewOrder(order, commentID);
			if (orderID != -1) {
				addDishesToOrder(order, orderID);
			}
			DatabaseResultsFeedbackProvider.showAddNewOrderSuccessMessage();
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showAddNewOrderFailedMessage();
		}
	}

	/**
	 * Adds a new customer to the database, specified by the parameter
	 * @param customer The new customer to be added to the database
	 */
	public static void writeNewCustomer(UnaddedCustomer customer) {
		int commentID;
		try {
			commentID = createCustomerNote(customer.comment);
			DatabaseConnection
					.executeWriteQuery("INSERT INTO Customer VALUES (NULL, '"
							+ customer.firstName + "', '" + customer.lastName
							+ "', '" + customer.address + "', "
							+ customer.postalCode + ", '" + customer.city
							+ "', " + customer.phoneNumber + ", " + commentID
							+ ", '" + createCustomerIdentifier(customer)
							+ "');");
			DatabaseResultsFeedbackProvider.showAddNewCustomerSuccessMessage();
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showAddNewCustomerFailedMessage();
			e.printStackTrace();
		}
	}

	/**
	 * Updates a customer with the values of the Customer object, specified by the customer ID of the input object
	 * @param customer
	 */
	public static void updateCustomerById(Customer customer) {
		int commentID;
		try {
			commentID = createCustomerNote(customer.comment);
			DatabaseConnection
					.executeWriteQuery("REPLACE INTO Customer VALUES ("
							+ customer.customerID + ", '" + customer.firstName
							+ "', '" + customer.lastName + "', '"
							+ customer.address + "', " + customer.postalCode
							+ ", '" + customer.city + "', "
							+ customer.phoneNumber + ", " + commentID + ", '"
							+ createCustomerIdentifier(customer) + "');");
			DatabaseResultsFeedbackProvider.showUpdateCustomerSuccessMessage();
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showUpdateCustomerFailedMessage();
			e.printStackTrace();
		}
	}

	public static void writeNewDish(Dish dish) {
		try {
			DatabaseConnection
					.executeWriteQuery("INSERT INTO Dishes VALUES (NULL, "
							+ dish.price + ", '" + dish.name + "', "
							+ dish.containsGluten + ", " + dish.containsNuts + ", "
							+ dish.containsDairy + ", " + dish.isSpicy + ", "
							+ dish.isVegetarian + ", '" + dish.description + "', "
							+ dish.isActive + ");");
			DatabaseResultsFeedbackProvider.showAddNewDishSucceededMessage();
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showAddNewDishFailedMessage();
			e.printStackTrace();
		}
	}

	public static void updateDishByDishID(Dish dish) {
		try {
			DatabaseConnection.executeWriteQuery("UPDATE Dishes SET Price="
					+ dish.price + ", Name='" + dish.name
					+ "', ContainsGluten=" + dish.containsGluten + ", ContainsNuts="
					+ dish.containsNuts + ", ContainsDairy=" + dish.containsDairy
					+ ", IsSpicy=" + dish.isSpicy + ", IsVegetarian=" + dish.isVegetarian
					+ ", Description='" + dish.description + "', IsActive="
					+ dish.isActive + " WHERE DishID=" + dish.dishID + ";");
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showUpdateDishFailedMessage();
			e.printStackTrace();
		}
	}

	public static void updateExtraByExtraID(Extra extra) {
		try {
			String price = extra.priceFuncPart
					+ Double.toString(extra.priceValPart);
			DatabaseConnection.executeWriteQuery("UPDATE Extras SET Name='"
					+ extra.name + "', Price='" + price + "', IsActive="
					+ extra.isActive + " WHERE ExtrasID=" + extra.id + ";");
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showUpdateExtraFailedMessage();
			e.printStackTrace();
		}
	}

	public static void writeNewExtra(Extra extra) {
		String priceString = extra.priceFuncPart
				+ Double.toString(extra.priceValPart);
		try {
			DatabaseConnection
					.executeWriteQuery("INSERT INTO Extras VALUES (NULL, '"
							+ extra.name + "', '" + priceString + "', "
							+ extra.isActive + ");");
			DatabaseResultsFeedbackProvider.showAddNewExtraSucceededMessage();
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showAddNewExtraFailedMessage();
			e.printStackTrace();
		}
	}

	public static void updateConfigValue(Setting setting) {
		try {
			DatabaseConnection
					.executeWriteQuery("UPDATE Config SET ConfigValue='"
							+ setting.value + "' WHERE ConfigKey='"
							+ setting.key + "'");
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider
					.showUpdateConfigValueFailedMessage();
			e.printStackTrace();
		}
	}

	private static void updateOrderStatusIfStatusMatchesCurrentStatus(
			Order order, String currentStatus, String newStatus) {
		try {
			lockTablesForUpdatingOrderStatus();
			String currentOrderStatus = getCurrentStatusOfOrder(order.orderID);
			if (currentOrderStatus.equals(currentStatus)) {
				updateOrderStatus(newStatus, order.orderID);
				unlockTables();
			} else {
				unlockTables();
				DatabaseResultsFeedbackProvider
						.showUpdateOrderStatusFailedMessage();
			}
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			unlockTables();
		}
	}

	private static String getCurrentStatusOfOrder(int orderID)
			throws SQLException {
		String query = "SELECT OrdersStatus FROM Orders AS OrdersRead WHERE OrdersID="
				+ orderID + ";";
		ResultSet results = DatabaseConnection.fetchData(query);
		results.next();
		String currentStatus = results.getString(1);
		return currentStatus;
	}

	private static void updateOrderStatus(String status, int orderID)
			throws SQLException {
		DatabaseConnection.executeWriteQuery("UPDATE Orders SET OrdersStatus='"
				+ status + "' WHERE OrdersID=" + orderID + ";");
	}

	private static void lockTablesForUpdatingOrderStatus() throws SQLException {
		DatabaseConnection
				.executeWriteQuery("LOCK TABLES Orders WRITE, Orders AS OrdersRead READ;");
	}

	private static void unlockTables() {
		DatabaseConnection.insertIntoDB("UNLOCK TABLES;");
	}

	private static void addDishesToOrder(UnaddedOrder order, int orderID)
			throws SQLException {
		for (OrderDish dish : order.orderedDishes) {
			addDishToOrder(dish, orderID);
		}
	}

	private static void addDishToOrder(OrderDish dish, int orderID)
			throws SQLException {
		int orderContentsID = DatabaseConnection
				.insertIntoDBAndReturnID("INSERT INTO OrdersContents VALUES (NULL, "
						+ orderID + ", " + dish.dish.dishID + ");");
		if (orderContentsID == -1) {
			return;
		}
		for (Extra extra : dish.getExtras()) {
			addExtraToOrder(orderContentsID, extra);
		}
	}

	private static void addExtraToOrder(int orderContentsID, Extra extra) {
		DatabaseConnection.insertIntoDB("INSERT INTO DishExtrasChosen VALUES ("
				+ orderContentsID + ", " + extra.id + ")");
	}

	private static int createOrderComment(String comment) throws SQLException {
		int commentID = DatabaseConnection
				.insertIntoDBAndReturnID("INSERT INTO OrderComments VALUES (NULL, '"
						+ comment + "');");
		return commentID;
	}

	private static int createNewOrder(UnaddedOrder order, int commentID)
			throws SQLException {
		int orderID = DatabaseConnection
				.insertIntoDBAndReturnID("INSERT INTO Orders VALUES (NULL, "
						+ order.customer.customerID + ", NOW(), '"
						+ Order.REGISTERED + "', '" + order.deliveryMethod
						+ "', " + commentID + ");");
		return orderID;
	}

	private static int createCustomerNote(String comment) throws SQLException {
		int commentID = DatabaseConnection
				.insertIntoDBAndReturnID("INSERT INTO OrderComments VALUES (NULL, '"
						+ comment + "');");
		return commentID;
	}

	public static String createCustomerIdentifier(Customer customer) {
		return customer.firstName.toLowerCase()
				+ customer.lastName.toLowerCase() + customer.phoneNumber;
	}

}// END