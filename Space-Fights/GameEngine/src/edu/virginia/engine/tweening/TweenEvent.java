package edu.virginia.engine.tweening;

import edu.virginia.engine.events.Event;

public class TweenEvent extends Event {
	public final static String TWEEN_COMPLETE_EVENT = "Tween is complete";
	private Tween tween;
	
	public TweenEvent(String type, Tween tween) {
		super(type, tween);
		this.tween = tween;
	}
	
	public Tween getTween() {
		return this.tween;
	}
}
