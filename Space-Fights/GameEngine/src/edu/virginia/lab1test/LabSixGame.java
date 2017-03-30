package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.events.CollisionManager;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.tweening.Function;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenableParam;
import edu.virginia.engine.util.SoundManager;

public class LabSixGame  extends Game {
	private PhysicsSprite mario;
	private Sprite scene;
	private Sprite coin1;
	private Sprite coin2;
	private Sprite plat1;
	private Sprite plat2;
	private Sprite ground;
	private CollisionManager collisionManager;
	private ArrayList<Sprite> platforms = new ArrayList<Sprite>();
	private ArrayList<Sprite> coins = new ArrayList<Sprite>();
	private boolean inAir;
	private int speed = 5;
	
	
	public LabSixGame() {
		super("Lab Six: The InbeTWEENers", 1200, 700);
		String[] images = {"mario_0.png", "mario_1.png", "mario_2.png", "mario_3.png"};
		mario = new PhysicsSprite("mario", images, 10, 0, 0, 0, PhysicsSprite.getGravity());
		scene = new Sprite("scene");
		coin1 = new Sprite("coin1", "coin.png");
		coin2 = new Sprite("coin2", "coin.png");
		plat1 = new Sprite("plat1", "platform.png");
		plat2 = new Sprite("plat2", "platform.png");
		ground = new Sprite("ground", "platform.png");
		collisionManager = new CollisionManager();
		TweenJuggler tweenJuggler = new TweenJuggler();
		
		scene.addChild(coin1);
		scene.addChild(coin2);
		scene.addChild(plat1);
		scene.addChild(plat2);
		scene.addChild(mario);
		scene.addChild(ground);

		mario.setParent(scene);
		coin1.setParent(scene);
		coin2.setParent(scene);
		plat1.setParent(scene);
		plat2.setParent(scene);
		ground.setParent(scene);
		
		coin1.setPosition(600, 500);
		coin2.setPosition(1000, 20);
		
		plat1.setPosition(300, 350);
		plat1.setScaleX(1.5);
		plat2.setPosition(900, 220);
		ground.setScaleX(6);
		ground.setPosition(-30, 650);
		
		platforms.add(ground);
		platforms.add(plat1);
		platforms.add(plat2);

		coins.add(coin1);
		coins.add(coin2);
		
		for(Sprite coin : coins) {
			coin.addEventListener(collisionManager, CollisionEvent.COIN);
		}
		
		mario.addEventListener(collisionManager, CollisionEvent.PLATFORM);
		
		SoundManager.playMusic("music.wav");
		
		Tween marioTween = new Tween(mario);
		marioTween.animate(TweenableParam.ALPHA, 0, 1, 5000000, Function.LINEAR);
		TweenJuggler.getInstance().add(marioTween);
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		if(scene != null) {
			/* grab the position before movement is calculated, to determine the direction of potential collisions */
			Point prevPos = new Point(mario.getPosition());
			
			double xv = 0, yv = mario.getYv();
			if(pressedKeys.contains("Up") && !inAir) {
				yv = -8*speed ;
				inAir = true;
			}
			if(pressedKeys.contains("Down"))
				yv += speed;
			if(pressedKeys.contains("Left"))
				xv -= speed;
			else xv += speed;
			if(pressedKeys.contains("Right")) 
				xv += speed;
			else xv -= speed;
			
			if(xv < 0 && mario.getScaleX() > 0) mario.setScaleX(-1);
			else if (xv > 0 && mario.getScaleX() < 0) mario.setScaleX(1);
			
			/* Set the velocities based on the key presses, then update position*/
			mario.setXv(xv);
			mario.setYv(yv);
			scene.update(pressedKeys);
			
			for(int i = 0; i < coins.size(); i++) {
				Sprite coin = coins.get(i);
				if(mario.collidesWith(coin)) {
					coin.dispatchEvent(new CollisionEvent(CollisionEvent.COIN, coin));
					coins.remove(i);
				}
			}
			
			
			for(Sprite plat: platforms) {
				if(mario.collidesWith(plat)) {
					mario.dispatchEvent(new Event(CollisionEvent.PLATFORM, mario));
					
					Rectangle mHB = mario.getHitbox();
					Rectangle pHB = plat.getHitbox();
					Rectangle overlap = mHB.intersection(pHB);
					
					if(overlap.width < overlap.height) {
						// coming from side; which side?
						if(mHB.x > pHB.x) {  // collision with right side of platform
							if(mario.getScaleX() > 0) // facing right
								mario.setPosition(pHB.getX() + pHB.getWidth(), mario.getPosition().y);
							else
								mario.setPosition(pHB.getX() + pHB.getWidth() + mHB.getWidth(), mario.getPosition().y);
							
						}
						else mario.setPosition(pHB.getX() - mHB.getWidth(), mario.getPosition().y); // on left side
						System.out.println("LR WALL HIT");
						mario.setXv(0);
					} else {
						// coming from top or bottom
						if(mHB.y < pHB.y) {
							mario.setPosition(mario.getPosition().x, pHB.getY() - mHB.getHeight()); //above
							inAir = false;
						}
						else mario.setPosition(mario.getPosition().x, pHB.getY() + pHB.getHeight()); // below
						mario.setYv(0);
					}
				}
			}
			
			/* if we have moved position, animate like we are running */
			if(!(prevPos.equals(mario.getPosition())) && !inAir) 
				mario.animate("run");
			else mario.animate("stand");
			
			TweenJuggler.getInstance().nextFrame();
		}
	}

	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if(scene != null){
			scene.draw(g);
		}
	}
	
	public static void main(String[] args) {
		LabSixGame game = new LabSixGame();
		game.start();
	}

}
