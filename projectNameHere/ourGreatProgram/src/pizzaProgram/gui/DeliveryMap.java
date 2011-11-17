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

	private BufferedImage kart;
	private int drawWidth;
	private int drawHeight;
	
	private final int googleMaxWidthAndHeight = 640;
	
	public DeliveryMap() {
		setBackground(Color.white);
		setPreferredSize(new Dimension());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(kart, 0, 0, drawWidth, drawHeight, null);
	}

	private String address() {
		//hjemmeadressen til restauranten
		String adr = "sem sælandsvei 2,trondheim,no";
		return adr;
	}

	/**
	 * 
	 * Method that converts an adress string to a valid text string to be used
	 * in the google maps API call and converts the norwegian special characters
	 * (æ,ø,å,) and replaces spaces with '+'
	 * 
	 * @param s - The address to get converted into a valid url for the google
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
	 * Loads an image into the mapdisplayarea, the width and height parameter 
	 * specifies how much space is awailable for the map
	 * the map wil be the correct aspectratio, and be zoomed so that the size of the map
	 * matches the width and height
	 */
	public void loadImage(Customer customer, int width, int height) {
		drawWidth = width;
		drawHeight = height;
		
		int requestWidth = 0;
		int requestHeight = 0;
		
		if(width > height){
			requestWidth = googleMaxWidthAndHeight;
			requestHeight = (int)(googleMaxWidthAndHeight * ((double)height/(double)width));
		}else{
			requestHeight = googleMaxWidthAndHeight;
			requestWidth = (int)(googleMaxWidthAndHeight * ((double)width/(double)height));
		}
		
		try {
			URL url = new URL(
					"http://maps.googleapis.com/maps/api/staticmap?size="+requestWidth+"x"+requestHeight+"&sensor=false&markers="
							+ formatAddress(address())+ "&markers="+ formatAddress(customer.address+","+customer.postalCode));
			kart = ImageIO.read(url);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		repaint();
	}

}//END
