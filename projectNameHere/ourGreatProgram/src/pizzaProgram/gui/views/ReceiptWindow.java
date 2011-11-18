package pizzaProgram.gui.views;

import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pizzaProgram.core.Constants;
import pizzaProgram.utils.PrintUtilities;

@SuppressWarnings("serial")
public class ReceiptWindow extends JFrame{

	private JLabel html;

	/**
	 * This class is a window, the window displays a receipt(o0r whatever string it is given as input)
	 * The rows parameter is how many rows of text the recipt contains, it is used for calculating height, but 
	 * will only work properly for html-tables
	 * @param kvittering
	 * @param rows
	 */
	public ReceiptWindow(String kvittering, int rows) {
		this.setLayout(null);
		this.setLocation(100, 100);
		this.setResizable(false);
		this.setTitle("Kvittering");

		html = new JLabel(kvittering, JLabel.LEFT);
		html.setBounds(0, 5, Constants.RECIPT_WIDTH, rows * Constants.RECIPT_ROW_HEIGHT);
		html.setVerticalAlignment(JLabel.TOP);
		html.setHorizontalTextPosition(JLabel.LEFT);
		html.setVerticalTextPosition(JLabel.TOP);

		this.add(html);
		this.setSize(html.getWidth() + 10, html.getHeight() + 40);//40 is the height of the framedecoration 10 is the width
		this.setVisible(true);

		
		//PrintUtilities pu = new PrintUtilities(html);
		//pu.print();
		
	}

}//END
