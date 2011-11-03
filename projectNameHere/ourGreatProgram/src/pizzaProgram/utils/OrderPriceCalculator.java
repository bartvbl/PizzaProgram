package pizzaProgram.utils;

import java.math.BigDecimal;
import java.util.ArrayList;

import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.OrderDish;

public class OrderPriceCalculator {
	private static final int FREE_DELIVERY_ABOVE_ORDER_COST = 50;
	private static final int DELIVERY_COST = 50;
	
	public static OrderPrice getPrice(OrderDish dish)
	{
		BigDecimal totalPrice = new BigDecimal(dish.dish.price);
		ArrayList<Extra> extraList = dish.getExtras();
		handleMultiplicationExtras(extraList, totalPrice);
		handleAdditionExtras(extraList, totalPrice);
		OrderPrice orderPrice = createOrderPriceObject(totalPrice);
		return orderPrice;
	}

	private static OrderPrice createOrderPriceObject(BigDecimal totalPrice) {
		BigDecimal freeDeliveryLimit = new BigDecimal(FREE_DELIVERY_ABOVE_ORDER_COST);
		BigDecimal deliveryCost = new BigDecimal(DELIVERY_COST);
		BigDecimal totalOverallOrderCost = deliveryCost.pow(1);//a bit hacky
		if(totalPrice.compareTo(freeDeliveryLimit) > 0)
		{
			deliveryCost = new BigDecimal(0);
		} else {
			totalPrice.add(deliveryCost);
		}
		setScales(deliveryCost, totalOverallOrderCost, totalPrice);
		OrderPrice orderPrice = new OrderPrice(totalPrice, deliveryCost, totalOverallOrderCost);
		return orderPrice;
	}

	private static void setScales(BigDecimal deliveryCost, BigDecimal totalOverallOrderCost, BigDecimal totalPrice) {
		deliveryCost.setScale(-2, BigDecimal.ROUND_HALF_EVEN);
		totalOverallOrderCost.setScale(-2, BigDecimal.ROUND_HALF_EVEN);
		totalPrice.setScale(-2, BigDecimal.ROUND_HALF_EVEN);
	}

	private static void handleAdditionExtras(ArrayList<Extra> extraList, BigDecimal totalPrice) {
		BigDecimal currentValueOfExtra;
		for(Extra extra : extraList)
		{
			currentValueOfExtra = new BigDecimal(extra.priceValPart);
			if(extra.priceFuncPart == '*')
			{
				totalPrice = totalPrice.multiply(currentValueOfExtra);
			} else if(extra.priceFuncPart == '/')
			{
				totalPrice = totalPrice.divide(currentValueOfExtra);
			}
		}
	}

	private static void handleMultiplicationExtras(ArrayList<Extra> extraList, BigDecimal totalPrice) {
		BigDecimal currentValueOfExtra;
		for(Extra extra : extraList)
		{
			currentValueOfExtra = new BigDecimal(extra.priceValPart);
			if(extra.priceFuncPart == '+')
			{
				totalPrice = totalPrice.add(currentValueOfExtra);
			} else if(extra.priceFuncPart == '-')
			{
				totalPrice = totalPrice.subtract(currentValueOfExtra);
			}
		}
	}
}
