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
	Sprite p1nrgFront, p1nrgBack, p2nrgFront, p2nrgBack;

	public SpaceFights() {
		super("Space Fights", 1200, 700);
		player1 = new Ship("ship", "ship.png", 10, 5, 1500, 1);
		player2 = new Ship("ship", "ship.png", 10, 5, 1500, 2);
		
		scene = new Sprite("scene", "background.png");
		scene.setScaleX(1);
		p1nrgFront = new Sprite("nrgFront", "frontNRG.png");
		p1nrgBack = new Sprite("nrgBack", "rearNRG.png");

		p2nrgFront = new Sprite("nrgFront", "frontNRG.png");
		p2nrgBack = new Sprite("nrgBack", "rearNRG.png");

		
		player1.setPosition(800, 350);
		player1.setScaleX(0.8);
		player1.setScaleY(0.65);

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
		scene.addChild(player1);
		scene.addChild(player2);
	}

	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		if(scene != null) { // makes sure we loaded everything 
			scene.update(pressedKeys, controllers);
			
			// adjusts the scale of the player nrg bars based on current nrg levels
			// ~ move to Ship class?
			p1nrgFront.setScaleX(player1.getNrg()/1500.0);
			p2nrgFront.setScaleX(player2.getNrg()/1500.0);
				
			Point shipPos = player1.getPosition();
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
			
			shipPos = player2.getPosition();
			if(shipPos.x < 0) {
				player2.setXPosition(0);
				player2.setXv(-player2.getXv());
			}
			else if(shipPos.x > 1200-player2.getWidth()) {
				player2.setXPosition(1200-player2.getWidth());
				player2.setXv(-player2.getXv());
			}
			if(shipPos.y < 0){
				player2.setYPosition(0);
				player2.setYv(-player2.getYv());
			}
			else if(shipPos.y > 700-player2.getHeight()) {
				player2.setYPosition(700-player2.getHeight());
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
