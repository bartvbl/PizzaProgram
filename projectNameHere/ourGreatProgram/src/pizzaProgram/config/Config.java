package pizzaProgram.config;

import java.util.ArrayList;

import pizzaProgram.dataObjects.Setting;
import pizzaProgram.database.databaseUtils.DatabaseReader;

/**
 * The Config class is a utility class that retrieves config values from the database
 * @author Bart
 *
 */
public class Config {
	//TODO: add possible config keys to javadoc?
	/**
	 * Returns the string value of the config key name inserted
	 * @param keyName The name of the config key
	 * @return the resultant config value as stored in the database
	 */
	public static String getConfigValueByKey(String keyName)
	{
		Setting setting = fetchSetting(keyName);
		return setting.value;
	}
	
	public static Setting getSettingByKey(String keyName)
	{
		return fetchSetting(keyName);
	}
	
	public static ArrayList<Setting> fetchAllSettings()
	{
		ArrayList<Setting> settings = DatabaseReader.getAllSettings();
		return settings;
	}
	
	private static Setting fetchSetting(String key)
	{
		Setting setting = DatabaseReader.getSettingByKey(key);
		return setting;
	}
}
