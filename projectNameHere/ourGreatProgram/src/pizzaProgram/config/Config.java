package pizzaProgram.config;

import java.util.ArrayList;

import pizzaProgram.dataObjects.Setting;
import pizzaProgram.database.databaseUtils.DatabaseReader;
import pizzaProgram.database.databaseUtils.DatabaseWriter;

/**
 * The Config class is a utility class that retrieves config values from the
 * database
 * 
 * @author Bart
 * 
 */
public class Config {
	// TODO: add possible config keys to javadoc?
	/**
	 * Returns the string value of the config key name inserted
	 * 
	 * @param keyName
	 *            The name of the config key
	 * @return the resultant config value as stored in the database
	 */
	public static String getConfigValueByKey(String keyName) {
		Setting setting = fetchSetting(keyName);
		return setting.value;
	}

	/**
	 * Retrieves the setting represented by the key from the database, and
	 * returns the setting's value
	 * 
	 * @param keyName
	 *            The key name of the setting
	 * @return The resultant value that this setting currently has
	 */
	public static Setting getSettingByKey(String keyName) {
		return fetchSetting(keyName);
	}

	/**
	 * Retrieves a list of all settings from the database
	 * 
	 * @return An ArrayList containing all settings found in the database's
	 *         Config table
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
	 * @return A Setting instance representing the setting found in the database
	 */
	private static Setting fetchSetting(String key) {
		Setting setting = DatabaseReader.getSettingByKey(key);
		return setting;
	}

	/**
	 * Updates the setting in the database that has a key of the parameter's
	 * key, and updates the database's value to the parameter's Setting
	 * instance's value.
	 * 
	 * @param setting
	 *            The Setting instance, whose key is used to determine the
	 *            setting to update, and the value is the new value that has to
	 *            be written to the database
	 */
	public static void updateValueOfSetting(Setting setting) {
		DatabaseWriter.updateConfigValue(setting);
	}
}
