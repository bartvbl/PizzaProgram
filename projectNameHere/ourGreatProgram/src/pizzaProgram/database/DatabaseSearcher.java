package pizzaProgram.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.database.databaseUtils.DatabaseDataObjectGenerator;
import pizzaProgram.database.databaseUtils.DatabaseReader;
import pizzaProgram.database.databaseUtils.DatabaseResultsFeedbackProvider;

public class DatabaseSearcher {

	public static ArrayList<Customer> searchCustomerByString(String searchQuery){
		String query = DatabaseSearcher.generateCustomerSearchQuery(searchQuery);
		ResultSet results = DatabaseConnection.fetchData(query);
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		try {
			customerList = DatabaseDataObjectGenerator.generateCustomerList(results);
			return customerList;
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showSearchCustomersFailedMessage();
		}
		return null;
	}

	public static ArrayList<Dish> searchDishByString(String searchQuery){
		String query = DatabaseSearcher.generateDishSearchQuery(searchQuery);
		ResultSet results = DatabaseConnection.fetchData(query);
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		try {
			dishList = DatabaseDataObjectGenerator.generateDishList(results);
			return dishList;
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showSearchDishesFailedMessage();
		}
		return null;
	}

	public static String generateCustomerSearchQuery(String searchQuery) {
		String query = "SELECT * FROM Customer LEFT JOIN CustomerNotes ON (Customer.commentID = CustomerNotes.NoteID) WHERE (";
		String[] keywords = searchQuery.split(" ");
		int counter = 0;
		for(String keyword : keywords){
			if(counter != 0){
				query += "OR";
			}
			query += "(Customer.FirstName LIKE '%"+keyword+"%') OR ";
			query += "(Customer.LastName LIKE '%"+keyword+"%') OR ";
			query += "(Customer.TelephoneNumber LIKE '%"+keyword+"%')";
			counter++;
		}
		query += ") LIMIT 30;";
		return query;
	}

	public static String generateDishSearchQuery(String searchQuery)
	{
		String query = "SELECT * FROM Dishes WHERE (";
		String[] keywords = searchQuery.split(" ");
		int counter = 0;
		for(String keyword : keywords){
			if(counter != 0){
				query += "OR";
			}
			query += "(Dishes.Price LIKE '%"+keyword+"%') OR ";
			query += "(Dishes.Name LIKE '%"+keyword+"%') OR ";
			query += "(Dishes.Description LIKE '%"+keyword+"%')";
			counter++;
		}
		query += ") LIMIT 30;";
		return query;
	}

	private static String generateExtraSearchQuery(String searchQuery)
	{
		String query = "SELECT * FROM Dishes WHERE (";
		String[] keywords = searchQuery.split(" ");
		int counter = 0;
		for(String keyword : keywords){
			if(counter != 0){
				query += "OR";
			}
			query += "(Dishes.Price LIKE '%"+keyword+"%') OR ";
			query += "(Dishes.Name LIKE '%"+keyword+"%') OR ";
			query += "(Dishes.Description LIKE '%"+keyword+"%')";
			counter++;
		}
		query += ") LIMIT 30;";
		return query;
	}

	public static ArrayList<Order> getOrdersByKeywords(String keywordString, String[] orderStatusStringList) {
		String whereClause = DatabaseSearcher.generateOrderSearchWhereClause(keywordString, orderStatusStringList);
		String query = DatabaseReader.getOrderSelectionQuery(whereClause, "LIMIT 30");
		ResultSet results = DatabaseConnection.fetchData(query);
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			orderList = DatabaseDataObjectGenerator.generateOrderListFromResultSet(results);
			return orderList;
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showSearchOrdersFailedMessage();
		}
		return null;
	}

	public static String generateOrderSearchWhereClause(String keywordString, String[] orderStatus){
		String[] keywords = keywordString.split(" ");
		String whereClause = "(";
		int counter = 0;
		for(String status : orderStatus){
			if(counter != 0){
				whereClause += " OR ";
			}
			
			counter++;
			whereClause += "(Orders.OrdersStatus = '"+status+"')";
		}
		
		whereClause += ") AND (";
		counter = 0;
		for(String keyword : keywords){
			if(counter != 0){
				whereClause += "OR ";
			}
			whereClause += "(Orders.OrdersStatus LIKE '%"+keyword+"%') OR ";
			whereClause += "(Orders.DeliveryMethod LIKE '%"+keyword+"%') OR ";
			whereClause += "(Orders.OrdersID LIKE '%"+keyword+"%') ";
			counter++;
		}
		whereClause += ")";
		return whereClause;
	}

}
