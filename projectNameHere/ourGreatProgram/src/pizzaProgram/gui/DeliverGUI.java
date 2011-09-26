package pizzaProgram.gui;

import java.awt.GridBagConstraints;
import java.awt.List;

import javax.swing.JFrame;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventHandler;
import pizzaProgram.modules.GUIModule;

public class DeliverGUI implements GUIModule, EventHandler{

	JFrame jFrame;
	
	DatabaseConnection database;
	
	
	public DeliverGUI(JFrame jFrame, DatabaseConnection dbc) {
		this.jFrame = jFrame;
		this.database = dbc;
		
		
		List liste = new List();
		GridBagConstraints llisteConstraint = new GridBagConstraints();
		llisteConstraint.gridx = 0;
		llisteConstraint.gridy = 0;
		llisteConstraint.weightx = 0.5;
		llisteConstraint.fill = GridBagConstraints.HORIZONTAL;
		
		
		for(Customer c : database.getCustomers()){
			liste.add(c.firstName + " " + c.lastName);
		}
		//jFrame.add(liste, llisteConstraint);
		
		
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
	}
}
