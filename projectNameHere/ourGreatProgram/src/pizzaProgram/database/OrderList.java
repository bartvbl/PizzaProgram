//package pizzaProgram.database;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import pizzaProgram.dataObjects.Customer;
//import pizzaProgram.dataObjects.Dish;
//import pizzaProgram.dataObjects.Extra;
//import pizzaProgram.dataObjects.Order;
//import pizzaProgram.dataObjects.OrderDish;
//import pizzaProgram.events.Event;
//import pizzaProgram.events.EventDispatcher;
//import pizzaProgram.events.EventHandler;
//import pizzaProgram.events.EventType;
//
///**
// * Object for handling orders in the database. The methods of the class handles
// * populating an {@link java.util.ArrayList ArrayList} and a
// * {@link java.util.HashMap HashMap} of all the different
// * {@link pizzaProgram.dataObject.Order orders} based on a fetch from the
// * database, as well as a {@link java.util.HashMap HashMap} of the open orders
// * with a mapping from a combination of the customerID and the orderStatus to
// * order for use in the GUI. These lists are publically available through the
// * getter methods.<br>
// * <br>
// * The methods of the class also handles the addition of new orders to the
// * database with two methods: one for adding an
// * {@link pizzaProgram.dataObject.Order order}, and another for adding
// * {@link pizzaProgram.dataObject.OrderDish ordered dishes} to the order, as
// * well as a method for changing the status of the order. <br>
// * <br>
// * CAUTION: ALL the other lists must be updated before the OrderList can be
// * updated, as it uses the maps created with the other lists to correctly assign
// * data.
// * 
// * @author IT1901 Group 03, Fall 2011
// */
//
//// TODO: Dispatch an event whenever the lists are updated
//
//public class OrderList implements EventHandler {
//	private final static ArrayList<Order> orderList = new ArrayList<Order>();
//	private final static HashMap<Integer, Order> orderMap = new HashMap<Integer, Order>();
//	private final static HashMap<String, Order> customerToOrderMap = new HashMap<String, Order>();
//	private static EventDispatcher eventDispatcher;
//
//	/**
//	 * This method clears the old lists, and repopulates the
//	 * {@link java.util.ArrayList ArrayList} and {@link java.util.HashMap
//	 * HashMaps} of all the different {@link pizzaProgram.dataObject.Order
//	 * orders} based on a fetch from the database. This method must be rerun
//	 * each time the Orders table of the database is modified.
//	 */
//	public OrderList(EventDispatcher eventDispatcher) {
//		OrderList.eventDispatcher = eventDispatcher;
//		eventDispatcher.addEventListener(this,
//				EventType.DATABASE_UPDATE_REQUESTED);
//		eventDispatcher.addEventListener(this, EventType.ADD_ORDER_REQUESTED);
//	}
//
//	public static void updateOrders() {
//		if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
//			System.err
//					.println("No valid database connection; unable to update lists.");
//			return;
//		}
//		orderList.clear();
//		orderMap.clear();
//		customerToOrderMap.clear();
//		CustomerList.updateCustomers();
//		DishList.updateDishes();
//		ExtraList.updateExtras();
//		HashMap<Integer, Customer> customerMap = CustomerList.getCustomerMap();
//		HashMap<Integer, Dish> dishMap = DishList.getDishMap();
//		HashMap<Integer, Extra> extraMap = ExtraList.getExtraMap();
//		HashMap<Integer, OrderDish> tempOrderDishMap = new HashMap<Integer, OrderDish>();
//		HashMap<Integer, String> orderComments = createOrderCommentList();
//		ResultSet results = DatabaseConnection
//				.fetchData("SELECT Orders.OrdersID,"
//						+ " Orders.CustomerID, Orders.TimeRegistered,"
//						+ " Orders.OrdersStatus, Orders.DeliveryMethod,"
//						+ " Orders.CommentID, OrdersContents.OrdersContentsID,"
//						+ " OrdersContents.DishID, DishExtrasChosen.DishExtraID"
//						+ " FROM Orders LEFT JOIN OrdersContents ON"
//						+ " Orders.OrdersID=OrdersContents.OrdersID"
//						+ " LEFT JOIN DishExtrasChosen ON"
//						+ " OrdersContents.OrdersContentsID=DishExtrasChosen.OrdersContentsID "
//						+ "ORDER BY Orders.OrdersID;");
//
//		try {
//			while (results.next()) {
//				if (orderMap.get(results.getInt(1)) == null) {
//					Order tempOrder = new Order(results.getInt(1),
//							customerMap.get(results.getInt(2)),
//							results.getString(3), results.getString(4),
//							results.getString(5), orderComments.get(results
//									.getInt(6)));
//					orderList.add(tempOrder);
//					orderMap.put(results.getInt(1), tempOrder);
//					if (!tempOrder.status.equals(Order.DELIVERED)) {
//						customerToOrderMap.put(tempOrder.customer.customerID
//								+ tempOrder.getStatus(), tempOrder);
//					}
//
//				}
//				if (tempOrderDishMap.get(results.getInt(7)) == null) {
//					if (dishMap.get(results.getInt(8)) != null) {
//						tempOrderDishMap.put(
//								results.getInt(7),
//								new OrderDish(results.getInt(1), dishMap
//										.get(results.getInt(8))));
//					}
//				}
//				if (extraMap.get(results.getInt(9)) != null) {
//					tempOrderDishMap.get(results.getInt(7)).addExtra(
//							extraMap.get(results.getInt(9)));
//				}
//			}
//			for (OrderDish d : tempOrderDishMap.values()) {
//				if (orderMap.get(d.orderID) != null) {
//					orderMap.get(d.orderID).addOrderDish(d);
//				}
//			}
//			results.close();
//		} catch (SQLException e) {
//			System.err.println("An error occured while updating the lists"
//					+ e.getMessage());
//		}
//		eventDispatcher.dispatchEvent(new Event<Object>(EventType.DATABASE_LISTS_UPDATED));
//	}
//
//	/**
//	 * Creates and returns the order commentlist, which is needed when creating
//	 * the lists
//	 * 
//	 * @return a {@link java.util.HashMap HashMap} from commentID to comment
//	 */
//
//	private static HashMap<Integer, String> createOrderCommentList() {
//		HashMap<Integer, String> orderComments = new HashMap<Integer, String>();
//		ResultSet results = DatabaseConnection
//				.fetchData("SELECT * FROM OrderComments;");
//		orderComments.put(-1, "");
//		try {
//			while (results.next()) {
//				orderComments.put(results.getInt(1), results.getString(2));
//			}
//			results.close();
//		} catch (SQLException e) {
//			System.err
//					.println("An error occured while creating the commentlist: "
//							+ e.getMessage());
//		}
//		return orderComments;
//	}
//
//	public static ArrayList<Order> getOrderList() {
//		return orderList;
//	}
//
//	public static HashMap<Integer, Order> getOrderMap() {
//		return orderMap;
//	}
//
//	public static HashMap<String, Order> getCustomerToOrderMap() {
//		return customerToOrderMap;
//	}
//
//	/**
//	 * Method that receives an {@link pizzaProgram.dataObjects.Order order}
//	 * object and adds it to the database.
//	 * 
//	 * @param o
//	 *            the {@link pizzaProgram.dataObjects.Order order}, with
//	 *            contents (dishes extras etc), to be added to the database
//	 */
//	public static void addOrder(Order o) {
//		if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
//			System.err
//					.println("No active database connection: please try again!");
//			return;
//		}
//		try {
//			if (!DatabaseConnection.fetchData(
//					"SELECT * FROM Orders WHERE CustomerID="
//							+ o.customer.customerID + " AND OrdersStatus='"
//							+ Order.REGISTERED + "';").next()) {
//				int commentID = -1;
//				if (!(o.comment == null || o.comment.equals(""))) {
//					DatabaseConnection
//							.insertIntoDB("INSERT INTO OrderComments (Comment) VALUES ('"
//									+ o.comment + "');");
//					ResultSet commentIDset = DatabaseConnection
//							.fetchData("SELECT CommentID FROM OrderComments WHERE Comment='"
//									+ o.comment + "';");
//					if (commentIDset.next()) {
//						commentID = commentIDset.getInt(1);
//					}
//				}
//				DatabaseConnection
//						.insertIntoDB("INSERT INTO Orders (CustomerID, TimeRegistered, DeliveryMethod, CommentID) VALUES ("
//								+ o.customer.customerID
//								+ ", NOW(), '"
//								+ o.deliveryMethod + "', " + commentID + ");");
//			}
//		} catch (SQLException e) {
//			System.err.println("An error occured during your database query: "
//					+ e.getMessage());
//			return;
//		}
//		updateOrders();
//		int orderID = customerToOrderMap.get(o.customer.customerID
//				+ Order.REGISTERED).orderID;
//		for (OrderDish od : o.getOrderedDishes()) {
//			int ordersContentsID = -1;
//			DatabaseConnection
//					.insertIntoDB("INSERT INTO OrdersContents (OrdersID, DishID) VALUES ("
//							+ orderID + ", " + od.dish.dishID + ");");
//			if (!(od.getExtras() == null || od.getExtras().isEmpty())) {
//				ResultSet currentOrderContentsID = DatabaseConnection
//						.fetchData("SELECT OrdersContentsID FROM OrdersContents WHERE OrdersID="
//								+ orderID
//								+ " AND DishID="
//								+ od.dish.dishID
//								+ " ORDER BY OrdersContentsID;");
//				try {
//					if (currentOrderContentsID.last()) {
//						ordersContentsID = currentOrderContentsID.getInt(1);
//					}
//				} catch (SQLException e) {
//					System.err
//							.println("An error occured while adding extras to the dish: "
//									+ e.getMessage());
//				}
//				for (Extra e : od.getExtras()) {
//					DatabaseConnection
//							.insertIntoDB("INSERT INTO DishExtrasChosen (OrdersContentsID, DishExtraID) VALUES ("
//									+ ordersContentsID + ", " + e.id + ");");
//				}
//			}
//		}
//
//	}
//
//	public static void changeOrderStatus(Order order, String status) {
//		if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
//			System.err
//					.println("No active database connection: please try again!");
//			return;
//		}
//		DatabaseConnection.insertIntoDB("UPDATE Orders SET OrdersStatus='"
//				+ status + "' WHERE OrdersID=" + order.orderID + ";");
//	}
//
//	// TODO: Add a remove order method, if we want to have one.
//
//	@Override
//	public void handleEvent(Event<?> event) {
//		if (event.eventType.equals(EventType.DATABASE_UPDATE_REQUESTED)) {
//			updateOrders();
//		} else if (event.eventType.equals(EventType.ADD_ORDER_REQUESTED)) {
//			if (event.getEventParameterObject() instanceof Order) {
//				addOrder((Order) event.getEventParameterObject());
//			}
//		}
//	}
//}