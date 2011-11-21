/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaProgram.gui.EventHandlers;


import pizzaProgram.gui.views.ProgramWindowFrameView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;
import javax.swing.JMenuItem;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventType;

/**
 * Handles events related to the main window's menu bar
 * @author Bart
 */
public class MenuBarEventHandler implements ActionListener {
	/**
	 * A reference to the system's main event dispatcher
	 */
	private final EventDispatcher eventDispatcher;
	/**
	 * A hashTable linking MenuItem instances to Strings representing the event type they produce
	 */
	private Hashtable<JMenuItem, String> menuItems;

	/**
	 * Adds event listeners to all the menu items of the main program's window
	 * @param eventDispatcher The system's main event dispatcher
	 */
	public MenuBarEventHandler(EventDispatcher eventDispatcher){
		this.eventDispatcher = eventDispatcher;
		this.menuItems = new Hashtable<JMenuItem, String>();
		this.fillMenuItemsTable();
		this.registerActionListeners();
	}

	/**
	 * A function that gets called by java AWT when the user clicks on a menu item
	 */
	public void actionPerformed(ActionEvent actionEvent) {

		if(!(actionEvent.getSource() instanceof JMenuItem)){
			System.out.println("ERROR: events handled by the MenuBar class can only originate from JMenuItem instances!");
			return;
		}
		String eventType = this.getEventNameByMenuItem((JMenuItem)actionEvent.getSource());
		this.eventDispatcher.dispatchEvent(new Event<Object>(eventType));
	}
	
	/**
	 * Returns the event type associated with the inserted JMenuItem instance
	 * @param menuItem The menu item to find the event type for
	 * @return The associated event type of the event that can be sent to the system
	 */
	private String getEventNameByMenuItem(JMenuItem menuItem){
		return this.menuItems.get(menuItem);
	}
	
	/**
	 * Links an event type to a JMenuItem
	 * @param menuItem The JMenuItem instance
	 * @param eventName The event type to associate interaction events produced by the JMenuItem with
	 */
	private void registerComponentEvent(JMenuItem menuItem, String eventName){
		this.menuItems.put(menuItem, eventName);
	}

	/**
	 * Associates all JMenuItem events with events that can be sent to the system
	 */
	private void fillMenuItemsTable() {
		this.registerComponentEvent(ProgramWindowFrameView.showSettingsGUIMenuItem, EventType.OPEN_SETTINGS_WINDOW_REQUESTED);
		this.registerComponentEvent(ProgramWindowFrameView.refreshDataMenuItem, EventType.DATA_REFRESH_REQUESTED);
		this.registerComponentEvent(ProgramWindowFrameView.showCookGUIMenuItem, EventType.COOK_GUI_REQUESTED);
		this.registerComponentEvent(ProgramWindowFrameView.showDeliveryGUIMenuItem, EventType.DELIVERY_GUI_REQUESTED);
		this.registerComponentEvent(ProgramWindowFrameView.showOrderGUIMenuItem, EventType.ORDER_GUI_REQUESTED);
	}

	/**
	 * Registers event listeners at the JMenuItem instances in the main window
	 */
	private void registerActionListeners() {
		ProgramWindowFrameView.showSettingsGUIMenuItem.addActionListener(this);
		ProgramWindowFrameView.refreshDataMenuItem.addActionListener(this);
		ProgramWindowFrameView.showCookGUIMenuItem.addActionListener(this);
		ProgramWindowFrameView.showDeliveryGUIMenuItem.addActionListener(this);
		ProgramWindowFrameView.showOrderGUIMenuItem.addActionListener(this);
	}
}
