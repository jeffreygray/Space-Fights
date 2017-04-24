package edu.virginia.engine.events;

import edu.virginia.engine.display.DisplayObject;

public class CollisionEvent extends Event {
	public static final String COIN = "found a coin";
	public static final String PLATFORM = "colliding with platform";

	private DisplayObject obj;

	public CollisionEvent(String type, DisplayObject src) {
		super(type, src);
		obj = src;
	}
	
	public DisplayObject getSprite() {
		return obj;
	}

}
