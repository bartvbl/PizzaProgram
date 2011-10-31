package pizzaProgram.database.databaseUtils;

import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.database.DatabaseConnection;

public class DatabaseWriter {
	public static void writeNewOrder(UnaddedOrder order)
	{
		
		int commentID = -1;
		if(order.comment != "")
		{
			commentID = createOrderComment(order.comment);
		}
		int orderID = createNewOrder(order, commentID);
		if(orderID != -1)
		{
			addDishesToOrder(order, orderID);
		}
	}

	private static void addDishesToOrder(UnaddedOrder order, int orderID) {
		for(OrderDish dish : order.orderedDishes)
		{
			addDishToOrder(dish, orderID);
		}
	}

	private static void addDishToOrder(OrderDish dish, int orderID) {
		int orderContentsID = DatabaseConnection.insertIntoDBAndReturnID("INSERT INTO OrdersContents VALUES (NULL, "+orderID+", "+dish.dish.dishID+");");
		if(orderContentsID == -1)
		{
			return;
		}
		for(Extra extra : dish.getExtras())
		{
			addExtraToOrder(orderContentsID, extra);
		}
	}

	private static void addExtraToOrder(int orderContentsID, Extra extra) {
		DatabaseConnection.insertIntoDB("INSERT INTO DishExtrasChosen VALUES ("+orderContentsID+", "+extra.id+")");
	}

	private static int createOrderComment(String comment) {
		int commentID = DatabaseConnection.insertIntoDBAndReturnID("INSERT INTO OrderComments VALUES (NULL, '"+comment+"');");
		return commentID;
	}

	private static int createNewOrder(UnaddedOrder order, int commentID) {
		int orderID = DatabaseConnection.insertIntoDBAndReturnID("INSERT INTO Orders VALUES (NULL, "+order.customer.customerID+", NOW(), '"+Order.REGISTERED+"', '"+order.deliveryMethod+"', "+commentID+");");
		return orderID;
	}
}
