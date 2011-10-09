package pizzaProgram.gui;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class DeliveryMap extends JPanel {
	
	
	BufferedImage kart = new BufferedImage(500, 300, BufferedImage.TYPE_INT_RGB);
	
	public DeliveryMap() {	
		setPreferredSize(new Dimension(500, 300));
		loadImage(adresse());
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(kart, 0, 0, null);
	}
	
	private String adresse(){
		String adr = "sem sælandsvei 2,trondheim,no";
		return adr.replaceAll(" ", "+");
		
	}
	private String formatAdress(String s){
		s = s.replace(' ', '+');
		s = s.replace("æ", "%C3%A6");
		s = s.replace("ø", "%C3%BÅ");
		s = s.replace("å", "%C3%A5");
		return s;
	}
	public void loadImage (String til){
		
		try {
			URL url = new URL("http://maps.googleapis.com/maps/api/staticmap?size=500x300&sensor=false&markers="+ formatAdress(adresse())+"&markers="+formatAdress(til));
			kart = ImageIO.read(url);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		repaint();
	}
	
}
