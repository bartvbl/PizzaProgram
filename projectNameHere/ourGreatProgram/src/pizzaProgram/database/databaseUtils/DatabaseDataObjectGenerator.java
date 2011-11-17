package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;

public class DatabaseDataObjectGenerator {

	public static ArrayList<Dish> generateDishList(ResultSet results) throws SQLException {
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		Dish currentDish;
		while(results.next()){
			currentDish = DatabaseDataObjectGenerator.createDish(results, 1);	
			dishList.add(currentDish);
		}
		return dishList;
	}

	public static ArrayList<Customer> generateCustomerList(ResultSet results) throws SQLException {
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		Customer currentCustomer;
		while(results.next()){
			currentCustomer = DatabaseDataObjectGenerator.createCustomer(results, 1, 9);
			customerList.add(currentCustomer);
		}
		return customerList;
	}

	public static ArrayList<Extra> generateExtrasList(ResultSet results) throws SQLException {
		ArrayList<Extra> extrasList = new ArrayList<Extra>();
		Extra currentExtra;
		while(results.next()){
			currentExtra = DatabaseDataObjectGenerator.createExtra(results, 1);
			extrasList.add(currentExtra);
		}
		return extrasList;
	}

	public static ArrayList<Order> generateOrderListFromResultSet(ResultSet result) throws SQLException{
		System.out.println("generating order list");
		ArrayList<Order> orderList = new ArrayList<Order>();
		//order has dummy parameters due to java not liking uninitialized variables. 
		//The variable will instantiate in the first loop; this one should therefore disappear
		Order currentOrder = new Order(-1, null, null, "", "", "");
		OrderDish currentOrderDish = new OrderDish(-1, null);
		Customer currentCustomer;
		Dish currentDish;
		Extra currentExtra;
		int currentOrderID = -1;
		int currentOrderContentID = -1;
		while(result.next()){
			if(result.getInt(1) != currentOrderID){
				currentOrderID = result.getInt(1);
				currentCustomer = DatabaseDataObjectGenerator.createCustomer(result, 1, 9);
				currentOrder = DatabaseDataObjectGenerator.createOrder(result, currentCustomer, 27, 25);
				orderList.add(currentOrder);
			}
			if(result.getInt(33) != currentOrderContentID){
				currentOrderContentID = result.getInt(33);
				currentDish = DatabaseDataObjectGenerator.createDish(result, 12);
				currentOrderDish = new OrderDish(currentOrder.orderID, currentDish);
				currentOrder.addOrderDish(currentOrderDish);
			}
			currentExtra = DatabaseDataObjectGenerator.createExtra(result, 22);
			currentOrderDish.addExtra(currentExtra);
		}
		return orderList;
	}

	static Customer createCustomer(ResultSet resultSet, int customerTableColumnOffset, int customerNotesTableColumnOffset) throws SQLException{
		int customerID = resultSet.getInt(customerTableColumnOffset + 0);
		String firstName = resultSet.getString(customerTableColumnOffset + 1);
		String lastName = resultSet.getString(customerTableColumnOffset + 2);
		String address = resultSet.getString(customerTableColumnOffset + 3);
		int postalCode = resultSet.getInt(customerTableColumnOffset + 4);
		String city = resultSet.getString(customerTableColumnOffset + 5);
		int phoneNumber = resultSet.getInt(customerTableColumnOffset + 6);
		String comment = resultSet.getString(customerNotesTableColumnOffset + 1);
		Customer customer = new Customer(customerID, firstName, lastName, address, postalCode, city, phoneNumber, comment);
		return customer;
	}

	static Dish createDish(ResultSet resultSet, int columnOffset) throws SQLException{
		int dishID = resultSet.getInt(columnOffset + 0);
		int price = resultSet.getInt(columnOffset + 1);
		String name = resultSet.getString(columnOffset + 2);
		boolean containsGluten = resultSet.getBoolean(columnOffset + 3);
		boolean containsNuts = resultSet.getBoolean(columnOffset + 4);
		boolean containsDairy = resultSet.getBoolean(columnOffset + 5);
		boolean isVegetarian = resultSet.getBoolean(columnOffset + 6);
		boolean isSpicy = resultSet.getBoolean(columnOffset + 7);
		String description = resultSet.getString(columnOffset + 8);
		boolean isActive = resultSet.getBoolean(columnOffset + 9);
		Dish dish = new Dish(dishID, price, name, containsGluten, containsNuts, containsDairy, isVegetarian, isSpicy, description, isActive);
		return dish;
	}

	static Extra createExtra(ResultSet resultSet, int columnOffset) throws SQLException{
		int extrasID = resultSet.getInt(columnOffset + 0);
		String name = resultSet.getString(columnOffset + 1);
		String price = resultSet.getString(columnOffset + 2);
		boolean isActive = resultSet.getBoolean(columnOffset + 3);
		Extra extra = new Extra(extrasID, name, price, isActive);
		return extra;
	}

	static Order createOrder(ResultSet resultSet, Customer customer, int orderTableColumnOffset, int orderCommentsTableColumnOffset) throws SQLException{
		int orderID = resultSet.getInt(orderTableColumnOffset + 1);
		String timeRegistered = resultSet.getString(orderTableColumnOffset + 3);
		String orderStatus = resultSet.getString(orderTableColumnOffset + 4);
		String deliveryMethod = resultSet.getString(orderTableColumnOffset + 5);
		String comment = resultSet.getString(orderCommentsTableColumnOffset + 1);
		Order order = new Order(orderID, customer, timeRegistered, orderStatus, deliveryMethod, comment);
		return order;
	}

}
