package pizzaProgram.gui;

import java.awt.GridBagConstraints;
import java.awt.List;
import java.awt.PopupMenu;
import java.awt.TextArea;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

public class CookGUI extends GUIModule implements EventHandler{
	
	//private List orderList;
	private JTable orderTable;
	private DefaultTableModel orderModel;
	private HashMap<String, Order> orderMap = new HashMap<String, Order>();
	//private List currentOrderList;
	private JTable currentOrderTable;
	private TextArea commentArea;
	private TextArea descriptionArea;
	
	private DatabaseConnection database;
	private JFrame jFrame;
	private HashMap<String, Dish> dishMap = new HashMap<String, Dish>();
	
	public CookGUI(DatabaseConnection dbc, JFrame jFrame, EventDispatcher eventDispatcher) {
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
		orderModel = new DefaultTableModel();
		orderModel.addColumn("ID");
		orderModel.addColumn("Dish name");
		orderModel.addColumn("Extras");
		orderTable = new JTable(orderModel);
		//orderList = new List();
		/*orderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				currentOrderTable.removeAll();
				//currentOrderList.removeAll();
				descriptionArea.setText("");
				DefaultTableModel model = new DefaultTableModel();
				model.addColumn("ID");
				model.addColumn("Dish name");
				model.addColumn("Extras");
				for(OrderDish d : orderMap.get(orderList.getSelectedItem()).getOrderedDishes()){
					model.addRow(new Object[]{d.dish.dishID, d.dish.name, ""});
					//currentOrderList.add(d.dish.name);
					dishMap.put(d.dish.name, d.dish);
					for(Extra e : d.getExtras()){
						String str = "   - " + e.name;
						model.addRow(new Object[]{"", "", e.name});
						dishMap.put(str, d.dish);
					}
					
					commentArea.setText(orderMap.get(orderList.getSelectedItem()).getComment());
				}
				currentOrderTable = new JTable(model);
			}
		});*/
		GridBagConstraints orderListConstraints = new GridBagConstraints();
		orderListConstraints.gridx = 0;
		orderListConstraints.gridy = 0;
		orderListConstraints.weightx = 0.5;
		orderListConstraints.weighty = 1;
		orderListConstraints.gridwidth = 1;
		orderListConstraints.gridheight = 6;
		orderListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(orderTable, orderListConstraints);
		//		this.jFrame.add(orderList, orderListConstraints);
		
		currentOrderTable = new JTable();
		//currentOrderList = new List();
		
		
		
		/*currentOrderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Dish diplayDish = dishMap.get(currentOrderList.getSelectedItem());
				descriptionArea.setText("Pizza nr: " + diplayDish.dishID + "\n" + diplayDish.name + "\n\n" + diplayDish.description);
				
			}
		});*/
		GridBagConstraints currentOrderListConstraints = new GridBagConstraints();
		currentOrderListConstraints.gridx = 1;
		currentOrderListConstraints.gridy = 0;
		currentOrderListConstraints.weightx = 0.5;
		currentOrderListConstraints.weighty = 0.5;
		currentOrderListConstraints.gridwidth = 1;
		currentOrderListConstraints.gridheight = 3;
		currentOrderListConstraints.fill = GridBagConstraints.BOTH;
		this.jFrame.add(currentOrderTable, currentOrderListConstraints);
		//this.jFrame.add(currentOrderList, currentOrderListConstraints);
		
		commentArea = new TextArea("", 2, 10, TextArea.SCROLLBARS_NONE);
		commentArea.setEditable(false);
		GridBagConstraints commentAreaConstraints = new GridBagConstraints();
		commentAreaConstraints.gridx = 1;
		commentAreaConstraints.gridy = 5;
		commentAreaConstraints.weightx = 0.5;
		commentAreaConstraints.gridwidth = 1;
		commentAreaConstraints.gridheight = 1;
		commentAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.jFrame.add(commentArea, commentAreaConstraints);
		
		
		this.addConstrainedDescriptionAreaToFrame();
	}
	
	
	
	private void addConstrainedDescriptionAreaToFrame()
	{
		descriptionArea = new TextArea("", 8, 10, TextArea.SCROLLBARS_NONE);
		descriptionArea.setEditable(false);
		GridBagConstraints descriptionAreaConstraints = new GridBagConstraints();
		descriptionAreaConstraints.gridx = 1;
		descriptionAreaConstraints.gridy = 3;
		descriptionAreaConstraints.weightx = 0.5;
		descriptionAreaConstraints.gridwidth = 1;
		descriptionAreaConstraints.gridheight = 1;
		descriptionAreaConstraints.fill = GridBagConstraints.HORIZONTAL;
		this.jFrame.add(descriptionArea, descriptionAreaConstraints);
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
				String sc = "Ordre nr: " + o.getID() + " Antall Retter: " + o.getOrderedDishes().size(); 
				this.orderModel.addRow(new Object[]{o.getID(), o.getOrderedDishes().size(), o.getStatus()});
				//orderList.add(sc);
				orderMap.put(sc, o);
			}
		}
	}

	@Override
	public void show() {
		orderTable.setVisible(true);
		//orderList.setVisible(true);
		currentOrderTable.setVisible(true);
		//currentOrderList.setVisible(true);
		commentArea.setVisible(true);
		descriptionArea.setVisible(true);
		jFrame.setVisible(true);
	}

	@Override
	public void hide() {
		orderTable.setVisible(false);
		//orderList.setVisible(false);
		currentOrderTable.setVisible(false);
		//currentOrderList.setVisible(false);
		commentArea.setVisible(false);
		descriptionArea.setVisible(false);
		jFrame.setVisible(true);
	}
}
