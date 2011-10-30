package pizzaProgram.events.moduleEventHandlers;

import java.sql.ResultSet;
import java.sql.SQLException;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.database.DatabaseModule;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;

public class Database_OrderEventHandler{
	private DatabaseConnection databaseConnection;
	private EventDispatcher eventDispatcher;
	
	
	public Database_OrderEventHandler(DatabaseConnection databaseConnection, EventDispatcher eventDispatcher) 
	{
		this.databaseConnection = databaseConnection;
		this.eventDispatcher = eventDispatcher;
	}
	
	public void handleEvent(Event<?> event)
	{
		
	}
	
	private void createNewOrder(Event<?> event)
	{
//		if(!(event.getEventParameterObject() instanceof Order))
//		{
//			System.err.println("ERROR: failed to add order to database; attached object is not an Order instance.");
//			return;
//		}
//		
//		Order order = event.getEventParameterObject();
//		
//		String deliverymethod = (isDeliverAtHome ? Order.DELIVER_AT_HOME
//				: Order.PICKUP_AT_RESTAURANT);
//		try {
//			if (!DatabaseConnection.fetchData(
//					"SELECT * FROM Orders WHERE CustomerID="
//							+ customer.customerID + " AND OrdersStatus='"
//							+ Order.REGISTERED + "';").next()) {
//				int commentID = -1;
//				if (!(comment == null || comment.equals(""))) {
//					DatabaseConnection
//							.insertIntoDB("INSERT INTO OrderComments (Comment) VALUES ('"
//									+ comment + "');");
//					ResultSet commentIDset = DatabaseConnection
//							.fetchData("SELECT CommentID FROM OrderComments WHERE Comment='"
//									+ comment + "';");
//					if (commentIDset.next()) {
//						commentID = commentIDset.getInt(1);
//					}
//				}
//				return DatabaseConnection
//						.insertIntoDB("INSERT INTO Orders (CustomerID, TimeRegistered, DeliveryMethod, CommentID) VALUES ("
//								+ customer.customerID
//								+ ", NOW(), '"
//								+ deliverymethod + "', " + commentID + ");");
//			}
//		} catch (SQLException e) {
//			System.err.println("An error occured during your database query: "
//					+ e.getMessage());
//			return false;
//		}
//		return false;

	}
	
	private String selectDeliveryMethod(String deliveryMethod)
	{
		return null;
	}
}
