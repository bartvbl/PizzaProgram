package pizzaProgram.gui;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFrame;

public class ProgramWindow {
	private final Canvas canvas;
	private final JFrame jframe;
	
	public static final String MAIN_WINDOW_NAME = "Pizza Manager";
	
	//AtomicReference is used because the AWT event system runs in a separate thread
	private AtomicReference<Dimension> canvasSize = new AtomicReference<Dimension>();
	
	public ProgramWindow()
	{
		Canvas canvas = new Canvas();
		JFrame frame = new JFrame(ProgramWindow.MAIN_WINDOW_NAME);
		this.canvas = canvas;
		this.jframe = frame;
	}
	
	public void createMainWindow(int width, int height)
	{
		ComponentAdapter adapter = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				resize();
			}
		};
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
}
