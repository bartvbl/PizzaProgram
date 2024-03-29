package pizzaProgram.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import pizzaProgram.events.Event;
import pizzaProgram.events.EventHandler;

/**
 * TODO: Create class javadoc TODO: Add input sanitation
 * 
 * @author Håvard Eidheim, Henning M. Wold
 * 
 */
public class DatabaseConnection implements EventHandler {
	private static final String URL_TYPE = "URL";
	private static final String USERNAME_TYPE = "USERNAME";
	private static final String PASSWORD_TYPE = "PASSWORD";
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

	private static Connection connection;
	private static QueryHandler queryHandler;

	/**
	 * Method that attempt to make a connection to the mySQL database that
	 * contains the data.
	 */
	public static void connect() {
		try {
			String url = "jdbc:";
			String username = "";
			String password = "";
			BufferedReader br = new BufferedReader(new FileReader("config/databaseinfo.cfg"));
			while (br.ready()) {
				String read = br.readLine();
				if (!(read==null||read.charAt(0)=='#')) {
					String[] line = read.split(":", 2);
					System.out.println(line[0] + " " + line[1]);
					String type = "";
					String contents = "";
					if (line.length == 2) {
						type = line[0];
						contents = line[1].trim();
					}
					else {
						System.out.println("There is an error in the configuration file in one of the uncommented lines.");
					}
					if (type.equals(URL_TYPE)) {
						url += contents;
					}
					else if (type.equals(USERNAME_TYPE)) {
						username = contents;
					}
					else if (type.equals(PASSWORD_TYPE)) {
						password = contents;
					}
				}
			}
			System.out.println(url + username + password);
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(url,
					username, password);
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
		} catch (FileNotFoundException e) {
			System.out.println("Unable to find the configurationfile for the database."
					+ "Please make sure ../config/databaseinfo.cfg exists: " + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to read the configfile." + e.getMessage());
			e.printStackTrace();
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
	public static boolean disconnect() {
		if (connection != null
				&& isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
			try {
				connection.close();
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
	public static boolean isConnected(int timeoutInMilliseconds) {
		if (connection != null) {
			try {
				return connection.isValid(timeoutInMilliseconds);
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
	static ResultSet fetchData(String query) {
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
	static boolean insertIntoDB(String query) {
		try {
			return connection.createStatement().execute(query);
		} catch (SQLException e) {
			System.err.println("Query Failed: " + e.getMessage());
			return false;
		}
	}

	public void handleEvent(Event event) {

		queryHandler.handleEvent(event);
	}
}