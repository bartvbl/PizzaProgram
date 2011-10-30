package pizzaProgram.gui;

import pizzaProgram.gui.views.CookView;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.List;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
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
	
	private JPanel cookView;
    private ProgramWindow programWindow;
	
	private List orderList;
	private HashMap<String, Order> orderMap = new HashMap<String, Order>();
	private List currentOrderList;
	private TextArea commentArea;
	private TextArea descriptionArea;
	
	private JFrame jFrame;
	private HashMap<String, Dish> dishMap = new HashMap<String, Dish>();
	
	private JButton beingMadeButton;
	private JButton finishedButton;
	
	public CookGUI(ProgramWindow mainWindow, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
                this.cookView = new CookView();
                mainWindow.addJPanel(this.cookView);
                this.programWindow = mainWindow;
                this.hide();
	}
	
	@Override
	public void initialize() {
		
		orderList = new List();
		orderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				currentOrderList.removeAll();
				descriptionArea.setText("");
				DefaultTableModel model = new DefaultTableModel();
				for(OrderDish d : orderMap.get(orderList.getSelectedItem()).getOrderedDishes()){
					model.addRow(new Object[]{d.dish.dishID, d.dish.name, ""});
					currentOrderList.add(d.dish.name);
					dishMap.put(d.dish.name, d.dish);
					for(Extra e : d.getExtras()){
						String str = "   - " + e.name;
						currentOrderList.add(str);
						dishMap.put(str, d.dish);
					}
					
					commentArea.setText(orderMap.get(orderList.getSelectedItem()).getComment());
				}
			}
		});
		this.jFrame.add(orderList, createConstrints(2, 0, 20, 6, 0.4, 1.0, GridBagConstraints.BOTH));
		
		currentOrderList = new List();
		currentOrderList.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Dish diplayDish = dishMap.get(currentOrderList.getSelectedItem());
				descriptionArea.setText("Pizza nr: " + diplayDish.dishID + "\n" + diplayDish.name + "\n\n" + diplayDish.description);
			}
		});
		this.jFrame.add(currentOrderList, createConstrints(22, 0, 23, 3, 0.6, 0.5, GridBagConstraints.BOTH));
		
		commentArea = new TextArea("", 3, 10, TextArea.SCROLLBARS_NONE);
		commentArea.setEditable(false);
		commentArea.setBackground(Color.white);
		this.jFrame.add(commentArea, createConstrints(22, 5, 23, 1, 0.6, 0.0, GridBagConstraints.BOTH));
		
		descriptionArea = new TextArea("", 8, 10, TextArea.SCROLLBARS_NONE);
		descriptionArea.setEditable(false);
		descriptionArea.setBackground(Color.white);
		this.jFrame.add(descriptionArea, createConstrints(22, 3, 23, 2, 0.6, 0.0, GridBagConstraints.BOTH));
		
		beingMadeButton = new JButton("Begynn tilbereding");
		beingMadeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderList.changeOrderStatus(orderMap.get(orderList.getSelectedItem()), Order.BEING_COOKED);
				populateLists();
			}
		});
		this.jFrame.add(beingMadeButton, createConstrints(3, 6, 1, 1, 0.14, 0.0, GridBagConstraints.BOTH));
		
		finishedButton = new JButton("Ferdig");
		finishedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderList.changeOrderStatus(orderMap.get(orderList.getSelectedItem()), Order.HAS_BEEN_COOKED);
				populateLists();
			}
		});
		this.jFrame.add(finishedButton, createConstrints(4, 6, 18, 1, 0.26, 0.0, GridBagConstraints.BOTH));
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
	
	@Override
	public void handleEvent(Event<?> event){
            if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			show();
		}
	}
	
	private void populateLists(){
		OrderList.updateOrders();
		orderMap.clear();
		orderList.removeAll();
		currentOrderList.removeAll();
		descriptionArea.setText("");
		commentArea.setText("");
		
		for(Order o : OrderList.getOrderList()){
			if(o.getStatus().equals(Order.REGISTERED) || o.getStatus().equals(Order.BEING_COOKED)){
				String sc = "Ordre nr: " + o.getID() + " Antall Retter: " + o.getOrderedDishes().size(); 
				String status = (o.getStatus().equals(Order.BEING_COOKED) ? "X" : "  ");
				sc = status + " " + sc;
				orderList.add(sc);
				orderMap.put(sc, o);
			}
		}
	}

	@Override
	public void show() 
	{
           this.programWindow.showPanel(this.cookView);
	}
	@Override
	public void hide() 
	{
           this.programWindow.hidePanel(this.cookView);
	}
}
