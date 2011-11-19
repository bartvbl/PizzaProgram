package pizzaProgram.utils;

import java.text.DecimalFormat;

import pizzaProgram.core.Constants;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;

public class PriceCalculators {
	private static DecimalFormat formatter = new DecimalFormat("00");

	public static String getPriceForDish(Dish d) {
		return d.price / 100 + "," + formatter.format(d.price % 100);
	}

	public static String getPriceForOrderWithVATAndDelivery(Order o) {
		int totalPrice = getPriceForOrder(o);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			totalPrice += totalPrice * (Constants.getDeliverMoms() / 100.0);
			if (totalPrice < Constants.getFreeDeliveryThreshold()) {
				totalPrice += Constants.getDeliveryCost();
			}
		} else {
			totalPrice += totalPrice * (Constants.getPickupMoms() / 100.0);
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
			totalPrice += totalPrice * (Constants.getDeliverMoms() / 100.0);
		} else {
			totalPrice += totalPrice * (Constants.getPickupMoms() / 100.0);
		}
		return totalPrice / 100 + "," + formatter.format(totalPrice % 100);
	}

	public static String getDeliveryCost(Order o) {
		int totalPrice = getPriceForOrder(o);
		totalPrice += totalPrice * (Constants.getDeliverMoms() / 100.0);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME)
				&& totalPrice < Constants.getFreeDeliveryThreshold()) {
			return Constants.getDeliveryCost() / 100 + ","
					+ formatter.format(Constants.getDeliveryCost() % 100);
		}
		return "0,00";
	}

	public static String getVATForOrder(Order o) {
		int totalPrice = getPriceForOrder(o);
		if (o.deliveryMethod.equals(Order.DELIVER_AT_HOME)) {
			totalPrice *= Constants.getDeliverMoms() / 100.0;
		} else {
			totalPrice *= Constants.getPickupMoms() / 100.0;
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

}
