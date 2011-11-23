package pizzaProgram.modules;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import pizzaProgram.config.Config;
import pizzaProgram.events.Event;
import pizzaProgram.events.EventDispatcher;
import pizzaProgram.events.EventType;
/**
 * Periodically dispatches a data refresh request event to the various GUI views, so that they can update their own data 
 * @author Bart
 *
 */
public class TimeredDataUpdater extends Module implements ActionListener{
	/**
	 * A timer used to determine the time that has passed since the previous data refresh
	 */
	private Timer updateTimer;
	/**
	 * A list of registered event types that cause a timer reset (the time should reset each time the GUI is updated)
	 */
	private ArrayList<String> eventTypesThatUpdateGUI;
	/**
	 * A reference to the system's main event dispatcher
	 */
	private EventDispatcher eventDispatcher;

	/**
	 * Creates the data refresh timer, and starts it. The start time of the timer is retrieved as a config value from the database
	 * @param eventDispatcher The system's main event dispatcher
	 */
	public TimeredDataUpdater(EventDispatcher eventDispatcher) {
		super(eventDispatcher);
		int updateDelay = Integer.parseInt(Config.getConfigValueByKey(Config.KEY_DB_AUTO_UPDATE_MILLIS));
		if(updateDelay == Config.KEY_DISABLE_DATA_AUTO_UPDATE_VALUE)
		{
			return;
		}
		this.updateTimer = new Timer(updateDelay, this);
		this.eventDispatcher = eventDispatcher;
		this.eventTypesThatUpdateGUI = new ArrayList<String>();
		this.addEventListeners();
	}

	/**
	 * Adds all events that will reset the timer
	 */
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
	
	/**
	 * Adds a specific event type that will represent a data refresh and hence a timer reset
	 * @param eventType A String representing the event to be added (MUST appear in EventType!)
	 */
	private void addEvent(String eventType)
	{
		this.eventTypesThatUpdateGUI.add(eventType);
		this.eventDispatcher.addEventListener(this, eventType);
	}

	/**
	 * Resets the timer to its initial value
	 */
	private void resetTimer()
	{
		this.updateTimer.restart();
	}
	
	/**
	 * Handles the event of a system data update (either by user interaction or automatic); resets the timer
	 */
	public void handleEvent(Event<?> event) {
		if(this.eventTypesThatUpdateGUI.contains(event.eventType))
		{
			this.resetTimer();
		}
	}

	/**
	 * Dispatches a data refresh event into the system when the timer runs out.
	 */
	public void actionPerformed(ActionEvent event) 
	{
		this.eventDispatcher.dispatchEvent(new Event<Object>(EventType.DATA_REFRESH_REQUESTED));
	}

}
