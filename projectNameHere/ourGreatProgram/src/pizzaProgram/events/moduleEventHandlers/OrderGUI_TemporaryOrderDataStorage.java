package pizzaProgram.events.moduleEventHandlers;

import java.util.ArrayList;
import java.util.regex.Matcher;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.dataObjects.UnaddedOrder;
import pizzaProgram.dataObjects.UnaddedOrderDish;

public class OrderGUI_TemporaryOrderDataStorage {
	private Customer currentCustomer;
	private ArrayList<UnaddedOrderDish> dishList = new ArrayList<UnaddedOrderDish>();
	private String orderComments;
	private String deliveryMethod;
	
	public OrderGUI_TemporaryOrderDataStorage()
	{
		
	}
	
	public UnaddedOrder convertToOrderObjectAndReset()
	{
		UnaddedOrder order = new UnaddedOrder(currentCustomer, this.deliveryMethod, this.orderComments);
		for(UnaddedOrderDish dish : this.dishList)
		{
			System.out.println("adding dish to new order");
			order.addOrderDish(dish);
		}
		
		this.reset();
		return order;
	}
	
	public void reset()
	{
		this.currentCustomer = null;
		this.orderComments = null;
		this.dishList = new ArrayList<UnaddedOrderDish>();
		this.deliveryMethod = null;
	}
	
	public void setDeliveryMethod(String method)
	{
		this.deliveryMethod = method;
	}
	
	public void setCustomer(Customer customer)
	{
		this.currentCustomer = customer;
	}
	
	public void setOrderComments(String comments)
	{
		this.orderComments = comments;
	}
	
	public void addDish(Dish dish, Extra[] extrasToBeAddedToDish)
	{
		UnaddedOrderDish orderDish = new UnaddedOrderDish(dish);
		for(Extra extra : extrasToBeAddedToDish)
		{
			orderDish.addExtra(extra);
		}
		this.dishList.add(orderDish);
	}
	
	public void removeDishFromOrder(int indexInTable)
	{
		this.dishList.remove(indexInTable);
		System.out.println("removed: " + this.dishList);
	}
	
	public void duplicateDishInOrder(int indexInTable)
	{
		this.dishList.add(this.dishList.get(indexInTable));
		System.out.println("duplicated: " + this.dishList);
	}
}
