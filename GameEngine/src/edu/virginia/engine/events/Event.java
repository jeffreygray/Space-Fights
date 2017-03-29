package edu.virginia.engine.events;

public class Event {
	private String eventType;
	private IEventDispatcher source; // The object that created this event with the "new" keyword
	
	public Event(String type, IEventDispatcher src) {
		eventType = type;
		source = src;
	}
	
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public IEventDispatcher getSource() {
		return source;
	}
	public void setSource(IEventDispatcher source) {
		this.source = source;
	}
	
}
