package pizzaProgram.gui;


import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.database.OrderList;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

public class DeliverGUI extends GUIModule implements EventHandler{

	private List orderList;
	private TextArea currentInfoList;
	private TextArea orderContentList;
	private DeliveryMap chartArea;
	private HashMap<String, Order> orderMap = new HashMap<String, Order>();
	
	private JFrame jFrame;
	JButton onRoute;
	JButton delivered;
	JButton receipt;
	
	public DeliverGUI(JFrame jFrame, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		this.jFrame = jFrame;
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		initialize();
		hide();
	}
	
	public String addSpace(String name){
		if (name.length() < 8){
			return name + "\t\t\t";
		}else{
			return name + "\t\t";
		}
	}
	
	public int calculatePrice(int pizzaPrice, double extraPrice, char operator){
		if (operator == '+'){
			return (int)extraPrice;
		}else if(operator == '-'){
			return (int)-extraPrice;
		}else{
			return (int)(pizzaPrice*extraPrice)-pizzaPrice;
		}
	}
	
	/**
	 * Her skal koden for ï¿½ lage og legge til komponenter ligger
	 */
	@Override
	public void initialize() {
		        
		orderList = new List();
		orderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				currentInfoList.setText("");
				orderContentList.setText("");
				
				
				Order o = orderMap.get(orderList.getSelectedItem());
				if (o == null){
					return;
				}
				
				Customer c = o.getCustomer();
				
				currentInfoList.append(c.firstName + " " + c.lastName +"\n");
				currentInfoList.append(c.address + "\n");
				currentInfoList.append(c.postalCode + " " + c.city + "\n");
				currentInfoList.append("+47 " + c.phoneNumber+ "\n");
				
				int pizzaPrice = 0;
				int totalPrice = 0; 
				int shipCost, midPrice;
				for (OrderDish od : o.getOrderedDishes()){
					orderContentList.append(addSpace(od.dish.name) + "  " + od.dish.price + "kr\n");
					String op;
					for (Extra ex : od.getExtras()){
						//char op = ex.priceFuncPart == '+' || ex.priceFuncPart == '*' ? '+';
						if (ex.priceFuncPart == '+' || ex.priceFuncPart == '*'){
							op = "+";
						}else{
							op = "";
						}
						midPrice = calculatePrice(od.dish.price, ex.priceValPart, ex.priceFuncPart);
						pizzaPrice += midPrice;
						orderContentList.append(addSpace("  - " + ex.name) + op + midPrice + "kr\n");
					}
					pizzaPrice += od.dish.price;
					totalPrice += pizzaPrice;
					orderContentList.append(addSpace("Sum pizza") + "  " + pizzaPrice + "kr\n");
					orderContentList.append("\n");
					pizzaPrice = 0;
				}
				if (totalPrice > 500){
					shipCost = 0;
				}else{
					shipCost = 50;
				}
				orderContentList.append(addSpace("Frakt") + "+" + shipCost + "kr\n");
				totalPrice += shipCost;
				orderContentList.append(addSpace("Sum Total") + "  " + totalPrice + "kr\n");
				chartArea.loadImage(c.address);
			}
		});
		this.jFrame.add(orderList, createConstrints(2, 0, 16, 2, 0.1, 1, GridBagConstraints.BOTH));
		
		//Kvitterings knappen
		receipt = new JButton("Kvittering");
		receipt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            	Order o = orderMap.get(orderList.getSelectedItem());
            	String toJOption = "";
            	for (OrderDish od : o.orderedDishes){
            		toJOption += od.dish.name + "\t" + od.dish.price + "\n";
            		for (Extra ex : od.getExtras()){
            			toJOption += "\t- " + ex.name + "\t" + ex.priceFuncPart + ex.priceValPart + "\n";
            		}
            	}
            	JOptionPane.showMessageDialog(null, "Kunde:\n" + o.customer.firstName + " " + o.customer.lastName + "\n" + o.customer.address + "\n" + o.customer.postalCode + "\n" + o.customer.phoneNumber + "\n\nOrdre:\n" + toJOption);
            }
		});
        this.jFrame.add(receipt, createConstrints(0, 2, 1, 1, 0.1, 0, GridBagConstraints.BOTH));
		
		//Utkj¿rt knappen
		onRoute = new JButton("Kjør");
		onRoute.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            	Order o = orderMap.get(orderList.getSelectedItem());
            	OrderList.changeOrderStatus(o, Order.BEING_DELIVERED);
            	populateLists();
            }
		});
        this.jFrame.add(onRoute, createConstrints(6, 2, 1, 1, 0.01, 0, GridBagConstraints.BOTH));
        
        //Levert knappen
		delivered = new JButton("Levert");
		delivered.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
            	Order o = orderMap.get(orderList.getSelectedItem());
            	OrderList.changeOrderStatus(o, Order.DELIVERED);
            	populateLists();
            }
		});
        this.jFrame.add(delivered, createConstrints(12, 2, 1, 1, 0.1, 0, GridBagConstraints.BOTH));
		
		// Gridden som inneholder Adresse
        currentInfoList = new TextArea("", 6, 12, TextArea.SCROLLBARS_NONE);
        currentInfoList.setEditable(false);
        currentInfoList.setBackground(Color.white);
		this.jFrame.add(currentInfoList, createConstrints(18, 0, 11, 1, 0.01, 1, GridBagConstraints.BOTH));
		
		// Gridden som inneholder innholdet i ordren
		orderContentList = new TextArea("", 6, 12, TextArea.SCROLLBARS_VERTICAL_ONLY);
		orderContentList.setEditable(false);
		orderContentList.setBackground(Color.white);
		this.jFrame.add(orderContentList, createConstrints(29, 0, 11, 1, 0.01, 1, GridBagConstraints.BOTH));
		
		// Gridden som inneholder kart
		chartArea = new DeliveryMap();
		this.jFrame.add(chartArea, createConstrints(18, 1, 22, 1, 0, 0, GridBagConstraints.BOTH));
		
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
	
	public void populateLists(){
		orderList.removeAll();
		currentInfoList.setText("");
		orderContentList.setText("");
		orderMap.clear();
		
		OrderList.updateOrders();
		ArrayList<Order> tempSort = new ArrayList<Order>();
		for (Order o : OrderList.getOrderList()){
			if (o.getStatus().equals(Order.HAS_BEEN_COOKED) || o.getStatus().equals(Order.BEING_DELIVERED)){
				tempSort.add(o);
			}
		}
		
		Comparator<Order> comp = new Comparator<Order>() {
			@Override
			public int compare(Order o1, Order o2) {
				return o1.getTimeRegistered().compareTo(o2.getTimeRegistered());
			}
		};
		
		Collections.sort(tempSort, comp);
		
		for (Order o : tempSort){
			String status = (o.status.equals(Order.HAS_BEEN_COOKED) ? "  " : "X");
			String sc = (status + " Order " + o.getID() + ": " + o.getCustomer().firstName + " " + o.getCustomer().lastName);
			orderList.add(sc);
			orderMap.put(sc, o);
		}
		
	}
	/**
	 * Her skal koden for ï¿½ vise komponentene ligge
	 */
	@Override
	public void show() {
		populateLists();
		receipt.setVisible(true);
		onRoute.setVisible(true);
		delivered.setVisible(true);
		orderList.setVisible(true);
		currentInfoList.setVisible(true);
		orderContentList.setVisible(true);
		chartArea.setVisible(true);
		jFrame.setVisible(true);
	}
	
	/**
	 * Her skal koden for ï¿½ skjule komponentene ligge
	 */
	@Override
	public void hide() {
		receipt.setVisible(false);
		onRoute.setVisible(false);
		delivered.setVisible(false);
		orderList.setVisible(false);
		currentInfoList.setVisible(false);
		orderContentList.setVisible(false);
		chartArea.setVisible(false);
		jFrame.setVisible(true);
	}
	
	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			hide();
		}else if(event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)){
			show();
		}else if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			hide();
		}
	}
}