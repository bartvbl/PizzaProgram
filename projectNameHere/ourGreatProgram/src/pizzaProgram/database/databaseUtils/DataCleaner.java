package pizzaProgram.database.databaseUtils;
/**
 * The DataCleaner class is tasked with cleaning database data before sending it to the database
 * @author Bart
 *
 */
public class DataCleaner {
	/**
	 * Makes sure that the database does not regard single quotes as the end of the string inserted
	 * @param string the String to be cleaned
	 * @return A cleaned String that can ben sent to the database
	 */
	public static String cleanDbData(String string){
		string = string.replace("'", "\\'");
		string = string.trim();
		return string;
	}
}
