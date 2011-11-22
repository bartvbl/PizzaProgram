package pizzaProgram.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import pizzaProgram.config.Config;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventType;

public class TimeredDataUpdater extends Module implements ActionListener{
	private Timer updateTimer;
	private ArrayList<String> eventTypesThatUpdateGUI;

	public TimeredDataUpdater(EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		int updateDelay = Integer.parseInt(Config.getConfigValueByKey("autoDataUpdateDelayInMillis"));
		this.updateTimer = new Timer(updateDelay, this);
		eventDispatcher.addEventListener(this, EventType.DATABASE_UPDATE_ADMIN_GUI_SEARCH_DISHES);
		this.eventTypesThatUpdateGUI = new ArrayList<String>();
		this.addEventListeners(eventDispatcher);
	}

	private void addEventListeners(EventDispatcher eventDispatcher) {
		
	}
	
	private void addEvent(String eventType)
	{
		this.eventTypesThatUpdateGUI.add(eventType);
		
	}

	private void resetTimer()
	{
		this.updateTimer.restart();
	}
	
	public void handleEvent(Event<?> event) {
		if(this.eventTypesThatUpdateGUI.contains(event.eventType))
		{
			this.resetTimer();
		}
	}

	public void actionPerformed(ActionEvent arg0) {
		
	}

}
