package pizzaProgram.database.databaseUtils;

import javax.swing.JOptionPane;

public class DatabaseResultsFeedbackProvider {
	private static final String DEFAULT_MESSAGE_TITLE = "Info";
	private static final String DEFAULT_ERROR_TITLE = "Error";
	
	public static void showGetAllCustomersFailedMessage() {
		JOptionPane.showMessageDialog(null, "Failed to retrieve list of customers.", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showSearchCustomersFailedMessage() {
		JOptionPane.showMessageDialog(null, "Failed to search for customers.", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showGetAllDishesFailedMessage() {
		JOptionPane.showMessageDialog(null, "Failed to get a list of all available dishes.", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showGetAllExtrasFailedMessage() {
		JOptionPane.showMessageDialog(null, "Failed to get a list of all available extras.", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showGetAllUncookedOrdersFailedMessage() {
		JOptionPane.showMessageDialog(null, "Failed to retrieve a list of currently uncooked orders.", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showAddNewCustomerFailedMessage() {
		JOptionPane.showMessageDialog(null, "Failed to register the new customer.", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showAddNewOrderFailedMessage() {
		JOptionPane.showMessageDialog(null, "Failed to register the new order.", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showAddNewCustomerSuccessMessage() {
		JOptionPane.showMessageDialog(null, "Customer has been registered successfully.", DEFAULT_MESSAGE_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showAddNewOrderSuccessMessage() {
		JOptionPane.showMessageDialog(null, "Order has been registered successfully.", DEFAULT_MESSAGE_TITLE, JOptionPane.INFORMATION_MESSAGE);
	}

	public static void showUpdateOrderStatusFailedMessage() {
		JOptionPane.showMessageDialog(null, "Someone else has already marked that order with a new status", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showSearchOrdersFailedMessage() {
		JOptionPane.showMessageDialog(null, "Failed to search for orders.", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showAddExtraWithoutDishFailedMessage() {
		JOptionPane.showMessageDialog(null, "You need to select a dish to add an extra!", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

	public static void showGetAllUndeliveredOrdersFailedMessage() {
		JOptionPane.showMessageDialog(null, "Failed to retrieve list of orders.", DEFAULT_ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
	}

}
