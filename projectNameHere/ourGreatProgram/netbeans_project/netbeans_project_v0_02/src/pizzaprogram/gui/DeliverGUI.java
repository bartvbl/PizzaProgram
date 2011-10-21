package pizzaProgram.gui;

import pizzaprogram.gui.views.DeliveryView;
import java.awt.GridBagConstraints;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pizzaProgram.dataObjects.Customer;
import pizzaProgram.dataObjects.Extra;
import pizzaProgram.dataObjects.Order;
import pizzaProgram.dataObjects.OrderDish;
import pizzaProgram.database.DatabaseConnection;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventHandler;
import pizzaProgram.events.EventType;
import pizzaProgram.modules.GUIModule;

public class DeliverGUI extends GUIModule implements EventHandler{

	private DeliveryView deliverView;
        private ProgramWindow programWindow;
	
	public DeliverGUI(ProgramWindow mainWindow, EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		
		eventDispatcher.addEventListener(this, EventType.COOK_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.ORDER_GUI_REQUESTED);
		eventDispatcher.addEventListener(this, EventType.DELIVERY_GUI_REQUESTED);
                this.deliverView = new DeliveryView();
                mainWindow.addJPanel(this.deliverView);
                this.programWindow = mainWindow;
                hide();
	}
	/**
	 * Her skal koden for � lage og legge til komponenter ligger
	 */
	@Override
	public void initialize() {
		
	}
	
	
	/**
	 * Her skal koden for � vise komponentene ligge
	 */
	@Override
	public void show() {
		this.programWindow.refreshFrame();
            this.deliverView.setVisible(true);
                
	}
	
	/**
	 * Her skal koden for � skjule komponentene ligge
	 */
	@Override
	public void hide() {
		this.programWindow.refreshFrame();
            this.deliverView.setVisible(false);
                
	}
	
	@Override
	public void handleEvent(Event<?> event) {
		if(event.eventType.equals(EventType.COOK_GUI_REQUESTED)){
			hide();
		}else if(event.eventType.equals(EventType.DELIVERY_GUI_REQUESTED)){
			show();
		}else if(event.eventType.equals(EventType.ORDER_GUI_REQUESTED)){
			hide();
		}
	}
	
}
