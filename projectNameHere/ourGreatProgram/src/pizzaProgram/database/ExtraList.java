package pizzaProgram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import pizzaProgram.dataObjects.Extra;

/**
 * Object for handling extras in the database. At construction the object
 * creates an {@link java.util.ArrayList ArrayList} and a
 * {@link java.util.HashMap HashMap} of all the different
 * {@link pizzaProgram.dataObject.Extra extras} based on a fetch from the
 * database; these lists are publically available through the getter methods.
 * For now it is suggested to discard this object any time a change occurs to an
 * extra in the database, and reconstruct it by a call to the constructor. The
 * methods of the class handles removal of existing extras from the database, as
 * well as adding new extras to the database.
 * 
 * @author IT1901 Group 03, Fall 2011
 */

// TODO: Dispatch an event whenever the lists are updated

public class ExtraList {
	private ArrayList<Extra> extraList;
	private HashMap<Integer, Extra> extraMap;
	private DatabaseConnection dbCon;

	/**
	 * Constructor that creates the list objects as specified in the class
	 * javadoc
	 * 
	 * @param dbCon
	 *            - the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @throws SQLException
	 */

	public ExtraList(DatabaseConnection dbCon) {
		this.dbCon = dbCon;
		if (!(dbCon != null && dbCon
				.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err
					.println("No active database connection: please try again!");
			return;
		}
		this.updateExtras();
	}

	public void updateExtras() {
		extraList = new ArrayList<Extra>();
		extraMap = new HashMap<Integer, Extra>();
		try {
			ResultSet results = dbCon.fetchData("SELECT * FROM Extras;");
			while (results.next()) {
				Extra tempExtra = new Extra(results.getInt(1),
						results.getString(2), results.getString(3));
				extraList.add(tempExtra);
				extraMap.put(tempExtra.id, tempExtra);
			}
			results.close();
		} catch (SQLException e) {
			System.err.println("An error occured during your query: "
					+ e.getMessage());
		}
	}

	public ArrayList<Extra> getExtraList() {
		return extraList;
	}

	public HashMap<Integer, Extra> getExtraMap() {
		return extraMap;
	}

	/**
	 * Adds an extra to the database (e.g. extra bacon, without onions, large
	 * pizza)
	 * 
	 * @param dbCon
	 *            the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @param name
	 *            the name of the extra as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection.VARCHAR_MAX_LENGTH_LONG
	 *            VARCHAR_MAX_LENGTH_LONG}
	 * @param price
	 *            the price modifier of the extra as a string, where the first
	 *            character is an operator (*, + or -), and the rest of the
	 *            characters is a floating point number (e.g. *1.25 or +30)
	 * @return returns true if the dish extra was successfully added to the
	 *         database, false in all other cases
	 */

	// TODO: Add validation to ensure the price parameter is in the correct
	// format

	public boolean addExtra(String name, String price) {
		if (!(dbCon != null && dbCon
				.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err
					.println("No valid database connection specified; extra not added to the database.");
			return false;
		}
		if (name.length() > DatabaseConnection.VARCHAR_MAX_LENGTH_LONG) {
			throw new IllegalArgumentException(
					"The name of the extra cannot be more than "
							+ DatabaseConnection.VARCHAR_MAX_LENGTH_LONG
							+ " characters long.");
		}
		return dbCon
				.insertIntoDB("INSERT IGNORE INTO Extras (Name, Price) VALUES ('"
						+ name + "', '" + price + "');");
	}

	/**
	 * Method to remove a dish extra from the database
	 * 
	 * @param dbCon
	 *            the {@link pizzaProgram.database.DatabaseConnection
	 *            DatabaseConnection} object with the current active connection
	 *            to the SQL database
	 * @param dish
	 *            the {@link pizzaProgram.dataObjects.Extra extra} to be removed
	 *            from the database
	 * @return returns true if the deletion of the extra was a success, returns
	 *         false in all other cases.
	 */

	public boolean removeExtra(Extra extra) {
		if (!(dbCon != null && dbCon
				.isConnected(DatabaseConnection.DEFAULT_TIMEOUT))) {
			System.err
					.println("No valid database connection specified; no extra removed from the database.");
			return false;
		}
		return dbCon.insertIntoDB("DELETE FROM Extras WHERE ExtrasID="
				+ extra.id + ");");
	}
}