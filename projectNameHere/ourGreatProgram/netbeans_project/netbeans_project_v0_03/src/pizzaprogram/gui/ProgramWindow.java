package pizzaProgram.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import pizzaprogram.gui.ProgramWindowFrameView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.application.SingleFrameApplication;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;

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
        private CardLayout cardLayoutManager;
        private JPanel mainJPanel;
        
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
                this.createFrame();
                mainApplication.show(this.frameView);
                this.menuBarEventHandler = new MenuBarEventHandler(this.frameView, this.eventDispatcher);
              
	}
	public JFrame getWindowFrame(){
		return jframe;
	}
	
        private void createFrame()
        {
            this.cardLayoutManager = new CardLayout();
            this.mainJPanel = new JPanel(this.cardLayoutManager);
            this.jframe.add(this.mainJPanel);
        }
			
        public void addJPanel(JPanel newPanel)
        {
            this.mainJPanel.add(newPanel, newPanel.toString());
         }
        
	/**
	 * Creates the main window
	 */
	public void createMainWindow(SingleFrameApplication app){
            
	}
	@Override
	public void handleEvent(Event<?> event) {

	}
        
        public void hidePanel(JPanel panel)
        {
            this.refreshFrame();
          
           
            
        }
        
        public void showPanel(JPanel panel)
        {
            
            this.refreshFrame();
         
            this.cardLayoutManager.show(this.mainJPanel, panel.toString());//.show(this.mainJPanel, panel.toString());
        }

    public void refreshFrame() {
      //  this.jframe.invalidate();
        this.jframe.validate();
        this.jframe.repaint();
 
    }
        
      
	
}//END
