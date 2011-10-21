package pizzaProgram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;

/**
 * Object for handling extras in the database. At construction the object
 * creates an {@link java.util.ArrayList ArrayList} and a
 * {@link java.util.HashMap HashMap} of all the different
 * {@link pizzaProgram.dataObject.Order orders} based on a fetch from the
 * database; these lists are publically available through the getter methods. In
 * addition it creates a {@link java.util.HashMap HashMap} of the open orders
 * with a mapping from customer to order for use in the GUI. <br>
 * <br>
 * For now it is suggested to discard this object any time a change occurs to an
 * order in the database, and reconstruct it by a call to the constructor. <br>
 * <br>
 * The methods of the class handles the addition of new orders to the database
 * with two methods: one for adding an {@link pizzaProgram.dataObject.Order
 * order}, and another for adding {@link pizzaProgram.dataObject.OrderDish
 * ordered dishes} to the order. <br>
 * <br>
 * CAUTION: ALL the other lists must be created before the OrderList can be
 * created, as it uses the maps created with the other lists to correctly assign
 * data.
 * 
 * @author IT1901 Group 03, Fall 2011
 */

// TODO: Dispatch an event whenever the lists are updated

//TODO: customertoordermap should use string customerid + orderstatus, one order pr status
public class OrderList {
	private ArrayList<Order> orderList;
	private HashMap<Integer, Order> orderMap;
	private HashMap<Customer, Order> customerToOrderMap;
	private DatabaseConnection dbCon;
	
	private CustomerList cl;
	private DishList dl;
	private ExtraList el;
	
	/**
	 * Constructor that creates the list objects as specified in the class
	 * javadoc
	 * 
	 * @param dbCon
	 *            - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @param cl
	 *            - the {@link pizzaProgram.database.CustomerList customer list}
	 *            this order list is based upon.
	 * @param dl
	 *            - the {@link pizzaProgram.database.DishList dish list} this
	 *            order list is based upon.
	 * @param el
	 *            - the {@link pizzaProgram.database.ExtraList extra list} this
	 *            order list is based upon.
	 * @throws SQLException
	 */

