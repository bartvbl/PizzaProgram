/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.events.moduleEventHandlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import pizzaProgram.events.Event;
import pizzaProgram.modules.Module;

/**
 *
 * @author Bart
 */
public class ComponentEventHandler implements ActionListener {
    private Hashtable<Component, String> eventTypeTable;
    private Module eventDispatchingModule;
    
    public ComponentEventHandler(Module eventDispatchingModule)
    {
        this.eventDispatchingModule = eventDispatchingModule;
        this.eventTypeTable = new Hashtable<Component, String>();
    }
    
    protected void registerEventType(Component eventSourceComponent, String eventName)
    {
        this.eventTypeTable.put(eventSourceComponent, eventName);
    }
    
    protected String getEventNameByComponent(Component component)
    {
        return this.eventTypeTable.get(component);
    }
    
    protected void dispatchEvent(Event<?> event)
    {
        this.eventDispatchingModule.dispatchEvent(event);
    }

    public void actionPerformed(ActionEvent e) {}
}
