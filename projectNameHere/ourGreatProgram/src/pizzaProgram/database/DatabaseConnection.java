package pizzaProgram.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class that handles the connection to the database. The URL username and
 * password to the database is stored in the config/databaseinfo.cfg file.
 * 
 * The class contains methods to check if there is an active connection, as well
 * as methods to send queries to and receive {@link java.sql.ResultSet
 * ResultSets} from the database.
 * 
 * @author Håvard Eidheim, Henning M. Wold
 * 
 */
public class DatabaseConnection {
	/**
	 * The default timeout period for database transactions, in case the connection is lost for any reason
	 */
	public static final int DEFAULT_TIMEOUT = 3000;
	/**
	 * The file path to the database credentials config file
	 */
	public static final String DATABASE_CREDENTIALS_CONFIG_FILE_PATH = "config/databaseinfo.cfg";
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
	
	/**
	 * A field that holds the connection to the database
	 */
	private static Connection connection;

	/**
	 * Method that attempts to make a connection to the mySQL database that
	 * contains the data, based on info stored in the config/databaseinfo.cfg
	 * file.
	 */
	public static void connect() {
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			DatabaseCredentials credentials = new DatabaseCredentials();
			credentials.loadFromFile(DatabaseConnection.DATABASE_CREDENTIALS_CONFIG_FILE_PATH);
			connection = DriverManager.getConnection(	credentials.getURL(), 
														credentials.getUsername(), 
														credentials.getPassword());
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
	 * @return true if there was no valid connection, or the disconnection was
	 *         successful; false if the method was unable to disconnect from the
	 *         database.
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
	public static ResultSet fetchData(String query) {
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
	public static boolean insertIntoDB(String query) {
		try {
			return connection.createStatement().execute(query);
		} catch (SQLException e) {
			System.err.println("Query Failed: " + e.getMessage());
			return false;
		}
	}
	
	/**
	 * Executes a write query like insertIntoDb(), but instead throws any errors such that they can be handled by any class that uses the function
	 * @param query The query to be executed by the database
	 * @return returns true if the query execution was a success, and false otherwise
	 * @throws SQLException Throws an SQLException if an error is raised during tge execution of the query.
	 */
	public static boolean executeWriteQuery(String query) throws SQLException {
			return connection.createStatement().execute(query);
	}
	
	/**
	 * Executes a write query in the database, and returns the row ID of the element inserted
	 * @param query The query to be executed in the database
	 * @return The ID of the last row inserted by the query
	 * @throws SQLException Throws an exception if an error is raised during the process
	 */
	public static int insertIntoDBAndReturnID(String query) throws SQLException
	{
		PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		int numberOfRowsAffected = statement.executeUpdate();
		if(numberOfRowsAffected == 0)
		{
			System.err.println("Query Failed (0 rows affected) ");
		}
		ResultSet generatedKey = statement.getGeneratedKeys();
		int id = -1;
		generatedKey.next();
		id = generatedKey.getInt(1);
		return id;
	}
}