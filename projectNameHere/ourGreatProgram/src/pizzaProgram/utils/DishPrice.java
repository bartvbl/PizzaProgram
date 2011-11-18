package pizzaProgram.utils;

import java.math.BigDecimal;

public class DishPrice {
	private BigDecimal totalPrice;
	
	public DishPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public String getPriceAsString(){
		this.totalPrice = this.totalPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		String valueString = this.totalPrice.toPlainString();
		if(valueString.indexOf('.') == -1){
			valueString += ".00";
		}
		valueString = "kr. " + valueString;
		return valueString;
	}
	
	public BigDecimal getPriceAsBigDecimal(){
		return this.totalPrice;
	}

}
