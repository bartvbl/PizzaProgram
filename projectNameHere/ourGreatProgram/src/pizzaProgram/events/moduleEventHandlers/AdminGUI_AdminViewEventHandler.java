package pizzaProgram.events.moduleEventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.AdminGUI;
import pizzaProgram.gui.views.AdminView;

public class AdminGUI_AdminViewEventHandler extends ComponentEventHandler implements ActionListener {
	private AdminGUI adminGUI;
	
	public AdminGUI_AdminViewEventHandler(AdminGUI adminGUI) {
		super(adminGUI);
		this.adminGUI = adminGUI;
		this.addEventListeners();
	}
	
	private void addEventListeners(){
		AdminView.allActiveDishesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleDishSelection(e);
			}
		});
		AdminView.addNewDishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleNewDishButtonClick();
			}
		});
		AdminView.confirmDishEditButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleDishConfirmButtonClick();
			}
		});
		AdminView.allRegisteredExtrasTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleExtraSelection(e);
			}
		});
		AdminView.confirmEditExtraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleExtraConfirmButtonClick();
			}
		});
		AdminView.createNewExtraButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleNewExtraButtonClick();
			}
		});
	}
	private void handleExtraSelection(ListSelectionEvent e) {
		int selectedIndex = ((DefaultListSelectionModel)e.getSource()).getMinSelectionIndex();
		if(selectedIndex == -1){
			return;
		}
		Extra selectedExtra = adminGUI.currentExtraList.get(selectedIndex);
		adminGUI.currentSelectedExtra = selectedExtra;
		
		//tekstbokser
		AdminView.editExtraExtraNameTextBox.setText(selectedExtra.name);
		AdminView.editExtraExtraPriceTextArea.setText(""+selectedExtra.priceFuncPart + selectedExtra.priceValPart);
		
		char selectchar = selectedExtra.isActive ? 'y' : 'n';
		AdminView.editExtraExtraIsActiveComboBox.selectWithKeyChar(selectchar);
		
	}
	private void handleExtraConfirmButtonClick() {
		if(adminGUI.currentSelectedExtra == null){
			String name = AdminView.editExtraExtraNameTextBox.getText();
			boolean active = AdminView.editExtraExtraIsActiveComboBox.getSelectedItem().equals("yes") ? true :false;
			String price = AdminView.editExtraExtraPriceTextArea.getText();
			//lagger til i databsen
			Extra e = new Extra(-1, name, price, active);
			this.dispatchEvent(new Event<Extra>(EventType.DATABASE_ADD_NEW_EXTRA, e));
			//oppdaterer
			showAllExtras();
		}

	}
	private void handleDishConfirmButtonClick() {
		if(adminGUI.currentSelectedDish == null){
			boolean diary = AdminView.editDishContainsDairyComboBox.getSelectedItem().equals("yes") ? true : false;
			boolean nuts = AdminView.editDIshContainsNutsComboBox.getSelectedItem().equals("yes") ? true : false;
			boolean gluten = AdminView.editDIshContainsGlutenComboBox.getSelectedItem().equals("yes") ? true : false;
			boolean spicy = AdminView.editDishIsPsicyComboBox.getSelectedItem().equals("yes") ? true : false;
			boolean active = AdminView.editDishIsDishActiveComboBox.getSelectedItem().equals("yes") ? true : false;
			boolean vegan = AdminView.editDishIsVegetarianComboBox.getSelectedItem().equals("yes") ? true : false;
			
			String description = AdminView.editDishDescriptionTextArea.getText();
			String name = AdminView.editDishNameTextBox.getText();
			int price = Integer.parseInt(AdminView.editDishDishPriceTextArea.getText());
			Dish d = new Dish(-1, price, name, gluten, nuts, diary, vegan, spicy, description, active);
			this.dispatchEvent(new Event<Dish>(EventType.DATABASE_ADD_NEW_DISH, d));
			showAllDishes();
		}
	}
	
	private void handleNewExtraButtonClick() {
		AdminView.allRegisteredExtrasTable.getSelectionModel().clearSelection();
		adminGUI.currentSelectedExtra = null;
		
		//tekstbokser
		AdminView.editExtraExtraNameTextBox.setText("Navn på tilbehør");
		AdminView.editExtraExtraPriceTextArea.setText("+50");
		
		AdminView.editExtraExtraIsActiveComboBox.selectWithKeyChar('y');

	}
	
	private void handleNewDishButtonClick() {
		AdminView.allActiveDishesTable.getSelectionModel().clearSelection();
		adminGUI.currentSelectedDish = null;
		
		AdminView.editDishNameTextBox.setText("Eksempelpizza");
		AdminView.editDishDescriptionTextArea.setText("Ost - Jarlsberg\nSkinke\nTomatsaus");
		AdminView.editDishDishPriceTextArea.setText("150");
		
		AdminView.editDishContainsDairyComboBox.selectWithKeyChar('y');
		AdminView.editDIshContainsGlutenComboBox.selectWithKeyChar('y');
		AdminView.editDIshContainsNutsComboBox.selectWithKeyChar('n');
		AdminView.editDishIsPsicyComboBox.selectWithKeyChar('n');
		AdminView.editDishIsVegetarianComboBox.selectWithKeyChar('n');
		AdminView.editDishIsDishActiveComboBox.selectWithKeyChar('y');
		
	}
	private void showAllDishes(){
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_DISHES));
	}
	private void showAllExtras(){
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_DISHES));
	}
	
	private void handleDishSelection(ListSelectionEvent e) {
		int selectedIndex = ((DefaultListSelectionModel)e.getSource()).getMinSelectionIndex();
		if(selectedIndex == -1){
			return;
		}
		Dish selectedDish = adminGUI.currentDishList.get(selectedIndex);
		adminGUI.currentSelectedDish = selectedDish;
		//tekstbokser
		AdminView.editDishNameTextBox.setText(selectedDish.name);
		AdminView.editDishDescriptionTextArea.setText(selectedDish.description);
		AdminView.editDishDishPriceTextArea.setText(""+selectedDish.price);
		
		//dropdownmenyer
		char selectDiaryYN = selectedDish.containsDiary ? 'y': 'n';
		AdminView.editDishContainsDairyComboBox.selectWithKeyChar(selectDiaryYN);
		
		char selectGlutenYN = selectedDish.containsGluten ? 'y': 'n';
		AdminView.editDIshContainsGlutenComboBox.selectWithKeyChar(selectGlutenYN);
		
		char selectNutsYN = selectedDish.containsNuts ? 'y': 'n';
		AdminView.editDIshContainsNutsComboBox.selectWithKeyChar(selectNutsYN);
		
		char selectSpicyYN = selectedDish.isSpicy ? 'y': 'n';
		AdminView.editDishIsPsicyComboBox.selectWithKeyChar(selectSpicyYN);
		
		char selectVeganYN = selectedDish.isVegetarian ? 'y': 'n';
		AdminView.editDishIsVegetarianComboBox.selectWithKeyChar(selectVeganYN);

		char selectActiveYN = selectedDish.isActive ? 'y': 'n';
		AdminView.editDishIsDishActiveComboBox.selectWithKeyChar(selectActiveYN);
		
	}
	
}//END
