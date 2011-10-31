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

@SuppressWarnings("serial")
public class NewCustomerWindow extends JFrame{

	OrderGUI parent;
	
	TextField fyllnavn;
	
	TextField fylletternavn;
	
	TextField fylladresse;
	
	TextField fyllpostnr;
	
	TextField fyllpoststed;
	
	TextField fylltlf;
	
	
	JFrame denne;
	public NewCustomerWindow(OrderGUI parentWindow) {
		parent = parentWindow;
		denne = this;
		this.setLayout(new GridBagLayout());
		this.setMinimumSize(new Dimension(220, 200));
		this.setLocation(100, 100);
		this.setResizable(false);
		this.setTitle("New customer");
		
		Label fornavn = new Label("Fornavn:");
		add(fornavn, createConstraints02(0, 0));
		
		Label etternavn = new Label("Etternavn:");
		add(etternavn, createConstraints02(0, 1));
		
		Label adresse = new Label("Adresse:");
		add(adresse, createConstraints02(0, 2));
		
		Label postnr = new Label("Postnr:");
		add(postnr, createConstraints02(0, 3));
		
		Label poststed = new Label("Poststed:");
		add(poststed, createConstraints02(0, 4));
		
		Label telefon = new Label("Telefon:");
		add(telefon, createConstraints02(0, 5));
		
		fyllnavn = new TextField();
		add(fyllnavn, createConstraints08(1, 0));
		
		fylletternavn = new TextField();
		add(fylletternavn, createConstraints08(1, 1));
		
		fylladresse = new TextField();
		add(fylladresse, createConstraints08(1, 2));
		
		fyllpostnr = new TextField();
		add(fyllpostnr, createConstraints08(1, 3));
		
		fyllpoststed = new TextField();
		add(fyllpoststed, createConstraints08(1, 4));
		
		fylltlf = new TextField();
		add(fylltlf, createConstraints08(1, 5));
		
		Label fill = new Label("");
		add(fill, createConstraints02(0, 5));
		
		JButton finishKnapp = new JButton("Ok");
		finishKnapp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int postNumber = 0;
				int phoneNumber = 0;
				try{
					postNumber = Integer.parseInt(fyllpostnr.getText());
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(denne, "Postnummer må være et tall", "Feil", JOptionPane.ERROR_MESSAGE);
					return;
				}
				try{
					phoneNumber = Integer.parseInt(fylltlf.getText());
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(denne, "Telefonnummer må være et tall", "Feil", JOptionPane.ERROR_MESSAGE);
					return;
				}
				UnaddedCustomer customer = generateCustomerObject(postNumber, phoneNumber);
				parent.createNewCustomer(customer);
				dispose();
			}
		});
		add(finishKnapp, createConstraints08(1, 6));
		
		setVisible(true);
		
	}
	
	private UnaddedCustomer generateCustomerObject(int postNumber, int phoneNumber)
	{
		
		String firstName = fyllnavn.getText();
		String lastName = fylletternavn.getText();
		String address = fylladresse.getText();
		String city = fyllpoststed.getText();
		UnaddedCustomer customer = new UnaddedCustomer(firstName, lastName, address, postNumber, city, phoneNumber, "");
		return customer;
	}
	
	private GridBagConstraints createConstraints08(int x, int y){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.8;
		gbc.weighty = 0.1;
		return gbc;
	}
	private GridBagConstraints createConstraints02(int x, int y){
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.2;
		gbc.weighty = 0.1;
		return gbc;
	}
	
}
