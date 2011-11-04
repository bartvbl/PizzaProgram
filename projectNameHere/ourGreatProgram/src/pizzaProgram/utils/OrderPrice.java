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
		String output = formatOutputString(this.orderPrice.toPlainString());
		return output;
	}
	
	public String getDeliveryCostString()
	{
		String output = formatOutputString(this.deliveryCost.toPlainString());
		return output;
	}
	
	public String getTotalOverallOrderCostString()
	{
		String output = formatOutputString(this.totalOverallOrderCost.toPlainString());
		return output;
	}
	
	private String formatOutputString(String valueString)
	{
		if(valueString.indexOf('.') == -1)
		{
			valueString += ".00";
		}
		valueString = "kr. " + valueString;
		return valueString;
	}
}
