package pizzaProgram.utils;

import java.text.DecimalFormat;

import javax.swing.JOptionPane;

import pizzaProgram.core.DatabaseConstants;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.database.databaseUtils.DatabaseReader;

public class PriceCalculators {
	private static DecimalFormat formatter = new DecimalFormat("00");
	
	private static int deliverMoms = -1;
	private static int pickupMoms = -1;

	private static int freeDeliveryThreshold = -1;
	private static int deliveryCost = -1;
	
	private static String restaurantName = "";

	public static String getPriceForDish(Dish d) {
		return d.price / 100 + "," + formatter.format(d.price % 100);
	}

	public static String getPriceForOrderWithVATAndDelivery(Order o) {
		int totalPrice = getPriceForOrder(o);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			totalPrice += totalPrice * (deliverMoms / 100.0);
			if (totalPrice < freeDeliveryThreshold) {
				totalPrice += deliveryCost;
			}
		} else {
			totalPrice += totalPrice * (pickupMoms / 100.0);
		}
		return totalPrice / 100 + "," + formatter.format(totalPrice % 100);
	}

	public static String getPriceForOrderWithoutVAT(Order o) {
		int price = getPriceForOrder(o);
		return price / 100 + "," + formatter.format(price % 100);
	}

	public static String getPriceForOrderWithVAT(Order o) {
		int totalPrice = getPriceForOrder(o);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			totalPrice += totalPrice * (deliverMoms / 100.0);
		} else {
			totalPrice += totalPrice * (pickupMoms / 100.0);
		}
		return totalPrice / 100 + "," + formatter.format(totalPrice % 100);
	}

	public static String getDeliveryCost(Order o) {
		int totalPrice = getPriceForOrder(o);
		totalPrice += totalPrice * (deliverMoms / 100.0);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME)
				&& totalPrice < freeDeliveryThreshold) {
			return deliveryCost / 100 + ","
					+ formatter.format(deliveryCost % 100);
		}
		return "0,00";
	}

	public static String getVATForOrder(Order o) {
		int totalPrice = getPriceForOrder(o);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			totalPrice *= deliverMoms / 100.0;
		} else {
			totalPrice *= pickupMoms / 100.0;
		}
		return totalPrice / 100 + "," + formatter.format(totalPrice % 100);

	}

	private static int getPriceForOrder(Order o) {
		int totalPrice = 0;
		for (OrderDish od : o.orderedDishes) {
			totalPrice += getValueForOrderDish(od);
		}
		return totalPrice;
	}

	private static int getValueForOrderDish(OrderDish od) {
		int totalPrice = od.dish.price;
		for (Extra e : od.getExtras()) {
			if (e.priceFuncPart == '*') {
				totalPrice += od.dish.price * ((100 - e.priceValPart) / 100.0);
			} else if (e.priceFuncPart == '-') {
				totalPrice -= e.priceValPart;
			} else {
				totalPrice += e.priceValPart;
			}
		}
		return totalPrice;
	}

	public static String getPriceForOrderDish(OrderDish od) {
		int totalPrice = getValueForOrderDish(od);
		return totalPrice / 100 + "," + formatter.format(totalPrice % 100);
	}

	public static String getPriceForExtraOnDish(Dish d, Extra e) {
		if (e.priceFuncPart != '*') {
			return e.priceValPart / 100 + ","
					+ formatter.format(e.priceValPart % 100);
		}
		int returnVal = (int) (d.price * ((100 - e.priceValPart) / 100.0));
		return returnVal / 100 + "," + formatter.format(returnVal % 100);
	}

	public static String getPriceForExtra(Extra e) {
		return e.priceFuncPart + e.priceValPart / 100 + ","
				+ formatter.format(e.priceValPart % 100);
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
