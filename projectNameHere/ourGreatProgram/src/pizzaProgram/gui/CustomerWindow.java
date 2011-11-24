package pizzaProgram.gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.constants.GUIMessages;
import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.UnaddedCustomer;
import pizzaProgram.database.databaseUtils.DataCleaner;

/**
 * Class controlling the GUI element used to add new customers.
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
@SuppressWarnings("serial")
public class CustomerWindow extends JFrame {

	private TextField fillFirstName;
	private TextField fillLastName;
	private TextField fillAddress;
	private TextField fillPostalCode;
	private TextField fillCity;
	private TextField fillPhoneNumber;
	/**
	 * Use as parameter in constructor if you wish to add a new customer.
	 */
	public static final String NEW_CUSTOMER = "new";
	/**
	 * Use as parameter in constructor if you wish to edit a customer
	 */
	public static final String UPDATE_CUSTOMER = "update";

	private OrderGUI source;
	private String type;
	private Customer updateCustomer;

	/**
	 * Creates a new CustomerWindow intsance
	 * 
	 * @param parent
	 *            the OrderGUI element that instantiated this class
	 * @param typeof
	 *            Wether this window is for adding or editing a customer, as
	 *            specified in the NEW_CUSTOMER and UPDATE_CUSTOMER constants
	 * @param c
	 *            The Customer this window is modifying. If adding a new
	 *            customer, this parameter should be null
	 */
	public CustomerWindow(OrderGUI parent, String typeof, Customer c) {
		this.source = parent;
		this.type = typeof;
		this.updateCustomer = c;
		this.setLayout(new GridBagLayout());
		this.setMinimumSize(new Dimension(220, 200));
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		Label fornavn = new Label("Fornavn:");
		add(fornavn, createConstraints(0, 0, 0.2));

		Label etternavn = new Label("Etternavn:");
		add(etternavn, createConstraints(0, 1, 0.2));

		Label adresse = new Label("Adresse:");
		add(adresse, createConstraints(0, 2, 0.2));

		Label postnr = new Label("Postnr:");
		add(postnr, createConstraints(0, 3, 0.2));

		Label poststed = new Label("Poststed:");
		add(poststed, createConstraints(0, 4, 0.2));

		Label telefon = new Label("Telefon:");
		add(telefon, createConstraints(0, 5, 0.2));

		fillFirstName = new TextField();
		add(fillFirstName, createConstraints(1, 0, 0.8));
		if (type.equals(CustomerWindow.UPDATE_CUSTOMER)) {
			fillFirstName.setText(c.firstName);
			fillFirstName.setEnabled(false);
			fillFirstName.setEditable(false);
		}

		fillLastName = new TextField();
		add(fillLastName, createConstraints(1, 1, 0.8));
		if (type.equals(CustomerWindow.UPDATE_CUSTOMER)) {
			fillLastName.setText(c.lastName);
			fillLastName.setEnabled(false);
			fillLastName.setEditable(false);
		}

		fillAddress = new TextField();
		add(fillAddress, createConstraints(1, 2, 0.8));
		if (type.equals(CustomerWindow.UPDATE_CUSTOMER)) {
			fillAddress.setText(c.address);
		}

		fillPostalCode = new TextField();
		add(fillPostalCode, createConstraints(1, 3, 0.8));
		if (type.equals(CustomerWindow.UPDATE_CUSTOMER)) {
			fillPostalCode.setText("" + c.postalCode);
		}

		fillCity = new TextField();
		add(fillCity, createConstraints(1, 4, 0.8));
		if (type.equals(CustomerWindow.UPDATE_CUSTOMER)) {
			fillCity.setText(c.city);
		}

		fillPhoneNumber = new TextField();
		add(fillPhoneNumber, createConstraints(1, 5, 0.8));
		if (type.equals(CustomerWindow.UPDATE_CUSTOMER)) {
			fillPhoneNumber.setText("" + c.phoneNumber);
		}

		Label fill = new Label("");
		add(fill, createConstraints(0, 5, 0.2));

		JButton finishKnapp = new JButton("Ok");
		finishKnapp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String postNumber;
				int phoneNumber = 0;
				String firstName = DataCleaner.cleanDbData(fillFirstName.getText());
				String lastName = DataCleaner.cleanDbData(fillLastName.getText());
				String address = DataCleaner.cleanDbData(fillAddress.getText());
				String city = DataCleaner.cleanDbData(fillCity.getText());
				postNumber = DataCleaner.cleanDbData(fillPostalCode.getText());

				try {
					if (fillPostalCode.getText().length() != 4) {
						throw new NumberFormatException();
					}
					Integer.parseInt(fillPostalCode.getText());
				} catch (NumberFormatException ex) {
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_POSTAL_CODE);
					return;
				}

				try {
					phoneNumber = Integer.parseInt(fillPhoneNumber.getText());
				} catch (NumberFormatException ex) {
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_PHONE_NUMBER);
					return;
				}

				if (!type.equals(CustomerWindow.UPDATE_CUSTOMER) && firstName.isEmpty() && firstName.length() < 50) {
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_FIRST_NAME);
					return;
				}
				if (!type.equals(CustomerWindow.UPDATE_CUSTOMER) && lastName.isEmpty() && lastName.length() < 50) {
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_LAST_NAME);
					return;
				}
				if (address.isEmpty() && address.length() < 50) {
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_ADDRESS);
					return;
				}
				if (city.isEmpty() && city.length() < 50) {
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_CITY);
					return;
				}

				if (type.equals(UPDATE_CUSTOMER)) {
					Customer customer = new Customer(updateCustomer.customerID, updateCustomer.firstName,
							updateCustomer.lastName, address, postNumber, city, phoneNumber);
					source.updateCustomer(customer);
				} else if (type.equals(NEW_CUSTOMER)) {
					UnaddedCustomer customer = new UnaddedCustomer(firstName, lastName, address, postNumber,
							city, phoneNumber);
					source.createNewCustomer(customer);
				}

				dispose();
			}
		});
		add(finishKnapp, createConstraints(1, 6, 0.8));

		setVisible(true);
	}

	/**
	 * A quick and pretty way to create a GridBagConstraints object that
	 * specifies the location and size of a component in a GridBagLayout
	 * 
	 * @param x
	 *            The x-coordinate of the new Constraints object (table
	 *            coordinate, not pixels)
	 * @param y
	 *            The y-coordinate of the new Constraints object (table
	 *            coordinate, not pixels)
	 * @param widthpercent
	 *            The x-weight of the component. You can look at this as a
	 *            decimal number from 0-1 representing the width of the
	 *            component in percent
	 * @return A GridBagConstraints object encapsulating the specified values
	 */
	private GridBagConstraints createConstraints(int x, int y, double widthpercent) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = widthpercent;
		gbc.weighty = 0.1;
		return gbc;
	}
}