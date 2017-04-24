package edu.virginia.engine.tweening;

import java.util.ArrayList;

public class TweenJuggler {
	private static TweenJuggler juggler;
	private static ArrayList<Tween> tweens;
	
	public TweenJuggler() {
		if(juggler != null) 
			throw new java.lang.Error("A TweenJuggler instance already exists");
		juggler = this;
		tweens = new ArrayList<Tween>();
	}
	
	public static TweenJuggler getInstance(){
		return juggler;
	}
	
	public void add(Tween tween) {
		tweens.add(tween);
		tween.beginStartTime();
	}
	
	public static void nextFrame() {
		for(int i = 0; i < tweens.size(); i++) {
			Tween t = tweens.get(i);
			t.update();
			if(t.isComplete()) {
				t.dispatchEvent(new TweenEvent(TweenEvent.TWEEN_COMPLETE_EVENT, t));
				tweens.remove(i);
			}
		}
	}
}
