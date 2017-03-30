// 03.09.2017 updated with nrg 

package edu.virginia.engine.display;

import java.util.ArrayList;

public class PhysicsSprite extends AnimatedSprite {
	private static int gravity = 2;
	public int nrg;
	private double m, xa, ya, xv, yv;
	private double x = 0, y = 0;
	
	public PhysicsSprite(String id, String[] imageFileName, int mass, int xvel, int xaccel, int yvel, int yaccel, int energy) {
		super(id, imageFileName);
		m = mass;
		xa = xaccel;
		ya = yaccel;
		xv = xvel;
		yv = yvel;
		nrg = energy;
	}
	
	public PhysicsSprite(String id, String imageFileName, int mass, int xvel, int xaccel, int yvel, int yaccel, int energy) {
		super(id, imageFileName);
		m = mass;
		xa = xaccel;
		ya = yaccel;
		xv = xvel;
		yv = yvel;
		nrg = energy;
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys) {
		/* each frame, update is called and changes velocity by acceleration and position by velocity.
		 * the fields x and y are floating points to allow the storage of post-decimal values before the
		 * int rounding forced by the Point class (which determines the physical position on screen) */
		xv += xa;
		yv += ya;
		x += xv;
		y += yv;
		position.x = (int) Math.round(x);
		position.y = (int) Math.round(y);
		super.update(pressedKeys);
	}
	
	public int getNrg() {
		return nrg;
	}
	
	public void setNrg(int energy) {
		this.nrg = energy;
	}

	public double getXa() {
		return xa;
	}

	public void setXa(double d) {
		this.xa = d;
	}

	public double getYa() {
		return ya;
	}

	public void setYa(double d) {
		this.ya = d;
	}

	public double getXv() {
		return xv;
	}

	public void setXv(double xv2) {
		this.xv = xv2;
	}

	public double getYv() {
		return yv;
	}

	public void setYv(double yv2) {
		this.yv = yv2;
	}

	public static int getGravity() {
		return gravity;
	}

	public static void setGravity(int gravity) {
		PhysicsSprite.gravity = gravity;
	}

	public double getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}
	
	@Override
	public void setPosition(double x, double y) {
		super.setPosition(x, y);
		this.x = x;
		this.y = y;
	}

}
