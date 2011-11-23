package pizzaProgram.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.utils.PriceCalculators;

/**
 * This class is a JPanel that can display a map a marker on the address of the
 * customer of the currently selected order, in addition to a marker on the
 * restaurants address (as specified in the address and city fields for the
 * restaurant in the config table of the database). To display the map, call the
 * loadImage(Customer) - method
 * 
 * @author IT1901 Group 3, Fall 2011
 */
@SuppressWarnings("serial")
public class DeliveryMap extends JPanel {

	private BufferedImage map;
	private int drawWidth;
	private int drawHeight;

	private final int googleMaxWidthAndHeight = 640;

	/**
	 * Does nothing exept for setting the backgroundcolor
	 */
	public DeliveryMap() {
		setBackground(Color.white);
	}

	/**
	 * Paints the current map on this component
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(map, 0, 0, drawWidth, drawHeight, null);
	}

	/**
	 * 
	 * Method that converts an adress string to a valid text string to be used
	 * in the google maps API call and converts the norwegian special characters
	 * (æ,ø,å) and replaces spaces with '+'
	 * 
	 * @param s
	 *            The address to get converted into a valid url for the google
	 *            maps API.
	 * @return Returns a valid String
	 */
	private String formatAddress(String s) {
		s = s.replace(' ', '+');
		s = s.replace("æ", "%C3%A6");
		s = s.replace("ø", "%C3%BÅ");
		s = s.replace("å", "%C3%A5");
		return s;
	}

	/**
	 * Loads an image into the mapdisplayarea, the width and height parameter
	 * specifies how much space is available for the map. The map wil be the
	 * correct aspect ratio, and be zoomed so that the size of the map matches
	 * the width and height
	 */
	public void loadImage(Customer customer, int width, int height) {
		drawWidth = width;
		drawHeight = height;

		int requestWidth = 0;
		int requestHeight = 0;

		if (width > height) {
			requestWidth = googleMaxWidthAndHeight;
			requestHeight = (int) (googleMaxWidthAndHeight * ((double) height / (double) width));
		} else {
			requestHeight = googleMaxWidthAndHeight;
			requestWidth = (int) (googleMaxWidthAndHeight * ((double) width / (double) height));
		}

		try {
			URL url = new URL("http://maps.googleapis.com/maps/api/staticmap?size="
					+ requestWidth
					+ "x"
					+ requestHeight
					+ "&sensor=false&markers="
					+ formatAddress(PriceCalculators.getRestaurantAddress() + ","
							+ PriceCalculators.getRestaurantCity()) + "&markers="
					+ formatAddress(customer.address + "," + customer.postalCode));
			map = ImageIO.read(url);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		repaint();
	}
}