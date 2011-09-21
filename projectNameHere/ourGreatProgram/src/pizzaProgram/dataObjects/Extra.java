package pizzaProgram.dataObjects;

public class Extra {

	private int price;
	private String name;
	private String description;
	
	public Extra(int price, String name, String description) {
		this.price = price;
		this.name = name;
		this.description = description;
	}

	public int getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
}//END