	public OrderList(DatabaseConnection dbCon, CustomerList cl, DishList dl,
			ExtraList el) throws SQLException {
		this.cl = cl;
		this.dl = dl;
		this.el = el;
		this.dbCon = dbCon;
		if (!(dbCon != null && dbCon
				.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err
					.println("No active database connection: please try again!");
			return;
		}
		this.updateOrders();
	}

	public void updateOrders() {
		orderList = new ArrayList<Order>();
		orderMap = new HashMap<Integer, Order>();
		customerToOrderMap = new HashMap<Customer, Order>();
		cl.updateCustomers();
		dl.updateDishes();
		el.updateExtras();
		HashMap<Integer, Customer> customerMap = cl.getCustomerMap();
		HashMap<Integer, Dish> dishMap = dl.getDishMap();
		HashMap<Integer, Extra> extraMap = el.getExtraMap();
		HashMap<Integer, OrderDish> tempOrderDishMap = new HashMap<Integer, OrderDish>();
		HashMap<Integer, String> orderComments = createOrderCommentList();
		ResultSet results = dbCon
				.fetchData("SELECT Orders.OrdersID,"
						+ " Orders.CustomerID, Orders.TimeRegistered,"
						+ " Orders.OrdersStatus, Orders.DeliveryMethod,"
						+ " Orders.CommentID, OrdersContents.OrdersContentsID,"
						+ " OrdersContents.DishID, DishExtrasChosen.DishExtraID"
						+ " FROM Orders LEFT JOIN OrdersContents ON"
						+ " Orders.OrdersID=OrdersContents.OrdersID"
						+ " LEFT JOIN DishExtrasChosen ON"
						+ " OrdersContents.OrdersContentsID=DishExtrasChosen.OrdersContentsID "
						+ "ORDER BY Orders.OrdersID;");

		try {
			while (results.next()) {
				if (orderMap.get(results.getInt(1)) == null) {
					Order tempOrder = new Order(results.getInt(1),
							customerMap.get(results.getInt(2)),
							results.getDate(3), results.getString(4),
							results.getString(5), orderComments.get(results
									.getInt(6)));
					orderList.add(tempOrder);
					orderMap.put(results.getInt(1), tempOrder);
					if (!tempOrder.status.equals(Order.DELIVERED)) {
						customerToOrderMap.put(tempOrder.customer, tempOrder);
					}

				}
				if (tempOrderDishMap.get(results.getInt(7)) == null) {
					if (dishMap.get(results.getInt(8)) != null) {
						tempOrderDishMap.put(
								results.getInt(7),
								new OrderDish(results.getInt(1), dishMap
										.get(results.getInt(8))));
					}
				}
				if (extraMap.get(results.getInt(9)) != null) {
					tempOrderDishMap.get(results.getInt(7)).addExtra(
							extraMap.get(results.getInt(9)));
				}
			}
			for (OrderDish d : tempOrderDishMap.values()) {
				if (orderMap.get(d.orderID) != null) {
					orderMap.get(d.orderID).addOrderDish(d);
				}
			}
			results.close();
		} catch (SQLException e) {
			System.err.println("An error occured during your query: "
					+ e.getMessage());
		}

	}

	/**
	 * Creates and returns the order commentlist, which is needed when creating
	 * the lists
	 * 
	 * @param dbCon
	 *            - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @return a {@link java.util.HashMap HashMap} from commentID to comment
	 * @throws SQLException
	 */

	private HashMap<Integer, String> createOrderCommentList() {
		HashMap<Integer, String> orderComments = new HashMap<Integer, String>();
		ResultSet results = dbCon.fetchData("SELECT * FROM OrderComments;");
		orderComments.put(-1, "");
		try {
			while (results.next()) {
				orderComments.put(results.getInt(1), results.getString(2));
			}
			results.close();
		} catch (SQLException e) {
			System.err.println("An error occured during your query: "
					+ e.getMessage());
		}
		return orderComments;
	}

	public ArrayList<Order> getOrderList() {
		return orderList;
	}

	public HashMap<Integer, Order> getOrderMap() {
		return orderMap;
	}

	public HashMap<Customer, Order> getCustomerToOrderMap() {
		return customerToOrderMap;
	}

	/**
	 * Method that adds a new order to the database.<br>
	 * The fields for time registered and order status are automatically set to,
	 * respectively, the current time and "registered" upon creation.
	 * 
	 * @param dbCon
	 *            - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @param customer
	 *            - the {@link pizzaProgram.dataObjects.Customer customer} this
	 *            order belongs to
	 * @param isDeliverAtHome
	 *            - set to true if this is a delivery, false if customer will
	 *            pick up this order at the restaurant
	 * @param comment
	 *            - any additional comments to this order as a String
	 * @return returns true if the order was successfully added to the database,
	 *         false in all other cases
	 * @throws SQLException
	 */

	public boolean addOrder(Customer customer, boolean isDeliverAtHome,
			String comment) {
		if (!(dbCon != null && dbCon
				.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err
					.println("No active database connection: please try again!");
			return false;
		}
		String deliverymethod = (isDeliverAtHome ? "deliver at home"
				: "pickup at restaurant");
		try {
			if (!dbCon.fetchData(
					"SELECT * FROM Orders WHERE CustomerID="
							+ customer.customerID
							+ " AND OrdersStatus<>'delivered';").next()) {
				int commentID = -1;
				if (!(comment == null || comment.equals(""))) {
					dbCon.insertIntoDB("INSERT INTO OrderComments (Comment) VALUES ('"
							+ comment + "');");
					ResultSet commentIDset = dbCon
							.fetchData("SELECT CommentID FROM OrderComments WHERE Comment='"
									+ comment + "';");
					if (commentIDset.next()) {
						commentID = commentIDset.getInt(1);
					}
				}
				return dbCon
						.insertIntoDB("INSERT INTO Orders (CustomerID, TimeRegistered, DeliveryMethod, CommentID) VALUES ("
								+ customer.customerID
								+ ", NOW(), '"
								+ deliverymethod + "', " + commentID + ");");
			}
		} catch (SQLException e) {
			System.err.println("An error occured during your database query: "
					+ e.getMessage());
			return false;
		}
		return false;

	}

	/**
	 * Method for adding a dish together with its associated extras to an order
	 * in the database
	 * 
	 * @param dbCon
	 *            - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @param order
	 *            - the {@link pizzaProgram.dataObjects.Order order} this dish
	 *            and extras belongs to
	 * @param dish
	 *            - the {@link pizzaProgram.dataObjects.Dish dish} to be added
	 *            to the order
	 * @param extras
	 *            - the {@link pizzaProgram.dataObjects.Extra extras}, in an
	 *            {@link java.util.ArrayList ArrayList}, that is to be added to
	 *            the dish added to the order
	 * @throws SQLException
	 */

	public void addDishToOrder(Order order, Dish dish, ArrayList<Extra> extras)
			throws SQLException {
		if (!(dbCon != null && dbCon
				.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err
					.println("No active database connection: please try again!");
			return;
		}
		int ordersContentsID = -1;
		dbCon.insertIntoDB("INSERT INTO OrdersContents (OrdersID, DishID) VALUES ("
				+ order.getID() + ", " + dish.dishID + ");");
		if (!(extras == null || extras.isEmpty())) {
			ResultSet currentOrderContentsID = dbCon
					.fetchData("SELECT OrdersContentsID FROM OrdersContents WHERE OrdersID="
							+ order.getID()
							+ " AND DishID="
							+ dish.dishID
							+ " ORDER BY OrdersContentsID;");
			if (currentOrderContentsID.last()) {
				ordersContentsID = currentOrderContentsID.getInt(1);
			}
			for (Extra e : extras) {
				dbCon.insertIntoDB("INSERT INTO DishExtrasChosen (OrdersContentsID, DishExtraID) VALUES ("
						+ ordersContentsID + ", " + e.id + ");");
			}
		}
	}

	public void changeOrderStatus(Order order, String status) {
		if (!(dbCon != null && dbCon
				.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err
					.println("No active database connection: please try again!");
			return;
		}
		dbCon.insertIntoDB("UPDATE Orders SET OrdersStatus='" + status
				+ "' WHERE OrdersID=" + order.orderID + ";");
	}
	// TODO: Add a remove order method, if we want to have one.
}