/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.gui;


import pizzaProgram.gui.ProgramWindowFrameView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventType;

/**
 *
 * @author Bart
 */
public class MenuBarEventHandler implements ActionListener {
    private final EventDispatcher eventDispatcher;
    private Hashtable<JMenuItem, String> menuItems;
    private ProgramWindowFrameView mainFrameView;
    
    public MenuBarEventHandler(ProgramWindowFrameView mainFrameView, EventDispatcher eventDispatcher)
    {
        this.eventDispatcher = eventDispatcher;
        this.menuItems = new Hashtable<JMenuItem, String>();
        this.mainFrameView = mainFrameView;
        this.fillMenuItemsTable();
        this.registerActionListeners();
    }

    public void actionPerformed(ActionEvent actionEvent) 
    {
        
        if(!(actionEvent.getSource() instanceof JMenuItem))
        {
            System.out.println("ERROR: events handled by the MenuBar class can only originate from JMenuItem instances!");
            return;
        }
        String eventType = this.getEventNameByMenuItem((JMenuItem)actionEvent.getSource());
        this.eventDispatcher.dispatchEvent(new Event<Object>(eventType));
        System.out.println("Event received: " + actionEvent.getActionCommand());

    }
    private String getEventNameByMenuItem(JMenuItem menuItem)
    {
        return this.menuItems.get(menuItem);
    }
    private void registerComponentEvent(JMenuItem menuItem, String eventName)
    {
        this.menuItems.put(menuItem, eventName);
    }

    private void fillMenuItemsTable() {
        this.registerComponentEvent(this.mainFrameView.getOpenEditMenuWindowMenuItem(), EventType.EDIT_MENU_VIEW_REQUESTED);
        this.registerComponentEvent(this.mainFrameView.getOpenSettingsWindowMenuItem(), EventType.OPEN_SETTINGS_WINDOW_REQUESTED);
        this.registerComponentEvent(this.mainFrameView.getRefreshDataMenuItem(), EventType.DATA_REFRESH_REQUESTED);
        this.registerComponentEvent(this.mainFrameView.getShowCookGUIMenuItem(), EventType.COOK_GUI_REQUESTED);
        this.registerComponentEvent(this.mainFrameView.getShowDeliveryGUIMenuItem(), EventType.DELIVERY_GUI_REQUESTED);
        this.registerComponentEvent(this.mainFrameView.getShowOrderGUIMenuItem(), EventType.ORDER_GUI_REQUESTED);
    }

    private void registerActionListeners() {
        this.mainFrameView.getOpenEditMenuWindowMenuItem().addActionListener(this);
        this.mainFrameView.getOpenSettingsWindowMenuItem().addActionListener(this);
        this.mainFrameView.getRefreshDataMenuItem().addActionListener(this);
        this.mainFrameView.getShowCookGUIMenuItem().addActionListener(this);
        this.mainFrameView.getShowDeliveryGUIMenuItem().addActionListener(this);
        this.mainFrameView.getShowOrderGUIMenuItem().addActionListener(this);
    }
}
