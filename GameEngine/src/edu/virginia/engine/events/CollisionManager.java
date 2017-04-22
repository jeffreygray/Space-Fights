package edu.virginia.engine.events;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.tweening.Function;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenEvent;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenableParam;
import edu.virginia.engine.util.SoundManager;
import edu.virginia.spacefights.classes.Ship;

public class CollisionManager implements IEventListener {
	
	public CollisionManager() {
		
	}

	public void handleEvent(Event event) {
		String eventType = event.getEventType();
		switch(eventType) {
		case CollisionEvent.PLATFORM:
			break;
		case CollisionEvent.COIN:
			System.out.println("Got Coin");
			DisplayObject coin = ((CollisionEvent) event).getSprite();
			
			Tween coinTween = new Tween(coin);
			coinTween.animate(TweenableParam.X, coin.getPosition().getX(), 600, 200000, Function.LINEAR);
			coinTween.animate(TweenableParam.Y, coin.getPosition().getY(), 300, 200000, Function.EASE_IN_OUT_QUAD);
			coinTween.addEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
			
			TweenJuggler.getInstance().add(coinTween);
			SoundManager.playSoundEffect("get_coin.wav");
			break;
		case TweenEvent.TWEEN_COMPLETE_EVENT:
			/*
			Tween tween = ((TweenEvent) event).getTween();
			DisplayObject dobj = tween.getObj();
			tween = new Tween(dobj);
			tween.animate(TweenableParam.ALPHA, 1, 0, 200000, Function.LINEAR);
			
			TweenJuggler.getInstance().add(tween); */
			break;
		case CollisionEvent.DEATH:
			//System.out.println("IN COLLISION MANAGER");
			Ship s = (Ship) (event.getSource());
//			Sprite dead;
			
//			Tween boom = new Tween(s);
//			boom.animate(TweenableParam.X, s.getPosition().getX(), s.getPosition().getX() + 10, 5000, Function.EASE_IN_OUT_QUAD);
//			boom.animate(TweenableParam.Y, s.getPosition().getY(), s.getPosition().getY() + 10, 5000, Function.EASE_IN_OUT_QUAD);
//			
//			boom.addEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
//			
//			TweenJuggler.getInstance().add(boom);

//			double x = s.getPosition().getX();
//			double y = s.getPosition().getY();
			s.dyingNow = true;
			
			// add explosion, sound and other death effects 
			// can use tween for explosion effect, calling a SPAWNED event 
			// on finish which will do the actual respawn of the ship
			s.respawn();
		
			
			
			
		
			
			
			SoundManager.playSoundEffect("death.wav");
			break;
			
		}
	}


}
