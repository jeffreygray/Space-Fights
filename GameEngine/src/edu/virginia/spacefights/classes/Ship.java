package edu.virginia.spacefights.classes;

import java.awt.Graphics;
import java.util.ArrayList;

import com.sun.glass.ui.CommonDialogs.Type;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.util.GameClock;

public class Ship extends PhysicsSprite {

	private int nrg;
	private int nrgCap;
	private int player;
	private double max_speed;
	private double rotate_speed;
	private double thrust;
	private GameClock clock;
	private int shotCost;
	private int shotCD;
	private ShipType type;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	
	/* 
	 * This will be if we want to use animated ship sprites
	public Ship(String id, String[] imageFileName, double mass, double xvel, double xaccel, double yvel, double yaccel, double energy) {
		super(id, imageFileName, mass, xvel, xaccel, yvel, yaccel, energy);
		// TODO Auto-generated constructor stub
	} */
	
	/**
	 * Creates a new ship of ship class <code>type</code> for player <code>playerNumber</code>
	 * @param type The ship class to which this new ship will belong (e.g. Vulture)
	 * @param playerNumber An integer representing which gamepad will control this ship
	 */
	public Ship(ShipType type, int playerNumber) {
		super(""+playerNumber, type.getImageName());
		nrgCap = type.getNrgCap();
		nrg = nrgCap;
		player = playerNumber;
		max_speed = 10;
		rotate_speed = 5;
		thrust = type.getThrust();
		this.type = type;
		clock = new GameClock();
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		this.setXa(0);
		this.setYa(0);
		this.setXv(getXv()*0.99);
		this.setYv(getYv()*0.99);
		double rotationInRads = Math.toRadians(this.getRotation()-90);
		/* currently handling different player controls with keyboard. Final should be able to simply query 
		 * the buttons pressed on this player's GamePad. i.e. playerController = controllers.get(player)
		 */
		if(player == 1) {
			if(pressedKeys.contains("Up") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
				this.setXa(Math.cos(rotationInRads) * thrust);
				this.setYa(Math.sin(rotationInRads) * thrust);
			}
			if(pressedKeys.contains("Down") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
				this.setXa(-Math.cos(rotationInRads) * thrust);
				this.setYa(-Math.sin(rotationInRads) * thrust);
				//System.out.println("XV: " + xv +"\t YV: " + yv);
			}
			if(pressedKeys.contains("Left")) {
				this.setRotation(this.getRotation()-rotate_speed);
			}
			if(pressedKeys.contains("Right")) {
				this.setRotation(this.getRotation()+rotate_speed);
			}
			if(pressedKeys.contains("Space") && clock.getElapsedTime() >= type.getCooldown() && nrg >= type.getFiringCost()) {
				nrg = nrg - type.getFiringCost();
				clock.resetGameClock();
				double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
				double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
				
				projectiles.add(new Projectile("bullet", "coin.png", x, y, this.getRotation()-90));
			}
		} else if(player == 2) {
			if(pressedKeys.contains("NumPad-5") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
				this.setXa(Math.cos(rotationInRads) * thrust);
				this.setYa(Math.sin(rotationInRads) * thrust);
			}
			if(pressedKeys.contains("NumPad-2") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
				this.setXa(-Math.cos(rotationInRads) * thrust);
				this.setYa(-Math.sin(rotationInRads) * thrust);
				//System.out.println("XV: " + xv +"\t YV: " + yv);
			}
			if(pressedKeys.contains("NumPad-1")) {
				this.setRotation(this.getRotation()-5);
			}
			if(pressedKeys.contains("NumPad-3")) {
				this.setRotation(this.getRotation()+5);
			}
			if(pressedKeys.contains("Ctrl") && clock.getElapsedTime() >= type.getCooldown() && nrg >= type.getFiringCost()) {
				nrg = nrg-type.getFiringCost();
				clock.resetGameClock();
				double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
				double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
				
				// each projectile will be genrated and store in projectiles and associated with source ship.
				// this may allow us to avoid friendly fire, if desired
				projectiles.add(new Projectile("bullet", "coin.png", x, y, this.getRotation()-90));
			}
		}
		
		// Regen the nrg over time
		if(nrg < 1500) 
			nrg += 200/60; 
		
		super.update(pressedKeys, controllers);
		// need to update each projectile by looping through this ship's list of active projectiles
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.update(pressedKeys, controllers);
			// remove projectiles that have travelled too far
			if(p.shouldRemove())
				projectiles.remove(i);
		}
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		// again, projectiles are not currently built into the display tree, so need to manually loop
		for(Projectile p : projectiles)
			p.draw(g);
	}
	
	public int getNrg() {
		return nrg;
	}
	
	public void setNrg(int energy) {
		this.nrg = energy;
	}

}
