package pizzaProgram.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;

import pizzaProgram.events.EventDispatcher;
import pizzaProgram.gui.controls.WindowMenuBar;

public class ProgramWindow {
	private final Canvas canvas;
	private final JFrame jframe;
	private final WindowMenuBar menuBar;
	private final EventDispatcher eventDispatcher;
	
	public static final String MAIN_WINDOW_NAME = "Pizza Manager";
	
	//AtomicReference is used because the AWT event system runs in a separate thread
	private AtomicReference<Dimension> canvasSize = new AtomicReference<Dimension>();
	
	public ProgramWindow(EventDispatcher eventDispatcher)
	{
		this.eventDispatcher = eventDispatcher;
		
		Canvas canvas = new Canvas();
		JFrame frame = new JFrame(ProgramWindow.MAIN_WINDOW_NAME);
		this.canvas = canvas;
		this.jframe = frame;
		
		this.menuBar = new WindowMenuBar(eventDispatcher, this.jframe);
	}
	
	public void createMainWindow(int width, int height)
	{
		ComponentAdapter adapter = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resize();
			}
		};
		this.createMenuBar(eventDispatcher);
		
		this.canvas.addComponentListener(adapter);
		this.jframe.setSize(width, height);
		this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.jframe.getContentPane().add(canvas);
		this.jframe.setVisible(true);
	}
	
	public void resize()
	{
		Dimension dim = this.canvas.getSize();
		canvasSize.set(dim);
		dim = null;
	}
	
	//---
	
	private void createMenuBar(EventDispatcher eventDispatcher)
	{
		this.menuBar.addMenu("File");
		this.menuBar.addMenuItem("Exit", "ProgramExitRequested");
		this.menuBar.addMenu("Edit");
		this.menuBar.addMenuItem("Settings", "OpenSettingsWindowRequested");
		this.menuBar.addMenu("View");
		this.menuBar.createButtonGroup();
		this.menuBar.addRadioMenuItem("Orders", "OrderGUIRequested", true);
		this.menuBar.addRadioMenuItem("Cook", "CookGUIRequested", false);
		this.menuBar.addRadioMenuItem("Delivery", "DeliveryGUIRequested", false);
		this.menuBar.pack();
	}
}
