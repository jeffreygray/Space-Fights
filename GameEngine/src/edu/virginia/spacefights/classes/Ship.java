package edu.virginia.spacefights.classes;

import edu.virginia.engine.display.PhysicsSprite;

public class Ship extends PhysicsSprite {

	public Ship(String id, String[] imageFileName, double mass, double xvel, double xaccel, double yvel, double yaccel, double energy) {
		super(id, imageFileName, mass, xvel, xaccel, yvel, yaccel, energy);
		// TODO Auto-generated constructor stub
	}

	public Ship(String id, String imageFileName, int mass, int xvel, int xaccel, int yvel, int yaccel, int energy) {
		super(id, imageFileName, mass, xvel, xaccel, yvel, yaccel, energy);
		// TODO Auto-generated constructor stub
	}

}
