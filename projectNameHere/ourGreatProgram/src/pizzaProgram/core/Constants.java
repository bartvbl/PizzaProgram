package pizzaProgram.core;

import javax.swing.JOptionPane;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.database.databaseUtils.DatabaseReader;

public class Constants {

	private static int deliverMoms = -1;
	private static int pickupMoms = -1;

	private static int freeDeliveryThreshold = -1;
	private static int deliveryCost = -1;

	private static String restaurantName = "";

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

	public static void getConstantsFromDataBase() {
		deliverMoms = Integer
				.parseInt(DatabaseReader
						.getSettingByKey(DatabaseConstants.SETTING_KEY_DELIVERY_AT_HOME_TAX).value);
		pickupMoms = Integer
				.parseInt(DatabaseReader
						.getSettingByKey(DatabaseConstants.SETTING_KEY_PICKUP_AT_RESTAURANT_TAX).value);
		freeDeliveryThreshold = Integer
				.parseInt(DatabaseReader
						.getSettingByKey(DatabaseConstants.SETTING_KEY_FREE_DELIVERY_LIMIT).value);
		deliveryCost = Integer
				.parseInt(DatabaseReader
						.getSettingByKey(DatabaseConstants.SETTING_KEY_DELIVERY_PRICE).value);
		restaurantName = DatabaseReader
				.getSettingByKey(DatabaseConstants.SETTING_KEY_RESTAURANT_NAME).value;
	}

	public static int getDeliverMoms() {
		if (deliverMoms == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Momsverdi ikke hentet ut korrekt fra databasen - sjekk tilkoblingen til databasen og prøv igjen!",
							"En alvorlig feil har oppstått!",
							JOptionPane.ERROR_MESSAGE, null);
		}
		return deliverMoms;
	}

	public static int getPickupMoms() {
		if (pickupMoms == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Momsverdi ikke hentet ut korrekt fra databasen - sjekk tilkoblingen til databasen og prøv igjen!",
							"En alvorlig feil har oppstått!",
							JOptionPane.ERROR_MESSAGE, null);
		}
		return pickupMoms;
	}

	public static int getFreeDeliveryThreshold() {
		if (freeDeliveryThreshold == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Grenseverdi for gratis frakt ikke hentet ut korrekt fra databasen - sjekk tilkoblingen til databasen og prøv igjen!",
							"En alvorlig feil har oppstått!",
							JOptionPane.ERROR_MESSAGE, null);
		}
		return freeDeliveryThreshold;
	}

	public static int getDeliveryCost() {
		if (deliveryCost == -1) {
			JOptionPane
					.showMessageDialog(
							null,
							"Utkjøringspris ikke hentet ut korrekt fra databasen - sjekk tilkoblingen til databasen og prøv igjen!",
							"En alvorlig feil har oppstått!",
							JOptionPane.ERROR_MESSAGE, null);
		}
		return deliveryCost;
	}

	public static String getRestaurantName() {
		if (restaurantName.equals("")) {
			JOptionPane
					.showMessageDialog(
							null,
							"Restaurantnavn ikke hentet ut korrekt fra databasen - sjekk tilkoblingen til databasen og prøv igjen!",
							"En alvorlig feil har oppstått!",
							JOptionPane.ERROR_MESSAGE, null);
		}
		return restaurantName;
	}
}