package pizzaProgram.dataObjects;

import java.sql.Date;
import java.util.ArrayList;

public class Order {

	private int orderID;
	private Customer customer;
	private Date timeRegistered;
	private ArrayList<OrderDish> orderedDishes;

	private String status;
	private String deliveryMethod;
	private String comment = "";

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
	public Order(int id, Customer customer, Date timeRegistered, String status,
			String deliveryMethod, String comment) {
		if (id < 0) {
			throw new IllegalArgumentException("id cannot be less than 0");
		} else if (customer == null) {
			throw new IllegalArgumentException("customer can't be null");
		} else if (timeRegistered == null) {
			throw new IllegalArgumentException("timeRegistered cannot be null");
		} else if (!(status.equals(Order.BEING_COOKED)
				|| status.equals(Order.BEING_DELIVERED)
				|| status.equals(Order.HAS_BEEN_COOKED)
				|| status.equals(Order.DELIVERED) || status
				.equals(Order.REGISTERED))) {
			throw new IllegalArgumentException(
					"the status of the order, must be one of Order.REGISTERED, Order.BEING_COOKED, Order.HAS_BEEN_COOKED, Order.BEING_DELIVERED or Order.DELIVERED");
		} else if (!(deliveryMethod.equals(Order.PICKUP_AT_RESTAURANT) || deliveryMethod
				.equals(Order.DELIVER_AT_HOME))) {
			throw new IllegalArgumentException(
					"the method of delivery, must be Order.DELIVER_AT_HOME or Order.PICKUP_AT_RESTAURANT");
		} else if (comment == null) {
			throw new IllegalArgumentException(
					"comment can be \"\" but not null");
		}

		this.orderID = id;
		this.customer = customer;
		this.timeRegistered = timeRegistered;
		this.status = status;
		this.deliveryMethod = deliveryMethod;
		this.comment = comment;
		orderedDishes = new ArrayList<OrderDish>();
	}

	/**
	 * @return - the customer of this order
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @return - the ID of this order
	 */
	public int getID() {
		return orderID;
	}

	/**
	 * @return - the dishes contained in this order
	 */
	public Date getTimeRegistered() {
		return timeRegistered;
	}

	/**
	 * @return - the status of this order
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return - the delivery method of this order
	 */
	public String getDeliveryMethod() {
		return deliveryMethod;
	}

	/**
	 * @return - the comment of this order
	 */
	public String getComment() {
		return comment;
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
