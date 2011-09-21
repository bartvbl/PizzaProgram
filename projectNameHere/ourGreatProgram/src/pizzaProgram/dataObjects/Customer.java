package pizzaProgram.dataObjects;

public class Customer {

	private int customerID;
	private String firstName;
	private String lastName;
	private String address;
	private int phoneNumber;
	private String comment;
	
	public Customer(int customerID, String firstName, String lastName, String address, int phoneNumber, String comment) {
		this.customerID = customerID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.comment = comment;
	}

	public int getCustomerID() {
		return customerID;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public int getPhoneNumber() {
		return phoneNumber;
	}

	public String getComment() {
		return comment;
	}
	
	
	
}//END