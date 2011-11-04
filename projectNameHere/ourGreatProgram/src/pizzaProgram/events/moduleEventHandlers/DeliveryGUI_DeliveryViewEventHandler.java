package pizzaProgram.events.moduleEventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pizzaProgram.dataObjects.Order;
import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.utils.DeliveryGUIUpdater;
import pizzaProgram.gui.views.DeliveryView;
import pizzaProgram.modules.Module;

public class DeliveryGUI_DeliveryViewEventHandler extends ComponentEventHandler implements ActionListener  {

	private DeliverGUI deliveryGUI;
	private DeliveryGUIUpdater guiUpdater;

	public DeliveryGUI_DeliveryViewEventHandler(DeliverGUI deliveryGUI) {
		super(deliveryGUI);
		this.deliveryGUI = deliveryGUI;
		this.resetUI();
		this.addEventListeners();
		this.guiUpdater = new DeliveryGUIUpdater();
	}
	
	private void addEventListeners() {
		DeliveryView.activeOrdersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleOrderSelection();
			}});
	}

	public void actionPerformed(ActionEvent e)
	{
		
	}
	
	private void handleOrderSelection()
	{
		int selectedIndex = DeliveryView.activeOrdersTable.getSelectionModel().getLeadSelectionIndex();
		if(selectedIndex == -1)
		{
			return;
		}
		Order order = this.deliveryGUI.currentOrderList.get(selectedIndex);
		this.guiUpdater.showOrder(order);
	}
	
	private void resetUI() {
		DeliveryView.orderCostDeliveryCost.setText("");
		DeliveryView.orderCostOrderPrice.setText("");
		DeliveryView.orderCostTotalCost.setText("");
		DeliveryView.orderCommentsTextArea.setText("");
		DeliveryView.orderAddressTextArea.setText("");
		DeliveryView.markOrderBeingDeliveredButton.setEnabled(false);
		DeliveryView.markOrderDeliveredButton.setEnabled(false);
	}

}
