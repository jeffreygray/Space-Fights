package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.display.DisplayObject;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;

public class LabThreeGame extends Game {

	private DisplayObjectContainer scene, center;
	private DisplayObjectContainer planet1, planet2, planet3, planet4, planet5, planet6, planet8;
	private DisplayObjectContainer moon3, moon4_1, moon4_2, moon5, moon8;
	private DisplayObjectContainer sun, rotation1, rotation2, rotation3, rotation4, rotation5, rotation6, rotation8;
	private DisplayObjectContainer m3rotation, m4rotation, m5rotation, m8rotation;
	
	public LabThreeGame() {
		super("Lab Three Solar System", 1200, 700);
		scene = new DisplayObjectContainer("Scene");
		center = new DisplayObjectContainer("center");
		planet1 = new DisplayObjectContainer("planet_1", "planet_1.png");
		planet2 = new DisplayObjectContainer("planet_2", "planet_2.png");
		planet3 = new DisplayObjectContainer("planet_3", "planet_3.png");
		planet4 = new DisplayObjectContainer("planet_4", "planet_4.png");
		planet5 = new DisplayObjectContainer("planet_5", "planet_5.png");
		planet6 = new DisplayObjectContainer("planet_6", "planet_6.png");
		planet8 = new DisplayObjectContainer("planet_8", "planet_8.png");
		
		moon3 = new DisplayObjectContainer("moon3", "moon.png");
		moon4_1 = new DisplayObjectContainer("moon4_1", "moon.png");
		moon4_2 = new DisplayObjectContainer("moon4_2", "moon.png");
		moon5 = new DisplayObjectContainer("moon5", "moon.png");
		moon8 = new DisplayObjectContainer("moon8", "moon.png");
		
		sun = new DisplayObjectContainer("sun", "sun.png");
		rotation1 = new DisplayObjectContainer("r1");
		rotation2 = new DisplayObjectContainer("r2");
		rotation3 = new DisplayObjectContainer("r3");
		rotation4 = new DisplayObjectContainer("r4");
		rotation5 = new DisplayObjectContainer("r5");
		rotation6 = new DisplayObjectContainer("r6");
		rotation8 = new DisplayObjectContainer("r8");

		m3rotation = new DisplayObjectContainer("MR3");
		m4rotation = new DisplayObjectContainer("MR4");
		m5rotation = new DisplayObjectContainer("MR5");
		m8rotation = new DisplayObjectContainer("MR8");
		
		scene.addChild(center);
		center.addChild(sun);
		
		center.setParent(scene);
		sun.setParent(scene);
		
		sun.addChild(rotation1);
		sun.addChild(rotation2);
		sun.addChild(rotation3);
		sun.addChild(rotation4);
		sun.addChild(rotation5);
		sun.addChild(rotation6);
		sun.addChild(rotation8);
		
		rotation1.setParent(sun);
		rotation2.setParent(sun);
		rotation3.setParent(sun);
		rotation4.setParent(sun);
		rotation5.setParent(sun);
		rotation6.setParent(sun);
		rotation8.setParent(sun);
		
		rotation1.addChild(planet1);
		rotation2.addChild(planet2);
		rotation3.addChild(planet3);
		rotation4.addChild(planet4);
		rotation5.addChild(planet5);
		rotation6.addChild(planet6);
		rotation8.addChild(planet8);
		
		planet1.setParent(rotation1);
		planet2.setParent(rotation2);
		planet3.setParent(rotation3);
		planet4.setParent(rotation4);
		planet5.setParent(rotation5);
		planet6.setParent(rotation6);
		planet8.setParent(rotation8);
		
		planet3.addChild(m3rotation);
		m3rotation.setParent(planet3);
		m3rotation.addChild(moon3);
		moon3.setParent(m3rotation);
		
		planet4.addChild(m4rotation);
		m4rotation.setParent(planet4);
		m4rotation.addChild(moon4_1);
		moon4_1.setParent(m4rotation);
		m4rotation.addChild(moon4_2);
		moon4_2.setParent(m4rotation);
		
		planet5.addChild(m5rotation);
		m5rotation.setParent(planet5);
		m5rotation.addChild(moon5);
		moon5.setParent(m5rotation);
		
		planet8.addChild(m8rotation);
		m8rotation.setParent(planet8);
		m8rotation.addChild(moon8);
		moon8.setParent(m8rotation);
		
		center.setPosition(this.getMainFrame().getWidth()/2, this.getMainFrame().getHeight()/2);
		sun.setPosition(-50, -50);
		sun.setPivotPoint(50, 50);
		
		rotation1.setPivotPoint(50, 50);
		rotation2.setPivotPoint(50, 50);
		rotation3.setPivotPoint(75, 50);
		rotation4.setPivotPoint(50, 50);
		rotation5.setPivotPoint(50, 50);
		rotation6.setPivotPoint(50, 50);
		rotation8.setPivotPoint(50, 50);
		
		rotation2.setRotation(20);
		rotation3.setRotation(40);
		rotation4.setRotation(60);
		rotation5.setRotation(80);
		rotation6.setRotation(100);
		
		planet1.setPosition(45,-10);
		planet2.setPosition(120, 0);
		planet3.setPosition(200, 0);
		planet4.setPosition(300, 0);
		planet5.setPosition(480, 0);
		planet6.setPosition(600, 0);
		planet8.setPosition(700, 0);
		
		planet1.setPivotPoint(9, 9);
		planet2.setPivotPoint(13, 13);
		planet3.setPivotPoint(14, 14);
		planet4.setPivotPoint(15, 15);
		planet5.setPivotPoint(40, 40);
		planet6.setPivotPoint(35, 35);
		planet8.setPivotPoint(30, 30);
		
		moon3.setPosition(30, 0);
		moon4_1.setPosition(45, 0);
		moon4_2.setPosition(-10, 20);
		moon8.setPosition(50, -5);
		
		m3rotation.setPivotPoint(14, 14);
		m4rotation.setPivotPoint(15, 15);
		m5rotation.setPivotPoint(50, 50);
		m8rotation.setPivotPoint(20, 20);
		
	}

