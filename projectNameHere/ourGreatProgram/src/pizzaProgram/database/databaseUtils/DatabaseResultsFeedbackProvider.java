package pizzaProgram.database.databaseUtils;

import javax.swing.JOptionPane;
/**
 * A class that can display error and information messages to provide feedback to the user in a number of situations
 * @author Bart
 *
 */
public class DatabaseResultsFeedbackProvider {
	/**
	 * The default window header of an info message
	 */
	private static final String DEFAULT_MESSAGE_TITLE = "Info";
	/**
	 * The default window header of an error message
	 */
	private static final String DEFAULT_ERROR_TITLE = "Error";
	/**
	 * Shows an error message with the default error header, and the message entered as a parameter
	 * @param message The message of the error message
	 */
	private static void showErrorMessage(String message)
	{
		JOptionPane.showMessageDialog(null, message, DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}
	/**
	 * Shows an information message with the default message title
	 * @param message The message to be displayed
	 */
	private static void showInformationMessage(String message) {
		JOptionPane.showMessageDialog(null, message, DEFAULT_MESSAGE_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void showGetAllCustomersFailedMessage() {
		showErrorMessage("Failed to retrieve list of customers.");
	}

	public static void showSearchCustomersFailedMessage() {
		showErrorMessage("Failed to search for customers.");
	}

	public static void showGetAllDishesFailedMessage() {
		showErrorMessage("Failed to get a list of all available dishes.");
	}

	public static void showGetAllExtrasFailedMessage() {
		showErrorMessage("Failed to get a list of all available extras.");
	}

	public static void showGetAllUncookedOrdersFailedMessage() {
		showErrorMessage("Failed to retrieve a list of currently uncooked orders.");
	}

	public static void showAddNewCustomerFailedMessage() {
		showErrorMessage("Failed to register the new customer.");
	}

	public static void showAddNewOrderFailedMessage() {
		showErrorMessage("Failed to register the new order.");
	}

	public static void showAddNewCustomerSuccessMessage() {
		showInformationMessage("Customer has been registered successfully.");
	}

	public static void showAddNewOrderSuccessMessage() {
		showInformationMessage("Order has been registered successfully.");
	}

	public static void showUpdateOrderStatusFailedMessage() {
		showErrorMessage("Someone else has already marked that order with a new status");
	}

	public static void showSearchOrdersFailedMessage() {
		showErrorMessage("Failed to search for orders.");
	}

	public static void showAddExtraWithoutDishFailedMessage() {
		showErrorMessage("You need to select a dish to add an extra!");
	}

	public static void showGetAllUndeliveredOrdersFailedMessage() {
		showErrorMessage("Failed to retrieve list of orders.");
	}

	public static void showUpdateOrderStatusFailedInvalidDeliveryMethodMessage() {
		showErrorMessage("You can not mark an order as being delivered when that order is set to be picked up at the restaurant!");
	}

	public static void showAddNewExtraFailedMessage() {
		showErrorMessage("Failed to create a new extra.");
	}

	public static void showAddNewExtraSucceededMessage() {
		showInformationMessage("New extra has been added successfully.");
	}

	public static void showAddNewDishFailedMessage() {
		showErrorMessage("Failed to create a new dish.");
	}

	public static void showAddNewDishSucceededMessage() {
		showInformationMessage("New dish has been added successfully.");
	}

	public static void showUpdateConfigValueFailedMessage() {
		showErrorMessage("Failed to update config value.");
	}

	public static void showUpdateExtraFailedMessage() {
		showErrorMessage("Failed to update extra.");
	}

	public static void showUpdateDishFailedMessage() {
		showErrorMessage("Failed to update dish.");
	}
	
	public static void showEditCustomerFailedNoCustomerSelectedMessage()
	{
		showErrorMessage("You must select a customer before you can edit it.");
	}

	public static void showUpdateCustomerFailedMessage() {
		showErrorMessage("Failed to update customer.");
	}

	public static void showUpdateCustomerSuccessMessage() {
		showInformationMessage("Customer updated successfully.");
	}
	
	public static void showSearchDishesFailedMessage() {
		showErrorMessage("Failed to search for dishes");
	}
	
	public static void showSearchExtrasFailedMessage() {
		showErrorMessage("Failed to search for extras.");
	}

	public static void showGetAllSettingsFailedMessage() {
		showErrorMessage("Failed to fetch all settings.");
	}

	public static void showSettingFailedMessage() {
		showErrorMessage("Failed to retrieve value of setting.");
	}
	public static void showDeleteCustomerFailedMessage() {
		showErrorMessage("Failed to delete customer!");
	}
}
