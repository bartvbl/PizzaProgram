package pizzaProgram.utils;

import java.math.BigDecimal;

public class OrderPriceCalculator {
	private static final int FREE_DELIVERY_ABOVE_ORDER_COST = 200;
	private static final int DELIVERY_COST = 50;
	private BigDecimal totalPrice = new BigDecimal(0);
	private BigDecimal totalOverallOrderCost = new BigDecimal(0);
	private BigDecimal deliveryCost = new BigDecimal(0);
	
	public void reset(){
		this.totalPrice = new BigDecimal(0);
		this.totalOverallOrderCost = new BigDecimal(0);
		this.deliveryCost = new BigDecimal(0);
	}
	
	public void addDishToTotalPrice(DishPrice dishPrice){
		this.totalPrice = this.totalPrice.add(dishPrice.getPriceAsBigDecimal());
	}
	
	public OrderPrice getTotalOrderPrice(){
		OrderPrice orderPrice = createOrderPriceObject();
		return orderPrice;
	}

	private OrderPrice createOrderPriceObject() {
		this.calculateIndividualPrices();
		OrderPrice orderPrice = new OrderPrice(this.totalPrice, this.deliveryCost, this.totalOverallOrderCost);
		return orderPrice;
	}
	
	private void calculateIndividualPrices(){
		this.deliveryCost = new BigDecimal(DELIVERY_COST);
		this.totalOverallOrderCost = this.totalPrice.add(new BigDecimal(0));//a bit hacky
		BigDecimal freeDeliveryLimit = new BigDecimal(FREE_DELIVERY_ABOVE_ORDER_COST);
		if(this.totalPrice.compareTo(freeDeliveryLimit) > 0){
			this.deliveryCost = new BigDecimal(0);
		}else {
			this.totalOverallOrderCost = this.totalOverallOrderCost.add(this.deliveryCost);
		}
	}
	
}//END