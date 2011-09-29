package pizzaProgram.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

import pizzaProgram.dataObjects.*;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

public class OrderGUI extends GUIModule implements EventHandler {

	private DatabaseConnection database;
	private JFrame jFrame;
	
	//GUI-components
    JButton newCustomerButton;
    List customerList;
    JButton addDishButton;
    List dishList;
    List dishExtraList;
    TextArea orderComment;
    Label customerLabel;
    JButton finishOrderButton;
    List orderedDishesList;
    HashMap<String, Customer> customerMap = new HashMap<String, Customer>();
    HashMap<String, Dish> dishMap = new HashMap<String, Dish>();
    HashMap<String, Extra> extraMap = new HashMap<String, Extra>();
    
    int orderContentCounter = 1;
    HashMap<String, ArrayList<Extra>> dishToExtrasMap = new HashMap<String, ArrayList<Extra>>();
    ArrayList<OrderDish> dishesInOrder = new ArrayList<OrderDish>();
	
	
	public OrderGUI(DatabaseConnection dbc, JFrame jFrame, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		this.database = dbc;
		this.jFrame = jFrame;
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		initialize();
		hide();
        populateLists();
	}
	
	@Override
	public void initialize() {
	    newCustomerButton = new JButton("Ny kunde");
        GridBagConstraints newCustomerButtonConstraints = new GridBagConstraints();
        newCustomerButtonConstraints.gridx = 0;
        newCustomerButtonConstraints.gridy = 0;
        newCustomerButtonConstraints.gridheight = 1;
        newCustomerButtonConstraints.gridwidth = 1;
        newCustomerButtonConstraints.fill = GridBagConstraints.BOTH;
        this.jFrame.add(newCustomerButton, newCustomerButtonConstraints);

        customerList = new List();
        customerList.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e){
               orderedDishesList.removeAll();
               Customer c = customerMap.get(customerList.getSelectedItem());
               customerLabel.setText(c.firstName + " " + c.lastName);
               dishesInOrder.clear();
            }
        });

        GridBagConstraints customerListConstraints = new GridBagConstraints();
        customerListConstraints.gridx = 0;
        customerListConstraints.gridy = 1;
        customerListConstraints.gridheight = 10;
        customerListConstraints.gridwidth = 1;
        customerListConstraints.weightx = 0.3;
        customerListConstraints.weighty = 1;
        customerListConstraints.fill = GridBagConstraints.BOTH;
        this.jFrame.add(customerList, customerListConstraints);

        addDishButton = new JButton("Legg til rett");
        addDishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                OrderDish o = new OrderDish(-1, dishMap.get(dishList.getSelectedItem()));
                String dishDisplay = orderContentCounter + " " +o.dish.name;
                orderContentCounter++;
                orderedDishesList.add(dishDisplay);
                ArrayList<Extra> extras = new ArrayList<Extra>();
                for(String s : dishExtraList.getSelectedItems()){
                	extras.add(extraMap.get(s));
                    o.addExtra(extraMap.get(s));
                    orderedDishesList.add("   - " + extraMap.get(s).name);
                }
                orderedDishesList.add("");
                dishToExtrasMap.put(dishDisplay, extras);
                dishesInOrder.add(o);
            }
        });
        GridBagConstraints addDishButtonConstraints = new GridBagConstraints();
        addDishButtonConstraints.gridx = 1;
        addDishButtonConstraints.gridy = 0;
        addDishButtonConstraints.gridheight = 1;
        addDishButtonConstraints.gridwidth = 2;
        addDishButtonConstraints.fill = GridBagConstraints.BOTH;
        this.jFrame.add(addDishButton, addDishButtonConstraints);

        dishList = new List();
        GridBagConstraints dishListConstraints = new GridBagConstraints();
        dishListConstraints.gridx = 1;
        dishListConstraints.gridy = 1;
        dishListConstraints.gridheight = 3;
        dishListConstraints.gridwidth = 1;
        dishListConstraints.weightx = 0.3;
        dishListConstraints.weighty = 0.5;
        dishListConstraints.fill = GridBagConstraints.BOTH;
        this.jFrame.add(dishList, dishListConstraints);

        dishExtraList = new List();
        dishExtraList.setMultipleMode(true);
        GridBagConstraints  dishExtraConstraints = new GridBagConstraints();
        dishExtraConstraints.gridx = 2;
        dishExtraConstraints.gridy = 1;
        dishExtraConstraints.gridheight = 3;
        dishExtraConstraints.gridwidth = 1;
        dishExtraConstraints.weightx = 0.3;
        dishExtraConstraints.weighty = 0.5;
        dishExtraConstraints.fill = GridBagConstraints.BOTH;
        this.jFrame.add(dishExtraList, dishExtraConstraints);

        orderComment = new TextArea("", 4, 20, TextArea.SCROLLBARS_NONE);
        orderComment.setText("dette er en tekst");
        GridBagConstraints orderCommentConstraints = new GridBagConstraints();
        orderCommentConstraints.gridx = 1;
        orderCommentConstraints.gridy = 4;
        orderCommentConstraints.gridheight = 2;
        orderCommentConstraints.gridwidth = 2;
        orderCommentConstraints.weightx = 0.6;
        orderCommentConstraints.weighty = 0;
        orderCommentConstraints.fill = GridBagConstraints.BOTH;
        this.jFrame.add(orderComment, orderCommentConstraints);

        customerLabel = new Label();
        customerLabel.setText("Label");
        GridBagConstraints customerLabelConstraints = new GridBagConstraints();
        customerLabelConstraints.gridx = 1;
        customerLabelConstraints.gridy = 6;
        customerLabelConstraints.gridheight = 1;
        customerLabelConstraints.gridwidth = 1;
        customerLabelConstraints.fill = GridBagConstraints.BOTH;
        this.jFrame.add(customerLabel, customerLabelConstraints);

        finishOrderButton = new JButton("Ferdigstill");
        finishOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderContentCounter = 1;
				
				Customer c = customerMap.get(customerList.getSelectedItem());
				try {
					database.addOrder(c, true, orderComment.getText());
					database.createOrderCommentList();
					database.createOrdersList();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				Order o = database.getCustomerToOrderMap().get(c);
				try{
					for(OrderDish od : dishesInOrder){
						database.addDishToOrder(o, od.dish, od.getExtras());
					}
				}catch(SQLException ex){
					ex.printStackTrace();
				}
				
				
			}
		});
        GridBagConstraints finishOrderButtonConstraints = new GridBagConstraints();
        finishOrderButtonConstraints.gridx = 2;
        finishOrderButtonConstraints.gridy = 6;
        finishOrderButtonConstraints.gridheight = 1;
        finishOrderButtonConstraints.gridwidth = 1;
        finishOrderButtonConstraints.fill = GridBagConstraints.BOTH;
        this.jFrame.add(finishOrderButton, finishOrderButtonConstraints);

        orderedDishesList = new List();
        GridBagConstraints orderedDishesListConstraints = new GridBagConstraints();
        orderedDishesListConstraints.gridx = 1;
        orderCommentConstraints.gridy = 7;
        orderCommentConstraints.gridheight = 3;
        orderedDishesListConstraints.gridwidth = 2;
        orderedDishesListConstraints.weightx = 0.3;
        orderCommentConstraints.weighty = 0.5;
        orderedDishesListConstraints.fill = GridBagConstraints.BOTH;
        this.jFrame.add(orderedDishesList, orderCommentConstraints);
	}
	
	private void populateLists(){
		customerList.removeAll();
        dishList.removeAll();
        dishExtraList.removeAll();
        for(Customer c : database.getCustomers()){
            String s = c.customerID + " " + c.firstName + " " + c.lastName;
            customerList.add(s);
            customerMap.put(s, c);
        }
        for(Dish d : database.getDishes()){
            String s = d.name;
            dishList.add(s);
            dishMap.put(s, d);
        }
        for(Extra e: database.getExtras()){
            String s = e.name;
            dishExtraList.add(s);
            extraMap.put(s, e);
        }
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
        newCustomerButton.setVisible(true);
        customerList.setVisible(true);
        addDishButton.setVisible(true);
        dishList.setVisible(true);
        dishExtraList.setVisible(true);
        orderComment.setVisible(true);
        customerLabel.setVisible(true);
        finishOrderButton.setVisible(true);
        orderedDishesList.setVisible(true);

		jFrame.setVisible(true);

	}
	@Override
	public void hide() {
        customerList.setVisible(false);
        newCustomerButton.setVisible(false);
        addDishButton.setVisible(false);
        dishList.setVisible(false);
        dishExtraList.setVisible(false);
        orderComment.setVisible(false);
        customerLabel.setVisible(false);
        finishOrderButton.setVisible(false);
        orderedDishesList.setVisible(false);

		jFrame.setVisible(true);
	}
}//END
