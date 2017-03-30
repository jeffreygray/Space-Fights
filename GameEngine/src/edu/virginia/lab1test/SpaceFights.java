// updated to print out energy and decrement upon hitting L

package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.display.*;

public class SpaceFights extends Game {
	Sprite scene;
	PhysicsSprite ship;
	int speed = 2;
	final int MAX_SPEED = 10;

	public SpaceFights() {
		super("Space Fights", 1400, 700);
		String shipImage[] = {"ship.png"};
		ship = new PhysicsSprite("ship", "ship.png", 0, 0, 0, 0, 0, 1000);
		scene = new Sprite("scene");
		ship.setPivotPoint(Math.round(ship.getWidth()/2), Math.round(ship.getHeight()/2));
		ship.setPosition(700, 350);
		
		scene.addChild(ship);
	}

	@Override
	public void update(ArrayList<String> pressedKeys) {
		if(scene != null) { // makes sure we loaded everything 
			Point shipPos = ship.getPosition();
			double xv = ship.getXv();
			double yv = ship.getYv();
			ship.setXa(0);
			ship.setYa(0);
			if(pressedKeys.contains("Up")) {
//				System.out.println(ship.getRotation());
				//System.out.println("BEFORE XV: " + xv +"\t YV: " + yv);
				ship.setYa(-(int) (Math.sin(Math.toRadians(ship.getRotation()+90)) * speed * .6));
				ship.setXa((int) (-Math.cos(Math.toRadians(ship.getRotation()+90)) * speed * .6));
				System.out.println("XV: " + xv +"\t YV: " + yv);
//				System.out.println(-Math.cos(Math.toRadians(ship.getRotation()+90)));
			}
			
			if(pressedKeys.contains("Down")) {
				ship.setYa((int) (Math.sin(Math.toRadians(ship.getRotation()+90)) * speed * .6));
				ship.setXa(-(int) (-Math.cos(Math.toRadians(ship.getRotation()+90)) * speed * .6));
				System.out.println("XV: " + xv +"\t YV: " + yv);

			}
			
			if(pressedKeys.contains("Left")) {
				ship.setRotation(ship.getRotation()-3);
			}
			
			if(pressedKeys.contains("Right")) {
				ship.setRotation(ship.getRotation()+3);
			}
			
			if(pressedKeys.contains("L")){
				ship.setNrg(ship.getNrg()-5);
//				System.out.println(ship.getNrg());
			}
				
			if(pressedKeys.contains("Space")) {

				ship.setPosition(700, 350);
				ship.setXa(0);
				ship.setXv(0);
				ship.setYa(0);
				ship.setYv(0);
			}
		

			
			ship.setXv(xv);
			ship.setYv(yv);
			ship.setScaleX(0.8);
			ship.setScaleY(0.8);
			scene.update(pressedKeys);
			
			shipPos = ship.getPosition();
			if(ship.getPosition().x < 0 || shipPos.x > 1400-ship.getWidth())
				ship.setXv(-ship.getXv());
			if(shipPos.y < 0 || shipPos.y > 700-ship.getHeight())
				ship.setYv(-ship.getYv());
			
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
