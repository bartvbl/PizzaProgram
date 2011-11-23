package pizzaProgram.gui.EventHandlers;

import java.util.ArrayList;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.dataObjects.UnaddedOrderDish;
import pizzaProgram.database.databaseUtils.DataCleaner;
/**
 * A class that temporarily holds order data while the user is creating a new order in the Order GUI
 * @author Bart
 *
 */
public class OrderGUI_TemporaryOrderDataStorage {
	/**
	 * The customer that is currently ordering a pizza
	 */
	private Customer currentCustomer;
	/**
	 * A list of dishes that have been added to the order
	 */
	private ArrayList<UnaddedOrderDish> dishList = new ArrayList<UnaddedOrderDish>();
	/**
	 * A String holding any comments about the order
	 */
	private String orderComments;
	/**
	 * The customer's choice for delivery method.
	 */
	private String deliveryMethod;
	
	/**
	 * This function generates an UnaddedOrder instance from the provided data, so that it can be sent to the database.
	 * It will then reset the temporary order storage, so that it can be used again for the next order.
	 * @return An UnaddedOrder instance containing all the order information stored in this object.
	 */
	public UnaddedOrder convertToOrderObjectAndReset(){
		String comments = DataCleaner.cleanDbData(this.orderComments);
		UnaddedOrder order = new UnaddedOrder(currentCustomer, this.deliveryMethod, comments);
		for(UnaddedOrderDish dish : this.dishList){
			order.addOrderDish(dish);
		}
		
		this.reset();
		return order;
	}
	
	/**
	 * Resets the data storage
	 */
	public void reset(){
		this.currentCustomer = null;
		this.orderComments = null;
		this.dishList = new ArrayList<UnaddedOrderDish>();
		this.deliveryMethod = null;
	}
	
	/**
	 * Sets the order's delivery method
	 * @param method A value that matches one of the enum values of the deliveryMethod column in the database
	 */
	public void setDeliveryMethod(String method){
		this.deliveryMethod = method;
	}
	
	/**
	 * Sets the customer that is ordering the order
	 * @param customer A Customer instance, referring to a row in the Customers table in the database
	 */
	public void setCustomer(Customer customer){
		this.currentCustomer = customer;
	}
	
	/**
	 * Sets any comments about the order.
	 * @param comments A String containing order comments
	 */
	public void setOrderComments(String comments){
		this.orderComments = comments;
	}
	
	/**
	 * Adds a dish to the order that is currently being created
	 * @param dish The dish to be added
	 * @param extrasToBeAddedToDish The extras that are to be added, and are related to this dish
	 */
	public void addDish(Dish dish, Extra[] extrasToBeAddedToDish){
		UnaddedOrderDish orderDish = new UnaddedOrderDish(dish);
		for(Extra extra : extrasToBeAddedToDish){
			orderDish.addExtra(extra);
		}
		this.dishList.add(orderDish);
	}
	
	/**
	 * Returns the number of dishes that have currently been added to the order
	 * @return The number of dishes that have currently been added to the order
	 */
	public int getNumberOfDishes(){
		return dishList.size();
	}
	
	/**
	 * Removes a dish from the order, specified by the index of the row in the 
	 * @param indexInTable The index of the row as the dish appears in the table in the order GUI
	 */
	public void removeDishFromOrder(int indexInTable){
		this.dishList.remove(indexInTable);
	}
	
	/**
	 * Duplicates a dish in the order, specified by the index of the row where the dish is shown in the table in the order GUI
	 * @param indexInTable The index of the row as the dish appears in the table in the order GUI
	 */
	public void duplicateDishInOrder(int indexInTable){
		this.dishList.add(this.dishList.get(indexInTable));
	}
	
}//END
