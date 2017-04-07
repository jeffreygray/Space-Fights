// updated to print out energy and decrement upon hitting L
// TODO: 4.7.2017 ships and ships bounce, fix bug hugging bottom of map, rotate hb

package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.events.CollisionManager;
import edu.virginia.engine.events.Event;
import edu.virginia.spacefights.classes.Projectile;
import edu.virginia.spacefights.classes.Ship;
import edu.virginia.spacefights.classes.ShipType;
import edu.virginia.engine.util.SoundManager;


public class SpaceFights extends Game {
	static int gameWidth = 1800;
	static int gameHeight = 1000;
	static double dampen = -0.75;
	Sprite scene, plat1, plat2, plat3, plat4, plat5;
	Ship player1, player2, player3;
//	Sprite p1nrgFront, p1nrgBack, p2nrgFront, p2nrgBack;
	ArrayList<Sprite> platforms = new ArrayList<Sprite>();
	ArrayList<Ship> players = new ArrayList<Ship>();
	CollisionManager collisionManager;


	public SpaceFights() {
		super("Space Fights", gameWidth, gameHeight);
		player1 = new Ship(ShipType.Vulture, 1);
		player2 = new Ship(ShipType.Rhino, 2);
//		player3 = new Ship(ShipType.Vulture, 3);

		
		players.add(player1);
		players.add(player2);
//		players.add(player3);
		
		scene = new Sprite("scene", "backgroundSF.png");
		scene.setScaleX(1);

		plat1 = new Sprite("plat1", "platformSpaceVertical.png");
		plat2 = new Sprite("plat2", "moon.png");
		plat2.setScaleX(9);
		plat2.setScaleY(9);
		plat3 = new Sprite("plat3", "platformSpaceVertical.png");
//		plat4 = new Sprite("plat4", "platformSmall.png");
//		plat5 = new Sprite("plat5", "platformSmall.png");

		platforms.add(plat1);
     	platforms.add(plat2);
		platforms.add(plat3);
//		platforms.add(plat4);
//		platforms.add(plat5);

		
		collisionManager = new CollisionManager();


		player1.setPosition(800, 350);
		player2.setPosition(300,150);
//		player3.setPosition(1,1);
		
		for(int i = 0; i < platforms.size(); i++) {
			Sprite plat = platforms.get(i);
			scene.addChild(plat);
		}
		
		plat1.setPosition(400, 250);
		plat2.setPosition(900, 300);
		plat3.setPosition(1350, 470);

//		plat3.setPosition(300, 348 +  2*plat2.getHeight());
//		plat4.setPosition(300, 347 + 3*plat2.getHeight());
//		plat5.setPosition(300, 346 + 4*plat2.getHeight());

		for(Ship player : players) {
			player.addEventListener(collisionManager, CollisionEvent.PLATFORM);
			player.addEventListener(collisionManager, CollisionEvent.DEATH);
			player.setScaleX(0.8);
			player.setScaleY(0.65);
			player.setPivotPoint(player.getWidth()/2, player.getHeight()/2);
			scene.addChild(player);		
		}
		SoundManager.playMusic("sound.wav");
	}

	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		if (scene != null) { // makes sure we loaded everything
			scene.update(pressedKeys, controllers);
			
			// Keeps players within bounds of game window by rebounding them back in
			for(Ship player: players) {
				Point shipPos = player.getPosition();
				if (shipPos.x <= 0) {
					player.setXPosition(1);
					player.setXv(-player.getXv());
				} else if (shipPos.x > gameWidth - player.getWidth()) {
					player.setXPosition(gameWidth - player.getWidth()-1);
					player.setXv(dampen * player.getXv());
				}
				if (shipPos.y <= 0) {
					player.setYPosition(1);
					player.setYv(dampen * player.getYv());
				} else if (shipPos.y > gameHeight - player.getHeight()) {
					player.setYPosition(gameHeight - player.getHeight()-1);
					player.setYv(dampen * player.getYv());
				}
			}
			/*
			 * for players:
			 *   for each plat:
			 *   	check collide
			 *   	for each for each proj:
			 *     		check collide w/ plat
			 *   for each projectile
			 *   	for each ship
			 *     		check collide
			 */

			for(Ship player: players) {
				
				for(Sprite plat: platforms) {
					
					// player-platform collision
					if (player.collidesWith(plat)) {
						player.dispatchEvent(new Event(CollisionEvent.PLATFORM, player));

						Rectangle mHB = player.getHitbox();
						Rectangle pHB = plat.getHitbox();
						Rectangle overlap = mHB.intersection(pHB);

						if (overlap.width < overlap.height) {
							// coming from side; which side?
							if (mHB.x > pHB.x) { // collision with right side
								player.setPosition(pHB.getX() + pHB.getWidth(), player.getPosition().y);	
							} else // left side
								player.setPosition(pHB.getX() - mHB.getWidth(), player.getPosition().y); 
							// System.out.println("LR WALL HIT");
							player.setXv(dampen * player.getXv());  
							// Dampen is currently 0.8, can change later
						} else {// coming from top or bottom
							if (mHB.y < pHB.y) { // above
								player.setPosition(player.getPosition().x, pHB.getY() - mHB.getHeight()); 
							} else { // below
								player.setPosition(player.getPosition().x, pHB.getY() + pHB.getHeight()); 
							}
							player.setYv(dampen * player.getYv());
						}

					}
					// projectile-platform collision
					for (Projectile p : player.getProjectiles()) {
						if (p.collidesWith(plat)) {
							p.dispatchEvent(new Event(CollisionEvent.PLATFORM, p));
							if (!p.isHasBounce()) {
								p.setRemove(true);
							}

							Rectangle mHB = p.getHitbox();
							Rectangle pHB = plat.getHitbox();
							Rectangle overlap = mHB.intersection(pHB);

							if (overlap.width < overlap.height) {
								// coming from side; which side?
								if (mHB.x > pHB.x) { // collision with right
									p.setPosition(pHB.getX() + pHB.getWidth(), p.getPosition().y);
								} else // left side
									p.setPosition(pHB.getX() - mHB.getWidth(), p.getPosition().y); 
								// System.out.println("LR WALL HIT");
								p.setXv(-p.getXv());
							} else { // either above or below platform
								if(p.isHasBounce())
									p.setYv(-p.getYv());
								// reset position to get it "out" of the platform (so hitboxes don't intersect)
								if (mHB.y < pHB.y) // above
									p.setPosition(p.getPosition().x, pHB.getY() - mHB.getHeight());
								else // below
									p.setPosition(p.getPosition().x, pHB.getY() + pHB.getHeight());
								
							}
						}
					}
				} // End platform collisions
				// player-projectile collision
				for (Projectile p : player.getProjectiles()) {
					for (Ship s : players) {
						// checks with collision with enemies
						if (p.collidesWith(s) && s.getPlayerNum() != player.getPlayerNum()) {
							s.setNrg(s.getNrg() - p.getDamage());
							p.setRemove(true);
						}
					}
				} // end player-projectile collision

			} // end for each player loop
		} // end not null check
//		System.out.println(pressedKeys);
	}

	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(scene !=null) {
			scene.draw(g);
		}
	}

	public static void main(String[] args) {
		SpaceFights game = new SpaceFights();
		game.start();
	}

}
