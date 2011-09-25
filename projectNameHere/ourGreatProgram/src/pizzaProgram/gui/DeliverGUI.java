package pizzaProgram.gui;

import javax.swing.JFrame;

import pizzaProgram.events.Event;
import pizzaProgram.events.EventHandler;
import pizzaProgram.modules.GUIModule;

public class DeliverGUI implements GUIModule, EventHandler{

	JFrame jFrame;
	
	
	
	public DeliverGUI(JFrame jFrame) {
		this.jFrame = jFrame;
	}
	
	@Override
	public void handleEvent(Event<Object> event) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
	}

	@Override
	public void clear() {
	}
}
