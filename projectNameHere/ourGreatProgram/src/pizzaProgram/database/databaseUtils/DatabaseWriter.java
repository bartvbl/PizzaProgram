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

public class DatabaseWriter {
	public static void markOrderAsInProgress(Order order) {
		updateOrderStatusIfStatusMatchesCurrentStatus(order, Order.REGISTERED,
				Order.BEING_COOKED);
	}

	public static void markOrderAsFinishedCooking(Order order) {
		updateOrderStatusIfStatusMatchesCurrentStatus(order,
				Order.BEING_COOKED, Order.HAS_BEEN_COOKED);
	}

	public static void markOrderAsBeingDelivered(Order order) {
		if (order.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			updateOrderStatusIfStatusMatchesCurrentStatus(order,
					Order.HAS_BEEN_COOKED, Order.BEING_DELIVERED);
		} else {
			DatabaseResultsFeedbackProvider
					.showUpdateOrderStatusFailedInvalidDeliveryMethodMessage();
		}
	}

	public static void markOrderAsDelivered(Order order) {
		if (order.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			updateOrderStatusIfStatusMatchesCurrentStatus(order,
					Order.BEING_DELIVERED, Order.DELIVERED);
		} else if (order.deliveryMethod.equals(Order.PICKUP_AT_RESTAURANT)) {
			updateOrderStatusIfStatusMatchesCurrentStatus(order,
					Order.HAS_BEEN_COOKED, Order.DELIVERED);
		}
	}

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
			DatabaseResultsFeedbackProvider.showAddNewCustomerSuccessMessage();
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showAddNewCustomerFailedMessage();
			e.printStackTrace();
		}
	}

	public static void writeNewDish(Dish dish) {
		int containsGluten = convertBooleanToTinyInt(dish.containsGluten);
		int containsNuts = convertBooleanToTinyInt(dish.containsNuts);
		int containsDairy = convertBooleanToTinyInt(dish.containsDiary);
		int isSpicy = convertBooleanToTinyInt(dish.isSpicy);
		int isVegetarian = convertBooleanToTinyInt(dish.isVegetarian);
		int dishIsActive = convertBooleanToTinyInt(dish.isActive);
		try {
			DatabaseConnection
					.executeWriteQuery("INSERT INTO Dishes VALUES (NULL, "
							+ dish.price + ", '" + dish.name + "', "
							+ containsGluten + ", " + containsNuts + ", "
							+ containsDairy + ", " + isSpicy + ", "
							+ isVegetarian + ", '" + dish.description + "', "
							+ dishIsActive + ");");
			DatabaseResultsFeedbackProvider.showAddNewDishSucceededMessage();
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showAddNewDishFailedMessage();
			e.printStackTrace();
		}
	}

	public static void updateDishByDishID(Dish dish) {
		try {
			int containsGluten = convertBooleanToTinyInt(dish.containsGluten);
			int containsDairy = convertBooleanToTinyInt(dish.containsDiary);
			int containsNuts = convertBooleanToTinyInt(dish.containsNuts);
			int isVegetarian = convertBooleanToTinyInt(dish.isVegetarian);
			int isSpicy = convertBooleanToTinyInt(dish.isSpicy);
			int isActive = convertBooleanToTinyInt(dish.isActive);
			DatabaseConnection.executeWriteQuery("UPDATE Dishes SET Price="
					+ dish.price + ", Name='" + dish.name
					+ "', ContainsGluten=" + containsGluten + ", ContainsNuts="
					+ containsNuts + ", ContainsDairy=" + containsDairy
					+ ", IsSpicy=" + isSpicy + ", IsVegetarian=" + isVegetarian
					+ ", Description='" + dish.description + "', IsActive="
					+ isActive + " WHERE DishID=" + dish.dishID + ";");
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showUpdateDishFailedMessage();
			e.printStackTrace();
		}
	}

	public static void updateExtraByExtraID(Extra extra) {
		try {
			//int isActive = convertBooleanToTinyInt(extra.isActive);
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
		int extraIsActive = convertBooleanToTinyInt(extra.isActive);
		String priceString = extra.priceFuncPart
				+ Double.toString(extra.priceValPart);
		try {
			DatabaseConnection
					.executeWriteQuery("INSERT INTO Extras VALUES (NULL, '"
							+ extra.name + "', '" + priceString + "', "
							+ extraIsActive + ");");
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

	private static int convertBooleanToTinyInt(boolean bool) {
		if (bool == true) {
			return 1;
		} else {
			return 0;
		}
	}

	public static String createCustomerIdentifier(Customer customer) {
		return customer.firstName.toLowerCase()
				+ customer.lastName.toLowerCase() + customer.phoneNumber;
	}

}// END
