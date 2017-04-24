package edu.virginia.engine.events;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.tweening.Function;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenEvent;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenableParam;
import edu.virginia.engine.util.SoundManager;
import edu.virginia.spacefights.classes.Ship;

public class DeathManager implements IEventListener {

	@Override
	public void handleEvent(Event event) {
		switch(event.getEventType()) {
		case CombatEvent.DEATH:
			//System.out.println("IN COLLISION MANAGER");
			Ship player = (Ship) (event.getSource());
			// add explosion, sound and other death effects 
			// can use tween for explosion effect, calling a SPAWNED event 
			// on finish which will do the actual respawn of the ship
			Tween explosion = new Tween(player);
			explosion.animate(TweenableParam.ROTATION, player.getRotation(), player.getRotation()+180, 800, Function.EASE_IN_OUT_QUAD);
			explosion.addEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
			TweenJuggler.getInstance().add(explosion);
			SoundManager.playSoundEffect("death.wav");
			break;
		case TweenEvent.TWEEN_COMPLETE_EVENT:
			Tween tween = ((TweenEvent) event).getTween();
			Ship s = (Ship)tween.getObj();
			s.respawn();
		}
	}

}
