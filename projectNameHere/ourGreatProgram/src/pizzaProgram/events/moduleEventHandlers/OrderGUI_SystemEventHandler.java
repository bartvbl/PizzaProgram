/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.events.moduleEventHandlers;

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
	
    public OrderGUI_SystemEventHandler(OrderView orderView, EventDispatcher eventDispatcher)
    {
        
    }
    
    public void handleEvent(Event<?> event)
    {
    	if(event.eventType.equals(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST))
    	{
    		this.updateCustomerList(event);
    	}
    }

	private void updateCustomerList(Event<?> event) {
		// TODO Auto-generated method stub
		
	}
}
