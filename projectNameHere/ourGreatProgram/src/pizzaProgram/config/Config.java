package pizzaProgram.config;

import pizzaProgram.database.DatabaseConnection;

/**
 * The Config class is a utility class that retrieves config values from the database
 * @author Bart
 *
 */
public class Config {
	/**
	 * Holds the reference to the database connection, so that the class can use it to retrieve config values
	 */
	private final DatabaseConnection databaseConnection;
	
	/**
	 * The constructor takes in the instance of DatabaseConnection and stores it for later use
	 * @param connection The instance of DatabaseConnection representing a connection to the database
	 */
	public Config(DatabaseConnection connection)
	{
		this.databaseConnection = connection;
	}
	
	
	//TODO: add possible config keys to javadoc?
	/**
	 * Returns the string value of the config key name inserted
	 * @param keyName The name of the config key
	 * @return the resultant config value as stored in the database
	 */
	public String getConfigValueByName(String keyName)
	{
		return null;
	}
}
