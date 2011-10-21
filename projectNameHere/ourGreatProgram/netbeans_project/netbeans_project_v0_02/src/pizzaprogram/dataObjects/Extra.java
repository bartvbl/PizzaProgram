package pizzaProgram.dataObjects;

public class Extra {

	public final int id;
	public final double priceValPart;
	public final char priceFuncPart;
	public final String name;

	public Extra(int id, String name, String priceFunc) {
		this.id = id;
		this.name = name;
		this.priceFuncPart = priceFunc.charAt(0);
		this.priceValPart = Double.parseDouble(priceFunc.substring(1));
	}
	
	public String toString() {
		return id + " " + priceFuncPart + " " + priceValPart + " " + name;
	}

}// END
