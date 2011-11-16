package pizzaProgram.database.databaseUtils;

public class DataCleaner {
	public static String cleanDbData(String string){
		string = string.replace("'", "\\'");
		string = string.trim();
		return string;
	}
}
