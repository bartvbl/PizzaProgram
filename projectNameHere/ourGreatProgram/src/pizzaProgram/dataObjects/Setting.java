package pizzaProgram.dataObjects;


/**
 * A data object representing a setting in the database
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
public class Setting {
	public final String key;
	public final String value;

	/**
	 * Creates a new Setting instance
	 * 
	 * @param key
	 *            The key representing the key of the setting
	 * @param value
	 *            The value of the setting representing by the key
	 */
	public Setting(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Compares this instance to another
	 * @param setting The Setting to compare this instance to
	 * @return true if the ID's of both objects match.
	 */
	public boolean equals(Setting setting)
	{
		if(setting == null)
		{
			return false;
		}
		return this.key.equals(setting.key);
	}
}