package pizzaProgram.gui.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;

public class WindowMenuBar implements ActionListener {
	private final EventDispatcher eventDispatcher;
	private Hashtable<JMenuItem, String> menuItems;
	
	private JFrame window;
	private JMenuBar menuBar;
	private JMenu currentMenu;
	private ButtonGroup currentButtonGroup;
	
	
	public WindowMenuBar(EventDispatcher eventDispatcher, JFrame window)
	{
		this.eventDispatcher = eventDispatcher;
		this.menuItems = new Hashtable<JMenuItem, String>();
		this.menuBar = new JMenuBar();
		this.window = window;
	}
	
	public void clearAndReset()
	{
		window.setJMenuBar(null);
		this.menuBar = new JMenuBar();
		this.menuItems = new Hashtable<JMenuItem, String>();
		window.setJMenuBar(this.menuBar);
	}
	
	public void addMenu(String name)
	{
		JMenu newMenu = new JMenu(name);
		this.currentMenu = newMenu;
		this.menuBar.add(newMenu);
	}
	
	public void addMenuSeparator()
	{
		this.currentMenu.addSeparator();
	}
	
	public void addMenuItem(String name, String eventName)
	{
		JMenuItem newItem = new JMenuItem(name);
		newItem.addActionListener(this);
		this.currentMenu.add(newItem);
		this.registerComponentEvent(newItem, eventName);
	}
	
	public void createButtonGroup()
	{
		this.currentButtonGroup = new ButtonGroup();
	}
	
	public void addRadioMenuItem(String name, String eventName, boolean isSelected)
	{
		JRadioButtonMenuItem newItem = new JRadioButtonMenuItem(name);
		newItem.addActionListener(this);
		if(isSelected == true)
		{
			newItem.setSelected(true);
		}
		this.currentButtonGroup.add(newItem);
		this.currentMenu.add(newItem);
		this.registerComponentEvent(newItem, eventName);
	}
	
	public void pack()
	{
		window.setJMenuBar(this.menuBar);
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
}
