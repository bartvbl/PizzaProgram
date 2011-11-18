package pizzaProgram.utils;

import java.math.BigDecimal;
import java.util.ArrayList;

import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.OrderDish;

public class DishPriceCalculator {

	public static DishPrice getPrice(OrderDish dish) {
		BigDecimal totalPrice = new BigDecimal(dish.dish.price);
		ArrayList<Extra> extraList = dish.getExtras();
		totalPrice = handleMultiplicationExtras(extraList, totalPrice);
		totalPrice = handleAdditionExtras(extraList, totalPrice);
		DishPrice dishPrice = new DishPrice(totalPrice);
		return dishPrice;
	}

	private static BigDecimal handleAdditionExtras(ArrayList<Extra> extraList,
			BigDecimal totalPrice) {
		BigDecimal currentValueOfExtra;
		for (Extra extra : extraList) {
			currentValueOfExtra = new BigDecimal(extra.priceValPart);
			if (extra.priceFuncPart == '*') {
				totalPrice = totalPrice.multiply(currentValueOfExtra);
			} else if (extra.priceFuncPart == '/') {
				totalPrice = totalPrice.divide(currentValueOfExtra);
			}
		}
		return totalPrice;
	}

	private static BigDecimal handleMultiplicationExtras(
			ArrayList<Extra> extraList, BigDecimal totalPrice) {
		BigDecimal currentValueOfExtra;
		for (Extra extra : extraList) {
			currentValueOfExtra = new BigDecimal(extra.priceValPart);
			if (extra.priceFuncPart == '+') {
				totalPrice = totalPrice.add(currentValueOfExtra);
			} else if (extra.priceFuncPart == '-') {
				totalPrice = totalPrice.subtract(currentValueOfExtra);
			}
		}
		return totalPrice;
	}
}
