package pizzaProgram.config;

import java.util.ArrayList;

import pizzaProgram.dataObjects.Setting;
import pizzaProgram.database.databaseUtils.DatabaseReader;
import pizzaProgram.database.databaseUtils.DatabaseWriter;

/**
 * The Config class is a utility class that retrieves config values from the
 * database. Valid config keys are stored in the
 * {@link pizzaProgram.constants.DatabaseQueryConstants DatabaseQueryConstants}
 * class, and all of the names of these constants start with the prefix
 * SETTING_KEY_
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class Config {
	/*
	 * Constants pertaining to the values in the key fields of the Config table
	 */
	/**
	 * Key value for the free delivery limit
	 * {@link pizzaProgram.dataObjects.Setting setting}
	 */
	public static final String KEY_FREE_DELIVERY_LIMIT = "freeDeliveryLimit";
	/**
	 * Key value for the restaurant name
	 * {@link pizzaProgram.dataObjects.Setting setting}
	 */
	public static final String KEY_RESTAURANT_NAME = "restaurantName";
	/**
	 * Key value for the restaurant address
	 * {@link pizzaProgram.dataObjects.Setting setting}
	 */
	public static final String KEY_RESTAURANT_ADDRESS = "restaurantAddress";
	/**
	 * Key value for the restaurant city
	 * {@link pizzaProgram.dataObjects.Setting setting}
	 */
	public static final String KEY_RESTAURANT_CITY = "restaurantCity";
	/**
	 * Key value for the delivery price {@link pizzaProgram.dataObjects.Setting
	 * setting}
	 */
	public static final String KEY_DELIVERY_PRICE = "deliveryPrice";
	/**
	 * Key value for the delivery at home VAT
	 * {@link pizzaProgram.dataObjects.Setting setting}
	 */
	public static final String KEY_DELIVERY_AT_HOME_TAX = "deliveryAtHomeTax";
	/**
	 * Key value for the pickup at restaurant VAT
	 * {@link pizzaProgram.dataObjects.Setting setting}
	 */
	public static final String KEY_PICKUP_AT_RESTAURANT_TAX = "pickupAtRestaurantTax";
	/**
	 * Key value for the Automatic Data Updates in Milliseconds
	 * {@link pizzaProgram.dataObjects.Setting setting}
	 */
	public static final String KEY_DB_AUTO_UPDATE_MILLIS =  "autoDataUpdateDelayInMillis";
	/**
	 * The value that the auto update time must be to disable automatic updating of the data
	 */
	public static final int KEY_DISABLE_DATA_AUTO_UPDATE_VALUE = -1;
	
	/**
	 * Returns the String value of the config key provided to the method
	 * 
	 * @param key
	 *            The config key to find the corresponding value for
	 * @return the resultant config value as stored in the database
	 */
	public static String getConfigValueByKey(String key) {
		Setting setting = fetchSetting(key);
		return setting.value;
	}

	/**
	 * Retrieves the setting represented by the key from the database, and
	 * returns a {@link pizzaProgram.dataObjects.Setting Setting} object
	 * instance
	 * 
	 * @param key
	 *            The key of the setting
	 * @return A {@link pizzaProgram.dataObjects.Setting Setting} object
	 *         encapsulating the provided key and its corresponding value as
	 *         stored in the database
	 */
	public static Setting getSettingByKey(String key) {
		return fetchSetting(key);
	}

	/**
	 * Retrieves a list of all settings from the database
	 * 
	 * @return An ArrayList containing {@link pizzaProgram.dataObjects.Setting
	 *         Setting} objects encapsulating all settings found in the Config
	 *         table of the database
	 */
	public static ArrayList<Setting> fetchAllSettings() {
		ArrayList<Setting> settings = DatabaseReader.getAllSettings();
		return settings;
	}

	/**
	 * Retrieves the setting from the database represented by the inserted key
	 * 
	 * @param key
	 *            The setting's key, representing the setting that should be
	 *            retrieved
	 * @return A {@link pizzaProgram.dataObjects.Setting Setting} instance
	 *         representing the setting found in the database
	 */
	private static Setting fetchSetting(String key) {
		Setting setting = DatabaseReader.getSettingByKey(key);
		return setting;
	}

	/**
	 * Updates the row in the database's Config table that has a key equal to
	 * the provided {@link pizzaProgram.dataObjects.Setting Setting} object's
	 * key field with the value of the provided
	 * {@link pizzaProgram.dataObjects.Setting Setting} object's value field.
	 * 
	 * @param setting
	 *            The {@link pizzaProgram.dataObjects.Setting Setting} instance,
	 *            whose key is used to determine the setting to update, and the
	 *            value is the new value that has to be written to the database
	 */
	public static void updateValueOfSetting(Setting setting) {
		DatabaseWriter.updateConfigValue(setting);
	}

	
}
