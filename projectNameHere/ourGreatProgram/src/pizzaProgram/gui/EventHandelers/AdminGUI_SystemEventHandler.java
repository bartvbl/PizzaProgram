package pizzaProgram.gui.EventHandelers;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.AdminGUI;
import pizzaProgram.gui.views.AdminView;
import pizzaProgram.utils.PriceCalculators;

public class AdminGUI_SystemEventHandler implements EventHandler {
	private EventDispatcher eventDispatcher;
	private AdminGUI adminGUI;
	
	public AdminGUI_SystemEventHandler(EventDispatcher eventDispatcher, AdminGUI adminGUI){
		this.eventDispatcher = eventDispatcher;
		this.adminGUI = adminGUI;
		this.addEventListeners();
	}

	private void addEventListeners() {
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_UPDATE_DISH_LIST);
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_UPDATE_EXTRA_LIST);
		this.eventDispatcher.addEventListener(this, EventType.ADMIN_GUI_REQUESTED);
	}

	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.ADMIN_GUI_UPDATE_DISH_LIST)){
			this.updateDishList(event);
		}
		else if(event.eventType.equals(EventType.ADMIN_GUI_UPDATE_EXTRA_LIST)){
			this.updateExtraList(event);
		}
		else if(event.eventType.equals(EventType.ADMIN_GUI_REQUESTED)){
			this.adminGUI.show();
		}
	}
	private void updateExtraList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.err.println("ERROR: got a list that was not a list of Order instances when trying to update the order list in the cook GUI.");
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Extra> extraList = (ArrayList<Extra>)event.getEventParameterObject();
		adminGUI.currentExtraList = extraList;
		DefaultTableModel tableModel = (DefaultTableModel)AdminView.allRegisteredExtrasTable.getModel();
		tableModel.setRowCount(0);
		for(Extra e : extraList){
			tableModel.addRow(new Object[]{e.name, PriceCalculators.getPriceForExtra(e), e.isActive ? GUIConstants.GUI_TRUE : GUIConstants.GUI_FALSE});
		}
	}
	
	private void updateDishList(Event<?> event) {
		if(!(event.getEventParameterObject() instanceof ArrayList<?>)){
			System.err.println("ERROR: got a list that was not a list of Order instances when trying to update the order list in the cook GUI.");
			return;
		}
		@SuppressWarnings("unchecked")
		ArrayList<Dish> dishList = (ArrayList<Dish>)event.getEventParameterObject();
		adminGUI.currentDishList = dishList;
		DefaultTableModel tableModel = (DefaultTableModel)AdminView.allActiveDishesTable.getModel();
		tableModel.setRowCount(0);
		for(Dish d : dishList){
			tableModel.addRow(new Object[]{d.name, PriceCalculators.getPriceForDish(d) + " kr", d.isActive ? GUIConstants.GUI_TRUE : GUIConstants.GUI_FALSE});
		}
	}
	
}//END
