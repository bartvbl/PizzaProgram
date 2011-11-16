/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.events.moduleEventHandlers;

import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.views.OrderView;

/**
 *
 * @author Bart
 */
public class OrderGUI_SystemEventHandler implements EventHandler {

	private OrderView orderView;
	private EventDispatcher eventDispatcher;
	private OrderGUI orderGUI;

	public OrderGUI_SystemEventHandler(OrderView orderView, EventDispatcher eventDispatcher, OrderGUI orderGUI){
		this.orderView = orderView;
		this.eventDispatcher = eventDispatcher;
		this.orderGUI = orderGUI;
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_DISH_LIST);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_EXTRAS_LIST);
	}

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

	private void updateExtrasList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.out.println("ERROR: received event containing a wrong data type [order GUI update extras list]");
			return;
		}
		ArrayList<Extra> extrasList = (ArrayList<Extra>)event.getEventParameterObject();
		this.orderGUI.currentExtrasList = extrasList;

		DefaultListModel model = (DefaultListModel)OrderView.extrasSelectionList.getModel();
		model.clear();
		System.out.println("updating extras list");
		for (Extra extra : extrasList) {
			model.addElement(extra.name + " ( " + extra.priceFuncPart + " " + extra.priceValPart + ")");
		}
	}

	private void updateDishList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.out.println("ERROR: received event containing a wrong data type [order GUI update dish list]");
			return;
		}
		System.out.println("updating dish list");
		ArrayList<Dish> dishList = (ArrayList<Dish>)event.getEventParameterObject();
		this.orderGUI.currentDishList = dishList;

		DefaultListModel model = (DefaultListModel)OrderView.dishSelectionList.getModel();
		model.clear();

		for (Dish dish : dishList) {
			model.addElement(dish.name + " (kr. " + dish.price + ")");
		}
	}

	private void updateCustomerList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.out.println("ERROR: received event containing a wrong data type [order GUI update customer list]");
			return;
		}
		ArrayList<Customer> customerList = (ArrayList<Customer>) event.getEventParameterObject();
		this.orderGUI.currentCustomerList = customerList;
		DefaultListModel model = (DefaultListModel)orderView.customerList.getModel();
		model.clear();

		for (Customer customer : customerList) {
			model.addElement(customer.firstName + " " + customer.lastName);
		}
	}
}
