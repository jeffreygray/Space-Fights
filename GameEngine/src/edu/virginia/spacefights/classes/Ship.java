package edu.virginia.spacefights.classes;

import edu.virginia.engine.display.PhysicsSprite;

public class Ship extends PhysicsSprite {

	private int nrg;
	private double max_speed;
	private double rotate_speed;
	private double thrust;
	
	/* 
	 * THis will be if we want to use animated ship sprites
	public Ship(String id, String[] imageFileName, double mass, double xvel, double xaccel, double yvel, double yaccel, double energy) {
		super(id, imageFileName, mass, xvel, xaccel, yvel, yaccel, energy);
		// TODO Auto-generated constructor stub
	} */

	public Ship(String id, String imageFileName, double maxSpeed, double rotateSpeed, int energy) {
		super(id, imageFileName, 0.0, 0.0, 0.0, 0.0, 0.0);
		nrg = energy;
		max_speed = 10;
		rotate_speed = 5;
		thrust = 0.8;
	}
	
	public int getNrg() {
		return nrg;
	}
	
	public void setNrg(int energy) {
		this.nrg = energy;
	}

}
