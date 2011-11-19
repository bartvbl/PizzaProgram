package pizzaProgram.utils;

import java.math.BigDecimal;

import pizzaProgram.core.Constants;

public class OrderPriceCalculator {
	private BigDecimal totalPrice = new BigDecimal(0);
	private BigDecimal totalOverallOrderCost = new BigDecimal(0);
	private BigDecimal deliveryCost = new BigDecimal(0);

	public void reset() {
		this.totalPrice = new BigDecimal(0);
		this.totalOverallOrderCost = new BigDecimal(0);
		this.deliveryCost = new BigDecimal(0);
	}

	public void addDishToTotalPrice(DishPrice dishPrice) {
		this.totalPrice = this.totalPrice.add(dishPrice.getPriceAsBigDecimal());
	}

	public OrderPrice getTotalOrderPrice() {
		OrderPrice orderPrice = createOrderPriceObject();
		return orderPrice;
	}

	private OrderPrice createOrderPriceObject() {
		this.calculateIndividualPrices();
		OrderPrice orderPrice = new OrderPrice(this.totalPrice,
				this.deliveryCost, this.totalOverallOrderCost);
		return orderPrice;
	}

	private void calculateIndividualPrices() {
		this.deliveryCost = new BigDecimal(Constants.getDeliveryCost());
		this.deliveryCost = this.deliveryCost.divide(new BigDecimal(100));
		this.totalOverallOrderCost = this.totalPrice.add(new BigDecimal(0));
		BigDecimal freeDeliveryLimit = new BigDecimal(
				Constants.getFreeDeliveryThreshold());
		freeDeliveryLimit = freeDeliveryLimit.divide(new BigDecimal(100));
		if (this.totalPrice.compareTo(freeDeliveryLimit) > 0) {
			this.deliveryCost = new BigDecimal(0);
		} else {
			this.totalOverallOrderCost = this.totalOverallOrderCost
					.add(this.deliveryCost);
		}
		this.totalOverallOrderCost = this.totalOverallOrderCost.setScale(2,
				BigDecimal.ROUND_HALF_EVEN);
		this.deliveryCost = this.deliveryCost.setScale(2,
				BigDecimal.ROUND_HALF_EVEN);
		this.totalPrice = this.totalPrice.setScale(2,
				BigDecimal.ROUND_HALF_EVEN);
	}

}// END