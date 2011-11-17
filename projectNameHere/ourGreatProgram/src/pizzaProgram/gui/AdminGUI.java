package pizzaProgram.gui;

import java.util.ArrayList;

import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import pizzaProgram.core.Constants;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.events.moduleEventHandlers.AdminGUI_AdminViewEventHandler;
import pizzaProgram.events.moduleEventHandlers.AdminGUI_SystemEventHandler;
import pizzaProgram.gui.views.AdminView;
import pizzaProgram.modules.GUIModule;

public class AdminGUI extends GUIModule implements EventHandler{
	private ProgramWindow programWindow;
	private AdminView adminView;

	public ArrayList<Dish> currentDishList;
	public Dish currentSelectedDish;

	public ArrayList<Extra> currentExtraList;
	public Extra currentSelectedExtra;

	public AdminGUI(ProgramWindow programWindow, EventDispatcher eventDispatcher){
		super(eventDispatcher);
		this.programWindow = programWindow;
		this.adminView = new AdminView();
		programWindow.addJPanel(this.adminView);
		eventDispatcher.addEventListener(this, EventType.OPEN_SETTINGS_WINDOW_REQUESTED);

		new AdminGUI_AdminViewEventHandler(this);
		new AdminGUI_SystemEventHandler(eventDispatcher, this);
		this.setupComponents();
		this.hide();
	}

	private void setupComponents() {
		DefaultTableModel dishTableModel = (DefaultTableModel) AdminView.allActiveDishesTable.getModel();
		dishTableModel.addColumn("Navn");
		dishTableModel.addColumn("Pris");
		dishTableModel.addColumn("I sortiment");
		AdminView.allActiveDishesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//DROPDOWNS
		AdminView.editDishContainsDairyComboBox.addItem(Constants.GUI_TRUE);
		AdminView.editDishContainsDairyComboBox.addItem(Constants.GUI_FALSE);
		AdminView.editDIshContainsGlutenComboBox.addItem(Constants.GUI_TRUE);
		AdminView.editDIshContainsGlutenComboBox.addItem(Constants.GUI_FALSE);
		AdminView.editDIshContainsNutsComboBox.addItem(Constants.GUI_TRUE);
		AdminView.editDIshContainsNutsComboBox.addItem(Constants.GUI_FALSE);
		AdminView.editDishIsDishActiveComboBox.addItem(Constants.GUI_TRUE);
		AdminView.editDishIsDishActiveComboBox.addItem(Constants.GUI_FALSE);
		AdminView.editDishIsPsicyComboBox.addItem(Constants.GUI_TRUE);
		AdminView.editDishIsPsicyComboBox.addItem(Constants.GUI_FALSE);
		AdminView.editDishIsVegetarianComboBox.addItem(Constants.GUI_TRUE);
		AdminView.editDishIsVegetarianComboBox.addItem(Constants.GUI_FALSE);
		
		DefaultTableModel extraTableModel = (DefaultTableModel) AdminView.allRegisteredExtrasTable.getModel();
		extraTableModel.addColumn("Navn");
		extraTableModel.addColumn("Pris");
		extraTableModel.addColumn("I sortiment");
		AdminView.allRegisteredExtrasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		AdminView.editExtraExtraIsActiveComboBox.addItem(Constants.GUI_TRUE);
		AdminView.editExtraExtraIsActiveComboBox.addItem(Constants.GUI_FALSE);

	}

//	private void resetUI() {
//		AdminView.allActiveDishesTable.getSelectionModel().clearSelection();
//		this.currentSelectedDish = null;
//
//		AdminView.editDishNameTextBox.setText("");
//		AdminView.editDishDescriptionTextArea.setText("");
//		AdminView.editDishDishPriceTextArea.setText("");
//
//		AdminView.editDishContainsDairyComboBox.selectWithKeyChar('n');
//		AdminView.editDIshContainsGlutenComboBox.selectWithKeyChar('n');
//		AdminView.editDIshContainsNutsComboBox.selectWithKeyChar('n');
//		AdminView.editDishIsPsicyComboBox.selectWithKeyChar('n');
//		AdminView.editDishIsVegetarianComboBox.selectWithKeyChar('n');
//		AdminView.editDishIsDishActiveComboBox.selectWithKeyChar('n');
//		
//	}

	public void show() {
		this.programWindow.showPanel(this.adminView);
	}

	public void hide() {
		this.programWindow.hidePanel(this.adminView);
	}

	@Override
	public void handleEvent(Event<?> event){
		if(event.eventType.equals(EventType.OPEN_SETTINGS_WINDOW_REQUESTED)){
			show();
			this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_DISHES));
			this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_EXTRAS));
		}
	}

}//END
