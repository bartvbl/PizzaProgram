/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.events.moduleEventHandlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.OrderGUI;
import pizzaProgram.gui.views.OrderView;

/**
 *
 * @author Bart
 */
public class OrderGUI_OrderViewEventHandler extends ComponentEventHandler implements ActionListener {
    private OrderView orderView;
    
    public OrderGUI_OrderViewEventHandler(OrderView orderView, OrderGUI eventDispatchingModule)
    {
       super(eventDispatchingModule);
        this.orderView = orderView;
        this.addEventListeners();
    }
    
    private void addEventListeners()
    {
        this.orderView.searchCustomerSearchButton.addActionListener(this);
        this.registerEventType(this.orderView.searchCustomerSearchButton, EventType.DATABASE_SEARCH_CUSTOMER_INFO_BY_NAME);
    }
    
    public void actionPerformed(ActionEvent event)
    {
        System.out.println("dispatched event: " + event.getSource());
        this.dispatchEvent(new Event(this.getEventNameByComponent((Component)event.getSource())));
    }
}
