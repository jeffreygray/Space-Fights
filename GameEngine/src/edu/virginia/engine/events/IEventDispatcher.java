package edu.virginia.engine.events;


public interface IEventDispatcher {
	
	public void addEventListener(IEventListener listener, String eventType);
	public void removeEventListener(IEventListener listener, String eventType);
	public void dispatchEvent(Event event);
	public boolean hasEventListener(IEventListener listener, String eventType);

}
