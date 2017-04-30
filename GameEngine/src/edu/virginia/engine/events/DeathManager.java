package edu.virginia.engine.events;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.tweening.Function;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenEvent;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenableParam;
import edu.virginia.engine.util.SoundManager;
import edu.virginia.spacefights.classes.Ship;

public class DeathManager implements IEventListener {
	
	public int deathTime = 1500;
	

	@Override
	public void handleEvent(Event event) {
		switch(event.getEventType()) {
		case CombatEvent.DEATH:
			//System.out.println("IN COLLISION MANAGER");
			
			Ship player = (Ship) (event.getSource());
//			String[] booming = {"boom.png", "boom1.png"};
			String[] booming = {"bewm1.png", "bewm2.png", "bewm3.png", "bewm4.png"};
			AnimatedSprite boom = new AnimatedSprite("boom", booming);
			boom.setPivotPoint(player.getPivotPoint());
			player.addChild(boom);
			
			// add explosion, sound and other death effects 
			// can use tween for explosion effect, calling a SPAWNED event 
			// on finish which will do the actual respawn of the ship
			Tween explosion = new Tween(boom);
		    Tween shipFade = new Tween(player);
		    player.setDying(true);
		    
		    shipFade.animate(TweenableParam.ALPHA, 1, 0, deathTime, Function.LINEAR);

		   
		    // DAT BOOM!!!!!!!!!!!!!!!!!!!!!
			explosion.animate(TweenableParam.ALPHA, 0, 1, 500, Function.LINEAR);
			explosion.animate(TweenableParam.ROTATION, player.getRotation(), player.getRotation() + 40, 500, Function.EASE_IN_OUT_QUAD);
			explosion.animate(TweenableParam.SCALE_X, 0.4, 1, 500, Function.LINEAR);
			explosion.animate(TweenableParam.SCALE_Y, 0.4, 1, 500, Function.LINEAR);
//			explosion.animate(TweenableParam.X, player.getWidth()/2-boom.getWidth()/2, 0, 1000, Function.EASE_IN_OUT_QUAD);
//			explosion.animate(TweenableParam.Y, player.getHeight()/2-boom.getHeight()/2, 0, 1000, Function.EASE_IN_OUT_QUAD);


			

			boom.setPlaying(true);	
			shipFade.addEventListener(this, TweenEvent.TWEEN_COMPLETE_EVENT);
			TweenJuggler.getInstance().add(explosion);
			TweenJuggler.getInstance().add(shipFade);
			SoundManager.playSoundEffect("death.wav");
			break;
		case TweenEvent.TWEEN_COMPLETE_EVENT:
			Tween tween = ((TweenEvent) event).getTween();
			Ship s = (Ship)tween.getObj();
			s.removeChildByID("boom");
			
		    s.setAlpha(1); // making ship visible again
		    
			s.respawn();
			
			
		}
	}

}
