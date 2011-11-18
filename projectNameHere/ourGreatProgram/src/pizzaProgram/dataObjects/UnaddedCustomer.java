package pizzaProgram.dataObjects;

public class UnaddedCustomer extends Customer {

	public UnaddedCustomer(String firstName, String lastName,
			String address, String postalCode, String city, int phoneNumber,
			String comment) {
		super(-1, firstName, lastName, address, postalCode, city, phoneNumber, comment);
	}

}
