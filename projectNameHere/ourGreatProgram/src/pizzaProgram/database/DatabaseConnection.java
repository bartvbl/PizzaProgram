package pizzaProgram.database;

import java.lang.String;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventHandler;

public class DatabaseConnection implements EventHandler {
	public static final String DATABASE_HOST = "jdbc:mysql://mysql.stud.ntnu.no/it1901g03_PizzaBase";
	public static final String DATABASE_USERNAME = "it1901g03_admin";
	public static final String DATABASE_PASSWORD = "batman";

	private Connection connection;
	private QueryHandler queryHandler;
	private ArrayList<Customer> customers;
	private HashMap<Integer, Customer> customerMap;
	private HashMap<Integer, String> customerComments;
	private HashMap<Integer, String> orderComments;
	private ArrayList<Dish> dishes;
	private HashMap<Integer, Dish> dishMap;
	private ArrayList<Extra> extras;
	private ArrayList<Order> orders;

	public DatabaseConnection() throws SQLException {
		// this.queryHandler = new QueryHandler();
	}

	/**
	 * Method that attempt to make a connection to the mySQL database that
	 * contains the data.
	 */
	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(DATABASE_HOST,
					DATABASE_USERNAME, DATABASE_PASSWORD);
			System.out.println("The connection was a success!");

		} catch (SQLException e) {
			System.out.println("Connection failed: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Failed during driverinitialization: "
					+ e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("Failed during driverinstantiation: "
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println("Failed during driverinstantiation: "
					+ e.getMessage());
		}
	}

	public void disconnect() {
		if (connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				System.out.println("Failed to close MySQL connection!");
			}
		}
	}

	public boolean isConnected(int timeoutInMilliseconds) {
		if (this.connection != null) {
			try {
				return this.connection.isValid(timeoutInMilliseconds);
			} catch (SQLException e) {
				System.err.println("Something is wrong with the connection: "
						+ e.getMessage());
				return false;
			}
		}
		return false;
	}

	// HERE BE JUKSEMETODER
	/**
	 * 
	 */
	private ResultSet executeQuery(String query) {
		try {
			return connection.createStatement().executeQuery(query);
		} catch (SQLException e) {
			System.err.println("Query Failed: " + e.getMessage());
		}
		return null;
	}

	public ArrayList<Order> getOrders() {
		ArrayList<Order> orders = new ArrayList<Order>();

		ResultSet results = executeQuery("");

		try {

			for (int i = 0; results.next(); i++) {
				results.getString(i);

				// TODO her leser vi inn

			}
			results.close();

		} catch (SQLException e) {
		}

		return null;
	}

	public void createCustomerList() throws SQLException {
		customers = new ArrayList<Customer>();
		customerMap = new HashMap<Integer, Customer>();
		ResultSet results = executeQuery("SELECT * FROM Customer;");
		while (results.next()) {
			Customer tempCustomer = new Customer(results.getInt(1),
					results.getString(2), results.getString(3),
					results.getString(4), results.getInt(5),
					customerComments.get(results.getInt(6)));

			customers.add(tempCustomer);
			customerMap.put(tempCustomer.getCustomerID(), tempCustomer);
		}
		results.close();
	}

	public void createCustomerCommentList() throws SQLException {
		customerComments = new HashMap<Integer, String>();
		ResultSet results = executeQuery("SELECT * FROM CustomerNotes;");
		customerComments.put(-1, "");
		while (results.next()) {
			customerComments.put(results.getInt(1), results.getString(2));
		}
		results.close();
	}

	public void createOrderCommentList() throws SQLException {
		orderComments = new HashMap<Integer, String>();
		ResultSet results = executeQuery("SELECT * FROM OrderComments;");
		orderComments.put(-1, "");
		while (results.next()) {
			orderComments.put(results.getInt(1), results.getString(2));
		}
		results.close();
	}

	public void createDishList() throws SQLException {
		dishes = new ArrayList<Dish>();
		dishMap = new HashMap<Integer, Dish>();
		ResultSet results = executeQuery("SELECT * FROM Dishes;");
		while (results.next()) {
			Dish tempDish = new Dish(results.getInt(1), results.getInt(2),
					results.getString(3), results.getBoolean(4),
					results.getBoolean(5), results.getBoolean(6),
					results.getBoolean(7), results.getBoolean(8),
					results.getString(9));

			dishes.add(tempDish);
			dishMap.put(tempDish.getDishID(), tempDish);
		}
		results.close();
	}

	public void createExtrasList() throws SQLException {
		extras = new ArrayList<Extra>();
		ResultSet results = executeQuery("SELECT * FROM Extras;");
		while (results.next()) {
			extras.add(new Extra(results.getInt(1), results.getInt(1), results
					.getString(2)));
		}
		results.close();
	}

	public void createOrdersList() throws SQLException {
		orders = new ArrayList<Order>();
		
		ResultSet results = executeQuery("SELECT Orders.OrdersID,"
				+ " Orders.CustomerID, Orders.TimeRegistered,"
				+ " Orders.OrdersStatus, Orders.DeliveryMethod,"
				+ " Orders.CommentID, OrdersContents.OrdersContentsID,"
				+ " OrdersContents.DishID, DishExtrasChosen.DishExtraID"
				+ " FROM Orders LEFT JOIN OrdersContents ON"
				+ " Orders.OrdersID=OrdersContents.OrdersID"
				+ " LEFT JOIN DishExtrasChosen ON"
				+ " OrdersContents.OrdersContentsID=DishExtrasChosen.OrdersContentsID "
				+ "ORDER BY Orders.OrdersID;");

		while (results.next()) {

		}
		results.close();
	}

	public ArrayList<Customer> getCustomers() {
		return this.customers;
	}

	public ArrayList<Dish> getDishes() {
		return dishes;
	}

	public ArrayList<Extra> getExtras() {
		return extras;
	}

	public void newOrder(Order order) {

	}

	public void newCustomer(Customer customer) {

	}

	public void changeOrder(Order order) {
	}

	// SLUTT P� JUKSEMETODER

	public DatabaseTable databaseQuery(String query) {
		return null;
	}

	public static void main(String[] args) throws SQLException {
		DatabaseConnection connection = new DatabaseConnection();
		connection.connect();
		connection.createCustomerCommentList();
		connection.createOrderCommentList();
		connection.createCustomerList();
		connection.createDishList();
		connection.createExtrasList();
		for (Customer c : connection.getCustomers()) {
			System.out.println(c.toString());
		}
		for (Dish d : connection.getDishes()) {
			System.out.println(d.toString());
		}
		for (Extra e : connection.getExtras()) {
			System.out.println(e.toString());
		}

		connection.disconnect();
	}

	public void handleEvent(Event event) {

		this.queryHandler.handleEvent(event);
	}
}