package pizzaProgram.utils;

import java.text.DecimalFormat;

import pizzaProgram.config.Config;
import pizzaProgram.constants.GUIConstants;
import pizzaProgram.constants.GUIMessages;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
//NOTE: swiss army knives are bad. For next time, it is better to keep calculators separated, so that you can find the calculator you look for more easily without having to digg into a pile of code

/**
 * Class for generating properly formatted strings of the various prices in the
 * system.
 */
public class PriceCalculators {
	/**
	 * A DecimalFormat instance used to format prices
	 */
	private static DecimalFormat formatter = new DecimalFormat("00");

	/**
	 * VAT value of delivering an order at home
	 */
	private static int deliverMoms = -1;
	/**
	 * VAT value of picking up the order at the restaurant
	 */
	private static int pickupMoms = -1;

	/**
	 * An integer representing the threshold price after which an order's delivery becomes free, in Øre.
	 */
	private static int freeDeliveryThreshold = -1;
	/**
	 * An integer represening the delivery cost of the order, in Øre
	 */
	private static int deliveryCost = -1;

	/**
	 * A String that stores the restaurant's name
	 */
	private static String restaurantName = "";
	/**
	 * A String holding the restaurant's address
	 */
	private static String restaurantAddress = "";
	/**
	 * A String holding the restaurant's city
	 */
	private static String restaurantCity = "";

	/**
	 * Generates a priceString on the form "0,00" based on the price of the
	 * dish, without VAT added (i.e. a price in norwegian øre of 12345 would
	 * return a string which contains "123,45".
	 * 
	 * @param d
	 *            the {@link pizzaProgram.dataObjects.Dish dish} to get the
	 *            priceString for.
	 * @return The priceString as specified above.
	 */
	public static String getPriceForDish(Dish d) {
		return d.price / 100 + "," + formatter.format(d.price % 100);
	}

	/**
	 * Returns a formatted String for the cost of pickup at the restaurant
	 * @param d The dish to calculate and format the price for
	 * @return A formatted String of the price for a dish for picking up at the restaurant
	 */
	public static String getPriceForDishPickupAtRestaurant(Dish dish) {
		int price = dish.price + (int) (dish.price * (pickupMoms / 100.0));
		return price / 100 + "," + formatter.format(price % 100);
	}

	/**
	 * Returns the formatted price for delivery at home
	 * @param dish The dish to calculate and format the price for
	 * @return A formatted Stirng representing the price for delivery at home 
	 */
	public static String getPriceForDishDeliveryAtHome(Dish dish) {
		int price = dish.price + (int) (dish.price * (deliverMoms / 100.0));
		return price / 100 + "," + formatter.format(price % 100);
	}

	/**
	 * Generates a priceString on the form "0,00" based on the sum of the items
	 * in the order, with VAT and delivery cost added (IE a price in norwegian
	 * øre of 12345 would return a string which contains "123,45".
	 * 
	 * @param o
	 *            the {@link pizzaProgram.dataObjects.Order order} to get the
	 *            priceString for.
	 * @return The priceString as specified above.
	 */
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

	/**
	 * Generates a priceString on the form "0,00" based on the sum of the items
	 * in the order, without VAT and delivery cost added (IE a price in
	 * norwegian øre of 12345 would return a string which contains "123,45".
	 * 
	 * @param o
	 *            the {@link pizzaProgram.dataObjects.Order order} to get the
	 *            priceString for.
	 * @return The priceString as specified above.
	 */
	public static String getPriceForOrderWithoutVAT(Order o) {
		int price = getPriceForOrder(o);
		return price / 100 + "," + formatter.format(price % 100);
	}

