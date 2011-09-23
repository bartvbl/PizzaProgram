package pizzaProgram.dataObjects;

public class Extra {

	private int id;
	private double priceValPart;
	private char priceFuncPart;
	private String name;

	public Extra(int id, String name, String priceFunc) {
		this.id = id;
		this.name = name;
		this.priceFuncPart = priceFunc.charAt(0);
		this.priceValPart = Double.parseDouble(priceFunc.substring(1));
	}

	public double getPriceValPart() {
		return priceValPart;
	}

	public char getPriceFuncPart() {
		return priceFuncPart;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	public String toString() {
		return id + " " + priceFuncPart + " " + priceValPart + " " + name;
	}

}// END
