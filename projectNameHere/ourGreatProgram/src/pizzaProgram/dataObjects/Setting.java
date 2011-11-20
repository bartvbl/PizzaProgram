package pizzaProgram.dataObjects;
/**
 * A data object representing a setting in the database
 * @author Bart
 *
 */
public class Setting {
	public final String key;
	public final String value;
	
	/**
	 * Creates a new Setting instance
	 * @param key The key representing the key of the setting
	 * @param value The value of the setting representing by the key
	 */
	public Setting(String key, String value)
	{
		this.key = key;
		this.value = value;
	}
}
