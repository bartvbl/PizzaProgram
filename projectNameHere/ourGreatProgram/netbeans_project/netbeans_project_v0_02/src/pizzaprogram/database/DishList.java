package pizzaProgram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import pizzaProgram.dataObjects.Dish;

/**
 * Object for handling dishes in the database. At construction the object
 * creates an {@link java.util.ArrayList ArrayList} and a
 * {@link java.util.HashMap HashMap} of all the different
 * {@link pizzaProgram.dataObject.Dish dishes} based on a fetch from the
 * database; these lists are publically available through the getter methods. 
 * For now it is suggested to discard this object any time a change
 * occurs to a dish in the database, and reconstruct it by a call to the 
 * constructor. The methods of the class handles removal of existing dishes 
 * from the database, as well as adding new dishes to the database.
 * 
 * @author IT1901 Group 03, Fall 2011
 */

//TODO: Dispatch an event whenever the lists are updated

public class DishList {
	private ArrayList<Dish> dishList;
	private HashMap<Integer, Dish> dishMap;

	/**
	 * Constructor that creates the list objects as specified in the class javadoc
	 * 
	 * @param dbCon - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @throws SQLException
	 */
	
	public DishList(DatabaseConnection dbCon) throws SQLException {
		dishList = new ArrayList<Dish>();
		dishMap = new HashMap<Integer, Dish>();
		if (!(dbCon != null && dbCon.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err.println("No active database connection: please try again!");
		} else {
			ResultSet results = dbCon.fetchData("SELECT * FROM Dishes;");			
			while (results.next()) {
				Dish tempDish = new Dish(
					results.getInt(1), results.getInt(2), results.getString(3), results.getBoolean(4), results.getBoolean(5), 
						results.getBoolean(6), results.getBoolean(7), results.getBoolean(8), results.getString(9));
				dishList.add(tempDish);
				dishMap.put(tempDish.dishID, tempDish);
			}			
			results.close();
		}
	}

	public ArrayList<Dish> getDishList() {
		return dishList;
	}

	public HashMap<Integer, Dish> getDishMap() {
		return dishMap;
	}

	/**
	 * Method for adding a new dish to the mySQL database.
	 * 
	 * @param dbCon
	 *            - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @param price
	 *            - the price of the dish as an integer
	 * @param name
	 *            - the name of the dish as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_LONG
	 *            VARCHAR_MAX_LENGTH_LONG}
	 * @param containsGluten
	 *            - set to true if the dish contains gluten, false if not
	 * @param containsNuts
	 *            - set to true if the dish contains nuts, false if not
	 * @param containsDairy
	 *            - set to true if the dish contains dairy products, false if not
	 * @param isVegetarian
	 *            - set to true if the dish is fully vegetarian in nature, false
	 *            if not
	 * @param isSpicy
	 *            - set to true if the dish is spicy in nature, false if not
	 * @param description
	 *            - a description of the dish as a String
	 * @return returns true if the dish was successfully added to the database,
	 *         false in all other cases
	 */
	
	public boolean addDish(DatabaseConnection dbCon, int price, String name, boolean containsGluten, boolean containsNuts, 
							boolean containsDairy, boolean isVegetarian, boolean isSpicy, String description) {
		if (!(dbCon != null && dbCon.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err.println("No valid database connection specified; dish not added to the database.");
			return false;
		}
		if (name.length() > DatabaseConnection.VARCHAR_MAX_LENGTH_LONG) {
			throw new IllegalArgumentException("The name of the dish cannot be more than " 
												+ DatabaseConnection.VARCHAR_MAX_LENGTH_LONG + " characters long.");
		}
		return dbCon.insertIntoDB(
			"INSERT IGNORE INTO Dishes (Price, Name, ContainsGluten, ContainsNuts, ContainsDairy, IsVegetarian, IsSpicy, Description) VALUES ("
				+ price + ", '" + name + "', " + containsGluten + ", " + containsNuts + ", " + containsDairy + ", " + isVegetarian 
					+ ", " + isSpicy + ", '" + description + "');");
	}

	/**
	 * Method to remove a dish from the database
	 * 
	 * @param dbCon
	 *            - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @param dish
	 *            - the {@link pizzaProgram.dataObjects.Dish dish} to be removed
	 *            from the database
	 * @return returns true if the deletion of the dish was a success, returns
	 *         false in all other cases.
	 */
	
	public boolean removeDish(DatabaseConnection dbCon, Dish dish) {
		if (!(dbCon != null && dbCon.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err.println("No valid database connection specified; no dish removed from the database.");
			return false;
		}
		return dbCon.insertIntoDB("DELETE FROM Dishes WHERE DishID=" + dish.dishID + ");");
	}
}