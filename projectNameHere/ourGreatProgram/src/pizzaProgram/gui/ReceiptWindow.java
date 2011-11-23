package pizzaProgram.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.utils.PrintUtilities;

/**
 * The receipt window shows a new window containing the receipt of an order
 * 
 * @author IT1901 Group 3, Fall 2011
 * 
 */
@SuppressWarnings("serial")
public class ReceiptWindow extends JFrame {

	/**
	 * A JLabel holding the receipt's text
	 */
	private JLabel html;
	/**
	 * The button the user can use to print out the receipt
	 */
	private JButton printButton;

	/**
	 * This class is a window, the window displays a receipt(or whatever string
	 * it is given as input) The rows parameter is how many rows of text the
	 * recipt contains, it is used for calculating height, but will only work
	 * properly for html-tables
	 * 
	 * @param kvittering
	 * @param rows
	 */
	public ReceiptWindow(CharSequence kvittering, int rows) {
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Kvittering");

		html = new JLabel(kvittering.toString(), JLabel.LEFT);
		html.setBounds(2, 2, GUIConstants.RECIPT_WIDTH, GUIConstants.RECIPT_ROW_HEIGHT * rows);
		html.setVerticalAlignment(JLabel.TOP);
		html.setHorizontalTextPosition(JLabel.LEFT);
		html.setVerticalTextPosition(JLabel.TOP);

		JScrollPane jsp = new JScrollPane(html);
		jsp.setBounds(0, 0, html.getWidth() + 30, Math.min(400, html.getY() + html.getHeight()));

		printButton = new JButton("Skriv ut");
		printButton.setBounds(5, jsp.getY() + jsp.getHeight() + 5, 80, 22);
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skrivUt();
			}
		});

		add(jsp);
		add(printButton);
		// 35 is the height of the framedecoration 6 is the width
		setSize(jsp.getWidth() + 6, printButton.getY() + printButton.getHeight() + 35);

		setVisible(true);
	}

	/**
	 * Prints out the receipt
	 */
	private void skrivUt() {
		PrintUtilities.printComponent(html);
	}
}