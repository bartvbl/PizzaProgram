package pizzaProgram.gui.views;

import javax.swing.JFrame;
import javax.swing.JLabel;

import pizzaProgram.core.Constants;

@SuppressWarnings("serial")
public class ReceiptWindow extends JFrame{

	private JLabel html;

	public ReceiptWindow(String kvittering, int rows) {
		this.setLayout(null);
		this.setLocation(100, 100);
		this.setResizable(false);
		this.setTitle("Kvittering");
		html = new JLabel(kvittering, JLabel.LEFT);
		html.setBounds(0, 5, Constants.RECIPT_WIDTH, rows * Constants.RECIPT_ROW_HEIGHT);
		this.setSize(html.getWidth() + 10, html.getHeight() + 40);//40 is the height of the framedecoration 10 is the width
		html.setVerticalAlignment(JLabel.TOP);
		html.setHorizontalTextPosition(JLabel.LEFT);
		html.setVerticalTextPosition(JLabel.TOP);
		add(html);
		setVisible(true);
	}

}//END
