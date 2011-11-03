package pizzaProgram.utils;

import java.math.BigDecimal;

public class OrderPrice {
	private BigDecimal orderPrice;
	private BigDecimal deliveryCost;
	private BigDecimal totalOverallOrderCost;
	
	public OrderPrice(BigDecimal orderPrice, BigDecimal deliveryCost, BigDecimal totalOverallOrderCost)
	{
		this.orderPrice = orderPrice;
		this.deliveryCost = deliveryCost;
		this.totalOverallOrderCost = totalOverallOrderCost;
	}
	
	public String getOrderPriceString()
	{
		return this.orderPrice.toString();
	}
	
	public String getDeliveryCostString()
	{
		return this.deliveryCost.toString();
	}
	
	public String getTotalOverallOrderCostString()
	{
		return this.totalOverallOrderCost.toString();
	}
}
