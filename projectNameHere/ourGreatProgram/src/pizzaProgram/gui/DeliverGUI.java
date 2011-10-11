package pizzaProgram.gui;

import java.awt.GridBagConstraints;
import java.awt.List;
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
	private List currentInfoList = new List();
	private List orderContentList = new List();
	private List orderStatusList = new List();
	private DeliveryMap chartArea;
	private HashMap<String, Order> orderMap = new HashMap<String, Order>();
	
	private OrderList databaseOrder;
	private JFrame jFrame;
	JButton onRoute;
	JButton delivered;
	JButton receipt;
	
	public DeliverGUI(OrderList ol, JFrame jFrame, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		this.databaseOrder = ol;
		this.jFrame = jFrame;
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
		initialize();
		hide();
		populateLists();
	}
	/**
	 * Her skal koden for ï¿½ lage og legge til komponenter ligger
	 */
	@Override
	public void initialize() {
		        
		orderList = new List();
		orderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				currentInfoList.removeAll();
				orderContentList.removeAll();
				
				
				Order o = orderMap.get(orderList.getSelectedItem());
				if (o == null){
					return;
				}
				
				Customer c = o.getCustomer();
				
				currentInfoList.add(c.firstName + " " + c.lastName);
				currentInfoList.add(c.address);
				currentInfoList.add(c.postalCode + " " + c.city);
				currentInfoList.add("+47 " + c.phoneNumber);
				for (OrderDish od : o.getOrderedDishes()){
					orderContentList.add(od.dish.name + od.dish.price + "kr");
					for (Extra ex : od.getExtras()){
						orderContentList.add("  - " + ex.name + ex.priceFuncPart + ex.priceValPart + "kr");
					}
				}
				chartArea.loadImage(c.address);
			}
		});
		
		// Gridden som inneholder Status
		GridBagConstraints statusListConstraints = new GridBagConstraints();
		statusListConstraints.gridx = 0;
		statusListConstraints.gridy = 0;
		statusListConstraints.weightx = 0;
		statusListConstraints.weighty = 1;
		statusListConstraints.gridwidth = 2;
		statusListConstraints.gridheight = 2;
		statusListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(orderStatusList, statusListConstraints);
		
		// Gridden som inneholder Ordre
		GridBagConstraints orderListConstraints = new GridBagConstraints();
		orderListConstraints.gridx = 2;
		orderListConstraints.gridy = 0;
		orderListConstraints.weightx = 0.1;
		orderListConstraints.weighty = 1;
		orderListConstraints.gridwidth = 16;
		orderListConstraints.gridheight = 2;
		orderListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(orderList, orderListConstraints);
		
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
		        GridBagConstraints receiptConstraints = new GridBagConstraints();
		        receiptConstraints.gridx = 0;
		        receiptConstraints.gridy = 2;
		        receiptConstraints.gridheight = 1;
		        receiptConstraints.gridwidth = 1;
		        receiptConstraints.weightx = 0.1;
		        receiptConstraints.fill = GridBagConstraints.BOTH;
		        this.jFrame.add(receipt, receiptConstraints);
				
				//Utkj¿rt knappen
				onRoute = new JButton("Utkj¿rt");
				onRoute.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent actionEvent) {
		            	Order o = orderMap.get(orderList.getSelectedItem());
		            	databaseOrder.changeOrderStatus(o, Order.BEING_DELIVERED);
		            }
				});
		        GridBagConstraints onRouteConstraints = new GridBagConstraints();
		        onRouteConstraints.gridx = 6;
		        onRouteConstraints.gridy = 2;
		        onRouteConstraints.gridheight = 1;
		        onRouteConstraints.gridwidth = 1;
		        onRouteConstraints.weightx = 0.1;
		        onRouteConstraints.fill = GridBagConstraints.BOTH;
		        this.jFrame.add(onRoute, onRouteConstraints);
		        
		      //Levert knappen
				delivered = new JButton("Levert");
				delivered.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent actionEvent) {
		            	Order o = orderMap.get(orderList.getSelectedItem());
		            	databaseOrder.changeOrderStatus(o, Order.DELIVERED);
		            }
				});
		        GridBagConstraints deliveredConstraints = new GridBagConstraints();
		        deliveredConstraints.gridx = 12;
		        deliveredConstraints.gridy = 2;
		        deliveredConstraints.gridheight = 1;
		        deliveredConstraints.gridwidth = 1;
		        deliveredConstraints.weightx = 0.1;
		        deliveredConstraints.fill = GridBagConstraints.BOTH;
		        this.jFrame.add(delivered, deliveredConstraints);
		
		// Gridden som inneholder Adresse
		GridBagConstraints currentInfoListConstraints = new GridBagConstraints();
		currentInfoListConstraints.gridx = 18;
		currentInfoListConstraints.gridy = 0;
		currentInfoListConstraints.weightx = 0.01;
		currentInfoListConstraints.weighty = 1;
		currentInfoListConstraints.gridwidth = 11;
		currentInfoListConstraints.gridheight = 1;
		currentInfoListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(currentInfoList, currentInfoListConstraints);
		
		// Gridden som inneholder innholdet i ordren
		GridBagConstraints orderContentListConstraints = new GridBagConstraints();
		orderContentListConstraints.gridx = 29;
		orderContentListConstraints.gridy = 0;
		orderContentListConstraints.weightx = 0.01;
		orderContentListConstraints.weighty = 1;
		orderContentListConstraints.gridwidth = 11;
		orderContentListConstraints.gridheight = 1;
		orderContentListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(orderContentList, orderContentListConstraints);
		
		// Gridden som inneholder kart
		chartArea = new DeliveryMap();
		GridBagConstraints chartAreaConstraints = new GridBagConstraints();
		chartAreaConstraints.gridx = 18;
		chartAreaConstraints.gridy = 1;
		chartAreaConstraints.weightx = 0;
		chartAreaConstraints.weighty = 0;
		chartAreaConstraints.gridwidth = 22;
		chartAreaConstraints.gridheight = 1;
		chartAreaConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(chartArea, chartAreaConstraints);
		
	}
	
	public void populateLists(){
		ArrayList<Order> tempSort = new ArrayList<Order>();
		
		for (Order o : databaseOrder.getOrderList()){
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
			String status = (o.status.equals(Order.HAS_BEEN_COOKED) ? "Ledig" : "Kj¿res");
			orderStatusList.add(status);
			//infoList.add(o.getCustomer().firstName);
		}
		
	}
	/**
	 * Her skal koden for ï¿½ vise komponentene ligge
	 */
	@Override
	public void show() {
		System.out.println("show");
		orderStatusList.setVisible(true);
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
		System.out.println("show");
		orderStatusList.setVisible(false);
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
