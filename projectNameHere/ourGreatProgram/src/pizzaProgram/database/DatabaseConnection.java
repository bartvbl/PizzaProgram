package pizzaProgram.database;

import java.lang.String;
import java.sql.*;
import java.util.ArrayList;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventHandler;

public class DatabaseConnection implements EventHandler {
	public static final String DATABASE_HOST = "jdbc:mysql://mysql.stud.ntnu.no/it1901g03_PizzaBase";
	public static final String DATABASE_USERNAME = "it1901g03_admin";
	public static final String DATABASE_PASSWORD = "batman";

	private Connection connection;
	private QueryHandler queryHandler;
	private Statement databaseStatement;

	public DatabaseConnection() throws SQLException {
		// this.queryHandler = new QueryHandler();
		connect();
		
	}

	public void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(DATABASE_HOST, DATABASE_USERNAME, DATABASE_PASSWORD);
			System.out.println("The connection was a success!.");
			databaseStatement = connection.createStatement();
			ResultSet queryResults = databaseStatement.executeQuery("SELECT * FROM Dishes");

		} catch (SQLException e) {
			System.out.println("Connection failed: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Failed during driverinitialization: "
					+ e.getMessage());
		} catch (InstantiationException e) {
			System.out.println("Failed during driverinstantiation:"
					+ e.getMessage());
		} catch (IllegalAccessException e) {
			System.out.println("Failed during driverinstantiation:"
					+ e.getMessage());
		}
	}

	public void disconnect() {
		if (connection != null) {
			try {
				this.connection.close();
			} catch (SQLException e) {
				System.out.println("Failed to clise MySQL connection!");
			}
		}
	}

	public boolean isConnected(int timeoutInMilliseconds) {
		if (this.connection != null) {
			try {
				if (this.connection.isValid(timeoutInMilliseconds)) {
					return true;
				}
			} catch (SQLException e) {
				return false;
			}
		}
		return false;
	}

	// HERE BE JUKSEMETODER
	private ResultSet executeQuery(String q) {
		try {
			Statement statement = connection.createStatement();
			return statement.executeQuery(q);
		} catch (SQLException e) {
			System.out.println("Query Failed: " + e.getMessage());
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

	public ArrayList<Customer> getCustomers() {
		return null;
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
		
		//TESTER HER
		ResultSet rs = connection.executeQuery("SELECT * FROM Dishes;");
		while(rs.next()){
			System.out.println(rs.getInt(1));
			System.out.println(rs.getString(2));
			System.out.println(rs.getString(3));
		}
		rs.close();
	}

	public void handleEvent(Event event) {

		this.queryHandler.handleEvent(event);
	}
}