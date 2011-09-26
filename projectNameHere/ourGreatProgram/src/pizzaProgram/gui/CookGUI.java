package pizzaProgram.gui;

import java.awt.GridBagConstraints;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.JFrame;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

public class CookGUI extends GUIModule implements EventHandler{
	
	private List orderList;
	private HashMap<String, Order> orderMap = new HashMap<String, Order>();
	private List currentOrderList;
	private TextArea commentArea;
	
	private DatabaseConnection database;
	private JFrame jFrame;
	
	public CookGUI(DatabaseConnection dbc, JFrame jFrame, EventDispatcher eventDispatcher) {
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
		orderList = new List();
		orderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				currentOrderList.removeAll();
				for(OrderDish d : orderMap.get(orderList.getSelectedItem()).getOrderedDishes()){
					currentOrderList.add(d.dish.name);
					commentArea.setText(orderMap.get(orderList.getSelectedItem()).getComment());
					System.out.println(orderMap.get(orderList.getSelectedItem()).getComment());
				}
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
		
		currentOrderList = new List();
		GridBagConstraints currentOrderListConstraints = new GridBagConstraints();
		currentOrderListConstraints.gridx = 1;
		currentOrderListConstraints.gridy = 0;
		currentOrderListConstraints.weightx = 0.5;
		currentOrderListConstraints.weighty = 0.8;
		currentOrderListConstraints.gridwidth = 1;
		currentOrderListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(currentOrderList, currentOrderListConstraints);
		
		commentArea = new TextArea();
		commentArea.setEditable(false);
		commentArea.setText("Kommentarer her...");
		GridBagConstraints commentAreaConstraints = new GridBagConstraints();
		commentAreaConstraints.gridx = 1;
		commentAreaConstraints.gridy = 1;
		commentAreaConstraints.weightx = 0.5;
		commentAreaConstraints.weighty = 0.2;
		commentAreaConstraints.gridwidth = 1;
		commentAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.jFrame.add(commentArea, commentAreaConstraints);
		
	}
	
	@Override
	public void handleEvent(Event<?> event){
		if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			show();
		}else if(event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)){
			hide();
		}else if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			hide();
		}
	}
	
	private void populateLists(){
		for(Order o : database.getOrders()){
			if(o.getStatus().equals(Order.REGISTERED) || o.getStatus().equals(Order.BEING_COOKED)){
				String sc = o.getOrderedDishes().size() + " Retter, Ordrenummer:" + o.getID();
				orderList.add(sc);
				orderMap.put(sc, o);
			}
		}
	}

	@Override
	public void show() {
		System.out.println("show");
		populateLists();
		orderList.setVisible(true);
		currentOrderList.setVisible(true);
		commentArea.setVisible(true);
	}

	@Override
	public void hide() {
		orderList.setVisible(false);
		currentOrderList.setVisible(false);
		commentArea.setVisible(false);
	}
}
