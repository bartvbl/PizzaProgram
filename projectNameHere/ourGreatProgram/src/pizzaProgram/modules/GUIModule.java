package pizzaProgram.modules;

import javax.swing.JFrame;

import pizzaProgram.events.EventDispatcher;

public class GUIModule extends Module {
	protected JFrame programWindowFrame;
	
	public GUIModule(EventDispatcher eventDispatcher)
	{
		super(eventDispatcher);
	}
	
	public void setJFrameReference(JFrame frame)
	{
		this.programWindowFrame = frame;
	}
	
	public void draw() {}
	public void clear() {}
}
