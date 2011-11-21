package pizzaProgram.events;

/**
 * The Event class represents an event that can propagate through various event listeners. A class that implements the EventHandler interface can accept and register to listen for an event
 * @author Bart
 *
 * @param <EventParamsDataType> The data type of the payload that the event is able to hold. NOTE: use "Event<Object>" as the default when there is no payload attached!
 */
public class Event<EventParamsDataType> 
{
	/**
	 * The data type of the event parameter
	 */
	private final EventParamsDataType eventParameter;
	
	/**
	 * A string that represents the event type. The value of the event type should be used should be from the EventType class, for the sake of system-wide consistency.
	 */
	public final String eventType;
	
	/**
	 * The constructor for creating an event without payload
	 * @param eventType A string representing the event type. Please use a constant from EventType for this for the sake of system-wide consistency
	 */
	public Event(String eventType)
	{
		this.eventType = eventType;
		this.eventParameter = null;
	}
	/**
	 * The constructor for creating an instance of Event with a payload attached to it.
	 * @param eventType A string representing the event type. Please use a constant from EventType for this for the sake of system-wide consistency
	 * @param eventParameterObject A payload object attached to the event. 
	 */
	public Event(String eventType, EventParamsDataType eventParameterObject)
	{
		this.eventType = eventType;
		this.eventParameter = eventParameterObject;
	}
	
	/**
	 * A function that can be called to retrieve the payload object. NOTE: Do NOT modify the object! There may be other event listeners wanting to use this object,
	 * @return the object attached to the event
	 */
	public EventParamsDataType getEventParameterObject()
	{
		return this.eventParameter;
	}
	
	/**
	 * Returns whether an payload object is attached to the event
	 * @return true if the object has a payload object
	 */
	public boolean hasParameterObject()
	{
		if(this.eventParameter != null)
		{
			return true;
		} else {
			return false;
		}
	}
}
