package edu.virginia.engine.events;

public class QuestEvent extends Event {
	public static String COIN_PICKED_UP = "Coin gotten";
	public static String MOVED_BACKWARDS = "moonwalk!";
	
	public QuestEvent(String type, IEventDispatcher src) {
		super(type, src);
	}

}
