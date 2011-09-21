package pizzaProgram.dataObjects;

import java.util.ArrayList;

public class Order {

	private int orderID;
	private Customer customer;
	private ArrayList<OrderDish> dishes;
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
	 * @param id - unique order id, must be > 0
	 * @param customer - the customer of the order , not null
	 * @param dishes -  a list of dishes in the order, not null
	 * @param status - the status of the order, must be one of Order.REGISTERED, Order.BEING_COOKED, Order.HAS_BEEN_COOKED, Order.BEING_DELIVERED or Order.DELIVERED
	 * @param deliveryMethod - the method of delivery, must be Order.DELIVER_AT_HOME or Order.PICKUP_AT_RESTAURANT
	 * @param comment - a comment for the order, can be empty but not null
	 */
	public Order(int id, Customer customer, ArrayList<OrderDish> dishes, String status, String deliveryMethod, String comment) {
		if(id < 0){
			throw new IllegalArgumentException("id cannot be less than 0");
		}
		else if(customer == null){
			throw new IllegalArgumentException("customer can't be null");
		}
		else if(dishes == null || dishes.isEmpty()){
			throw new IllegalArgumentException("dishes can't be null or empty");
		}
		else if(!(status.equals(Order.BEING_COOKED) || status.equals(Order.BEING_DELIVERED) ||status.equals(Order.HAS_BEEN_COOKED) || status.equals(Order.DELIVERED) || status.equals(Order.REGISTERED))){
			throw new IllegalArgumentException("the status of the order, must be one of Order.REGISTERED, Order.BEING_COOKED, Order.HAS_BEEN_COOKED, Order.BEING_DELIVERED or Order.DELIVERED");
		}
		else if(!(status.equals(Order.PICKUP_AT_RESTAURANT) || status.equals(Order.DELIVER_AT_HOME))){
			throw new IllegalArgumentException("the method of delivery, must be Order.DELIVER_AT_HOME or Order.PICKUP_AT_RESTAURANT");
		}
		else if(comment == null){
			throw new IllegalArgumentException("comment can be \"\" but not null");
		}
		
		this.orderID = id;
		this.customer = customer;
		this.dishes = dishes;
		this.status = status;
		this.deliveryMethod = deliveryMethod;
		this.comment = comment;
	}
	
	/**
	 * @return - the customer of this order
	 */
	public Customer getCustomer(){
		return customer;
	}
	
	/**
	 * @return - the ID of this order
	 */
	public int getID(){
		return orderID;
	}
	
	/**
	 * @return - the dishes contained in this order
	 */
	public ArrayList<OrderDish> getDishes() {
		return dishes;
	}
	
	/**
	 * @return -  the status of this order
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

	
}///END
