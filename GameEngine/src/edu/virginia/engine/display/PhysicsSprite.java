// 03.09.2017 updated with nrg 

package edu.virginia.engine.display;

import java.util.ArrayList;

public class PhysicsSprite extends AnimatedSprite {
	private static int gravity = 2;
	public int nrg;
	private double m, xa, ya, xv, yv;
	
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
		xv += xa;
		yv += ya;
		position.x += Math.round(xv);
		position.y += Math.round(yv);
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

	public void setXa(int xa) {
		this.xa = xa;
	}

	public double getYa() {
		return ya;
	}

	public void setYa(int ya) {
		this.ya = ya;
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

}
