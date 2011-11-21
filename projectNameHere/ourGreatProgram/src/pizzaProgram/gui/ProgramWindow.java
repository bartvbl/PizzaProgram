package pizzaProgram.gui;

import java.awt.CardLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.application.SingleFrameApplication;

import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.gui.EventHandlers.MenuBarEventHandler;
import pizzaProgram.gui.views.ProgramWindowFrameView;

/**
 * The ProgramWindow class creates the application's main window, with several
 * features attached to it
 * 
 * @author Bart
 * 
 */
public class ProgramWindow implements EventHandler {
	private final EventDispatcher eventDispatcher;

	/**
	 * A constant holding the name as it will appear inside the window
	 */
	public static final String MAIN_WINDOW_NAME = "Pizza Manager";
	private CardLayout cardLayoutManager;
	private JPanel mainJPanel;

	private JFrame jframe;

	/**
	 * Creates the program's main window and initializes it.
	 * @param eventDispatcher The system's main event dispatcher
	 */
	public ProgramWindow(EventDispatcher eventDispatcher, SingleFrameApplication mainApplication) {
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
		ProgramWindowFrameView frameView = new ProgramWindowFrameView(mainApplication);
		this.jframe = frameView.getFrame();
		this.jframe.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {
				jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
		});
		
		this.createFrame();
		mainApplication.show(frameView);
		new MenuBarEventHandler(this.eventDispatcher);
	}

	/**
	 * initializes the JFrame of the create FrameView
	 */
	private void createFrame() {
		this.cardLayoutManager = new CardLayout();
		this.mainJPanel = new JPanel(this.cardLayoutManager);
		this.jframe.add(this.mainJPanel);
	}

	/**
	 * Adds a new JPanel to the program's main window.
	 * @param newPanel The new JPanel to add
	 */
	public void addJPanel(JPanel newPanel) {
		this.mainJPanel.add(newPanel, newPanel.toString());
	}

	/**
	 * Implementation of a required method, unused.
	 */
	@Override
	public void handleEvent(Event<?> event) {

	}
	
	/**
	 * Unused function; does nothing. Can be utilized if the implementation of the main program window ever changes
	 */
	public void hidePanel(JPanel panel) {
		this.refreshFrame();
	}

	/**
	 * Shows the JPanel inserted in the program's main window. NOTE: the panel must be registered first! Call ProgramWindow.addJPanel() to do so.
	 * @param panel The JPanel to display
	 */
	public void showPanel(JPanel panel) {
		this.refreshFrame();
		this.cardLayoutManager.show(this.mainJPanel, panel.toString());																// panel.toString());
	}

	/**
	 * Refreshes the main frame, as required by swing
	 */
	public void refreshFrame() {
		this.jframe.validate();
		this.jframe.repaint();
	}

}// END

