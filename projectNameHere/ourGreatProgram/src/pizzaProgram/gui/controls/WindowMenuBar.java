package pizzaProgram.gui.controls;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import pizzaProgram.events.EventDispatcher;

public class WindowMenuBar implements ActionListener {
	private final EventDispatcher eventDispatcher;
	private Hashtable<JMenuItem, String> menuItems;
	
	private JFrame window;
	private JMenuBar menuBar;
	private JMenu currentMenu;
	
	
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
	
	public void addMenuItem(String name, String eventName)
	{
		JMenuItem newItem = new JMenuItem(name);
		newItem.addActionListener(this);
		this.currentMenu.add(newItem);
	}
	
	public void pack()
	{
		window.setJMenuBar(this.menuBar);
	}
	
	public void actionPerformed(ActionEvent actionEvent) 
	{
		//TODO: convert ActionEvent into an Event object that can be read by the rest of the system. Mostly a matter of converting one string to a system wide accepted string.
		System.out.println("Event received: " + actionEvent.getActionCommand());
	}
}
