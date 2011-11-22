package pizzaProgram.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import pizzaProgram.config.Config;
import pizzaProgram.constants.DatabaseQueryConstants;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventType;

public class TimeredDataUpdater extends Module implements ActionListener{
	private Timer updateTimer;
	private ArrayList<String> eventTypesThatUpdateGUI;
	private EventDispatcher eventDispatcher;

	public TimeredDataUpdater(EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		int updateDelay = Integer.parseInt(Config.getConfigValueByKey(DatabaseQueryConstants.SETTING_KEY_DB_AUTO_UPDATE_MILLIS));
		if(updateDelay == DatabaseQueryConstants.SETTING_KEY_DISABLE_DATA_AUTO_UPDATE)
		{
			return;
		}
		this.updateTimer = new Timer(updateDelay, this);
		this.eventDispatcher = eventDispatcher;
		this.eventTypesThatUpdateGUI = new ArrayList<String>();
		this.addEventListeners();
	}

	private void addEventListeners() 
	{
		this.addEvent(EventType.ADMIN_GUI_UPDATE_DISH_LIST);
		this.addEvent(EventType.ADMIN_GUI_UPDATE_EXTRA_LIST);
		this.addEvent(EventType.ADMIN_GUI_UPDATE_ORDER_LIST);
		this.addEvent(EventType.COOK_GUI_UPDATE_ORDER_LIST);
		this.addEvent(EventType.DELIVERY_GUI_UPDATE_ORDER_LIST);
		this.addEvent(EventType.ORDER_GUI_UPDATE_CUSTOMER_LIST);
		this.addEvent(EventType.ORDER_GUI_UPDATE_DISH_LIST);
		this.addEvent(EventType.ORDER_GUI_UPDATE_EXTRAS_LIST);
		this.addEvent(EventType.DATA_REFRESH_REQUESTED);
	}
	
	private void addEvent(String eventType)
	{
		this.eventTypesThatUpdateGUI.add(eventType);
		this.eventDispatcher.addEventListener(this, eventType);
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

	public void actionPerformed(ActionEvent event) 
	{
		this.eventDispatcher.dispatchEvent(new Event<Object>(EventType.DATA_REFRESH_REQUESTED));
	}

}
