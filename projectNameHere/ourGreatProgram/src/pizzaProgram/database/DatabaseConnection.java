package pizzaProgram.database;

import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventHandler;

public class DatabaseConnection implements EventHandler{
    public static final String DATABASE_HOST = "jdbc:mysql://mysql.stud.ntnu.no/henninwo_it1901";
    public static final String DATABASE_USERNAME = "henninwo_it1901";
    public static final String DATABASE_PASSWORD = "pizzalulz";
    //TODO: add the database name
    public static final String DATABASE_NAME = "???";
    
    private Connection connection;
    private QueryHandler queryHandler;

    public DatabaseConnection() {
        this.queryHandler = new QueryHandler();
    }

    public void connect() {
    	Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(DATABASE_HOST, DATABASE_USERNAME, DATABASE_PASSWORD);
            System.out.println("Tilkoblingen fungerte.");
        } catch (SQLException ex) {
            System.out.println("Tilkobling feilet: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("Feilet under driverlasting: " + ex.getMessage());
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            if (connection != null) this.connection = connection;
        }
    }

    public void disconnect() {
        try {
			this.connection.close();
		} catch (SQLException e) {
			System.out.println("Failed to clise MySQL connection!");
		}
    }

    public boolean isConnected(int timeoutInMilliseconds) {
    	if(this.connection != null)
    	{
    		try {
				if(this.connection.isValid(timeoutInMilliseconds))
				{
					return true;
				}
			} catch (SQLException e) {
				return false;
			}
    	}
    	return false;
    }

    
    //HERE BE JUKSEMETODER
    private ResultSet executeQuery(String q){
    	try {
			java.sql.Statement statement = connection.createStatement();
			return statement.executeQuery(q);
		} catch (SQLException e) {
			System.out.println("Query Failed: " + e.getMessage());
		}
		return null;
    }
    
    public ArrayList<Order> getOrders(){
    	ArrayList<Order> orders = new ArrayList<Order>();
    	ResultSet results = executeQuery("");
    	
    	
    	try {
    		
			for(int i = 0; results.next(); i++){
				results.getString(i);
				
				
				//TODO her leser vi inn 
				
			}
			results.close();
			
		} catch (SQLException e) {
		}
    	
    	return null;
    }
    public ArrayList<Customer> getCustomers(){
    	return null;
    }
    public void newOrder(Order order){
    	
    }
    public void newCustomer(Customer customer){
    	
    }
    public void changeOrder(Order order){
    	
    }
    //SLUTT PÅ JUKSEMETODER
    
    public DatabaseTable databaseQuery(String query) {
        return null;
    }

    public static void main(String[] args) {
        DatabaseConnection connection = new DatabaseConnection();
    }

	public void handleEvent(Event event) {
		
		this.queryHandler.handleEvent(event);
	}
}