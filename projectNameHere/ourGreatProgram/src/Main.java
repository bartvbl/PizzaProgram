import javax.swing.JFrame;

import pizzaProgram.events.EventDispatcher;
import pizzaProgram.gui.ProgramWindow;
import pizzaProgram.modules.GUIModule;


public class Main {
	public EventDispatcher eventDispatcher;
	private GUIModule currentActiveGUIModule;
	private ProgramWindow programWindow;
	
	public void initialize()
	{
		this.programWindow = new ProgramWindow();
		this.programWindow.createMainWindow(640, 480);
	}
	public void run()
	{
		
	}
	public void switchGUI(GUIModule newModule)
	{
		
	}
	public ProgramWindow getProgramWindow()
	{
		return this.programWindow;
	}
}
