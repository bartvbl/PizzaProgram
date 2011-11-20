package pizzaProgram.gui.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.database.databaseUtils.DataCleaner;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventType;
import pizzaProgram.events.moduleEventHandlers.ComponentEventHandler;
import pizzaProgram.gui.AdminGUI;
import pizzaProgram.gui.views.AdminView;
import pizzaProgram.utils.PriceCalculators;

/**
 * This class hendles events dispatched from the gui-components in AdminWiev
 */
public class AdminGUI_AdminViewEventHandler extends ComponentEventHandler implements ActionListener {
	private AdminGUI adminGUI;

	public AdminGUI_AdminViewEventHandler(AdminGUI adminGUI) {
		super(adminGUI);
		this.adminGUI = adminGUI;
		this.addEventListeners();
	}

	/**
	 * This method adds listeners to the desiered components in AdminWiev
	 * The code to be run when an event is recived is splitt up into different methodes, one for each component/event
	 */
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

		AdminView.searchDishTextBox.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				handleDishSearchTyping(); }
			}
		);

		AdminView.searchExtraTextBox.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e) {}
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				handleExtraSearchTyping(); }
			}
		);


	}

	/**
	 * The method called when a user selects an extra from the extra-list
	 * @param e
	 */
	private void handleExtraSelection(ListSelectionEvent e) {
		int selectedIndex = ((DefaultListSelectionModel)e.getSource()).getMinSelectionIndex();
		if(selectedIndex == -1){
			return;
		}

		Extra selectedExtra = adminGUI.currentExtraList.get(selectedIndex);
		adminGUI.currentSelectedExtra = selectedExtra;

		//tekstbokser
		AdminView.editExtraExtraNameTextBox.setText(selectedExtra.name);
		AdminView.editExtraExtraNameTextBox.setEnabled(false);
		AdminView.editExtraExtraPriceTextArea.setText(PriceCalculators.getPriceForExtra(selectedExtra));

		char selectchar = selectedExtra.isActive ? GUIConstants.GUI_TRUE.charAt(0) : GUIConstants.GUI_FALSE.charAt(0);
		AdminView.editExtraExtraIsActiveComboBox.selectWithKeyChar(selectchar);
	}

	/**
	 * called when the user types in the dish-search-box
	 */
	protected void handleDishSearchTyping(){
		String query = AdminView.searchDishTextBox.getText();
		if(query.length() == 0){
			showAllDishes();
		} else {
			searchDishes(query);
		}
	}
	/**
	 * called when the user types in the extra-search-box
	 */
	protected void handleExtraSearchTyping(){
		String query = AdminView.searchExtraTextBox.getText();
		if(query.length() == 0){
			this.showAllExtras();
		} else {
			this.searchExtras(query);
		}
	}

	/**
	 * sends an event that indicates that something is searched for, this event is recived by 
	 * listeneres that dispatch an event with the requested list of found items
	 * @param what to search for
	 */
	private void searchExtras(String query) {
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_EXTRAS, query));
	}

	/**
	 * sends an event that indicates that something is searched for, this event is recived by 
	 * listeneres that dispatch an event with the requested list of found items
	 * @param what to search for
	 */
	private void searchDishes(String query) {
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_DISHES, query));
	}

	/**
	 * called when the user clicks confirm when editing or creating a new extra
	 * this mathod contains inputvalidation and sends an evant that indicates that
	 * an extra is being changed
	 */
	private void handleExtraConfirmButtonClick() {
		boolean active = AdminView.editExtraExtraIsActiveComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true :false;
		String name = DataCleaner.cleanDbData(AdminView.editExtraExtraNameTextBox.getText());
		String price = DataCleaner.cleanDbData(AdminView.editExtraExtraPriceTextArea.getText()).replaceAll(" ", "");

		if(name.isEmpty()){
			JOptionPane.showMessageDialog(null, "Feltet med navn kan ikke være tomt!");
			return;
		}

		if(!(price.charAt(0) == '+' || price.charAt(0) == '*' || price.charAt(0) == '-')){
			JOptionPane.showMessageDialog(null, "Første tegn i prisen skal være +(pluss) -(minus) eller *(gange)\n" +
					"Dette anngir om prisen på ekstraen er pristillegg, prisfradrag, eller en multiplikasjon");
			return;
		}

		try {
			Double.parseDouble(price.substring(1));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Tegnene etter +/-/* i prisen må være et tall!");
			return;
		}

		if(adminGUI.currentSelectedExtra == null){
			Extra e = new Extra(-1, name, price, active);
			this.dispatchEvent(new Event<Extra>(EventType.DATABASE_ADD_NEW_EXTRA, e));
		}
		else{
			Extra e = new Extra(adminGUI.currentSelectedExtra.id, name, price, active);
			this.dispatchEvent(new Event<Extra>(EventType.DATABASE_UPDATE_EXTRA_BY_EXTRA_ID, e));
		}
		//oppdaterer
		showAllExtras();
	}
	
	/**
	 * called when the user clicks confirm when editing or creating a new dish
	 * this mathod contains inputvalidation and sends an event that indicates that
	 * a dish is being changed
	 */
	private void handleDishConfirmButtonClick() {
		boolean diary = AdminView.editDishContainsDairyComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean nuts = AdminView.editDIshContainsNutsComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean gluten = AdminView.editDIshContainsGlutenComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean spicy = AdminView.editDishIsPsicyComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean active = AdminView.editDishIsDishActiveComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean vegan = AdminView.editDishIsVegetarianComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;

		String name = DataCleaner.cleanDbData(AdminView.editDishNameTextBox.getText());
		String description = DataCleaner.cleanDbData(AdminView.editDishDescriptionTextArea.getText());
		int price = 0;

		if(description.isEmpty()){
			JOptionPane.showMessageDialog(null, "Feltet med innhold i retten kan ikke være tomt!");
			return;
		}

		if(name.isEmpty()){
			JOptionPane.showMessageDialog(null, "Feltet med navn kan ikke være tomt!");
			return;
		}

		try {
			price = Integer.parseInt(AdminView.editDishDishPriceTextArea.getText().trim());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Feltet med pris må være et tall!");
			return;
		}

		if(adminGUI.currentSelectedDish == null){
			Dish d = new Dish(-1, price, name, gluten, nuts, diary, vegan, spicy, description, active);
			this.dispatchEvent(new Event<Dish>(EventType.DATABASE_ADD_NEW_DISH, d));
		}
		else{
			Dish d = new Dish(adminGUI.currentSelectedDish.dishID, price, name, gluten, nuts, diary, vegan, spicy, description, active);
			this.dispatchEvent(new Event<Dish>(EventType.DATABASE_UPDATE_DISH_BY_DISH_ID, d));
		}

		//update lists after update
		showAllDishes();
	}

	/**
	 * called when the user click the newExtraButton, this method fills the 
	 * form with exampledata making it easier for the user to see whats to be filled in
	 */
	private void handleNewExtraButtonClick() {
		AdminView.allRegisteredExtrasTable.getSelectionModel().clearSelection();
		adminGUI.currentSelectedExtra = null;

		//tekstbokser
		AdminView.editExtraExtraNameTextBox.setText("Navn på tilbehør");
		AdminView.editExtraExtraNameTextBox.setEnabled(true);
		AdminView.editExtraExtraPriceTextArea.setText("+50");

		AdminView.editExtraExtraIsActiveComboBox.selectWithKeyChar(GUIConstants.GUI_TRUE.charAt(0));
	}
	
	/**
	 * called when the user click the newDishButton, this method fills the 
	 * form with exampledata making it easier for the user to see whats to be filled in
	 */
	private void handleNewDishButtonClick() {
		AdminView.allActiveDishesTable.getSelectionModel().clearSelection();
		adminGUI.currentSelectedDish = null;

		AdminView.editDishNameTextBox.setText("Eksempelpizza");
		AdminView.editDishNameTextBox.setEnabled(true);
		AdminView.editDishDescriptionTextArea.setText("Ost - Jarlsberg\nSkinke\nTomatsaus");
		AdminView.editDishDishPriceTextArea.setText("150");

		char trueChar = GUIConstants.GUI_TRUE.charAt(0);
		char falseChar = GUIConstants.GUI_FALSE.charAt(0);

		AdminView.editDishContainsDairyComboBox.selectWithKeyChar(trueChar);
		AdminView.editDIshContainsGlutenComboBox.selectWithKeyChar(trueChar);
		AdminView.editDIshContainsNutsComboBox.selectWithKeyChar(falseChar);
		AdminView.editDishIsPsicyComboBox.selectWithKeyChar(falseChar);
		AdminView.editDishIsVegetarianComboBox.selectWithKeyChar(trueChar);
		AdminView.editDishIsDishActiveComboBox.selectWithKeyChar(trueChar);
	}
	
	/**
	 * dispatches an event that requests a list of all dishes
	 */
	private void showAllDishes(){
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_DISHES));
	}

	/**
	 * dispatches an event that requests a list of all extras
	 */
	private void showAllExtras(){
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_EXTRAS));
	}
	
	/**
	 * called when a user selects a dish, the form is filled with tha data
	 * of the dish that was selected
	 * @param e
	 */
	private void handleDishSelection(ListSelectionEvent e) {
		int selectedIndex = ((DefaultListSelectionModel)e.getSource()).getMinSelectionIndex();
		if(selectedIndex == -1){
			return;
		}
		Dish selectedDish = adminGUI.currentDishList.get(selectedIndex);
		adminGUI.currentSelectedDish = selectedDish;
		//tekstbokser
		AdminView.editDishNameTextBox.setText(selectedDish.name);
		AdminView.editDishNameTextBox.setEnabled(false);
		AdminView.editDishDescriptionTextArea.setText(selectedDish.description);
		AdminView.editDishDishPriceTextArea.setText(PriceCalculators.getPriceForDish(selectedDish));

		//her resetter vi alle valgboksene til sin defaultverdi
		char trueChar = GUIConstants.GUI_TRUE.charAt(0);
		char falseChar = GUIConstants.GUI_FALSE.charAt(0);

		char selectDiaryYN = selectedDish.containsDairy ? trueChar: falseChar;
		AdminView.editDishContainsDairyComboBox.selectWithKeyChar(selectDiaryYN);

		char selectGlutenYN = selectedDish.containsGluten ? trueChar: falseChar;
		AdminView.editDIshContainsGlutenComboBox.selectWithKeyChar(selectGlutenYN);

		char selectNutsYN = selectedDish.containsNuts ? trueChar: falseChar;
		AdminView.editDIshContainsNutsComboBox.selectWithKeyChar(selectNutsYN);

		char selectSpicyYN = selectedDish.isSpicy ? trueChar: falseChar;
		AdminView.editDishIsPsicyComboBox.selectWithKeyChar(selectSpicyYN);

		char selectVeganYN = selectedDish.isVegetarian ? trueChar: falseChar;
		AdminView.editDishIsVegetarianComboBox.selectWithKeyChar(selectVeganYN);

		char selectActiveYN = selectedDish.isActive ? trueChar: falseChar;
		AdminView.editDishIsDishActiveComboBox.selectWithKeyChar(selectActiveYN);
	}

	@Override
	public void actionPerformed(ActionEvent e) {}

}//END
