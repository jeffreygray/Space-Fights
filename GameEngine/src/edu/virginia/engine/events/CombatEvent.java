package edu.virginia.engine.events;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.spacefights.classes.Ship;

public class CombatEvent extends Event{
	public static final String DEATH = "you ded boi";
	public static final String RESPAWN = "time to respawn";
	private DisplayObject obj;
	
	public CombatEvent(String type, DisplayObject src) {
		super(type, src);
		obj = src;
	}
	
	public Ship getShip() {
		return (Ship)obj;
	}
	
}
