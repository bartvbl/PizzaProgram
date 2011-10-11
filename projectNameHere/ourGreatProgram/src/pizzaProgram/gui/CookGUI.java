package pizzaProgram.gui;

import java.awt.GridBagConstraints;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.database.OrderList;
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
	private TextArea descriptionArea;
	
	private OrderList databaseOrders;
	private JFrame jFrame;
	private HashMap<String, Dish> dishMap = new HashMap<String, Dish>();
	
	private JButton beingMadeButton;
	private JButton finishedButton;
	
	public CookGUI(OrderList ol, JFrame jFrame, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		this.databaseOrders = ol;
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
		//orderModel = new DefaultTableModel();
		//orderModel.addColumn("ID");
		//orderModel.addColumn("Dish name");
		//orderModel.addColumn("Extras");
		//orderTable = new JTable(orderModel);
		orderList = new List();
		orderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				//currentOrderTable.removeAll();
				currentOrderList.removeAll();
				descriptionArea.setText("");
				DefaultTableModel model = new DefaultTableModel();
				//model.addColumn("ID");
				//model.addColumn("Dish name");
				//model.addColumn("Extras");
				for(OrderDish d : orderMap.get(orderList.getSelectedItem()).getOrderedDishes()){
					model.addRow(new Object[]{d.dish.dishID, d.dish.name, ""});
					currentOrderList.add(d.dish.name);
					dishMap.put(d.dish.name, d.dish);
					for(Extra e : d.getExtras()){
						String str = "   - " + e.name;
						//model.addRow(new Object[]{"", "", e.name});
						currentOrderList.add(str);
						dishMap.put(str, d.dish);
					}
					
					commentArea.setText(orderMap.get(orderList.getSelectedItem()).getComment());
				}
				//currentOrderTable = new JTable(model);
			}
		});
		GridBagConstraints orderListConstraints = new GridBagConstraints();
		orderListConstraints.gridx = 0;
		orderListConstraints.gridy = 0;
		orderListConstraints.weightx = 0.4;
		orderListConstraints.weighty = 1;
		orderListConstraints.gridwidth = 2;
		orderListConstraints.gridheight = 6;
		orderListConstraints.fill = GridBagConstraints.BOTH;
		//this.jFrame.add(orderTable, orderListConstraints);
		this.jFrame.add(orderList, orderListConstraints);
		
		//currentOrderTable = new JTable();
		currentOrderList = new List();
		
		
		
		currentOrderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Dish diplayDish = dishMap.get(currentOrderList.getSelectedItem());
				descriptionArea.setText("Pizza nr: " + diplayDish.dishID + "\n" + diplayDish.name + "\n\n" + diplayDish.description);
			}
		});
		GridBagConstraints currentOrderListConstraints = new GridBagConstraints();
		currentOrderListConstraints.gridx = 2;
		currentOrderListConstraints.gridy = 0;
		currentOrderListConstraints.weightx = 0.6;
		currentOrderListConstraints.weighty = 0.5;
		currentOrderListConstraints.gridwidth = 2;
		currentOrderListConstraints.gridheight = 3;
		currentOrderListConstraints.fill = GridBagConstraints.BOTH;
		//this.jFrame.add(currentOrderTable, currentOrderListConstraints);
		this.jFrame.add(currentOrderList, currentOrderListConstraints);
		
		commentArea = new TextArea("", 3, 10, TextArea.SCROLLBARS_NONE);
		commentArea.setEditable(false);
		this.jFrame.add(commentArea, createConstrints(2, 5, 2, 1, 0.6, 0.0));
		
		descriptionArea = new TextArea("", 8, 10, TextArea.SCROLLBARS_NONE);
		descriptionArea.setEditable(false);
		this.jFrame.add(descriptionArea, createConstrints(2, 3, 2, 2, 0.6, 0.0));
		
		beingMadeButton = new JButton("Sett under laging");
		this.jFrame.add(beingMadeButton, createConstrints(0, 6, 1, 1, 0.14, 0.0));
		
		finishedButton = new JButton("Ferdig");
		this.jFrame.add(finishedButton, createConstrints(1, 6, 1, 1, 0.26, 0.0));
	}
	
	private GridBagConstraints createConstrints(int xx, int yy, int width, int height, double xweight, double yweight){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = xx;
		gbc.gridy = yy;
		gbc.gridwidth = width;
		gbc.gridheight = height;
		gbc.weightx = xweight;
		gbc.weighty = yweight;
		gbc.fill = GridBagConstraints.BOTH;
		return gbc;
	}
	
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
		for(Order o : databaseOrders.getOrderList()){
			if(o.getStatus().equals(Order.REGISTERED) || o.getStatus().equals(Order.BEING_COOKED)){
				String sc = "Ordre nr: " + o.getID() + " Antall Retter: " + o.getOrderedDishes().size(); 
				//this.orderModel.addRow(new Object[]{o.getID(), o.getOrderedDishes().size(), o.getStatus()});
				orderList.add(sc);
				orderMap.put(sc, o);
			}
		}
	}

	@Override
	public void show() {
		//orderTable.setVisible(true);
		orderList.setVisible(true);
		//currentOrderTable.setVisible(true);
		currentOrderList.setVisible(true);
		commentArea.setVisible(true);
		descriptionArea.setVisible(true);
		beingMadeButton.setVisible(true);
		finishedButton.setVisible(true);
		jFrame.setVisible(true);
	}

	@Override
	public void hide() {
		//orderTable.setVisible(false);
		orderList.setVisible(false);
		//currentOrderTable.setVisible(false);
		currentOrderList.setVisible(false);
		commentArea.setVisible(false);
		descriptionArea.setVisible(false);
		beingMadeButton.setVisible(false);
		finishedButton.setVisible(false);
		jFrame.setVisible(true);
	}
}
