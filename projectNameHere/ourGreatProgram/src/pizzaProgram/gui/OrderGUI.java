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
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

public class OrderGUI extends GUIModule implements EventHandler, ItemListener {

	private DatabaseConnection database;
	private JFrame jFrame;
	
	//GUI-components
	private Button newCutomerButton;
	private TextField customerSearchArea;
	private List customerList;
	private List customerDetails;
	
	private TextField dishSearchArea;
	private Choice dishList;
	
	private HashMap<String, Customer> testmap = new HashMap<String, Customer>();
	
	public OrderGUI(DatabaseConnection dbc, JFrame jFrame, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		this.database = dbc;
		this.jFrame = jFrame;
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		initialize();
		hide();
	}
	
	@Override
	public void initialize() {
		newCutomerButton = new Button();
		newCutomerButton.setLabel("Ny Kunde");
		GridBagConstraints newCustomerConstraints = new GridBagConstraints();
		newCustomerConstraints.gridx = 0;
		newCustomerConstraints.gridy = 0;
		newCustomerConstraints.weightx = 0.3;
		newCustomerConstraints.gridwidth = 1;
		newCustomerConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.jFrame.add(newCutomerButton, newCustomerConstraints); 
		
		customerSearchArea = new TextField();
		customerSearchArea.setText("Søk etter kunde...");
		GridBagConstraints searchAreaConstraints = new GridBagConstraints();
		searchAreaConstraints.gridx = 0;
		searchAreaConstraints.gridy = 1;
		searchAreaConstraints.weightx = 0.3;
		searchAreaConstraints.gridwidth = 1;
		searchAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.jFrame.add(customerSearchArea, searchAreaConstraints); 
		
		customerList = new List();
		customerList.addItemListener(this);
		GridBagConstraints customerListConstraints = new GridBagConstraints();
		customerListConstraints.gridx = 0;
		customerListConstraints.gridy = 3;
		customerListConstraints.weightx = 0.3;
		customerListConstraints.weighty = 1;
		customerListConstraints.gridwidth = 1;
		customerListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(customerList, customerListConstraints);
		
		customerDetails = new List();
		GridBagConstraints customerDetailsConstraints = new GridBagConstraints();
		customerDetailsConstraints.gridx = 1;
		customerDetailsConstraints.gridy = 0;
		customerDetailsConstraints.gridwidth = 1;
		customerDetailsConstraints.gridheight = 2;
		customerDetailsConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.jFrame.add(customerDetails, customerDetailsConstraints);
		
		dishSearchArea = new TextField();
		dishSearchArea.setText("Søk etter rett...");
		GridBagConstraints dishSearchAreaConstraints = new GridBagConstraints();
		dishSearchAreaConstraints.gridx = 1;
		dishSearchAreaConstraints.gridy = 1;
		dishSearchAreaConstraints.weightx = 0.3;
		dishSearchAreaConstraints.gridwidth = 1;
		dishSearchAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.jFrame.add(dishSearchArea, dishSearchAreaConstraints); 
		
		dishList = new Choice();
		GridBagConstraints dishListConstraints = new GridBagConstraints();
		dishListConstraints.gridx = 1;
		dishListConstraints.gridy = 3;
		dishListConstraints.weightx = 0.3;
		dishListConstraints.weighty = 0.3;
		dishListConstraints.gridwidth = 1;
		dishListConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.jFrame.add(dishList, dishListConstraints);
		
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
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			hide();
		}else if(event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)){
			hide();
		}else if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			show();
		}
	}
	@Override
	public void show() {
		newCutomerButton.setVisible(true);
		customerSearchArea.setVisible(true);
		customerList.setVisible(true);
		customerDetails.setVisible(true);
		dishSearchArea.setVisible(true);
		dishList.setVisible(true);
	}
	@Override
	public void hide() {
		newCutomerButton.setVisible(false);
		customerSearchArea.setVisible(false);
		customerList.setVisible(false);
		customerDetails.setVisible(false);
		dishSearchArea.setVisible(false);
		dishList.setVisible(false);
	}
}//END
