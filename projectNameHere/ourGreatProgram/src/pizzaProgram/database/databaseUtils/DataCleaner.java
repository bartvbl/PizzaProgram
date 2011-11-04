package pizzaProgram.database.databaseUtils;

public class DataCleaner {
	public static String cleanDbData(String string)
	{
		return string.replace("'", "\\'");
	}
}
