package pizzaProgram.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventHandler;
import pizzaProgram.modules.GUIModule;

public class OrderGUI implements GUIModule, EventHandler, ItemListener {

	
	DatabaseConnection database;
	ArrayList<Customer> displaylist;
	List liste = new List();
	List infoliste = new List();
	HashMap<String, Customer> testmap = new HashMap<String, Customer>();
	
	
	public OrderGUI(JFrame jframe, DatabaseConnection dbc) {
		database = dbc;
		
		jframe.add(liste, BorderLayout.WEST);
		jframe.add(infoliste, BorderLayout.EAST);
		displaylist = new ArrayList<Customer>();
		
		liste.addItemListener(this);
		
		for(Customer c : database.getCustomers()){
			displaylist.add(c);
			
		}
		for(Customer c : displaylist){
			String sc = c.firstName + " " + c.lastName + " " + c.customerID;
			liste.add(sc);
			testmap.put(sc, c);
			/*liste.add("   - " + c.phoneNumber);
			liste.add("   - " + c.address);
			liste.add("");*/
		}
		
	}
	
	@Override
	public void handleEvent(Event<Object> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		infoliste.removeAll();
		Customer c = testmap.get(liste.getSelectedItem());
		infoliste.add(c.firstName + " " + c.lastName);
		infoliste.add(c.address);
		infoliste.add(""+c.phoneNumber);
		
	}
}
