package pizzaProgram.events.moduleEventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import pizzaProgram.gui.DeliverGUI;
import pizzaProgram.gui.views.DeliveryView;
import pizzaProgram.modules.Module;

public class DeliveryGUI_DeliveryViewEventHandler extends ComponentEventHandler implements ActionListener  {

	private DeliverGUI deliveryGUI;

	public DeliveryGUI_DeliveryViewEventHandler(DeliverGUI deliveryGUI) {
		super(deliveryGUI);
		this.deliveryGUI = deliveryGUI;
		this.resetUI();
		this.addEventListeners();
	}
	
	private void addEventListeners() {
		
	}

	public void actionPerformed(ActionEvent e)
	{
		
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
