package pizzaProgram.dataObjects;

public class Extra {

	private int id;
	private int price;
	private String name;

	public Extra(int id, String name, int price) {
		this.id = id;
		this.price = price;
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
	
	public String toString() {
		return id + " " + price + " " + name;
	}

}// END
