// 03.09.2017 updated with nrg 

package edu.virginia.engine.display;

import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;

public class PhysicsSprite extends AnimatedSprite {
	private static int gravity = 2;
	private double m, xa, ya, xv, yv;
	private double x = 0, y = 0;
	
	public PhysicsSprite(String id, String[] imageFileName, double mass, double xvel, double xaccel, double yvel, double yaccel) {
		super(id, imageFileName);
		m = mass;
		xa = xaccel;
		ya = yaccel;
		xv = xvel;
		yv = yvel;
	}
	
	public PhysicsSprite(String id, String imageFileName, double mass, double xvel, double xaccel, double yvel, double yaccel) {
		super(id, imageFileName);
		m = mass;
		xa = xaccel;
		ya = yaccel;
		xv = xvel;
		yv = yvel;
	}
	
	public PhysicsSprite(String id, String imageFileName) {
		super(id, imageFileName);
		m = 0;
		xv = 0;
		yv = 0;
		xa = 0;
		ya = 0;
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		/* each frame, update is called and changes velocity by acceleration and position by velocity.
		 * the fields x and y are floating points to allow the storage of post-decimal values before the
		 * int rounding forced by the Point class (which determines the physical position on screen) */
		xv += xa;
		yv += ya;
		setX(getX() + xv);
		setY(getY() + yv);
		position.x = (int) Math.round(getX());
		position.y = (int) Math.round(getY());
		super.update(pressedKeys, controllers);
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
		this.setX(x);
		this.setY(y);
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		this.position.y = (int) y;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		this.position.x = (int) x;
	}

}
