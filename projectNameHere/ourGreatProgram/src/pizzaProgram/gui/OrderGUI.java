package pizzaProgram.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Label;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.database.CustomerList;
import pizzaProgram.database.DishList;
import pizzaProgram.database.ExtraList;
import pizzaProgram.database.OrderList;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

public class OrderGUI extends GUIModule implements EventHandler {

	private JFrame jFrame;

	// GUI-components
	JButton newCustomerButton;
	List customerList;
	JButton addDishButton;
	List dishList;
	List dishExtraList;
	TextArea orderComment;
	Label customerLabel;
	JButton finishOrderButton;
	List orderedDishesList;
	TextArea customerInfo;
	
	HashMap<String, Customer> customerMap = new HashMap<String, Customer>();
	HashMap<String, Dish> dishMap = new HashMap<String, Dish>();
	HashMap<String, Extra> extraMap = new HashMap<String, Extra>();

	int orderContentCounter = 1;
	HashMap<String, ArrayList<Extra>> dishToExtrasMap = new HashMap<String, ArrayList<Extra>>();
	ArrayList<OrderDish> dishesInOrder = new ArrayList<OrderDish>();

	OrderGUI thisGUI;

	public OrderGUI(JFrame jFrame, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		thisGUI = this;
		this.jFrame = jFrame;
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher
				.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		initialize();
		hide();
	}

