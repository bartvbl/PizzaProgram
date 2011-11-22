package pizzaProgram.core;

import javax.swing.JOptionPane;

import pizzaProgram.dataObjects.Order;
/**
 * Class containing constants used in various areas of the GUI.
 */
public class GUIConstants {
	/**
	 * The text width of the receipt window, measured in characters
	 */
	public static final int RECIPT_WIDTH = 180;
	/**
	 * The row height of each row in the receipt window, measured in pixels
	 */
	public static final int RECIPT_ROW_HEIGHT = 20;
	/**
	 * A value representing the boolean True, in case it should be represented somewhere as text in the user interface
	 */
	public static final String GUI_TRUE = "Ja";
	/**
	 * A value representing the boolean False, in case it should be represented as a String somewhere in the user interface
	 */
	public static final String GUI_FALSE = "Nei";
	/**
	 * The text of the "Deliver at home" option in the delivery method combobox in the Order GUI. 
	 */
	public static final String GUI_DELIVER = "Lever hjem";
	/**
	 * The text of the "Pickup at restaurant" option in the delivery method conbobox in the Order GUI.
	 */
	public static final String GUI_PICKUP = "Hent selv";

	/**
	 * The guiword for registered status
	 */
	public static final String GUI_REGISTERED = "Registrert";

	/**
	 * The guiword for cooking status
	 */
	public static final String GUI_COOKING = "Tilberedes";

	/**
	 * The guiword for cooking status
	 */
	public static final String GUI_FINCOOKING = "Klar for levering";

	/**
	 * The guiword for being cooked status
	 */
	public static final String GUI_DELIVERING = "Underveis";
	/**
	 * The default text that should appear in search boxes.
	 */
	public static final String SEARCH_FIELD_DEFAULT = "";
	
	/**
	 * Translates an enum string from the delivery method column in the Orders table to a readable String for the user interface
	 * @param deliveryMethodString The string representing the enum value from the database.
	 * @return A string representing the way the enum should be shpwn in the user interface
	 */
	public static String translateDeliveryMethod(String deliveryMethodString) {
		if (deliveryMethodString.equals(Order.DELIVER_AT_HOME)) {
			return GUI_DELIVER;
		} else {
			return GUI_PICKUP;
		}
	}
	
	/**
	 * Returns a string representing the value of the order status enum column in the database, as the way it should appear in the user interface.
	 * @param orderStatusString A string representing the order status enum field in the database
	 * @return A string representing the entered enum value, as a string that can be displayed in the user interface
	 */
	public static String translateOrderStatus(String orderStatusString) {
		if (orderStatusString.equals(Order.REGISTERED)) {
			return GUI_REGISTERED;
		} else if (orderStatusString.equals(Order.BEING_COOKED)) {
			return GUI_COOKING;
		} else if (orderStatusString.equals(Order.HAS_BEEN_COOKED)) {
			return GUI_FINCOOKING;
		} else {
			return GUI_DELIVERING;
		}

	}
	/**
	 * Shows an error message to the user
	 * @param message The message of that the error message box should display
	 */
	public static void showErrorMessage(String message){
		JOptionPane.showMessageDialog(null, message, "Feil", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Shows an information message to the user
	 * @param message
	 */
	public static void showConfirmMessage(String message){
		JOptionPane.showMessageDialog(null, message, "Informasjon", JOptionPane.INFORMATION_MESSAGE);
	}
	
}