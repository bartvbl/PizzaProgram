package pizzaProgram.utils;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.RepaintManager;
/**
 * A class used to print out components of the GUI, specifically the receipt of an order
 * @author Håvard, Bart
 *
 */
public class PrintUtilities implements Printable {
	/**
	 * The component that should be printed
	 */
	private Component componentToBePrinted;
	/**
	 * The method that can be called to print out components. It invokes the private constructor of PrintUtilities, such that it can be sent to the printer.
	 * @param componentToBePrinted
	 */
	public static void printComponent(Component componentToBePrinted) {
		new PrintUtilities(componentToBePrinted).print();
	}

	/**
	 * The constructor of PrintUtilities is private. To print a component, use printComponent().
	 * @param componentToBePrinted The component to be printed
	 */
	private PrintUtilities(Component componentToBePrinted) {
		this.componentToBePrinted = componentToBePrinted;
	}

	/**
	 * Creates a new printerjob, and prints the component
	 */
	private void print() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		if (printJob.printDialog()){
			try {
				printJob.print();
			} catch(PrinterException pe) {
				System.out.println("Error printing: " + pe);
			}
		}
	}

	/**
	 * Draws the component on the inserted graphics, so that the printer can print it
	 */
	public int print(Graphics graphicsToBeDrawnOn, PageFormat pageFormat, int pageIndex) {
		if (pageIndex > 0) {
			return(NO_SUCH_PAGE);
		}
		else {
			Graphics2D g2d = (Graphics2D)graphicsToBeDrawnOn;
			g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
			disableDoubleBuffering(componentToBePrinted);
			componentToBePrinted.paint(g2d);
			enableDoubleBuffering(componentToBePrinted);
			return(PAGE_EXISTS);
		}
	}

	/**
	 * Disables the double buffering in the inserted component
	 * @param component The component to disable double buffering on
	 */
	public static void disableDoubleBuffering(Component component) {
		RepaintManager currentManager = RepaintManager.currentManager(component);
		currentManager.setDoubleBufferingEnabled(false);
	}

	/**
	 * Enables double buffering on the inserted component
	 * @param component The component to enable double buffering on
	 */
	public static void enableDoubleBuffering(Component component) {
		RepaintManager currentManager = RepaintManager.currentManager(component);
		currentManager.setDoubleBufferingEnabled(true);
	}
}