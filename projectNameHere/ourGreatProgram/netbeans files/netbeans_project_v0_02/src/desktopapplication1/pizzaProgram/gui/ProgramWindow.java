package pizzaProgram.gui;

import desktopapplication1.ProgramWindowFrameView;
import desktopapplication1.pizzaProgram.gui.MenuBarEventHandler;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.application.SingleFrameApplication;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.gui.controls.WindowMenuBar;
import pizzaProgram.modules.GUIModule;

/**
 * The ProgramWindow class creates the application's main window, with several features attached to it
 * @author Bart
 *
 */
public class ProgramWindow implements EventHandler{
	private final EventDispatcher eventDispatcher;
	
	/**
	 * A constant holding the name as it will appear inside the window
	 */
	public static final String MAIN_WINDOW_NAME = "Pizza Manager";
	
        private ProgramWindowFrameView frameView;
        private MenuBarEventHandler menuBarEventHandler;
        
        private JFrame jframe;
	/**
	 * The constructor of the program takes in the event dispatcher, and creates t
	 * @param eventDispatcher
	 */
	public ProgramWindow(EventDispatcher eventDispatcher, SingleFrameApplication mainApplication){
		this.eventDispatcher = eventDispatcher;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
                this.frameView = new ProgramWindowFrameView(mainApplication);
		this.jframe = this.frameView.getFrame();
                mainApplication.show(this.frameView);
                this.menuBarEventHandler = new MenuBarEventHandler(this.frameView, this.eventDispatcher);
                
                
	}
	public JFrame getWindowFrame(){
		return jframe;
	}
	
        public void addJPanel(JPanel newPanel)
        {
            this.jframe.add(newPanel);
        }
        
	/**
	 * Creates the main window
	 */
	public void createMainWindow(SingleFrameApplication app){
            
	}
	@Override
	public void handleEvent(Event<?> event) {

	}

    public void refreshFrame() {
        this.jframe.validate();
    }
        
      
	
}//END
