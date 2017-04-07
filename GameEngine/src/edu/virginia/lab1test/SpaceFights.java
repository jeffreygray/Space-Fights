// updated to print out energy and decrement upon hitting L

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
	static int gameWidth = 1500;
	static int gameHeight = 900;
	Sprite scene, plat1, plat2, plat3, plat4, plat5, plat6;
	Ship player1, player2;
	Sprite p1nrgFront, p1nrgBack, p2nrgFront, p2nrgBack;
	ArrayList<Sprite> platforms = new ArrayList<Sprite>();
	ArrayList<Ship> players = new ArrayList<Ship>();
	CollisionManager collisionManager;


	public SpaceFights() {
		super("Space Fights", gameWidth, gameHeight);
		player1 = new Ship(ShipType.Rhino, 1);
		player2 = new Ship(ShipType.Vulture, 2);
		
		scene = new Sprite("scene", "background.png");
		scene.setScaleX(1);
		p1nrgFront = new Sprite("nrgFront", "frontNRG.png");
		p1nrgBack = new Sprite("nrgBack", "rearNRG.png");

		p2nrgFront = new Sprite("nrgFront", "frontNRG.png");
		p2nrgBack = new Sprite("nrgBack", "rearNRG.png");

		plat1 = new Sprite("plat1", "platformSpace.png");
		plat2 = new Sprite("plat2", "platformSmall.png");
		plat3 = new Sprite("plat3", "platformSpace.png");
		plat4 = new Sprite("plat4", "platformSmall.png");
		plat5 = new Sprite("plat5", "platformSmall.png");
		plat6 = new Sprite("plat6", "platformSmall.png");



		collisionManager = new CollisionManager();
		
		double p1Scale = 1.15;
		player1.setPosition(800, 350);
		player1.setScaleX(0.8 * p1Scale);
		player1.setScaleY(0.65 * p1Scale);

		player2.setPosition(300,150);
		player2.setScaleX(0.8);
		player2.setScaleY(0.65);
		
		player1.setPivotPoint(player1.getWidth()/2, player1.getHeight()/2);
		player2.setPivotPoint(player2.getWidth()/2, player2.getHeight()/2);
		
		p1nrgBack.setScaleY(-0.8);
		p1nrgBack.setScaleX(0.6);
		p2nrgBack.setScaleY(-0.8);
		p2nrgBack.setScaleX(0.6);
		
		scene.addChild(p1nrgBack);
		scene.addChild(p2nrgBack);
		p1nrgBack.addChild(p1nrgFront);
		p2nrgBack.addChild(p2nrgFront);
		
		//adding attributes to scene
		scene.addChild(player1);
		scene.addChild(player2);
		scene.addChild(plat1);
		scene.addChild(plat2);
		scene.addChild(plat3);
		scene.addChild(plat4);
		scene.addChild(plat5);
		scene.addChild(plat6);
		plat1.setParent(scene);
		plat2.setParent(scene);
		plat3.setParent(scene);
		plat4.setParent(scene);
		plat5.setParent(scene);
		plat6.setParent(scene);

		//placing location of platforms
		plat1.setPosition(300, 350);
		plat2.setPosition(300,350 + plat2.getHeight());
		plat3.setPosition(300,800);
		plat4.setPosition(300,350 + 2 * plat2.getHeight());
		plat5.setPosition(300,350 + 3 * plat2.getHeight());
		plat6.setPosition(300,350 + 4 * plat2.getHeight());
		

		//updating platforms and players arrays with their constituents
		platforms.add(plat1);
		platforms.add(plat2);
		platforms.add(plat3);
		platforms.add(plat4);
		platforms.add(plat5);
		platforms.add(plat6);
		//
		players.add(player1);
		players.add(player2);
		//
		
		for(Ship player : players) {
			player.addEventListener(collisionManager, CollisionEvent.PLATFORM);
			player.addEventListener(collisionManager, CollisionEvent.DEATH);
		}

	}

	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		if(scene != null) { // makes sure we loaded everything 
			scene.update(pressedKeys, controllers);
			// adjusts the scale of the player nrg bars based on current nrg levels
			// ~ move to Ship class?
			p1nrgFront.setScaleX((double) player1.getNrg()/player1.getShipType().getNrgCap());
			p2nrgFront.setScaleX((double) player2.getNrg()/player2.getShipType().getNrgCap());
				
			Point shipPos = player1.getPosition();
			if(shipPos.x < 0) {
				player1.setXPosition(0);
				player1.setXv(-player1.getXv());
			}
			else if(shipPos.x > gameWidth-player1.getWidth()) {
				player1.setXPosition(gameWidth-player1.getWidth());
				player1.setXv(-player1.getXv());
			}
			if(shipPos.y < 0){
				player1.setYPosition(0);
				player1.setYv(-player1.getYv());
			}
			else if(shipPos.y > gameHeight-player1.getHeight()) {
				player1.setYPosition(gameHeight-player1.getHeight());
				player1.setYv(-player1.getYv());
			}
			
			// collisions p1
			for(Sprite plat: platforms) {
				for(Ship player: players) {
					if(player.collidesWith(plat)) {
						player.dispatchEvent(new Event(CollisionEvent.PLATFORM, player));
						
						Rectangle mHB = player.getHitbox();
						Rectangle pHB = plat.getHitbox();
						Rectangle overlap = mHB.intersection(pHB);
						
						if(overlap.width < overlap.height) {
							// coming from side; which side?
							if(mHB.x > pHB.x) {  // collision with right side of platform
								if(player.getScaleX() > 0) // facing right
									player.setPosition(pHB.getX() + pHB.getWidth(), player.getPosition().y);
								else
									player.setPosition(pHB.getX() + pHB.getWidth() + mHB.getWidth(), player.getPosition().y);
								
							}
							else player.setPosition(pHB.getX() - mHB.getWidth(), player.getPosition().y); // on left side
							//System.out.println("LR WALL HIT");
							player.setXv(-0.8*player.getXv()); // Dampen is currently 0.8, can change later
						} else {
							// coming from top or bottom
							if(mHB.y < pHB.y) {
								player.setPosition(player.getPosition().x, pHB.getY() - mHB.getHeight()); //above
							}
							else player.setPosition(player.getPosition().x, pHB.getY() + pHB.getHeight()); // below
							player.setYv(-0.8*player.getYv());
						}
					}
					for(Projectile p: player.getProjectiles()) {
						if(p.collidesWith(plat)) {
							p.dispatchEvent(new Event(CollisionEvent.PLATFORM, p));
							
							Rectangle mHB = p.getHitbox();
							Rectangle pHB = plat.getHitbox();
							Rectangle overlap = mHB.intersection(pHB);
							
							if(overlap.width < overlap.height) {
								// coming from side; which side?
								if(mHB.x > pHB.x) {  // collision with right side of platform
									if(p.getScaleX() > 0) // facing right
										p.setPosition(pHB.getX() + pHB.getWidth(), p.getPosition().y);
									else
										p.setPosition(pHB.getX() + pHB.getWidth() + mHB.getWidth(), p.getPosition().y);
									
								}
								else p.setPosition(pHB.getX() - mHB.getWidth(), p.getPosition().y); // on left side
								//System.out.println("LR WALL HIT");
								p.setXv(-p.getXv()); // Dampen is currently 0.8, can change later
							} else {
								// coming from top or bottom
								if(mHB.y < pHB.y) {
									p.setPosition(p.getPosition().x, pHB.getY() - mHB.getHeight()); //above
								}
								else p.setPosition(p.getPosition().x, pHB.getY() + pHB.getHeight()); // below
								p.setYv(-p.getYv());
							}
						} 
						// for each player, check if this projectile collides with it
						for(Ship s:players) {
							// checks with collision with enemies
							if(p.collidesWith(s) && s.getPlayerNum() != player.getPlayerNum()) {
								s.setNrg(s.getNrg()-p.getDamage());
								p.setRemove(true);
							}
						}
					}
				}
			}
			
			p1nrgBack.setPosition(shipPos.x-player1.getWidth()/2, shipPos.y-player1.getHeight()/3);
			
			shipPos = player2.getPosition();
			if(shipPos.x < 0) {
				player2.setXPosition(0);
				player2.setXv(-player2.getXv());
			}
			else if(shipPos.x > gameWidth-player2.getWidth()) {
				player2.setXPosition(gameWidth-player2.getWidth());
				player2.setXv(-player2.getXv());
			}
			if(shipPos.y < 0){
				player2.setYPosition(0);
				player2.setYv(-player2.getYv());
			}
			else if(shipPos.y > gameHeight-player2.getHeight()) {
				player2.setYPosition(gameHeight-player2.getHeight());
				player2.setYv(-player2.getYv());
			}
			
			p2nrgBack.setPosition(shipPos.x-player2.getWidth()/2, shipPos.y-player2.getHeight()/3);

			
		}
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
