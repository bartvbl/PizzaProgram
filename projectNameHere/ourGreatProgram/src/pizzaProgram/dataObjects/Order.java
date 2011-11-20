package pizzaProgram.dataObjects;

import java.util.ArrayList;

import pizzaProgram.utils.DateFormatter;
/**
 * Represents an order from the database
 * @author Bart
 *
 */
public class Order {

	public final int orderID;
	public final Customer customer;
	public final String timeRegistered;
	public final ArrayList<OrderDish> orderedDishes;

	public final String status;
	public final String deliveryMethod;
	public final String comment;

	public static final String REGISTERED = "registered";
	public static final String BEING_COOKED = "being cooked";
	public static final String HAS_BEEN_COOKED = "has been cooked";
	public static final String BEING_DELIVERED = "being delivered";
	public static final String DELIVERED = "delivered";
	public static final String DELIVER_AT_HOME = "deliver at home";
	public static final String PICKUP_AT_RESTAURANT = "pickup at restaurant";

	/**
	 * @param id
	 *            - unique order id, must be > 0
	 * @param customer
	 *            - the customer of the order , not null
	 * @param timeRegistered
	 *            - the time the order was registered, not null
	 * @param status
	 *            - the status of the order, must be one of Order.REGISTERED,
	 *            Order.BEING_COOKED, Order.HAS_BEEN_COOKED,
	 *            Order.BEING_DELIVERED or Order.DELIVERED
	 * @param deliveryMethod
	 *            - the method of delivery, must be Order.DELIVER_AT_HOME or
	 *            Order.PICKUP_AT_RESTAURANT
	 * @param comment
	 *            - a comment for the order, can be empty but not null
	 */
	public Order(int id, Customer customer, String timeRegistered, String status,
			String deliveryMethod, String comment) {
		
		this.orderID = id;
		this.customer = customer;
		if(timeRegistered != null)
		{
			this.timeRegistered = DateFormatter.formatDateString(timeRegistered);
		} else {
			this.timeRegistered = timeRegistered;
		}
		this.status = status;
		this.deliveryMethod = deliveryMethod;
		this.comment = comment;
		orderedDishes = new ArrayList<OrderDish>();
	}
	/**
	 * @return - the list of dishes ordered in this order
	 */
	public ArrayList<OrderDish> getOrderedDishes() {
		return orderedDishes;
	}

	/**
	 * Method to add a dish to the dishes ordered in this order
	 * 
	 * @param orderDish
	 *            - the dish to add to the list of ordered items
	 */
	public void addOrderDish(OrderDish orderDish) {
		orderedDishes.add(orderDish);
	}

	public String toString() {
		String tempString = "Order ID: " + orderID + ", Name: "
				+ customer.firstName + " " + customer.lastName
				+ ", Time registered: " + timeRegistered.toString()
				+ ", Order status: " + status + ", Delivery method: "
				+ deliveryMethod + ". The contents of the order is:\n";
		for (OrderDish od : orderedDishes)
			if (od != null) {
				tempString += od.toString();
			}
		return tempString;
	}

}// /END
