package pizzaProgram.events;

public class Event {
	//leave this class for now. I have a finished class on my pc at home; Ill replace that class by this one this evening.
	private boolean isPropagating = true;
	
	public String eventType;
	
	public void stopPropagation()
	{
		this.isPropagating = true;
	}
	public boolean isPropagating()
	{
		return this.isPropagating;
	}
}
