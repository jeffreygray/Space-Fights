package edu.virginia.engine.events;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class EventDispatcher implements IEventDispatcher {

	private Hashtable<IEventListener, ArrayList<String>> listeners = new Hashtable<IEventListener, ArrayList<String>>();
	
	@Override
	public void addEventListener(IEventListener listener, String eventType) {
		if( !listeners.containsKey(listener) )
			listeners.put(listener, new ArrayList<String>());
		listeners.get(listener).add(eventType);
	}

	@Override
	public void removeEventListener(IEventListener listener, String eventType) {
		listeners.get(listener).remove(eventType);
	}

	@Override
	public void dispatchEvent(Event event) {
		for(Map.Entry<IEventListener, ArrayList<String>> entry : listeners.entrySet()) {
			if(entry.getValue().contains(event.getEventType()))
				entry.getKey().handleEvent(event);
		}
	}

	@Override
	public boolean hasEventListener(IEventListener listener, String eventType) {
		return false;
	}

}
