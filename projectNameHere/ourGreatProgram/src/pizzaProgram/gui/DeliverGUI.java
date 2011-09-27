package pizzaProgram.gui;

import java.awt.GridBagConstraints;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JFrame;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

public class DeliverGUI extends GUIModule implements EventHandler{

	private List orderList = new List();
	private List infoList = new List();
	private List currentInfoList = new List();
	private HashMap<String, Order> orderMap = new HashMap<String, Order>();
	
	private DatabaseConnection database;
	private JFrame jFrame;
	
	
	public DeliverGUI(DatabaseConnection dbc, JFrame jFrame, EventDispatcher eventDispatcher) {
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
	/**
	 * Her skal koden for å lage og legge til komponenter ligger
	 */
	@Override
	public void initialize() {
		
		orderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				currentInfoList.removeAll();
				
				Order o = orderMap.get(orderList.getSelectedItem());
				Customer c = o.getCustomer();
				
				//Customer c = orderMap.get(orderList.getSelectedItem()).getCustomer();
				currentInfoList.add(c.firstName + " " + c.lastName + "\n" + c.address + "\n" + c.phoneNumber);
					
				//System.out.println(orderMap.get(orderList.getSelectedItem()).getComment());
			}
		});
		
		GridBagConstraints orderListConstraints = new GridBagConstraints();
		orderListConstraints.gridx = 0;
		orderListConstraints.gridy = 0;
		orderListConstraints.weightx = 0.5;
		orderListConstraints.weighty = 1;
		orderListConstraints.gridwidth = 1;
		orderListConstraints.gridheight = 2;
		orderListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(orderList, orderListConstraints);
		
		
		GridBagConstraints currentInfoListConstraints = new GridBagConstraints();
		currentInfoListConstraints.gridx = 1;
		currentInfoListConstraints.gridy = 0;
		currentInfoListConstraints.weightx = 0.5;
		currentInfoListConstraints.weighty = 0.8;
		currentInfoListConstraints.gridwidth = 1;
		currentInfoListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(currentInfoList, currentInfoListConstraints);

	}
	
	public void populateLists(){
		ArrayList<Order> tempSort = new ArrayList<Order>();
		
		for (Order o : database.getOrders()){
			if (o.getStatus().equals(Order.HAS_BEEN_COOKED) || o.getStatus().equals(Order.BEING_DELIVERED)){
				String sc = ("Order " + o.getID() + ": " + o.getCustomer().firstName + " " + o.getCustomer().lastName);
				tempSort.add(o);
				orderMap.put(sc, o);
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
			orderList.add("Order " + o.getID() + ": " + o.getCustomer().firstName + " " + o.getCustomer().lastName);
			//infoList.add(o.getCustomer().firstName);
		}
	}
	/**
	 * Her skal koden for å vise komponentene ligge
	 */
	@Override
	public void show() {
		System.out.println("show");
		orderList.setVisible(true);
		currentInfoList.setVisible(true);
		jFrame.setVisible(true);
	}
	
	/**
	 * Her skal koden for å skjule komponentene ligge
	 */
	@Override
	public void hide() {
		System.out.println("show");
		orderList.setVisible(false);
		currentInfoList.setVisible(false);
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
