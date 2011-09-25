package pizzaProgram.gui;

import java.awt.Button;
import java.awt.Choice;
import java.awt.GridBagConstraints;
import java.awt.List;
import java.awt.TextField;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.JFrame;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventHandler;
import pizzaProgram.modules.GUIModule;

public class OrderGUI implements GUIModule, EventHandler, ItemListener {

	DatabaseConnection database;
	
	//GUI-components
	Button newCutomerButton;
	TextField customerSearchArea;
	List customerList;
	List customerDetails;
	
	TextField dishSearchArea;
	Choice dishList;
	
	
	HashMap<String, Customer> testmap = new HashMap<String, Customer>();
	JFrame jFrame;
	
	public OrderGUI(JFrame jFrame) {
		this.jFrame = jFrame;
	}
	
	private void populateLists(){
		for(Customer c : database.getCustomers()){
			String sc = c.firstName + " " + c.lastName + " " + c.customerID;
			customerList.add(sc);
			testmap.put(sc, c);
		}
		for(Dish d : database.getDishes()){
			String sc = d.name;
			dishList.add(sc);
		}
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		customerDetails.removeAll();
		Customer c = testmap.get(customerList.getSelectedItem());
		customerDetails.add(c.firstName + " " + c.lastName);
		customerDetails.add(c.address);
		customerDetails.add(""+c.phoneNumber);
		
	}
	@Override
	public void handleEvent(Event<Object> event) {
		// TODO Auto-generated method stub
	}
	@Override
	public void draw() {
		newCutomerButton = new Button();
		newCutomerButton.setLabel("Ny Kunde");
		GridBagConstraints newCustomerConstraints = new GridBagConstraints();
		newCustomerConstraints.gridx = 0;
		newCustomerConstraints.gridy = 0;
		newCustomerConstraints.weightx = 0.3;
		newCustomerConstraints.gridwidth = 1;
		newCustomerConstraints.fill = GridBagConstraints.HORIZONTAL;
		jFrame.add(newCutomerButton, newCustomerConstraints); 
		
		customerSearchArea = new TextField();
		customerSearchArea.setText("Søk etter kunde...");
		GridBagConstraints searchAreaConstraints = new GridBagConstraints();
		searchAreaConstraints.gridx = 0;
		searchAreaConstraints.gridy = 1;
		searchAreaConstraints.weightx = 0.3;
		searchAreaConstraints.gridwidth = 1;
		searchAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
		jFrame.add(customerSearchArea, searchAreaConstraints); 
		
		customerList = new List();
		customerList.addItemListener(this);
		GridBagConstraints customerListConstraints = new GridBagConstraints();
		customerListConstraints.gridx = 0;
		customerListConstraints.gridy = 3;
		customerListConstraints.weightx = 0.3;
		customerListConstraints.weighty = 1;
		customerListConstraints.gridwidth = 1;
		customerListConstraints.fill = GridBagConstraints.BOTH;
		jFrame.add(customerList, customerListConstraints);
		
		customerDetails = new List();
		GridBagConstraints customerDetailsConstraints = new GridBagConstraints();
		customerDetailsConstraints.gridx = 1;
		customerDetailsConstraints.gridy = 0;
		customerDetailsConstraints.gridwidth = 1;
		customerDetailsConstraints.gridheight = 2;
		customerDetailsConstraints.fill = GridBagConstraints.HORIZONTAL;
		jFrame.add(customerDetails, customerDetailsConstraints);
		
		/*dishSearchArea = new TextField();
		dishSearchArea.setText("Søk etter rett...");
		GridBagConstraints dishSearchAreaConstraints = new GridBagConstraints();
		dishSearchAreaConstraints.gridx = 1;
		dishSearchAreaConstraints.gridy = 1;
		dishSearchAreaConstraints.weightx = 0.3;
		dishSearchAreaConstraints.gridwidth = 1;
		dishSearchAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
		jframe.add(dishSearchArea, dishSearchAreaConstraints); */
		
		dishList = new Choice();
		GridBagConstraints dishListConstraints = new GridBagConstraints();
		dishListConstraints.gridx = 1;
		dishListConstraints.gridy = 3;
		dishListConstraints.weightx = 0.3;
		dishListConstraints.weighty = 0.3;
		dishListConstraints.gridwidth = 1;
		dishListConstraints.fill = GridBagConstraints.HORIZONTAL;
		jFrame.add(dishList, dishListConstraints);
	}
	@Override
	public void clear() {
	}
}//END
