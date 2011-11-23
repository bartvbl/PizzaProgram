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
import pizzaProgram.constants.GUIConstants;
import pizzaProgram.constants.GUIMessages;
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
	/**
	 * A reference to the AdminGUI
	 */
	private AdminGUI adminGUI;

	/**
	 * The constructor registers all events that this class handles at the various components in the AdminView
	 * @param adminGUI a reference to the main AdminGUI module
	 */
	public AdminGUI_AdminViewEventHandler(AdminGUI adminGUI) {
		super(adminGUI);
		this.adminGUI = adminGUI;
		this.addEventListeners();
	}

	/**
	 * This method adds listeners to the desiered components in AdminWiev The
	 * code to be run when an event is recived is splitt up into different
	 * methodes, one for each component/event
	 */
	private void addEventListeners() {

		AdminView.ordersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				handleOrderSelection(e);
			}
		});

		AdminView.allActiveDishesTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
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
		AdminView.allRegisteredExtrasTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {
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

		AdminView.searchDishTextBox.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				handleDishSearchTyping();
			}
		});

		AdminView.searchExtraTextBox.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
			}

			public void keyTyped(KeyEvent e) {
			}

			public void keyReleased(KeyEvent e) {
				handleExtraSearchTyping();
			}
		});

		AdminView.searchDishTextBox.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
			}

			public void focusGained(FocusEvent arg0) {
				handleDishSearchBoxSelection();
			}
		});

		AdminView.searchExtraTextBox.addFocusListener(new FocusListener() {
			public void focusLost(FocusEvent arg0) {
			}

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
	 * 
	 * @param e
	 *            the listSelectionEvent
	 */
	private void handleOrderSelection(ListSelectionEvent e) {
		int indexOfSelectedOrder = ((DefaultListSelectionModel) e.getSource()).getMinSelectionIndex();
		if (indexOfSelectedOrder < 0) {
			return;
		}
		Order order = adminGUI.currentOrderList.get(indexOfSelectedOrder);
		AdminView.orderReceiptLabel.setText(ReceiptGenerator.generateReceipt(order));
	}

	/**
	 * called when the user clics reset in the settings-window resets all the
	 * data in the textfields to their databasevalue
	 */
	private void handleSettingResetButtonClicked() {
		AdminView.settingsDeliveryPriceTextBox.setText(PriceCalculators.getDeliveryCost());
		AdminView.settingsEditNameOfRestaurantTextBox.setText(PriceCalculators.getRestaurantName());
		AdminView.settingsEditMinimumPriceFreeDeliveryTextBox.setText(PriceCalculators
				.getFreeDeliveryTreshold());
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

		int orePrice = PriceCalculators.priceInputVerifier(priceText);
		if (orePrice < 0) {
			return;
		}
		Setting priceSetting = new Setting(Config.KEY_DELIVERY_PRICE, "" + orePrice);

		if (nameText.length() < 3 || nameText.length() > 18) {
			GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_RESTAURANT_NAME_LENGTH);
			return;
		}
		Setting nameSetting = new Setting(Config.KEY_RESTAURANT_NAME, nameText);

		if (addressText.length() < 3) {
			GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_RESTAURANT_ADDRESS_LENGTH);
			return;
		}
		Setting addressSetting = new Setting(Config.KEY_RESTAURANT_ADDRESS,
				addressText);

		if (cityText.length() < 1) {
			GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_RESTAURANT_CITY_LENGTH);
			return;
		}
		Setting citySetting = new Setting(Config.KEY_RESTAURANT_CITY, cityText);

		int oreDeliveryTreshold = PriceCalculators.priceInputVerifier(delivertTresholdText);
		if (oreDeliveryTreshold < 0) {
			return;
		}
		Setting deliveryTresholdSetting = new Setting(Config.KEY_FREE_DELIVERY_LIMIT,
				"" + oreDeliveryTreshold);

		boolean somethingChanged = false;
		if (!priceText.equals(PriceCalculators.getDeliveryCost())) {
			Config.updateValueOfSetting(priceSetting);
			somethingChanged = true;
		}
		if (!nameText.equals(PriceCalculators.getRestaurantName())) {
			Config.updateValueOfSetting(nameSetting);
			somethingChanged = true;
		}
		if (!delivertTresholdText.equals(PriceCalculators.getFreeDeliveryTreshold())) {
			Config.updateValueOfSetting(deliveryTresholdSetting);
			somethingChanged = true;
		}
		if (!addressText.equals(PriceCalculators.getRestaurantAddress())) {
			Config.updateValueOfSetting(addressSetting);
			somethingChanged = true;
		}
		if (!cityText.equals(PriceCalculators.getRestaurantCity())) {
			Config.updateValueOfSetting(citySetting);
			somethingChanged = true;
		}

		if (!somethingChanged) {
			GUIConstants.showConfirmMessage(GUIMessages.NOTHING_TO_CHANGE);
		} else {
			GUIConstants.showConfirmMessage(GUIMessages.SETTINGS_CHANGED_SUCCESSFULLY);
		}
		PriceCalculators.getConstantsFromDataBase();
		handleSettingResetButtonClicked();
	}

	/**
	 * The method called when a user selects an extra from the extra-list
	 * 
	 * @param e
	 */
	private void handleExtraSelection(ListSelectionEvent e) {
		int selectedIndex = ((DefaultListSelectionModel) e.getSource()).getMinSelectionIndex();
		if (selectedIndex == -1) {
			return;
		}

		Extra selectedExtra = adminGUI.currentExtraList.get(selectedIndex);
		adminGUI.currentSelectedExtra = selectedExtra;

		// tekstbokser
		AdminView.editExtraExtraNameTextBox.setText(selectedExtra.name);
		AdminView.editExtraExtraNameTextBox.setEnabled(false);
		AdminView.editExtraExtraPriceTextArea.setText(PriceCalculators.getPriceForExtra(selectedExtra));

		char selectchar = selectedExtra.isActive ? GUIConstants.GUI_TRUE.charAt(0) : GUIConstants.GUI_FALSE
				.charAt(0);
		AdminView.editExtraExtraIsActiveComboBox.selectWithKeyChar(selectchar);
	}

	/**
	 * Selects all text in the dish search box when the user gives it focus
	 */
	protected void handleDishSearchBoxSelection() {
		AdminView.searchDishTextBox.setSelectionStart(0);
		AdminView.searchDishTextBox.setSelectionEnd(AdminView.searchDishTextBox.getText().length());
	}

	/**
	 * Selects all text in the extra search box when the user gives it focus
	 */
	protected void handleExtraSearchBoxSelection() {
		AdminView.searchExtraTextBox.setSelectionStart(0);
		AdminView.searchExtraTextBox.setSelectionEnd(AdminView.searchExtraTextBox.getText().length());
	}

	/**
	 * Searches for the contents of the dish search box if something has been written in it. It retrieves all dishes otherwise
	 */
	public void handleDishSearchTyping(){
		String query = AdminView.searchDishTextBox.getText();
		if (query.length() == 0) {
			showAllDishes();
		} else {
			searchDishes(query);
		}
	}

	/**
	 * Searches for the contents of the extra search box if something has been written in it. It retrieves all extras otherwise
	 */
	public void handleExtraSearchTyping(){
		String query = AdminView.searchExtraTextBox.getText();
		if (query.length() == 0) {
			this.showAllExtras();
		} else {
			this.searchExtras(query);
		}
	}

	/**
	 * Searches for extras in the database that match the query passed in the parameter
	 * @param query the query to search for
	 */
	private void searchExtras(String query) {
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_EXTRAS, query));
	}

	/**
	 * Searches for dishes in the database that match the query passed in the parameter
	 * @param query the query to search for
	 */
	private void searchDishes(String query) {
		this.dispatchEvent(new Event<String>(EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_DISHES, query));
	}

	/**
	 * Updates or creates a new extra, depending on whether the user was in the process or creating a new extra or editing an existing one
	 */
	private void handleExtraConfirmButtonClick() {
		boolean active = AdminView.editExtraExtraIsActiveComboBox.getSelectedItem().equals(
				GUIConstants.GUI_TRUE) ? true : false;
		String name = DataCleaner.cleanDbData(AdminView.editExtraExtraNameTextBox.getText());
		String price = AdminView.editExtraExtraPriceTextArea.getText().replaceAll(" ", "");

		char priceFunc = price.charAt(0);
		String priceVal = price.substring(1);

		if (name.isEmpty()) {
			GUIConstants.showErrorMessage(GUIMessages.NAME_FIELD_CANNOT_BE_EMPTY);
			return;
		}

		if (!(priceFunc == '+' || priceFunc == '*' || priceFunc == '-')) {
			GUIConstants
					.showErrorMessage(GUIMessages.ILLEGAL_EXTRA_OPERAND);
			return;
		}

		int orepris = PriceCalculators.priceInputVerifier(priceVal);
		if (orepris < 0) {
			return;
		}

		if (adminGUI.currentSelectedExtra == null) {
			Extra e = new Extra(-1, name, priceFunc + "" + orepris, active);
			this.dispatchEvent(new Event<Extra>(EventType.DATABASE_ADD_NEW_EXTRA, e));
		} else {
			Extra e = new Extra(adminGUI.currentSelectedExtra.id, name, priceFunc + "" + orepris, active);
			this.dispatchEvent(new Event<Extra>(EventType.DATABASE_UPDATE_EXTRA_BY_EXTRA_ID, e));
		}
		// oppdaterer
		showAllExtras();
	}

	/**
	 * Updates or creates a dish in the database, depending on whether the user has been creating a new one or editing an existing one
	 */
	private void handleDishConfirmButtonClick() {
		boolean diary = AdminView.editDishContainsDairyComboBox.getSelectedItem().equals(
				GUIConstants.GUI_TRUE) ? true : false;
		boolean nuts = AdminView.editDIshContainsNutsComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true
				: false;
		boolean gluten = AdminView.editDIshContainsGlutenComboBox.getSelectedItem().equals(
				GUIConstants.GUI_TRUE) ? true : false;
		boolean spicy = AdminView.editDishIsPsicyComboBox.getSelectedItem().equals(GUIConstants.GUI_TRUE) ? true
				: false;
		boolean active = AdminView.editDishIsDishActiveComboBox.getSelectedItem().equals(
				GUIConstants.GUI_TRUE) ? true : false;
		boolean vegan = AdminView.editDishIsVegetarianComboBox.getSelectedItem()
				.equals(GUIConstants.GUI_TRUE) ? true : false;

		String name = DataCleaner.cleanDbData(AdminView.editDishNameTextBox.getText());
		String description = DataCleaner.cleanDbData(AdminView.editDishDescriptionTextArea.getText());
		String price = AdminView.editDishDishPriceTextArea.getText().trim();
		int orePrice = 0;

		if (description.isEmpty()) {
			GUIConstants.showErrorMessage(GUIMessages.DISH_DESCRIPTION_CANNOT_BE_EMPTY);
			return;
		}

		if (name.isEmpty()) {
			GUIConstants.showErrorMessage(GUIMessages.NAME_FIELD_CANNOT_BE_EMPTY);
			return;
		}

		orePrice = PriceCalculators.priceInputVerifier(price);
		if (orePrice < 0) {
			return;
		}

		if (adminGUI.currentSelectedDish == null) {
			Dish d = new Dish(-1, orePrice, name, gluten, nuts, diary, vegan, spicy, description, active);
			this.dispatchEvent(new Event<Dish>(EventType.DATABASE_ADD_NEW_DISH, d));
		} else {
			Dish d = new Dish(adminGUI.currentSelectedDish.dishID, orePrice, name, gluten, nuts, diary,
					vegan, spicy, description, active);
			this.dispatchEvent(new Event<Dish>(EventType.DATABASE_UPDATE_DISH_BY_DISH_ID, d));
		}

		// update lists after update
		showAllDishes();
	}

	/**
	 * Sets up the UI for creating a new extra
	 */
	private void handleNewExtraButtonClick() {
		AdminView.allRegisteredExtrasTable.getSelectionModel().clearSelection();
		adminGUI.currentSelectedExtra = null;

		// tekstbokser
		AdminView.editExtraExtraNameTextBox.setText("Navn på tilbehør");
		AdminView.editExtraExtraNameTextBox.setEnabled(true);
		AdminView.editExtraExtraPriceTextArea.setText("+49,90");

		AdminView.editExtraExtraIsActiveComboBox.selectWithKeyChar(GUIConstants.GUI_TRUE.charAt(0));
	}

	/**
	 * Sets up the UI for creating a new dish
	 */
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

	/**
	 * Shows all dishes in the dish overview table
	 */
	private void showAllDishes() {
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_DISHES));
	}

	/**
	 * Shows all extras in the extra overview table
	 */
	private void showAllExtras() {
		this.dispatchEvent(new Event<Object>(EventType.DATABASE_UPDATE_ADMINGUI_GUI_SEND_ALL_EXTRAS));
	}

	/**
	 * handles the event of the user selecting a dish in the dish overview table. Sets up the dish editing UI so that the dish can be edited
	 * @param e The listSelectionEvent dispatched by the java AWT thread
	 */
	private void handleDishSelection(ListSelectionEvent e) {
		int selectedIndex = ((DefaultListSelectionModel) e.getSource()).getMinSelectionIndex();
		if (selectedIndex == -1) {
			return;
		}
		Dish selectedDish = adminGUI.currentDishList.get(selectedIndex);
		adminGUI.currentSelectedDish = selectedDish;
		// tekstbokser
		AdminView.editDishNameTextBox.setText(selectedDish.name);
		AdminView.editDishNameTextBox.setEnabled(false);
		AdminView.editDishDescriptionTextArea.setText(selectedDish.description);
		AdminView.editDishDishPriceTextArea.setText("" + PriceCalculators.getPriceForDish(selectedDish));

		// her resetter vi alle valgboksene til sin defaultverdi
		char trueChar = GUIConstants.GUI_TRUE.charAt(0);
		char falseChar = GUIConstants.GUI_FALSE.charAt(0);

		char selectDiaryYN = selectedDish.containsDairy ? trueChar : falseChar;
		AdminView.editDishContainsDairyComboBox.selectWithKeyChar(selectDiaryYN);

		char selectGlutenYN = selectedDish.containsGluten ? trueChar : falseChar;
		AdminView.editDIshContainsGlutenComboBox.selectWithKeyChar(selectGlutenYN);

		char selectNutsYN = selectedDish.containsNuts ? trueChar : falseChar;
		AdminView.editDIshContainsNutsComboBox.selectWithKeyChar(selectNutsYN);

		char selectSpicyYN = selectedDish.isSpicy ? trueChar : falseChar;
		AdminView.editDishIsPsicyComboBox.selectWithKeyChar(selectSpicyYN);

		char selectVeganYN = selectedDish.isVegetarian ? trueChar : falseChar;
		AdminView.editDishIsVegetarianComboBox.selectWithKeyChar(selectVeganYN);

		char selectActiveYN = selectedDish.isActive ? trueChar : falseChar;
		AdminView.editDishIsDishActiveComboBox.selectWithKeyChar(selectActiveYN);
	}

	/**
	 * An unused, required method
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}// END
