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


@SuppressWarnings("serial")
public class NewCustomerWindow extends JFrame{

	private TextField fyllnavn;
	private TextField fylletternavn;
	private TextField fylladresse;
	private TextField fyllpostnr;
	private TextField fyllpoststed;
	private TextField fylltlf;

	public static final String NEW_CUSTOMER = "new";
	public static final String UPDATE_CUSTOMER = "update";
	
	private OrderGUI source;
	private String type;
	private Customer updateCustomer;
	
	public NewCustomerWindow(OrderGUI parent, String typeof, Customer c) {
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

		fyllnavn = new TextField();
		add(fyllnavn, createConstraints(1, 0, 0.8));
		if(type.equals(NewCustomerWindow.UPDATE_CUSTOMER)){
			fyllnavn.setText(c.firstName);
			fyllnavn.setEnabled(false);
			fyllnavn.setEditable(false);
		}

		fylletternavn = new TextField();
		add(fylletternavn, createConstraints(1, 1, 0.8));
		if(type.equals(NewCustomerWindow.UPDATE_CUSTOMER)){
			fylletternavn.setText(c.lastName);
			fylletternavn.setEnabled(false);
			fylletternavn.setEditable(false);
		}

		fylladresse = new TextField();
		add(fylladresse, createConstraints(1, 2, 0.8));
		if(type.equals(NewCustomerWindow.UPDATE_CUSTOMER)){
			fylladresse.setText(c.address);
		}

		fyllpostnr = new TextField();
		add(fyllpostnr, createConstraints(1, 3, 0.8));
		if(type.equals(NewCustomerWindow.UPDATE_CUSTOMER)){
			fyllpostnr.setText(""+c.postalCode);
		}

		fyllpoststed = new TextField();
		add(fyllpoststed, createConstraints(1, 4, 0.8));
		if(type.equals(NewCustomerWindow.UPDATE_CUSTOMER)){
			fyllpoststed.setText(c.city);
		}

		fylltlf = new TextField();
		add(fylltlf, createConstraints(1, 5, 0.8));
		if(type.equals(NewCustomerWindow.UPDATE_CUSTOMER)){
			fylltlf.setText(""+c.phoneNumber);
		}

		Label fill = new Label("");
		add(fill, createConstraints(0, 5, 0.2));

		JButton finishKnapp = new JButton("Ok");
		finishKnapp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				String postNumber;
				int phoneNumber = 0;
				String firstName = DataCleaner.cleanDbData(fyllnavn.getText());
				String lastName = DataCleaner.cleanDbData(fylletternavn.getText());
				String address = DataCleaner.cleanDbData(fylladresse.getText());
				String city = DataCleaner.cleanDbData(fyllpoststed.getText());
				postNumber = DataCleaner.cleanDbData(fyllpostnr.getText());

				try{
					if(fyllpostnr.getText().length() != 4){
						throw new NumberFormatException();
					}
					Integer.parseInt(fyllpostnr.getText());
				}catch(NumberFormatException ex){
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_POSTAL_CODE);
					return;
				}

				try{
					phoneNumber = Integer.parseInt(fylltlf.getText());
				}catch(NumberFormatException ex){
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_PHONE_NUMBER);
					return;
				}

				if(!type.equals(NewCustomerWindow.UPDATE_CUSTOMER) && firstName.isEmpty()){
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_FIRST_NAME);
					return;
				}
				if(!type.equals(NewCustomerWindow.UPDATE_CUSTOMER) && lastName.isEmpty()){
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_LAST_NAME);
					return;
				}
				if(address.isEmpty()){
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_ADDRESS);
					return;
				}
				if(city.isEmpty()){
					GUIConstants.showErrorMessage(GUIMessages.ILLEGAL_CITY);
					return;
				}
	
				if(type.equals(UPDATE_CUSTOMER)){
					Customer customer = new Customer(updateCustomer.customerID, updateCustomer.firstName, updateCustomer.lastName, address, postNumber, city, phoneNumber);
					source.updateCustomer(customer);
				}else if(type.equals(NEW_CUSTOMER)){
					UnaddedCustomer customer = new UnaddedCustomer(firstName, lastName, address, postNumber, city, phoneNumber);
					source.createNewCustomer(customer);
				}
					
				dispose();
			}
		});
		add(finishKnapp, createConstraints(1, 6, 0.8));

		setVisible(true);
	}

	private GridBagConstraints createConstraints(int x, int y, double widthpercent){
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

}//END