	@Override
	public void initialize() {
		newCustomerButton = new JButton("Ny kunde");
		this.jFrame.add(newCustomerButton, createConstrints(0, 10, 1, 1, 0, 0, GridBagConstraints.BOTH));
		newCustomerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new NewCutomerWindow(thisGUI);
			}
		});

		customerList = new List();
		this.jFrame.add(customerList, createConstrints(0, 1, 1, 7, 0.3, 1, GridBagConstraints.BOTH));
		customerList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				orderContentCounter = 1;
				orderedDishesList.removeAll();
				Customer c = customerMap.get(customerList.getSelectedItem());
				customerLabel.setText(c.firstName + " " + c.lastName);
				dishesInOrder.clear();
				customerInfo.setText("");
				customerInfo.append(c.firstName + " " + c.lastName+ "\n");
				customerInfo.append("Kundenummer: " + c.customerID+ "\n");
				customerInfo.append(c.address+ "\n");
				customerInfo.append(c.postalCode + " " + c.city+ "\n");
				customerInfo.append("Tlf: " + c.phoneNumber+ "\n");
			}
		});

		
		customerInfo = new TextArea("ting", 4, 8, TextArea.SCROLLBARS_NONE);
		customerInfo.setEditable(false);
		customerInfo.setBackground(Color.white);
		this.jFrame.add(customerInfo, createConstrints(0, 8, 1, 3, 0, 0, GridBagConstraints.BOTH));
		
		addDishButton = new JButton("Legg til rett");
		this.jFrame.add(addDishButton, createConstrints(2, 4, 1, 1, 0, 0, GridBagConstraints.BOTH));
		addDishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				if (customerList.getSelectedItem() == null) {
					return;
				}
				OrderDish o = new OrderDish(-1, dishMap.get(dishList.getSelectedItem()));
				String dishDisplay = orderContentCounter + " " + o.dish.name;
				orderContentCounter++;
				orderedDishesList.add(dishDisplay);
				ArrayList<Extra> extras = new ArrayList<Extra>();
				for (String s : dishExtraList.getSelectedItems()) {
					extras.add(extraMap.get(s));
					o.addExtra(extraMap.get(s));
					orderedDishesList.add("   - " + extraMap.get(s).name);
				}
				orderedDishesList.add("");
				dishToExtrasMap.put(dishDisplay, extras);
				dishesInOrder.add(o);
			}
		});

		dishList = new List();
		this.jFrame.add(dishList, createConstrints(1, 0, 1, 3, 0.3, 0.5, GridBagConstraints.BOTH));

		dishExtraList = new List();
		dishExtraList.setMultipleMode(true);
		this.jFrame.add(dishExtraList, createConstrints(2, 0, 1, 3, 0.5, 0.3, GridBagConstraints.BOTH));

		orderComment = new TextArea("", 4, 20, TextArea.SCROLLBARS_NONE);
		orderComment.setText("Kommentarer til orderen");
		this.jFrame.add(orderComment, createConstrints(1, 8, 2, 2, 0.6, 0, GridBagConstraints.BOTH));

		customerLabel = new Label();
		customerLabel.setText("Navn");
		this.jFrame.add(customerLabel, createConstrints(1, 4, 1, 1, 0, 0, GridBagConstraints.BOTH));

		finishOrderButton = new JButton("Ferdigstill");
		this.jFrame.add(finishOrderButton, createConstrints(1, 10, 2, 1, 0, 0, GridBagConstraints.BOTH));
		finishOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				orderContentCounter = 1;
				if (dishesInOrder.size() < 1) {
					return;
				}
				
				String selectedCustomerString = customerList.getSelectedItem();
				Customer c = customerMap.get(selectedCustomerString);
				
				OrderList.addOrder(c, true, orderComment.getText());
				
				populateLists();
				c = customerMap.get(selectedCustomerString);
			
				Order o = OrderList.getCustomerToOrderMap().get(c);
				try {
					for (OrderDish od : dishesInOrder) {
						OrderList.addDishToOrder(o, od.dish, od.getExtras());
					}
					orderedDishesList.removeAll();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}

			}
		});

		orderedDishesList = new List();
		this.jFrame.add(orderedDishesList, createConstrints(1, 5, 2, 3, 0.3, 0.5, GridBagConstraints.BOTH));
	}
	/**
	 * A method that returns a GridBagConstraints-Object with the parameters passed to this function
	 * This method is purely cosmetical
	 */
	private GridBagConstraints createConstrints(int xx, int yy, int width, int height, double xweight, double yweight, int fill){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = xx;
		gbc.gridy = yy;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = xweight;
		gbc.weighty = yweight;
		gbc.fill = fill;
		return gbc;
	}

	public void createNewCustomer(String firstName, String lastName,
			String address, int postalCode, String city, int phoneNumber) {
		try {
			CustomerList.addCustomer(firstName, lastName, address,
					postalCode, city, phoneNumber);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		populateLists();
	}

	private void populateLists() {
		customerList.removeAll();
		customerMap.clear();
		dishList.removeAll();
		dishExtraList.removeAll();
		OrderList.updateOrders();
		for (Customer c : CustomerList.getCustomerList()) {
			String s = c.customerID + " " + c.firstName + " " + c.lastName;
			customerList.add(s);
			customerMap.put(s, c);
		}
		for (Dish d : DishList.getDishList()) {
			String s = d.name;
			dishList.add(s);
			dishMap.put(s, d);
		}
		for (Extra e : ExtraList.getExtraList()) {
			String s = e.name;
			dishExtraList.add(s);
			extraMap.put(s, e);
		}
	}

	@Override
	public void handleEvent(Event<?> event) {
		if (event.eventType.equals(EventType.COOK_GUI_REQUESTED)) {
			hide();
		} else if (event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)) {
			hide();
		} else if (event.eventType.equals(EventType.ORDER_GUI_REQUESTED)) {
			show();
		}
	}

	@Override
	public void show() {
		populateLists();
		newCustomerButton.setVisible(true);
		customerList.setVisible(true);
		addDishButton.setVisible(true);
		dishList.setVisible(true);
		dishExtraList.setVisible(true);
		orderComment.setVisible(true);
		customerLabel.setVisible(true);
		finishOrderButton.setVisible(true);
		orderedDishesList.setVisible(true);
		customerInfo.setVisible(true);
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
		customerInfo.setVisible(false);
		jFrame.setVisible(true);
	}
}// END
