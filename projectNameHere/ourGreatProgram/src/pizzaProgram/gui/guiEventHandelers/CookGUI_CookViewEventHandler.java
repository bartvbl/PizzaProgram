package pizzaProgram.gui.guiEventHandelers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventType;
import pizzaProgram.events.moduleEventHandlers.ComponentEventHandler;
import pizzaProgram.gui.CookGUI;
import pizzaProgram.gui.views.CookView;

public class CookGUI_CookViewEventHandler extends ComponentEventHandler implements ActionListener {
	
	private CookGUI cookGUI;
	
	public CookGUI_CookViewEventHandler(CookGUI cookGUI) {
		super(cookGUI);
		this.cookGUI = cookGUI;
		this.resetUI();
		this.addEventListeners();
	}
	
	private void addEventListeners(){
		CookView.orderDetailsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleOrderSelection(e);
			}
		});
		
		CookView.orderSearchTextPane.addFocusListener(new FocusListener(){public void focusLost(FocusEvent arg0) {}
		public void focusGained(FocusEvent arg0) {
			handleSearchBoxSelection();}});
		
		CookView.markOrderInProgressButton.addActionListener(this);
		this.registerEventType(CookView.markOrderInProgressButton, "markOrderAsInProgress");
		
		CookView.markOrderCompletedButton.addActionListener(this);
		this.registerEventType(CookView.markOrderCompletedButton, "markOrderCompleted");
		
		CookView.orderSearchTextPane.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent arg0) {}
			public void keyTyped(KeyEvent arg0) {}
			public void keyReleased(KeyEvent arg0) {
				if(CookView.orderSearchTextPane.getText().trim().isEmpty()) {
					showAllOrders();
				} else {
					searchOrdersBySearchBoxQuery();
				}
			}
		});
	}

	public void actionPerformed(ActionEvent event) {
		if (this.getEventNameByComponent((Component) event.getSource()).equals("markOrderAsInProgress")) {
			this.markOrderAsInProgress(event);
		} else if (this.getEventNameByComponent((Component) event.getSource()).equals("markOrderCompleted")) {
			this.markOrderCompleted(event);
		} else if (this.getEventNameByComponent((Component) event.getSource()).equals("searchOrders")) {
			this.searchOrdersBySearchBoxQuery();
		}
	}
	
	protected void handleSearchBoxSelection() {
		CookView.orderSearchTextPane.setSelectionStart(0);
		CookView.orderSearchTextPane.setSelectionEnd(CookView.orderSearchTextPane.getText().length());
	}
	
	private void showAllOrders(){
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_COOK_GUI_SEND_ALL_ORDERS));
	}
	
	private void markOrderCompleted(ActionEvent event) {
		this.dispatchEvent(new Event<Order>(EventType.DATABASE_MARK_ORDER_FINISHED_COOKING, this.cookGUI.currentSelectedOrder));
		this.resetUI();
		this.updateCurrentOrder(Order.HAS_BEEN_COOKED);
	}
	
	private void markOrderAsInProgress(ActionEvent event) {
		this.dispatchEvent(new Event<Order>(EventType.DATABASE_MARK_ORDER_IN_PROGRESS, this.cookGUI.currentSelectedOrder));
		this.updateCurrentOrder(Order.BEING_COOKED);
	}
	
	private void updateCurrentOrder(String beingCooked) {
		int selectedIndex = CookView.orderDetailsTable.getSelectionModel().getMinSelectionIndex();
		showAllOrders();
		if(selectedIndex == -1){
			return;
		}
		CookView.orderDetailsTable.getSelectionModel().setLeadSelectionIndex(selectedIndex);
		Order order = this.cookGUI.currentOrderList.get(selectedIndex);
		this.cookGUI.currentSelectedOrder = order;
		this.showOrder(order);
		if(CookView.orderSearchTextPane.getText().isEmpty()){
			showAllOrders();
		}else{
			searchOrdersBySearchBoxQuery();
		}
	}
	
	private void searchOrdersBySearchBoxQuery() {
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_COOK_GUI_SEARCH_ORDERS_BY_KEYWORDS, CookView.orderSearchTextPane.getText().trim()));
	}
	
	private void resetUI(){
		CookView.markOrderCompletedButton.setEnabled(false);
		CookView.markOrderInProgressButton.setEnabled(false);
		DefaultTableModel tableModel = (DefaultTableModel)CookView.currentOrderTable.getModel();
		tableModel.setNumRows(0);
		tableModel = (DefaultTableModel)CookView.orderDetailsTable.getModel();
		tableModel.setNumRows(0);
		CookView.orderCommentsTextArea.setText("");
	}
	
	private void handleOrderSelection(ListSelectionEvent e) {
		int selectedIndex = ((DefaultListSelectionModel)e.getSource()).getMinSelectionIndex();
		if(selectedIndex == -1){
			return;
		}
		Order order = this.cookGUI.currentOrderList.get(selectedIndex);
		this.cookGUI.currentSelectedOrder = order;
		this.showOrder(order);
	}
	
	private void showOrder(Order order) {
		DefaultTableModel tableModel = (DefaultTableModel) CookView.currentOrderTable.getModel();
		tableModel.setRowCount(0);
		String extrasString;
		for(OrderDish dish : order.getOrderedDishes()){
			extrasString = generateExtrasStringFromExtrasList(dish.getExtras());
			tableModel.addRow(new Object[]{dish.dish.name, extrasString});
		}
		CookView.orderCommentsTextArea.setText(order.comment);
		this.enableOrderMarkButtons(order);
	}
	
	private void enableOrderMarkButtons(Order order) {
		if(order.status.equals(Order.REGISTERED)){
			CookView.markOrderCompletedButton.setEnabled(false);
			CookView.markOrderInProgressButton.setEnabled(true);
		}
		else if (order.status.equals(Order.BEING_COOKED)){
			CookView.markOrderCompletedButton.setEnabled(true);
			CookView.markOrderInProgressButton.setEnabled(false);
		}
		else {
			CookView.markOrderCompletedButton.setEnabled(false);
			CookView.markOrderInProgressButton.setEnabled(false);
		}
	}
	private String generateExtrasStringFromExtrasList(ArrayList<Extra> extrasList){
		String extrasString = "";
		int counter = 0;
		for(Extra extra : extrasList){
			if(counter != 0){
				extrasString += ", ";
			}
			counter = 1;
			extrasString += extra.name;
		}
		return extrasString;
	}

}//END
