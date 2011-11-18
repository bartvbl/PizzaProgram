package pizzaProgram.database.databaseUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.Setting;
import pizzaProgram.database.DatabaseConnection;

public class DatabaseReader {
	
	public static ArrayList<Customer> getAllCustomers(){
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Customer LEFT JOIN CustomerNotes ON ( Customer.commentID = CustomerNotes.NoteID );");
		ArrayList<Customer> customerList = new ArrayList<Customer>();
		try {
			customerList = DatabaseDataObjectGenerator.generateCustomerList(results);
			return customerList;
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showGetAllCustomersFailedMessage();
		}
		return null;
	}
	
	public static Setting getSettingByKey(String key)
	{
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Config WHERE ConfigKey='"+key+"';");
		try {
			Setting setting = DatabaseDataObjectGenerator.createSetting(results, 1);
			return setting;
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showSettingFailedMessage();
		}
		return null;
	}
	
	public static ArrayList<Setting> getAllSettings()
	{
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Config;");
		try {
			ArrayList<Setting> settings = DatabaseDataObjectGenerator.generateSettingsList(results);
			return settings;
		} catch (SQLException e) {
			DatabaseResultsFeedbackProvider.showSettingFailedMessage();
		}
		return null;
	}
	
	public static ArrayList<Dish> getAllActiveDishes() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Dishes WHERE (isactive=1);");
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		try {
			dishList = DatabaseDataObjectGenerator.generateDishList(results);
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showGetAllDishesFailedMessage();
		}
		return dishList;
	}
	
	public static ArrayList<Dish> getAllDishes() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Dishes;");
		ArrayList<Dish> dishList = new ArrayList<Dish>();
		try {
			dishList = DatabaseDataObjectGenerator.generateDishList(results);
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showGetAllDishesFailedMessage();
		}
		return dishList;
	}
	
	public static ArrayList<Extra> getAllActiveExtras() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Extras WHERE (isactive=1);");
		ArrayList<Extra> dishList = new ArrayList<Extra>();
		try {
			dishList = DatabaseDataObjectGenerator.generateExtrasList(results);
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showGetAllExtrasFailedMessage();
		}
		return dishList;
	}
	
	public static ArrayList<Extra> getAllExtras() {
		ResultSet results = DatabaseConnection.fetchData("SELECT * FROM Extras;");
		ArrayList<Extra> dishList = new ArrayList<Extra>();
		try {
			dishList = DatabaseDataObjectGenerator.generateExtrasList(results);
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showGetAllExtrasFailedMessage();
		}
		return dishList;
	}
	
	public static ArrayList<Order> getAllUncookedOrders() {
		ResultSet result = DatabaseConnection.fetchData(getOrderSelectionQuery("Orders.OrdersStatus = '"+Order.REGISTERED+"' OR Orders.OrdersStatus = '"+Order.BEING_COOKED+"'", ""));
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			orderList = DatabaseDataObjectGenerator.generateOrderListFromResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showGetAllUncookedOrdersFailedMessage();
		}
		return orderList;
	}
	
	public static String getOrderSelectionQuery(String whereClause, String extraOptions){
		String query = "SELECT Customer.* , CustomerNotes.* , Dishes.* , Extras.* , OrderComments.* , Orders.*, OrdersContents.* FROM Orders " +
		"LEFT JOIN OrderComments ON ( Orders.CommentID = OrderComments.CommentID ) " +
		"INNER JOIN OrdersContents ON ( Orders.OrdersID = OrdersContents.OrdersID ) " +
		"INNER JOIN DishExtrasChosen ON ( OrdersContents.OrdersContentsID = DishExtrasChosen.OrdersContentsID ) " +
		"LEFT JOIN Customer ON ( Orders.CustomerID = Customer.CustomerID ) " +
		"LEFT JOIN CustomerNotes ON ( Customer.CommentID = CustomerNotes.NoteID ) " +
		"LEFT JOIN Dishes ON ( OrdersContents.DishID = Dishes.DishID ) " +
		"LEFT JOIN Extras ON ( Extras.ExtrasID = DishExtrasChosen.DishExtraID ) " +
		"WHERE (" + whereClause + ") "+extraOptions+" ;"; 
		return query;
	}
	
	public static ArrayList<Order> getAllUndeliveredOrders() {
		ResultSet result = DatabaseConnection.fetchData(getOrderSelectionQuery("Orders.OrdersStatus = '"+Order.HAS_BEEN_COOKED+"' OR Orders.OrdersStatus = '"+Order.BEING_DELIVERED+"'", ""));
		ArrayList<Order> orderList = new ArrayList<Order>();
		try {
			orderList = DatabaseDataObjectGenerator.generateOrderListFromResultSet(result);
		} catch (SQLException e) {
			e.printStackTrace();
			DatabaseResultsFeedbackProvider.showGetAllUndeliveredOrdersFailedMessage();
		}
		return orderList;
	}

}//END
