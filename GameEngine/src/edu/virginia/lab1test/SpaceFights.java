// updated to print out energy and decrement upon hitting L

package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.util.GameClock;
import edu.virginia.spacefights.classes.Projectile;
import edu.virginia.spacefights.classes.Ship;

public class SpaceFights extends Game {
	Sprite scene;
	Ship player1, player2;
	ArrayList<Projectile> projectiles;
	Sprite p1nrgFront, p1nrgBack, p2nrgFront, p2nrgBack;
	double lastShotP1, lastShotP2;
	double shipAccel = 0.8;
	final int MAX_SPEED = 10;
	private GameClock clock;

	public SpaceFights() {
		super("Space Fights", 1200, 700);
		String shipImage[] = {"ship.png"};
		projectiles = new ArrayList<Projectile>();
		player1 = new Ship("ship", "ship.png", 10, 5, 1500);
		player2 = new Ship("ship", "ship.png", 10, 5, 1500);
		
		scene = new Sprite("scene", "background.png");
		scene.setScaleX(1);
		p1nrgFront = new Sprite("nrgFront", "frontNRG.png");
		p1nrgBack = new Sprite("nrgBack", "rearNRG.png");

		p2nrgFront = new Sprite("nrgFront", "frontNRG.png");
		p2nrgBack = new Sprite("nrgBack", "rearNRG.png");
		
		lastShotP1 = 0;
		lastShotP2 = 0;
		
		clock = new GameClock();
		
		player1.setPosition(700, 350);
		player1.setScaleX(0.8);
		player1.setScaleY(0.65);

		player2.setScaleX(0.8);
		player2.setScaleY(0.65);
		
		player1.setPivotPoint(player1.getWidth()/2, player1.getHeight()/2);
		player2.setPivotPoint(player2.getWidth()/2, player2.getHeight()/2);
		
		p1nrgBack.setScaleY(-p1nrgBack.getScaleY()*0.8);
		p1nrgBack.setScaleX(0.6);
		
		scene.addChild(p1nrgBack);
		p1nrgBack.addChild(p1nrgFront);
		scene.addChild(player1);
		scene.addChild(player2);
	}

	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		if(scene != null) { // makes sure we loaded everything 
			Point shipPos = player1.getPosition();
			double rotation = player1.getRotation()-90; // will save resources by not making method calls
			double rotation2 = player2.getRotation()-90;
			double xv = player1.getXv();
			double yv = player1.getYv();
			player1.setXa(0);
			player1.setYa(0);
			
			double xv2 = player2.getXv();
			double yv2 = player2.getYv();
			player2.setXa(0);
			player2.setYa(0);
			
			if(pressedKeys.contains("Up") && Math.hypot(xv, yv) < MAX_SPEED) {
//				System.out.println(ship.getRotation());
				//System.out.println("BEFORE XV: " + xv +"\t YV: " + yv);
				player1.setYa(Math.sin(Math.toRadians(rotation)) * shipAccel);
				player1.setXa(Math.cos(Math.toRadians(rotation)) * shipAccel);
				//System.out.println("XV: " + xv +"\t YV: " + yv);
//				System.out.println(-Math.cos(Math.toRadians(ship.getRotation()+90)));
			}
			if(pressedKeys.contains("Down") && Math.hypot(xv, yv) < MAX_SPEED) {
				player1.setYa(-Math.sin(Math.toRadians(rotation)) * shipAccel);
				player1.setXa(-Math.cos(Math.toRadians(rotation)) * shipAccel);
				System.out.println("XV: " + xv +"\t YV: " + yv);
			}
			if(pressedKeys.contains("Left")) {
				player1.setRotation(player1.getRotation()-5);
			}
			
			if(pressedKeys.contains("Right")) {
				player1.setRotation(player1.getRotation()+5);
			}
			
			/* Fires projectiles if have adequate energy and enough time has passed for the CD */
			if(pressedKeys.contains("Space") && clock.getElapsedTime() >= lastShotP1+333 && player1.getNrg() >= 200) {
				player1.setNrg(Math.max(player1.getNrg()-200, 0));
				lastShotP1 = clock.getElapsedTime();
				double x = shipPos.x + player1.getPivotPoint().x + Math.cos(Math.toRadians(rotation))*player1.getHeight()/2;
				double y = shipPos.y + player1.getPivotPoint().y + Math.sin(Math.toRadians(rotation))*player1.getWidth()/2;
				
				projectiles.add(new Projectile("bullet", "coin.png", x, y, rotation));
			}
			
			// Begin Player2 inputs
			//NumPad-1
			if(pressedKeys.contains("NumPad-5") && Math.hypot(xv2, yv2) < MAX_SPEED) {
//				System.out.println(ship.getRotation());
				//System.out.println("BEFORE XV: " + xv +"\t YV: " + yv);
				player2.setYa(Math.sin(Math.toRadians(rotation2)) * shipAccel);
				player2.setXa(Math.cos(Math.toRadians(rotation2)) * shipAccel);
				//System.out.println("XV: " + xv +"\t YV: " + yv);
//				System.out.println(-Math.cos(Math.toRadians(ship.getRotation()+90)));
			}
			if(pressedKeys.contains("NumPad-2") && Math.hypot(xv2, yv2) < MAX_SPEED) {
				player2.setYa(-Math.sin(Math.toRadians(rotation2)) * shipAccel);
				player2.setXa(-Math.cos(Math.toRadians(rotation2)) * shipAccel);
				//System.out.println("XV: " + xv +"\t YV: " + yv);
			}
			if(pressedKeys.contains("NumPad-1")) {
				player2.setRotation(player2.getRotation()-5);
			}
			
			if(pressedKeys.contains("NumPad-3")) {
				player2.setRotation(player2.getRotation()+5);
			}
			
			/* Fires projectiles if have adequate energy and enough time has passed for the CD */
			if(pressedKeys.contains("Ctrl") && clock.getElapsedTime() >= lastShotP2+333 && player2.getNrg() >= 200) {
				player2.setNrg(Math.max(player2.getNrg()-200, 0));
				lastShotP2 = clock.getElapsedTime();
				double x = shipPos.x + player2.getPivotPoint().x + Math.cos(Math.toRadians(rotation2))*player2.getHeight()/2;
				double y = shipPos.y + player2.getPivotPoint().y + Math.sin(Math.toRadians(rotation2))*player2.getWidth()/2;
				
				projectiles.add(new Projectile("bullet", "coin.png", x, y, rotation2));
			}
			
			if(player1.getNrg() < 1500) {
				player1.setNrg(player1.getNrg()+200/60); 
			}
			p1nrgFront.setScaleX(player1.getNrg()/1500.0);
			if(player2.getNrg() < 1500) {
				player2.setNrg(player2.getNrg()+200/60); 
			}
			p2nrgFront.setScaleX(player2.getNrg()/1500.0);
			
		
			player1.setXv(xv*0.99); // the 0.99 here and in next line act as "drag", slowing the ship down slightly over time
			player1.setYv(yv*0.99);
			
			
			scene.update(pressedKeys, controllers);
			
			shipPos = player1.getPosition();
			if(shipPos.x < 0) {
				player1.setXPosition(0);
				player1.setXv(-player1.getXv());
			}
			else if(shipPos.x > 1200-player1.getWidth()) {
				player1.setXPosition(1200-player1.getWidth());
				player1.setXv(-player1.getXv());
			}
			if(shipPos.y < 0){
				player1.setYPosition(0);
				player1.setYv(-player1.getYv());
			}
			else if(shipPos.y > 700-player1.getHeight()) {
				player1.setYPosition(700-player1.getHeight());
				player1.setYv(-player1.getYv());
			}
			
			
			p1nrgBack.setPosition(shipPos.x-player1.getWidth()/2, shipPos.y-player1.getHeight()/3);

			for(int i = 0; i < projectiles.size(); i++) {
				Projectile p = projectiles.get(i);
				p.update(pressedKeys, controllers);
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