	public static void main(String[] args) {
		LabThreeGame game = new LabThreeGame();
		game.start();
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		if(planet8 != null) {
			/* Pan using arrow keys */
			Point p = scene.getPosition();
			if(pressedKeys.contains("Up"))
				p.translate(0, -25);
			if(pressedKeys.contains("Left"))
				p.translate(-25, 0);
			if(pressedKeys.contains("Down"))
				p.translate(0, 25);
			if(pressedKeys.contains("Right"))
				p.translate(25, 0);
			scene.setPosition(p);

			/* Rotate using A or S*/
			if(pressedKeys.contains("A"))
				sun.setRotation(sun.getRotation()-5);
			if(pressedKeys.contains("S"))
				sun.setRotation(sun.getRotation()+5);

			/* Zoom using Q or W */
			if(pressedKeys.contains("Q")) {
				center.setScaleX(center.getScaleX()+0.07);
				center.setScaleY(center.getScaleY()+0.07);
			}
			if(pressedKeys.contains("W")) {
				center.setScaleX(center.getScaleX()-0.07);
				center.setScaleY(center.getScaleY()-0.07);
			}

			/* planets rotate around the sun */
			sun.setRotation(sun.getRotation()+0.1);
			rotation1.setRotation(rotation1.getRotation()-8*5/planet1.getPosition().getY());
			rotation2.setRotation(rotation2.getRotation()+1.2*100/planet2.getPosition().getX());
			rotation3.setRotation(rotation3.getRotation()+1.0*170/planet3.getPosition().getX());
			rotation4.setRotation(rotation4.getRotation()-0.95*240/planet4.getPosition().getX());
			rotation5.setRotation(rotation5.getRotation()+0.8*400/planet5.getPosition().getX());
			rotation6.setRotation(rotation6.getRotation()+0.6*480/planet6.getPosition().getX());
			rotation8.setRotation(rotation8.getRotation()+0.3*580/planet8.getPosition().getX());

			/* planet have elliptical orbits */
			double sunRot = sun.getRotation();
			planet1.setPosition(45, -10+ 5*Math.cos(Math.toRadians(rotation1.getRotation()+sunRot)));
			planet2.setPosition(120 + 20*Math.sin(Math.toRadians(rotation2.getRotation()+sunRot)), 0);
			planet3.setPosition(200+30*Math.cos(Math.toRadians(rotation3.getRotation()+sunRot)), 0);
			planet4.setPosition(300+60*Math.sin(Math.toRadians(rotation4.getRotation()+sunRot)), 0);
			planet5.setPosition(480+80*Math.cos(Math.toRadians(rotation5.getRotation()+sunRot)), 0);
			planet6.setPosition(600+120*Math.sin(Math.toRadians(rotation6.getRotation()+sunRot)), 0);
			planet8.setPosition(700 + 120*Math.cos(Math.toRadians(rotation8.getRotation()+sunRot)), 60);
			
			/*planets rotate on axis */
			planet1.setRotation(planet1.getRotation()+1);
			planet2.setRotation(planet2.getRotation()+2);
			planet3.setRotation(planet3.getRotation()+0.7);
			planet4.setRotation(planet4.getRotation()+0.2);
			planet5.setRotation(planet5.getRotation()+2);
			planet6.setRotation(planet6.getRotation()+3);
			planet8.setRotation(planet8.getRotation()+1);
			
			/* moons rotate around their planets */
			m3rotation.setRotation(m3rotation.getRotation()+8);
			m4rotation.setRotation(m4rotation.getRotation()+3);
			m5rotation.setRotation(m5rotation.getRotation()+0.2);
			m8rotation.setRotation(m8rotation.getRotation()+5);
			/* in an ellipse */
			moon3.setPosition(30+15*Math.cos(Math.toRadians(m3rotation.getRotation())), 0);
			moon4_1.setPosition(45+20*Math.cos(Math.toRadians(m4rotation.getRotation())), 0);
			moon4_2.setPosition(-10+5*Math.cos(Math.toRadians(m4rotation.getRotation())), 20);
			moon8.setPosition(50+25*Math.cos(Math.toRadians(m8rotation.getRotation())), -5);
		}

	}
	
	@Override
	public void draw(Graphics g){
		super.draw(g);
		scene.draw(g);
	}

}
