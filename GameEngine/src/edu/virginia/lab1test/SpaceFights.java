// updated to print out energy and decrement upon hitting L
// TODO: 4.7.2017 ships and ships bounce, lasers destroy upon hitting platforms, player agnosticism
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

public class SpaceFights extends Game {
	static int gameWidth = 1800;
	static int gameHeight = 1000;
	Sprite scene, plat1, plat2, plat3, plat4, plat5;
	Ship player1, player2, player3;
//	Sprite p1nrgFront, p1nrgBack, p2nrgFront, p2nrgBack;
	ArrayList<Sprite> platforms = new ArrayList<Sprite>();
	ArrayList<Ship> players = new ArrayList<Ship>();
	CollisionManager collisionManager;


	public SpaceFights() {
		super("Space Fights", gameWidth, gameHeight);
		player1 = new Ship(ShipType.Vulture, 1);
		player2 = new Ship(ShipType.Vulture, 2);
		player3 = new Ship(ShipType.Vulture, 3);

		
		players.add(player1);
		players.add(player2);
		players.add(player3);
		
		scene = new Sprite("scene", "background.png");
		scene.setScaleX(1);

/*		p1nrgFront = new Sprite("nrgFront", "frontNRG.png");
		p1nrgBack = new Sprite("nrgBack", "rearNRG.png");

		p2nrgFront = new Sprite("nrgFront", "frontNRG.png");
		p2nrgBack = new Sprite("nrgBack", "rearNRG.png");
*/
		plat1 = new Sprite("plat1", "platformSpace.png");
		plat2 = new Sprite("plat2", "platformSmall.png");
		plat3 = new Sprite("plat3", "platformSmall.png");
		plat4 = new Sprite("plat4", "platformSmall.png");
		plat5 = new Sprite("plat5", "platformSmall.png");

		platforms.add(plat1);
		platforms.add(plat2);
		platforms.add(plat3);
		platforms.add(plat4);
		platforms.add(plat5);

		
		collisionManager = new CollisionManager();


		player1.setPosition(800, 350);
		player2.setPosition(300,150);
		player3.setPosition(1,1);
	

/*		
		p1nrgBack.setScaleY(-0.8);
		p1nrgBack.setScaleX(0.6);
		p2nrgBack.setScaleY(-0.8);
		p2nrgBack.setScaleX(0.6);

		scene.addChild(p1nrgBack);
		scene.addChild(p2nrgBack);
		p1nrgBack.addChild(p1nrgFront);
		p2nrgBack.addChild(p2nrgFront); */
		
		for(int i = 0; i < platforms.size(); i++) {
			Sprite plat = platforms.get(i);
			scene.addChild(plat);
		}
			/*scene.addChild(plat1);
		scene.addChild(plat2);
		scene.addChild(plat3);
		scene.addChild(plat4);
		scene.addChild(plat5);
*/
		//		
		plat1.setPosition(300, 350);
		plat2.setPosition(300, 350 + plat2.getHeight());
		plat3.setPosition(300, 350 +  2*plat2.getHeight());
		plat4.setPosition(300, 350 + 3*plat2.getHeight());
		plat5.setPosition(300, 350 + 4*plat2.getHeight());




				
		for(Ship player : players) {
			player.addEventListener(collisionManager, CollisionEvent.PLATFORM);
			player.addEventListener(collisionManager, CollisionEvent.DEATH);
			player.setScaleX(0.8);
			player.setScaleY(0.65);
			player.setPivotPoint(player.getWidth()/2, player.getHeight()/2);
			scene.addChild(player);
		
			
		}

	}

	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		if (scene != null) { // makes sure we loaded everything
			scene.update(pressedKeys, controllers);
			// adjusts the scale of the player nrg bars based on current nrg
			// levels
			// ~ move to Ship class?
//			p1nrgFront.setScaleX((double) player1.getNrg() / player1.getShipType().getNrgCap());
//			p2nrgFront.setScaleX((double) player2.getNrg() / player2.getShipType().getNrgCap());
			for(Ship player: players) {
				Point shipPos = player.getPosition();
				if (shipPos.x < 0) {
					player.setXPosition(0);
					player.setXv(-player1.getXv());
				} else if (shipPos.x > gameWidth - player.getWidth()) {
					player.setXPosition(gameWidth - player.getWidth());
					player.setXv(-player1.getXv());
				}
				if (shipPos.y < 0) {
					player.setYPosition(0);
					player.setYv(-player.getYv());
				} else if (shipPos.y > gameHeight - player.getHeight()) {
					player.setYPosition(gameHeight - player.getHeight());
					player.setYv(-player.getYv());
				}
			}
			// collisions p1
			/*
			 * for players:
			 *   for each plat:
			 *   	check collide
			 *   	for each for each proj:
			 *     		check collide w/ plat
			 *   for each projectile
			 *   	for each ship
			 *     		check collide
			 *     
			 * 
			 */

			for(Ship player: players) {
				for(Sprite plat: platforms) {
					if (player.collidesWith(plat)) {
						player.dispatchEvent(new Event(CollisionEvent.PLATFORM, player));

						Rectangle mHB = player.getHitbox();
						Rectangle pHB = plat.getHitbox();
						Rectangle overlap = mHB.intersection(pHB);

						if (overlap.width < overlap.height) {
							// coming from side; which side?
							if (mHB.x > pHB.x) { // collision with right side of
								// platform
								if (player.getScaleX() > 0) // facing right
									player.setPosition(pHB.getX() + pHB.getWidth(), player.getPosition().y);
								else
									player.setPosition(pHB.getX() + pHB.getWidth() + mHB.getWidth(),
											player.getPosition().y);

							} else
								player.setPosition(pHB.getX() - mHB.getWidth(), player.getPosition().y); // on
							// left
							// side
							// System.out.println("LR WALL HIT");
							player.setXv(-0.8 * player.getXv()); // Dampen is
							// currently
							// 0.8, can
							// change
							// later
						} else {
							// coming from top or bottom
							if (mHB.y < pHB.y) {
								player.setPosition(player.getPosition().x, pHB.getY() - mHB.getHeight()); // above
							} else
								player.setPosition(player.getPosition().x, pHB.getY() + pHB.getHeight()); // below
							player.setYv(-0.8 * player.getYv());
						}

					}
					for (Projectile p : player.getProjectiles()) {
						if (p.collidesWith(plat)) {
							p.dispatchEvent(new Event(CollisionEvent.PLATFORM, p));

							Rectangle mHB = p.getHitbox();
							Rectangle pHB = plat.getHitbox();
							Rectangle overlap = mHB.intersection(pHB);

							if (overlap.width < overlap.height) {
								// coming from side; which side?
								if (mHB.x > pHB.x) { // collision with right
									// side of platform
									if (p.getScaleX() > 0) // facing right
										p.setPosition(pHB.getX() + pHB.getWidth(), p.getPosition().y);
									else
										p.setPosition(pHB.getX() + pHB.getWidth() + mHB.getWidth(),
												p.getPosition().y);

								} else
									p.setPosition(pHB.getX() - mHB.getWidth(), p.getPosition().y); // on
								// left
								// side
								// System.out.println("LR WALL HIT");
								p.setXv(-p.getXv()); // Dampen is currently
								// 0.8, can change
								// later
							} else {
								// coming from top or bottom
								if (mHB.y < pHB.y) {
									p.setPosition(p.getPosition().x, pHB.getY() - mHB.getHeight()); // above
								} else
									p.setPosition(p.getPosition().x, pHB.getY() + pHB.getHeight()); // below
								p.setYv(-p.getYv());
							}
						}
					}
				}
				for (Projectile p : player.getProjectiles()) {
					for (Ship s : players) {
						// checks with collision with enemies
						if (p.collidesWith(s) && s.getPlayerNum() != player.getPlayerNum()) {
							s.setNrg(s.getNrg() - p.getDamage());
							p.setRemove(true);
						}
					}
				}

			}
			
			
		}
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
