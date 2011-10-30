package pizzaProgram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import pizzaProgram.dataObjects.Extra;

/**
 * Object for handling extras in the database. The methods of the class handles
 * creation of the {@link java.util.ArrayList ArrayList} and a
 * {@link java.util.HashMap HashMap} of all the different
 * {@link pizzaProgram.dataObject.Extra extras} based on a fetch from the
 * database, adding new extras to the database, as well as tagging extras as
 * active/inactive
 * 
 * @author IT1901 Group 03, Fall 2011
 */

// TODO: Dispatch an event whenever the lists are updated

public class ExtraList {
	private final static ArrayList<Extra> extraList = new ArrayList<Extra>();
	private final static HashMap<Integer, Extra> extraMap = new HashMap<Integer, Extra>();

	/**
	 * This method clears the old lists, and repopulates the
	 * {@link java.util.ArrayList ArrayList} and {@link java.util.HashMap
	 * HashMap} of all the different {@link pizzaProgram.dataObject.Extra
	 * extras} based on a fetch from the database. This method must be rerun
	 * each time the Extras table of the database is modified.
	 */
	public static void updateExtras() {
		if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
			System.err
					.println("No valid database connection; unable to update lists.");
			return;
		}
		extraList.clear();
		extraMap.clear();
		try {
			ResultSet results = DatabaseConnection
					.fetchData("SELECT * FROM Extras;");
			while (results.next()) {
				Extra tempExtra = new Extra(results.getInt(1),
						results.getString(2), results.getString(3),
						results.getBoolean(4));
				extraList.add(tempExtra);
				extraMap.put(tempExtra.id, tempExtra);
			}
			results.close();
		} catch (SQLException e) {
			System.err.println("An error occured during your query: "
					+ e.getMessage());
		}
	}

	public static ArrayList<Extra> getExtraList() {
		return extraList;
	}

	public static HashMap<Integer, Extra> getExtraMap() {
		return extraMap;
	}

	/**
	 * Adds an extra to the database (e.g. extra bacon, without onions, large
	 * pizza)
	 * 
	 * @param name
	 *            the name of the extra as a String no longer than
	 *            {@link pizzaProgram.database.DatabaseConnection#VARCHAR_MAX_LENGTH_LONG
	 *            VARCHAR_MAX_LENGTH_LONG}
	 * @param price
	 *            the price modifier of the extra as a string, where the first
	 *            character is an operator (*, + or -), and the rest of the
	 *            characters is a floating point number (e.g. *1.25 or +30)
	 * @param isActive
	 *            wether or not the Extra should be visible in the orderGUI
	 * @return true if the dish extra was successfully added to the database,
	 *         false in all other cases
	 */

	// TODO: Add validation to ensure the price parameter is in the correct
	// format

	public static boolean addExtra(String name, String price, boolean isActive) {
		if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
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
		return DatabaseConnection
				.insertIntoDB("INSERT IGNORE INTO Extras (Name, Price, isActive) VALUES ('"
						+ name + "', '" + price + "', " + isActive + ");");
	}

	/**
	 * Method to set the extra active (available in the order GUI) or inactive
	 * (only available through the admin interface.
	 * 
	 * @param extra
	 *            - the extra to change the status for.
	 * @param newStatus
	 *            - true if the extra is to be visible in the order GUI,
	 *            invisible if not.
	 * @return true if the change was made successfully, false if not.
	 */
	public static boolean changeExtraStatus(Extra extra, boolean newStatus) {
		if (!DatabaseConnection.isConnected(DatabaseConnection.DEFAULT_TIMEOUT)) {
			System.err
					.println("No valid database connection; extra status not changed.");
			return false;
		}
		return DatabaseConnection.insertIntoDB("UPDATE Extras SET isActive="
				+ newStatus + " WHERE ExtrasID=" + extra.id + ";");
	}
}