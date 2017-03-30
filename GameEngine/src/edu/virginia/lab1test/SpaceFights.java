// updated to print out energy and decrement upon hitting L

package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.display.*;

public class SpaceFights extends Game {
	Sprite scene;
	PhysicsSprite ship;
	double shipAccel = 0.8;
	final int MAX_SPEED = 10;

	public SpaceFights() {
		super("Space Fights", 1400, 700);
		String shipImage[] = {"ship.png"};
		ship = new PhysicsSprite("ship", "ship.png", 0, 0, 0, 0, 0, 1000);
		scene = new Sprite("scene");
		
		ship.setPosition(700, 350);
		ship.setScaleX(0.8);
		ship.setScaleY(0.7);
		ship.setPivotPoint(ship.getWidth()/2, ship.getHeight()/2);
		
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
			
			if(pressedKeys.contains("L")){
				ship.setNrg(ship.getNrg()-5);
//				System.out.println(ship.getNrg());
			}
				
			if(pressedKeys.contains("Space")) {
				ship.setPosition(700, 350);
				xv = 0;
				yv = 0;
				ship.setYa(0);
				ship.setXa(0);
			}
		
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
				
		}
	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(scene !=null)
			scene.draw(g);
	}
	
	public static void main(String[] args) {
		SpaceFights game = new SpaceFights();
		game.start();
	}

}
