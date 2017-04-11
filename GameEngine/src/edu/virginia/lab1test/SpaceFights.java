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
	static int gameWidth = 1200;
	static int gameHeight = 700;

	static double dampen = -0.65;
	Sprite scene, plat1, plat2, plat3, plat4, plat5, plat6, plat7, plat8, plat9, plat10, plat11, plat12, plat13, plat14, plat15;
	Ship player1, player2, player3;
	ArrayList<Sprite> platforms = new ArrayList<Sprite>();
	ArrayList<Ship> players = new ArrayList<Ship>();
	CollisionManager collisionManager;
	private double elasticity = 1.7;


	public SpaceFights() {
		super("Space Fights", gameWidth, gameHeight);
		scene = new Sprite("scene", "background.png");
		collisionManager = new CollisionManager();

		player1 = new Ship(ShipType.Vulture, 1);
		player2 = new Ship(ShipType.Rhino, 2);
//		player3 = new Ship(ShipType.Vulture, 3);

		
		players.add(player1);
		players.add(player2);
//		players.add(player3);
		
		for(Ship player : players) {
			player.addEventListener(collisionManager, CollisionEvent.PLATFORM);
			player.addEventListener(collisionManager, CollisionEvent.DEATH);
			player.setScaleX(0.8);
			player.setScaleY(0.65);
			player.setPivotPoint(player.getWidth()/2, player.getHeight()/2);
			scene.addChild(player);		
		}
		

		plat1 = new Sprite("plat1", "platformSpaceVertical.png");
		plat2 = new Sprite("plat2", "moon.png");
		plat2.setScaleX(9);
		plat2.setScaleY(9);
		plat3 = new Sprite("plat3", "platformSpaceVertical.png");
		plat4 = new Sprite("plat4", "platformSpace.png");
		plat5 = new Sprite("plat5", "platformSpace.png");
		plat6 = new Sprite("plat6", "platformSpace.png");
		plat7 = new Sprite("plat7", "platformSpace.png");
		plat8 = new Sprite("plat8", "platformSpace.png");
		plat9 = new Sprite("plat9", "platformSpace.png");
		plat10 = new Sprite("plat10", "platformSpace.png");
		plat11 = new Sprite("plat11", "platformSpace.png");
		plat12 = new Sprite("plat12", "platformSpace.png");
		plat13 = new Sprite("plat13", "platformSpaceVertical.png");
		plat14 = new Sprite("plat14", "platformSpace.png");
		plat15 = new Sprite("plat15", "platformSpaceVertical.png");






		platforms.add(plat1);
     	platforms.add(plat2);
		platforms.add(plat3);
		platforms.add(plat4);
		platforms.add(plat5);
		platforms.add(plat6);
		platforms.add(plat7);
		platforms.add(plat8);
		platforms.add(plat9);
		platforms.add(plat10);
		platforms.add(plat11);
		platforms.add(plat12);
		platforms.add(plat13);
		platforms.add(plat14);
		platforms.add(plat15);

		

		
		for(int i = 0; i < platforms.size(); i++) {
			Sprite plat = platforms.get(i);
			scene.addChild(plat);
		}
		
		plat1.setPosition(400, 250);
		plat2.setPosition(900, 300);
		plat3.setPosition(1350, 470);
		plat4.setPosition(0, 920);
		plat5.setPosition(plat4.getWidth(), 925);
		plat6.setPosition(plat4.getWidth() * 2, 925);
		plat7.setPosition(plat4.getWidth() * 3, 925);
		plat8.setPosition(plat4.getWidth() * 4, 925);
		plat9.setPosition(plat4.getWidth() * 5, 925);
		plat10.setPosition(plat4.getWidth() * 6, 925);
		plat11.setPosition(plat4.getWidth() * 7, 925);
		plat12.setPosition(plat4.getWidth() * 8, 925);
		plat13.setPosition(0,0);
		plat13.setScaleY(10);
		plat14.setPosition(1,0);
		plat14.setScaleX(10);
		plat15.setPosition(gameWidth - plat15.getWidth(), 0);
		plat15.setScaleY(10);

		

//		plat3.setPosition(300, 348 +  2*plat2.getHeight());
//		plat4.setPosition(300, 347 + 3*plat2.getHeight());
//		plat5.setPosition(300, 346 + 4*plat2.getHeight());

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
				// ship-to-ship collision
				for(Ship other: players) {
					if(player.collidesWith(other) && other.getPlayerNum() != player.getPlayerNum()) {
//						System.out.println("COLLISION");
						
						Rectangle myHB = player.getHitbox();
						Rectangle otherHB = player.getHitbox();
						Rectangle overlap = myHB.intersection(otherHB);
						if (overlap.width < overlap.height) {
							player.setNrg((int) (player.getNrg()-Math.abs(2*other.getM()*other.getXv())));
							other.setNrg((int) (other.getNrg()-Math.abs(2*player.getM()*player.getXv())));
							System.out.println(player.getNrg());
							// momentum formulae
							double oldV1 = player.getXv();
							player.setXv((elasticity*other.getM()*other.getXv() + player.getXv()*(player.getM()-elasticity*other.getM()))/(other.getM() + player.getM()));  
							other.setXv((elasticity *player.getM()*oldV1 + other.getXv()*(other.getM()-elasticity*player.getM()))/(other.getM() + player.getM()));
							// Dampen is currently 0.8, can change later
						} else {// coming from top or bottom
							player.setNrg((int) (player.getNrg()-Math.abs(2*other.getM()*other.getYv())));
							other.setNrg((int) (other.getNrg()-Math.abs(2*player.getM()*player.getYv())));
							double oldV2 = player.getYv();
							player.setYv((elasticity*other.getM()*other.getYv() + player.getYv()*(player.getM()-elasticity*other.getM()))/(other.getM() + player.getM()));  
							other.setYv((elasticity*player.getM()*oldV2 + other.getYv()*(other.getM()-elasticity*player.getM()))/(other.getM() + player.getM()));
						}
					}
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
							//SoundManager.playMusic("bullet.wav");

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
