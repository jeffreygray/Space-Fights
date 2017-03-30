// updated to print out energy and decrement upon hitting L

package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.util.GameClock;
import edu.virginia.spacefights.classes.Projectile;

public class SpaceFights extends Game {
	Sprite scene;
	PhysicsSprite ship;
	ArrayList<Projectile> projectiles;
	Sprite nrgFront, nrgBack;
	double lastShot;
	double shipAccel = 0.8;
	final int MAX_SPEED = 10;
	private GameClock clock;

	public SpaceFights() {
		super("Space Fights", 1400, 700);
		String shipImage[] = {"ship.png"};
		projectiles = new ArrayList<Projectile>();
		ship = new PhysicsSprite("ship", "ship.png", 0, 0, 0, 0, 0, 1500);
		scene = new Sprite("scene");
		nrgFront = new Sprite("nrgFront", "frontNRG.png");
		nrgBack = new Sprite("nrgBack", "rearNRG.png");
		lastShot = 0;
		clock = new GameClock();
		
		ship.setPosition(700, 350);
		ship.setScaleX(0.8);
		ship.setScaleY(0.65);
		ship.setPivotPoint(ship.getWidth()/2, ship.getHeight()/2);
		
		nrgBack.setScaleY(-nrgBack.getScaleY()*0.8);
		nrgBack.setScaleX(0.6);
		
		scene.addChild(nrgBack);
		nrgBack.addChild(nrgFront);
		scene.addChild(ship);
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		if(scene != null) { // makes sure we loaded everything 
			Point shipPos = ship.getPosition();
			double rotation = ship.getRotation()-90; // will save resources by not making method calls
			double xv = ship.getXv();
			double yv = ship.getYv();
			ship.setXa(0);
			ship.setYa(0);
			
			if(pressedKeys.contains("Up") && Math.hypot(xv, yv) < MAX_SPEED) {
//				System.out.println(ship.getRotation());
				//System.out.println("BEFORE XV: " + xv +"\t YV: " + yv);
				ship.setYa(Math.sin(Math.toRadians(rotation)) * shipAccel);
				ship.setXa(Math.cos(Math.toRadians(rotation)) * shipAccel);
				//System.out.println("XV: " + xv +"\t YV: " + yv);
//				System.out.println(-Math.cos(Math.toRadians(ship.getRotation()+90)));
			}
			if(pressedKeys.contains("Down") && Math.hypot(xv, yv) < MAX_SPEED) {
				ship.setYa(-Math.sin(Math.toRadians(rotation)) * shipAccel);
				ship.setXa(-Math.cos(Math.toRadians(rotation)) * shipAccel);
				System.out.println("XV: " + xv +"\t YV: " + yv);
			}
			if(pressedKeys.contains("Left")) {
				ship.setRotation(ship.getRotation()-5);
			}
			
			if(pressedKeys.contains("Right")) {
				ship.setRotation(ship.getRotation()+5);
			}
			
			/* Fires projectiles if have adequate energy and enough time has passed for the CD */
			if(pressedKeys.contains("Space") && clock.getElapsedTime() >= lastShot+333 && ship.getNrg() >= 200) {
				ship.setNrg(Math.max(ship.getNrg()-200, 0));
				lastShot = clock.getElapsedTime();
				double x = shipPos.x + ship.getPivotPoint().x + Math.cos(Math.toRadians(rotation))*ship.getHeight()/2;
				double y = shipPos.y + ship.getPivotPoint().y + Math.sin(Math.toRadians(rotation))*ship.getWidth()/2;
				
				projectiles.add(new Projectile("bullet", "coin.png", x, y, rotation));
			}
			
			if(ship.getNrg() < 1500) {
				ship.setNrg(ship.getNrg()+200/60); 
			}
			nrgFront.setScaleX(ship.getNrg()/1500.0);
			
		
			ship.setXv(xv*0.99); // the 0.99 here and in next line act as "drag", slowing the ship down slightly over time
			ship.setYv(yv*0.99);
			
			scene.update(pressedKeys);
			
			shipPos = ship.getPosition();
			if(shipPos.x < 0) {
				ship.setXPosition(0);
				ship.setXv(-ship.getXv());
			}
			else if(shipPos.x > 1400-ship.getWidth()) {
				ship.setXPosition(1400-ship.getWidth());
				ship.setXv(-ship.getXv());
			}
			if(shipPos.y < 0){
				ship.setYPosition(0);
				ship.setYv(-ship.getYv());
			}
			else if(shipPos.y > 700-ship.getHeight()) {
				ship.setYPosition(700-ship.getHeight());
				ship.setYv(-ship.getYv());
			}
			
			
			nrgBack.setPosition(shipPos.x-ship.getWidth()/2, shipPos.y-ship.getHeight()/3);

			for(int i = 0; i < projectiles.size(); i++) {
				Projectile p = projectiles.get(i);
				p.update(pressedKeys);
				if(p.shouldRemove())
					projectiles.remove(i);
			}
		}
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(scene !=null) {
			scene.draw(g);
			for(Projectile p : projectiles)
				p.draw(g);
		}
	}
	
	public static void main(String[] args) {
		SpaceFights game = new SpaceFights();
		game.start();
	}

}
