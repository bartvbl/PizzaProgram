package pizzaProgram.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import pizzaProgram.dataObjects.Customer;

@SuppressWarnings("serial")
public class DeliveryMap extends JPanel {

	BufferedImage kart;
	/**
	 * sets dimensions
	 * 
	 */
	public DeliveryMap() {
		setBackground(Color.white);
//		loadImage(address());
		setPreferredSize(new Dimension());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(kart, 0, 0, null);
	}

	/**
	 * 
	 * Home address for the Pizza house to be placed on the google map to get a
	 * Reference point
	 * 
	 * @return returns The address
	 */
	private String address() {
		String adr = "sem sælandsvei 2,trondheim,no";
		return adr;

	}

	/**
	 * 
	 * Method that converts an adress string to a valid text string to be used
	 * in the google maps API call and converts the norwegian special characters
	 * (æ,ø,å,) and replaces spaces with '+'
	 * 
	 * @param s
	 *            - The address to get converted into a valid url for the google
	 *            maps API.
	 * @return - Returns a valid string
	 */
	private String formatAddress(String s) {
		s = s.replace(' ', '+');
		s = s.replace("æ", "%C3%A6");
		s = s.replace("ø", "%C3%BÅ");
		s = s.replace("å", "%C3%A5");
		return s;
	}

	/**
	 * 
	 * This is not final code
	 * 
	 * @param z
	 *            input for zoom-level
	 * 
	 * 
	 * @return
	 */
	/*
	 * private String zoom(String z){ String x = z; while (z >= "10" ) {} return
	 * x; }
	 */

	/**
	 * 
	 * loadImage gets an image from the google static API, so that the image can
	 * be used for pasting in the Delivery GUI
	 * 
	 * @param til
	 *            - Used
	 */

	public void loadImage(Customer customer, int width, int height) {

		try {
			URL url = new URL(
					"http://maps.googleapis.com/maps/api/staticmap?size="+width+"x"+height+"&sensor=false&markers="
							+ formatAddress(address())+ "&markers="+ formatAddress(customer.address));
			kart = ImageIO.read(url);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		repaint();
	}

}
