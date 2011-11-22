package pizzaProgram.gui.EventHandlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import pizzaProgram.config.Config;
import pizzaProgram.core.DatabaseQueryConstants;
import pizzaProgram.core.GUIConstants;
import pizzaProgram.dataObjects.Dish;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.Setting;
import pizzaProgram.database.databaseUtils.DataCleaner;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.AdminGUI;
import pizzaProgram.gui.views.AdminView;
import pizzaProgram.utils.PriceCalculators;
import pizzaProgram.utils.ReceiptGenerator;

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
		
		
		AdminView.ordersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleOrderSelection(e);
			}
		});
		
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
		
		AdminView.searchDishTextBox.addKeyListener(new KeyListener(){public void keyPressed(KeyEvent e) {}public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				handleDishSearchTyping(); 
			}
		});
		
		AdminView.searchExtraTextBox.addKeyListener(new KeyListener(){public void keyPressed(KeyEvent e) {}public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {
				handleExtraSearchTyping(); 
			}
		});
		
		AdminView.searchDishTextBox.addFocusListener(new FocusListener(){public void focusLost(FocusEvent arg0) {}
			public void focusGained(FocusEvent arg0) {
				handleDishSearchBoxSelection();
			}
		});
		
		AdminView.searchExtraTextBox.addFocusListener(new FocusListener(){
			public void focusLost(FocusEvent arg0) {}
			public void focusGained(FocusEvent arg0) {
				handleExtraSearchBoxSelection();
			}
			
		});
		
		AdminView.settingsApplyChangesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleSettingConfirmedButtonClicked();
			}
		});
		
		AdminView.settingsResetSettingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleSettingResetButtonClicked();
			}
		});
	
	}
	
	/**
	 * called when the user selects an order in the orderhistory
	 * @param the listSelectionEvent
	 */
	private void handleOrderSelection(ListSelectionEvent e) {
		int indexOfSelectedOrder = ((DefaultListSelectionModel)e.getSource()).getMinSelectionIndex();
		if(indexOfSelectedOrder < 0){
			return;
		}
		Order order = adminGUI.currentOrderList.get(indexOfSelectedOrder);
		AdminView.orderReceiptLabel.setText(ReceiptGenerator.generateReceipt(order));
	}
	
	/**
	 * called when the user clics reset in the settings-window
	 * resets all the data in the textfields to their databasevalue
	 */
	private void handleSettingResetButtonClicked() {
		AdminView.settingsDeliveryPriceTextBox.setText(PriceCalculators.getDeliveryCost());
		AdminView.settingsEditNameOfRestaurantTextBox.setText(PriceCalculators.getRestaurantName());
		AdminView.settingsEditMinimumPriceFreeDeliveryTextBox.setText(PriceCalculators.getFreeDeliveryTreshold());
	}
	
	/**
	 * called when the user clicks confirm when editing settings
	 */
	private void handleSettingConfirmedButtonClicked() {
		String priceText = AdminView.settingsDeliveryPriceTextBox.getText();
		String nameText = DataCleaner.cleanDbData(AdminView.settingsEditNameOfRestaurantTextBox.getText());
		String addressText = AdminView.settingsEditAdressOfRestaurantTextBox.getText();
		String cityText = AdminView.settingsEditCityOfRestaurantTextBox.getText();
		String delivertTresholdText = AdminView.settingsEditMinimumPriceFreeDeliveryTextBox.getText();
		
		int orePrice = 0;
		
		String[] splitt =priceText.split(",");
		if(splitt.length > 2 || splitt.length < 2){
			GUIConstants.showErrorMessage("Det må være nøyaktig et komma i prisen!");
			return;
		}
		try {
			orePrice = Integer.parseInt(splitt[0]) * 100;
		} catch (NumberFormatException e) {
			GUIConstants.showErrorMessage("Prisen må være et tall!");
			return;
		}
		if(splitt[1].length() > 2){
			GUIConstants.showErrorMessage("Det kan ikke være mer enn to siffer etter komma!");
			return;
		}
		try {
			orePrice +=Integer.parseInt(splitt[1]);
		} catch (Exception e) {
			GUIConstants.showErrorMessage("Prisen må være et tall!");
			return;
		}
		
		Setting priceSetting = new Setting(DatabaseQueryConstants.SETTING_KEY_DELIVERY_PRICE, ""+orePrice);
		
		if(nameText.length() < 3 || nameText.length() > 18){
			GUIConstants.showErrorMessage("Navnet på restauranten kan ikke være mindre enn 3 bokstaver eller mer enn 10 bokstaver");
			return;
		}
		Setting nameSetting = new Setting(DatabaseQueryConstants.SETTING_KEY_RESTAURANT_NAME, nameText);
		
		if(addressText.length() < 3){
			GUIConstants.showErrorMessage("Addressen til restauranten kan ikke være mindre enn 3 bokstaver");
			return;
		}
		Setting addressSetting = new Setting(DatabaseQueryConstants.SETTING_KEY_RESTAURANT_ADDRESS, addressText);
		
		if(cityText.length() < 1){
			GUIConstants.showErrorMessage("Addressen til restauranten kan ikke være mindre enn 1 bokstav");
			return;
		}
		Setting citySetting = new Setting(DatabaseQueryConstants.SETTING_KEY_RESTAURANT_CITY, cityText);
		
		
		
		int oreDeliveryTreshold = 0;
		String[] splittTreshold = delivertTresholdText.split(",");
		if(splittTreshold.length > 2){
			GUIConstants.showErrorMessage("Det må være nøyaktig et komma i grensen for gratis levering!");
			return;
		}
		try {
			oreDeliveryTreshold +=Integer.parseInt(splittTreshold[0]) * 100;
		} catch (Exception e) {
			GUIConstants.showErrorMessage("Grensen for gratis levering må være et tall!");
			return;
		}
		if(splittTreshold[1].length() > 2){
			GUIConstants.showErrorMessage("Det kan ikke være mer enn to siffer etter komma!");
			return;
		}
		try {
			oreDeliveryTreshold +=Integer.parseInt(splittTreshold[1]);
		} catch (Exception e) {
			GUIConstants.showErrorMessage("Grensen for gratis levering må være et tall!");
			return;
		}
		
		Setting deliveryTresholdSetting = new Setting(DatabaseQueryConstants.SETTING_KEY_FREE_DELIVERY_LIMIT, ""+oreDeliveryTreshold);
		
		boolean somethingChanged = false;
		if(!priceText.equals(PriceCalculators.getDeliveryCost())){
			Config.updateValueOfSetting(priceSetting);
			somethingChanged = true;
		}
		if(!nameText.equals(PriceCalculators.getRestaurantName())){
			Config.updateValueOfSetting(nameSetting);
			somethingChanged = true;
		}
		if(!delivertTresholdText.equals(PriceCalculators.getFreeDeliveryTreshold())){
			Config.updateValueOfSetting(deliveryTresholdSetting);
			somethingChanged = true;
		}
		if(!addressText.equals(PriceCalculators.getRestaurantAddress())){
			Config.updateValueOfSetting(addressSetting);
			somethingChanged = true;
		}
		if(!cityText.equals(PriceCalculators.getRestaurantCity())){
			Config.updateValueOfSetting(citySetting);
			somethingChanged = true;
		}
		
		if(!somethingChanged){
			GUIConstants.showConfirmMessage("Ingenting å endre");
		}else{
			GUIConstants.showConfirmMessage("Data endret");
		}
		PriceCalculators.getConstantsFromDataBase();
		handleSettingResetButtonClicked();
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
	

	protected void handleDishSearchBoxSelection() {
		AdminView.searchDishTextBox.setSelectionStart(0);
		AdminView.searchDishTextBox.setSelectionEnd(AdminView.searchDishTextBox.getText().length());
	}
	
	protected void handleExtraSearchBoxSelection() {
		AdminView.searchExtraTextBox.setSelectionStart(0);
		AdminView.searchExtraTextBox.setSelectionEnd(AdminView.searchExtraTextBox.getText().length());
	}
	
	protected void handleDishSearchTyping(){
		String query = AdminView.searchDishTextBox.getText();
		if(query.length() == 0){
			showAllDishes();
		} else {
			searchDishes(query);
		}
	}

	protected void handleExtraSearchTyping(){
		String query = AdminView.searchExtraTextBox.getText();
		if(query.length() == 0){
			this.showAllExtras();
		} else {
			this.searchExtras(query);
		}
	}
	
	private void searchExtras(String query) {
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_EXTRAS, query));
	}
	
	private void searchDishes(String query) {
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_DISHES, query));
	}

	private void handleExtraConfirmButtonClick() {
		boolean active = AdminView.editExtraExtraIsActiveComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true :false;
		String name = DataCleaner.cleanDbData(AdminView.editExtraExtraNameTextBox.getText());
		String price = AdminView.editExtraExtraPriceTextArea.getText().replaceAll(" ", "");
		
		char priceFunc = price.charAt(0);
		String priceVal = price.substring(1);
		
		if(name.isEmpty()){
			GUIConstants.showErrorMessage("Feltet med navn kan ikke være tomt!");
			return;
		}
		
		if(!(priceFunc == '+' || priceFunc == '*' || priceFunc == '-')){
			GUIConstants.showErrorMessage("Første tegn i prisen skal være +(pluss) -(minus) eller *(gange)\n" +
					"Dette anngir om prisen på ekstraen er pristillegg, prisfradrag, eller en multiplikasjon");
			return;
		}
		
		
		String[] splitt = priceVal.split(",");
		int orepris = 0;
		if(splitt.length > 2 || splitt.length < 2){
			GUIConstants.showErrorMessage("Det må være nøyaktig et komma i prisen!");
			return;
		}
		try {
			orepris = Integer.parseInt(splitt[0]) * 100;
		} catch (NumberFormatException e) {
			GUIConstants.showErrorMessage("Prisen må være et tall!");
			return;
		}
		if(splitt[1].length() > 2){
			GUIConstants.showErrorMessage("Det kan ikke være mer enn to siffer etter komma!");
			return;
		}
		try {
			orepris +=Integer.parseInt(splitt[1]);
		} catch (Exception e) {
			GUIConstants.showErrorMessage("Prisen må være et tall!");
			return;
		}
		if(adminGUI.currentSelectedExtra == null){
			Extra e = new Extra(-1, name, priceFunc + "" + orepris, active);
			this.dispatchEvent(new Event<Extra>(EventType.DATABASE_ADD_NEW_EXTRA, e));
		}
		else{
			Extra e = new Extra(adminGUI.currentSelectedExtra.id, name,  priceFunc + "" + orepris, active);
			this.dispatchEvent(new Event<Extra>(EventType.DATABASE_UPDATE_EXTRA_BY_EXTRA_ID, e));
		}
		//oppdaterer
		showAllExtras();
	}
	private void handleDishConfirmButtonClick() {
		boolean diary = AdminView.editDishContainsDairyComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean nuts = AdminView.editDIshContainsNutsComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean gluten = AdminView.editDIshContainsGlutenComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean spicy = AdminView.editDishIsPsicyComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean active = AdminView.editDishIsDishActiveComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		boolean vegan = AdminView.editDishIsVegetarianComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true : false;
		
		String name = DataCleaner.cleanDbData(AdminView.editDishNameTextBox.getText());
		String description = DataCleaner.cleanDbData(AdminView.editDishDescriptionTextArea.getText());
		String price = AdminView.editDishDishPriceTextArea.getText().trim();
		int orePrice = 0;
		
		if(description.isEmpty()){
			GUIConstants.showErrorMessage("Feltet med innhold i retten kan ikke være tomt!");
			return;
		}
		
		if(name.isEmpty()){
			GUIConstants.showErrorMessage("Feltet med navn kan ikke være tomt!");
			return;
		}
		
		
		String[] splitt = price.split(",");
		if(splitt.length > 2 || splitt.length < 2){
			GUIConstants.showErrorMessage("Det må være nøyaktig et komma i prisen!");
			return;
		}
		try {
			orePrice = Integer.parseInt(splitt[0]) * 100;
		} catch (NumberFormatException e) {
			GUIConstants.showErrorMessage("Prisen må være et tall!");
			return;
		}
		if(splitt[1].length() > 2){
			GUIConstants.showErrorMessage("Det kan ikke være mer enn to siffer etter komma!");
			return;
		}
		try {
			orePrice +=Integer.parseInt(splitt[1]);
		} catch (Exception e) {
			GUIConstants.showErrorMessage("Prisen må være et tall!");
			return;
		}
		
		if(adminGUI.currentSelectedDish == null){
			Dish d = new Dish(-1, orePrice, name, gluten, nuts, diary, vegan, spicy, description, active);
			this.dispatchEvent(new Event<Dish>(EventType.DATABASE_ADD_NEW_DISH, d));
		}
		else{
			Dish d = new Dish(adminGUI.currentSelectedDish.dishID, orePrice, name, gluten, nuts, diary, vegan, spicy, description, active);
			this.dispatchEvent(new Event<Dish>(EventType.DATABASE_UPDATE_DISH_BY_DISH_ID, d));
		}
		
		//update lists after update
		showAllDishes();
	}
	
	private void handleNewExtraButtonClick() {
		AdminView.allRegisteredExtrasTable.getSelectionModel().clearSelection();
		adminGUI.currentSelectedExtra = null;
		
		//tekstbokser
		AdminView.editExtraExtraNameTextBox.setText("Navn på tilbehør");
		AdminView.editExtraExtraNameTextBox.setEnabled(true);
		AdminView.editExtraExtraPriceTextArea.setText("+49,90");
		
		AdminView.editExtraExtraIsActiveComboBox.selectWithKeyChar(GUIConstants.GUI_TRUE.charAt(0));
	}
	
	private void handleNewDishButtonClick() {
		AdminView.allActiveDishesTable.getSelectionModel().clearSelection();
		adminGUI.currentSelectedDish = null;
		
		AdminView.editDishNameTextBox.setText("Eksempelpizza");
		AdminView.editDishNameTextBox.setEnabled(true);
		AdminView.editDishDescriptionTextArea.setText("Ost - Jarlsberg\nSkinke\nTomatsaus");
		AdminView.editDishDishPriceTextArea.setText("169,70");
		
		char trueChar = GUIConstants.GUI_TRUE.charAt(0);
		char falseChar = GUIConstants.GUI_FALSE.charAt(0);
		
		AdminView.editDishContainsDairyComboBox.selectWithKeyChar(trueChar);
		AdminView.editDIshContainsGlutenComboBox.selectWithKeyChar(trueChar);
		AdminView.editDIshContainsNutsComboBox.selectWithKeyChar(falseChar);
		AdminView.editDishIsPsicyComboBox.selectWithKeyChar(falseChar);
		AdminView.editDishIsVegetarianComboBox.selectWithKeyChar(trueChar);
		AdminView.editDishIsDishActiveComboBox.selectWithKeyChar(trueChar);
	}
	
	private void showAllDishes(){
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_DISHES));
	}
	
	private void showAllExtras(){
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_EXTRAS));
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
		AdminView.editDishNameTextBox.setEnabled(false);
		AdminView.editDishDescriptionTextArea.setText(selectedDish.description);
		AdminView.editDishDishPriceTextArea.setText(""+PriceCalculators.getPriceForDish(selectedDish));
		
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}//END
