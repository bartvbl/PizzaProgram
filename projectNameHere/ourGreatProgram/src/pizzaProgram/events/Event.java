package pizzaProgram.events;

public class Event<EventParamsDataType> 
{
	private EventParamsDataType eventParameter = null;
	private boolean isPropagating = true;
	
	public String eventType;
	
	public Event(String eventType)
	{
		this.eventType = eventType;
	}
	public Event(String eventType, EventParamsDataType eventParameterObject)
	{
		this.eventType = eventType;
		this.eventParameter = eventParameterObject;
	}
	
	public EventParamsDataType getEventParameterObject()
	{
		return this.eventParameter;
	}
	public boolean hasParameterObject()
	{
		if(this.eventParameter != null)
		{
			return true;
		} else {
			return false;
		}
	}
	public String getEventType()
	{
		return this.eventType;
	}

	public void stopPropagation()
	{
		this.isPropagating = true;
	}
	public boolean isPropagating()
	{
		return this.isPropagating;
	}
}
