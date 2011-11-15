package pizzaProgram.gui.views;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class ReceiptWindow extends JFrame{

	private JLabel html;
	
	public ReceiptWindow(String kvittering) {
		this.setLayout(null);
		this.setMinimumSize(new Dimension(300, 400));
		this.setLocation(100, 100);
		this.setResizable(false);
		this.setTitle("Kvittering");
		html = new JLabel();
		html.setText(kvittering);
		html.setBounds(0, 0, 260, 400);
		add(html);
		setVisible(true);
	}
	
}//END