	/**
	 * Generates a priceString on the form "0,00" based on the sum of the items
	 * in the order, with VAT, but without delivery cost added (IE a price in
	 * norwegian øre of 12345 would return a string which contains "123,45".
	 * 
	 * @param o
	 *            the {@link pizzaProgram.dataObjects.Order order} to get the
	 *            priceString for.
	 * @return The priceString as specified above.
	 */
	public static String getPriceForOrderWithVAT(Order o) {
		int totalPrice = getPriceForOrder(o);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			totalPrice += totalPrice * (deliverMoms / 100.0);
		} else {
			totalPrice += totalPrice * (pickupMoms / 100.0);
		}
		return totalPrice / 100 + "," + formatter.format(totalPrice % 100);
	}

	/**
	 * Generates a priceString on the form "0,00" containing the delivery cost
	 * of the provided order (IE a price in norwegian øre of 12345 would return
	 * a string which contains "123,45".
	 * 
	 * @param o
	 *            the {@link pizzaProgram.dataObjects.Order order} to get the
	 *            delivery cost for.
	 * @return The priceString as specified above.
	 */
	public static String getDeliveryCostForOrder(Order o) {
		int totalPrice = getPriceForOrder(o);
		totalPrice += totalPrice * (deliverMoms / 100.0);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME) && totalPrice < freeDeliveryThreshold) {
			return deliveryCost / 100 + "," + formatter.format(deliveryCost % 100);
		}
		return "0,00";
	}

	/**
	 * Returns a formatted String representing the cost of the delivery of the order
	 * @return A formatted String representing the cost of the delivery of the order
	 */
	public static String getDeliveryCost() {
		return deliveryCost / 100 + "," + formatter.format(deliveryCost % 100);
	}

	/**
	 * Returns a String representing the threshold after which delivery becomes free
	 * @return A String representing the threshold after which delivery becomes free
	 */
	public static String getFreeDeliveryTreshold() {
		return freeDeliveryThreshold / 100 + "," + formatter.format(freeDeliveryThreshold % 100);
	}

	/**
	 * Generates a priceString on the form "0,00" containing the VAT for the
	 * provided order (IE a price in norwegian øre of 12345 would return a
	 * string which contains "123,45".
	 * 
	 * @param o
	 *            the {@link pizzaProgram.dataObjects.Order order} to get the
	 *            VAT for.
	 * @return The priceString as specified above.
	 */
	public static String getVATForOrder(Order o) {
		int totalPrice = getPriceForOrder(o);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			totalPrice *= deliverMoms / 100.0;
		} else {
			totalPrice *= pickupMoms / 100.0;
		}
		return totalPrice / 100 + "," + formatter.format(totalPrice % 100);
	}

	/**
	 * Verifies input for prices, all prices need to be on the form assured
	 * here, it will return the øreprice if the input is correct, else it will
	 * return -1
	 * 
	 * @param priceText
	 *            the pricestring to be formatted
	 * @return the correct øreprice or -1 if the string is wrong
	 */
	public static int priceInputVerifier(String priceText) {
		String[] splitt = priceText.trim().replace(" ", "").split(",");
		int orePrice = 0;

		if (splitt.length > 2 || splitt.length < 2) {
			GUIConstants.showErrorMessage(GUIMessages.TOO_MANY_COMMAS_IN_VALUE);
			return -1;
		}
		try {
			orePrice = Integer.parseInt(splitt[0]) * 100;
		} catch (NumberFormatException e) {
			GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_NUMBER_INPUT);
			return -1;
		}
		if (splitt[1].length() > 2 || splitt[1].length() < 2) {
			GUIConstants.showErrorMessage(GUIMessages.TOO_MANY_DIGITS_AFTER_COMMA);
			return -1;
		}
		try {
			orePrice += Integer.parseInt(splitt[1]);
		} catch (Exception e) {
			GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_NUMBER_INPUT);
			return -1;
		}
		return orePrice;
	}

	/**
	 * Generates the value of the order, in norwegian øre (1/100 NOK), based on
	 * the sum of the ordered dishes and extras in the order. Does not include
	 * VAT or delivery cost.
	 * 
	 * @param o
	 *            the {@link pizzaProgram.dataObjects.Order order} to get the
	 *            value for.
	 * @return The value of the order in norwegian øre as an int.
	 */
	private static int getPriceForOrder(Order o) {
		int totalPrice = 0;
		for (OrderDish od : o.orderedDishes) {
			totalPrice += getValueForOrderDish(od);
		}
		return totalPrice;
	}

	/**
	 * Generates the value of the ordered dish, in norwegian øre (1/100 NOK),
	 * based on the sum of the ordered dish and its extras. Does not include
	 * VAT.
	 * 
	 * @param od
	 *            the {@link pizzaProgram.dataObjects.OrderDish ordered dish} to
	 *            get the value for.
	 * @return The value of the ordered dish in norwegian øre as an int.
	 */
	private static int getValueForOrderDish(OrderDish od) {
		int totalPrice = od.dish.price;
		for (Extra e : od.getExtras()) {
			if (e.priceFuncPart == '*') {
				totalPrice += od.dish.price * ((e.priceValPart - 100) / 100.0);
			} else if (e.priceFuncPart == '-') {
				totalPrice -= e.priceValPart;
			} else {
				totalPrice += e.priceValPart;
			}
		}
		return totalPrice;
	}

	/**
	 * Generates a priceString on the form "0,00" containing the VAT for the
	 * provided ordered dish (IE a price in norwegian øre of 12345 would return
	 * a string which contains "123,45".
	 * 
	 * @param od
	 *            the {@link pizzaProgram.dataObjects.OrderDish ordered dish} to
	 *            get the priceString for.
	 * @return The priceString as specified above.
	 */
	public static String getPriceForOrderDish(OrderDish od) {
		int totalPrice = getValueForOrderDish(od);
		return totalPrice / 100 + "," + formatter.format(totalPrice % 100);
	}

	/**
	 * 
	 * @param d
	 *            the {@link pizzaProgram.dataObjects.Dish dish} the info in the
	 *            string is to be in relation to.
	 * @param e
	 *            the {@link pizzaProgram.dataObjects.Extra extra} the info in
	 *            the string is to be in relation to.
	 * @return returns an integer øre-price of an extra on a spesific order
	 *         Extra to the given Dish. used to calculate the price with
	 *         different taxes
	 */
	private static int getPriceForExtraOnDish(Dish d, Extra e) {
		if (e.priceFuncPart == '+') {
			return e.priceValPart;
		} else if (e.priceFuncPart == '-') {
			return -e.priceValPart;
		} else {
			return (int) (d.price * ((e.priceValPart - 100) / 100.0));
		}
	}

	/**
	 * Generates a string displaying the change in price if you add the given
	 * Extra to the given Dish with the VAT for picking up the order at the
	 * restaurant added.
	 * 
	 * @param d
	 *            the {@link pizzaProgram.dataObjects.Dish dish} the info in the
	 *            string is to be in relation to.
	 * @param e
	 *            the {@link pizzaProgram.dataObjects.Extra extra} the info in
	 *            the string is to be in relation to.
	 * @return a string in the form of "+0,00" or "-0,00" depending on wether or
	 *         not it is a positive or negative price change.
	 */
	public static String getPriceForExtraOnDishPickup(Dish d, Extra e) {
		int price = getPriceForExtraOnDish(d, e);
		price += (int) (price * (pickupMoms / 100.0));
		char func = price >= 0 ? '+' : '-';
		price = Math.abs(price);
		return func + "" + price / 100 + "," + formatter.format(price % 100);

	}

	/**
	 * Generates a string displaying the change in price if you add the given
	 * Extra to the given Dish with the VAT for deliveries added.
	 * 
	 * @param d
	 *            the {@link pizzaProgram.dataObjects.Dish dish} the info in the
	 *            string is to be in relation to.
	 * @param e
	 *            the {@link pizzaProgram.dataObjects.Extra extra} the info in
	 *            the string is to be in relation to.
	 * @return a string in the form of "+0,00" or "-0,00" depending on wether or
	 *         not it is a positive or negative price change.
	 */
	public static String getPriceForExtraOnDishDeliver(Dish d, Extra e) {
		int price = getPriceForExtraOnDish(d, e);
		price += (int) (price * (deliverMoms / 100.0));
		char func = price >= 0 ? '+' : '-';
		price = Math.abs(price);
		return func + "" + price / 100 + "," + formatter.format(price % 100);

	}

	/**
	 * Generates a string on the form "+0,00", "-0,00" or "*0,00" containing the
	 * price modification adding the given extra will have on a dish.
	 * 
	 * @param e
	 *            the {@link pizzaProgram.dataObjects.Extra extra} to get the
	 *            price modification from.
	 * @return the priceString as specified above.
	 */
	public static String getPriceForExtra(Extra e) {
		return e.priceFuncPart + "" + e.priceValPart / 100 + "," + formatter.format(e.priceValPart % 100);
	}

	/**
	 * Method that sets the static fields of the class based on a fetch from the
	 * Config table of the database.
	 */
	public static void getConstantsFromDataBase() {
		deliverMoms = Integer.parseInt(Config
				.getConfigValueByKey(Config.KEY_DELIVERY_AT_HOME_TAX));
		pickupMoms = Integer.parseInt(Config
				.getConfigValueByKey(Config.KEY_PICKUP_AT_RESTAURANT_TAX));
		freeDeliveryThreshold = Integer.parseInt(Config
				.getConfigValueByKey(Config.KEY_FREE_DELIVERY_LIMIT));
		deliveryCost = Integer.parseInt(Config
				.getConfigValueByKey(Config.KEY_DELIVERY_PRICE));
		restaurantName = Config.getConfigValueByKey(Config.KEY_RESTAURANT_NAME);
		restaurantAddress = Config.getConfigValueByKey(Config.KEY_RESTAURANT_ADDRESS);
		restaurantCity = Config.getConfigValueByKey(Config.KEY_RESTAURANT_CITY);
	}

	/**
	 * Returns the name of the restaurant
	 * @return The restaurant's name
	 */
	public static String getRestaurantName() {
		return restaurantName;
	}

	/**
	 * Returns a String representing the address of the restaurant
	 * @return A String representing the restaurant's address
	 */
	public static String getRestaurantAddress() {
		return restaurantAddress;
	}

	/**
	 * Returns a String representing the city where the restaurant is located
	 * @return The city where the restaurant is located
	 */
	public static String getRestaurantCity() {
		return restaurantCity;
	}

}
