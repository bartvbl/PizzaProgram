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
import javax.swing.JOptionPane;

import pizzaProgram.dataObjects.UnaddedCustomer;
import pizzaProgram.database.databaseUtils.DataCleaner;

@SuppressWarnings("serial")
public class NewCustomerWindow extends JFrame{

	private OrderGUI parent;
	private TextField fyllnavn;
	private TextField fylletternavn;
	private TextField fylladresse;
	private TextField fyllpostnr;
	private TextField fyllpoststed;
	private TextField fylltlf;
	private JFrame denne;

	public NewCustomerWindow(OrderGUI parentWindow) {
		parent = parentWindow;
		denne = this;
		this.setLayout(new GridBagLayout());
		this.setMinimumSize(new Dimension(220, 200));
		this.setLocation(100, 100);
		this.setResizable(false);
		this.setTitle("New customer");

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

		fylletternavn = new TextField();
		add(fylletternavn, createConstraints(1, 1, 0.8));

		fylladresse = new TextField();
		add(fylladresse, createConstraints(1, 2, 0.8));

		fyllpostnr = new TextField();
		add(fyllpostnr, createConstraints(1, 3, 0.8));

		fyllpoststed = new TextField();
		add(fyllpoststed, createConstraints(1, 4, 0.8));

		fylltlf = new TextField();
		add(fylltlf, createConstraints(1, 5, 0.8));

		Label fill = new Label("");
		add(fill, createConstraints(0, 5, 0.2));

		JButton finishKnapp = new JButton("Ok");
		finishKnapp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int postNumber = 0;
				int phoneNumber = 0;
				String firstName = DataCleaner.cleanDbData(fyllnavn.getText());
				String lastName = DataCleaner.cleanDbData(fylletternavn.getText());
				String address = DataCleaner.cleanDbData(fylladresse.getText());
				String city = DataCleaner.cleanDbData(fyllpoststed.getText());

				try{
					postNumber = Integer.parseInt(fyllpostnr.getText());
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(denne, "Postnummer må være et tall!", "Feil", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try{
					phoneNumber = Integer.parseInt(fylltlf.getText());
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(denne, "Telefonnummer må være et tall!", "Feil", JOptionPane.ERROR_MESSAGE);
					return;
				}

				if(firstName.isEmpty()){
					JOptionPane.showMessageDialog(denne, "Fornavn kan ikke være tomt!", "Feil", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(lastName.isEmpty()){
					JOptionPane.showMessageDialog(denne, "Etternavn kan ikke være tomt!", "Feil", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(address.isEmpty()){
					JOptionPane.showMessageDialog(denne, "Adressen kan ikke være tomt!", "Feil", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(city.isEmpty()){
					JOptionPane.showMessageDialog(denne, "By kan ikke være tomt!", "Feil", JOptionPane.ERROR_MESSAGE);
					return;
				}


				UnaddedCustomer customer = new UnaddedCustomer(firstName, lastName, address, postNumber, city, phoneNumber, "");
				parent.createNewCustomer(customer);
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

}
