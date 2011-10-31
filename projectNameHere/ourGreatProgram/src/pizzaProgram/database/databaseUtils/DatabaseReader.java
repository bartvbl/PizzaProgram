package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.database.DatabaseConnection;

public class DatabaseReader {
	public static ArrayList<Customer> getAllCustomers()
	{
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Customer LEFT JOIN CustomerNotes ON ( Customer.commentID = CustomerNotes.NoteID );");
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		try {
			customerList = generateCustomerList(results);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customerList;
	}

	private static ArrayList<Customer> generateCustomerList(ResultSet results) throws SQLException {
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		Customer currentCustomer;
		while(results.next())
		{
			currentCustomer = new Customer(	results.getInt(1),results.getString(2), results.getString(3),results.getString(4), results.getInt(5),results.getString(6), results.getInt(7), results.getString(11));
			customerList.add(currentCustomer);
		}
		return customerList;
	}

	public static ArrayList<Dish> getAllDishes() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Dishes WHERE (isActive=1);");
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		try {
			dishList = generateDishList(results);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dishList;
	}

	private static ArrayList<Dish> generateDishList(ResultSet results) throws SQLException {
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		Dish currentDish;
		while(results.next())
		{
			currentDish = new Dish(	results.getInt(1), results.getInt(2),results.getString(3), results.getBoolean(4),results.getBoolean(5), results.getBoolean(6),results.getBoolean(7), results.getBoolean(8),results.getString(9), results.getBoolean(10));	
			dishList.add(currentDish);
		}
		return dishList;
	}

	public static ArrayList<Extra> getAllExtras() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Extras WHERE (isActive=1);");
		ArrayList<Extra> dishList = new ArrayList<Extra>();
		try {
			dishList = generateExtrasList(results);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dishList;
	}

	private static ArrayList<Extra> generateExtrasList(ResultSet results) throws SQLException {
		ArrayList<Extra> extrasList = new ArrayList<Extra>();
		Extra currentExtra;
		while(results.next())
		{
			currentExtra = new Extra(results.getInt(1),results.getString(2), results.getString(3),results.getBoolean(4));
			extrasList.add(currentExtra);
		}
		return extrasList;
	}
}
