package pizzaProgram.events;

import java.util.ArrayList;

public class EventDispatcher {
	private ArrayList<ArrayList<EventHandler>> listeners = new ArrayList<ArrayList<EventHandler>>();
	private ArrayList<String> eventTypes = new ArrayList<String>();
	
	public void dispatchEvent(Event event)
	{
		if(!eventTypeExists(event.eventType))
		{
			return;
		}
		
		int index = eventTypes.indexOf(event.eventType);
		for(EventHandler i : listeners.get(index))
		{
			if(event.hasStoppedPropagating())
			{
				break;
			}
			i.handleEvent(event);
		}
	}
	public void addEventListener(EventHandler listenerModule, String eventType)
	{
		int index = eventTypes.indexOf(eventType);
		addEventTypeIfNotExistent(eventType);
		this.listeners.get(index).add(listenerModule);
	}
	
	private void addEventTypeIfNotExistent(String eventType)
	{
		if(!eventTypeExists(eventType))
		{
			this.eventTypes.add(eventType);
			this.listeners.add(new ArrayList<EventHandler>());
		}
	}
	
	private boolean eventTypeExists(String eventType)
	{
		if(this.eventTypes.indexOf(eventType) != -1)
		{
			return true;
		} else {
			return false;
		}
	}
}
