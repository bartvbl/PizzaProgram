package pizzaProgram.dataObjects;

public class Extra {

	public final int id;
	public final int priceValPart;
	public final char priceFuncPart;
	public final String name;
	public final boolean isActive;

	public Extra(int id, String name, String priceFunc, boolean isActive) {
		this.id = id;
		this.name = name;
		this.priceFuncPart = priceFunc.charAt(0);
		this.priceValPart = Integer.parseInt(priceFunc.substring(1));
		this.isActive = isActive;
	}
	
	public String toString() {
		return id + " " + priceFuncPart + " " + priceValPart + " " + name;
	}

}// END
