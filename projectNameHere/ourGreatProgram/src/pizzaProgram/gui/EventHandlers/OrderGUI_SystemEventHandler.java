/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.gui.EventHandlers;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.views.OrderView;
import pizzaProgram.utils.PriceCalculators;

/**
 * Handles all the events coming from the system directed at the Order GUI.
 * @author Bart
 */
public class OrderGUI_SystemEventHandler implements EventHandler {
	/**
	 * A reference to the order GUI main module class
	 */
	private OrderGUI orderGUI;

	/**
	 * Creates a new instance and registers all event listeners at the main event dispatcher.
	 * @param eventDispatcher The system's main event dispatcher
	 * @param orderGUI A reference to the order GUI module
	 */
	public OrderGUI_SystemEventHandler(EventDispatcher eventDispatcher, OrderGUI orderGUI){
		this.orderGUI = orderGUI;
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_DISH_LIST);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_EXTRAS_LIST);
	}

	/**
	 * Handles an incoming event, and calls the appropiate internal handling function
	 */
	public void handleEvent(Event<?> event){
		if(event.eventType.equals(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST)){
			this.updateCustomerList(event);
		} 
		else if(event.eventType.equals(EventType.ORDER_GUI_UPDATE_DISH_LIST)){
			this.updateDishList(event);
		} 
		else if(event.eventType.equals(EventType.ORDER_GUI_UPDATE_EXTRAS_LIST)){
			this.updateExtrasList(event);
		}
	}

	/**
	 * Shows the list of Extra instances attached to the event in the Order GUI
	 * @param event The Event instance 
	 */
	private void updateExtrasList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.out.println("ERROR: received event containing a wrong data type [order GUI update extras list]");
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Extra> extrasList = (ArrayList<Extra>)event.getEventParameterObject();
		this.orderGUI.currentExtrasList = extrasList;

		DefaultListModel model = (DefaultListModel)OrderView.extrasSelectionList.getModel();
		model.clear();
		for (Extra extra : extrasList) {
			if(orderGUI.currentSelectedDish != null){
				model.addElement(extra.name + " (Levert hjem: " + PriceCalculators.getPriceForExtraOnDishDeliver(orderGUI.currentSelectedDish, extra) + "     Hent selv:  " +  PriceCalculators.getPriceForExtraOnDishPickup(orderGUI.currentSelectedDish, extra) + ")");
			}else{
				model.addElement(extra.name + " ( " + PriceCalculators.getPriceForExtra(extra) + ")");
			}
		}
	}

	/**
	 * Updates the dish list in the Order GUI, according to the ArrayList of Dish instances sent with the event
	 * @param event The event containing an ArrayList with Dish instances
	 */
	private void updateDishList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.out.println("ERROR: received event containing a wrong data type [order GUI update dish list]");
			return;
		}
		System.out.println("updating dish list");
		@SuppressWarnings("unchecked")
		ArrayList<Dish> dishList = (ArrayList<Dish>)event.getEventParameterObject();
		this.orderGUI.currentDishList = dishList;

		DefaultListModel model = (DefaultListModel)OrderView.dishSelectionList.getModel();
		model.clear();

		for (Dish dish : dishList) {
			model.addElement(dish.name + " (Levert hjem: " + PriceCalculators.getPriceForDishDeliver(dish) + "     Hent selv:  " + PriceCalculators.getPriceForDishPickup(dish) + ")");
		}
	}

	/**
	 * Updates the list of customers in the Order UI.
	 * @param event An Event instance with an ArrayList of Customer objects in it.
	 */
	private void updateCustomerList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.out.println("ERROR: received event containing a wrong data type [order GUI update customer list]");
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Customer> customerList = (ArrayList<Customer>) event.getEventParameterObject();
		this.orderGUI.currentCustomerList = customerList;
		DefaultListModel model = (DefaultListModel)OrderView.customerList.getModel();
		model.clear();

		for (Customer customer : customerList) {
			model.addElement(customer.firstName + " " + customer.lastName);
		}
	}
}
