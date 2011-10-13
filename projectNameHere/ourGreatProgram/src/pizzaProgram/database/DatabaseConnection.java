package pizzaProgram.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventHandler;

/**
 * TODO: Create class javadoc TODO: Add input sanitation
 * 
 * @author Håvard Eidheim, Henning M. Wold
 * 
 */
public class DatabaseConnection implements EventHandler {
	private static final String DATABASE_HOST = "jdbc:mysql://mysql.stud.ntnu.no/it1901g03_PizzaBase";
	private static final String DATABASE_USERNAME = "it1901g03_admin";
	private static final String DATABASE_PASSWORD = "batman";
	public static final int DEFAULT_TIMEOUT = 3000;
	/**
	 * The maximum amount of allowed characters in a short VARCHAR column in the
	 * database
	 */
	static final int VARCHAR_MAX_LENGTH_SHORT = 50;
	/**
	 * The maximum amount of allowed characters in a long VARCHAR column in the
	 * database
	 */
	static final int VARCHAR_MAX_LENGTH_LONG = 100;

	private Connection connection;
	private QueryHandler queryHandler;

	public DatabaseConnection() {
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

	/**
	 * Method that tries to disconnect from the database if there is a valid
	 * connection
	 * 
	 * @return returns true if there was no valid connection, or the
	 *         disconnection was successful. Returns false if the method was
	 *         unable to disconnect from the database.
	 */
	public boolean disconnect() {
		if (connection != null
				&& isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				System.out.println("Failed to close MySQL connection: "
						+ e.getMessage());
				return false;
			}
		}
		return true;
	}

	/**
	 * Method that checks if the the client is still connected to the database.
	 * 
	 * @param timeoutInMilliseconds
	 *            The time in milliseconds as an int to wait for an answer from
	 *            the server
	 * @return true if the connection is still valid, false if not
	 */
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

	/**
	 * Method to fetch data from the the mySQL database using the provided query
	 * 
	 * @param query
	 *            - a {@link java.lang.String String} containing the query that
	 *            is to be sent to the database
	 * @return a {@link java.sql.ResultSet ResultSet} containing the result of
	 *         the query
	 */
	ResultSet fetchData(String query) {
		try {
			return connection.createStatement().executeQuery(query);
		} catch (SQLException e) {
			System.err.println("Query Failed: " + e.getMessage());
		}
		return null;
	}

	/**
	 * Method to insert data into the mySQL database using the provided query
	 * 
	 * @param query
	 *            - a {@link java.lang.String String} containing the query that
	 *            is to be sent to the database
	 * @return true if the insertion was a success, false in all other cases
	 */
	boolean insertIntoDB(String query) {
		try {
			return connection.createStatement().execute(query);
		} catch (SQLException e) {
			System.err.println("Query Failed: " + e.getMessage());
			return false;
		}
	}

	public static void main(String[] args) throws SQLException {
		DatabaseConnection connection = new DatabaseConnection();
		connection.connect();
		long starttid = System.currentTimeMillis();
		DatabaseConnection databaseConnection = new DatabaseConnection();
		CustomerList customerList = null;
		DishList dishList = null;
		ExtraList extraList = null;
		OrderList orderList = null;
		try {
			databaseConnection.connect();
		} catch (Exception e) {
			System.out.println("SHENANIGANS!");
		}
		customerList = new CustomerList(databaseConnection);
		dishList = new DishList(databaseConnection);
		extraList = new ExtraList(databaseConnection);
		orderList = new OrderList(databaseConnection, customerList, dishList,
				extraList);
		for (Customer c : customerList.getCustomerList()) {
			System.out.println(c.toString());
		}
		for (Dish d : dishList.getDishList()) {
			System.out.println(d.toString());
		}
		for (Extra e : extraList.getExtraList()) {
			System.out.println(e.toString());
		}
		for (Order o : orderList.getOrderList()) {
			System.out.println(o.toString());
		}
		System.out.println(System.currentTimeMillis() - starttid);
		System.out.println();
		connection.disconnect();
	}

	public void handleEvent(Event event) {

		this.queryHandler.handleEvent(event);
	}
}