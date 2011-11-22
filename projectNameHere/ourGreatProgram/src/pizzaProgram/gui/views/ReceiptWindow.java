package pizzaProgram.gui.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pizzaProgram.constants.GUIConstants;
import pizzaProgram.utils.PrintUtilities;

/**
 * The receipt window shows a new window containing the receipt of an order
 * @author Bart
 *
 */
@SuppressWarnings("serial")
public class ReceiptWindow extends JFrame{

	/**
	 * A JLabel holding the receipt's text
	 */
	private JLabel html;
	/**
	 * The button the user can use to print out the receipt
	 */
	private JButton printButton;
	/**
	 * This class is a window, the window displays a receipt(o0r whatever string it is given as input)
	 * The rows parameter is how many rows of text the recipt contains, it is used for calculating height, but 
	 * will only work properly for html-tables
	 * @param kvittering
	 * @param rows
	 */
	public ReceiptWindow(String kvittering, int rows) {
		setLayout(null);
		setLocationRelativeTo(null);
		setResizable(false);
		setTitle("Kvittering");

		html = new JLabel(kvittering, JLabel.LEFT);
		html.setBounds(0, 5, GUIConstants.RECIPT_WIDTH, rows * GUIConstants.RECIPT_ROW_HEIGHT);
		html.setVerticalAlignment(JLabel.TOP);
		html.setHorizontalTextPosition(JLabel.LEFT);
		html.setVerticalTextPosition(JLabel.TOP);

		printButton = new JButton("Skriv ut");
		printButton.setBounds(0, html.getY()+html.getHeight() +5, 80, 22);
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				skrivUt();
			}
		});
		
		add(html);
		add(printButton);
		setSize(html.getWidth() + 10, printButton.getY() + printButton.getHeight() + 40);//40 is the height of the framedecoration 10 is the width
		setVisible(true);
	}
	
	/**
	 * Prints out the receipt
	 */
	private void skrivUt() {
		PrintUtilities.printComponent(html);
	}

}//END
