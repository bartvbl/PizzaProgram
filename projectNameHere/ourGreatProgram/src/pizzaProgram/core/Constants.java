package pizzaProgram.core;

import pizzaProgram.dataObjects.Order;

public class Constants {

	public static final int RECIPT_WIDTH = 160;
	public static final int RECIPT_ROW_HEIGHT = 20;

	// gui representation of true/false
	public static final String GUI_TRUE = "Ja";
	public static final String GUI_FALSE = "Nei";

	// norsk delivery metjod
	public static final String GUI_DELIVER = "Lever hjem";
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
	public static final String SEARCH_FIELD_DEFAULT = "";

	public static String translateDeliveryMethod(String s) {
		if (s.equals(Order.DELIVER_AT_HOME)) {
			return GUI_DELIVER;
		} else {
			return GUI_PICKUP;
		}
	}

	public static String translateOrderStatus(String s) {
		if (s.equals(Order.REGISTERED)) {
			return GUI_REGISTERED;
		} else if (s.equals(Order.BEING_COOKED)) {
			return GUI_COOKING;
		} else if (s.equals(Order.HAS_BEEN_COOKED)) {
			return GUI_FINCOOKING;
		} else {
			return GUI_DELIVERING;
		}

	}
}