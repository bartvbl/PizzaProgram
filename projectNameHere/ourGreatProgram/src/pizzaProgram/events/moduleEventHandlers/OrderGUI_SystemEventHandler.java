/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.events.moduleEventHandlers;

import java.awt.Label;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.database.CustomerList;
import pizzaProgram.database.OrderList;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.views.OrderView;

/**
 *
 * @author Bart
 */
public class OrderGUI_SystemEventHandler implements EventHandler {
	
    private OrderView orderView;
	private EventDispatcher eventDispatcher;
	
    public OrderGUI_SystemEventHandler(OrderView orderView, EventDispatcher eventDispatcher){
        this.orderView = orderView;
        this.eventDispatcher = eventDispatcher;
        //DEBUG
        eventDispatcher.addEventListener(this, EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST);
        //END DEBUG
    }
    
    public void handleEvent(Event<?> event)
    {
    	if(event.eventType.equals(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST))
    	{
    		System.out.println("gotevent1");
    		this.updateCustomerList(event);
    	}
    }

	private void updateCustomerList(Event<?> event) {
		DefaultListModel model = (DefaultListModel)orderView.customerList.getModel();
		model.clear();
		System.out.println("gotevent");
		for (Customer c : CustomerList.getCustomerList()) {
			System.out.println("inloop");
			model.addElement(c.firstName + " " + c.lastName);
		}
	}
}
