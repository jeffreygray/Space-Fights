package edu.virginia.spacefights.classes;

import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.util.GameClock;

public class Projectile extends PhysicsSprite {
	GameClock clock;
	int damage;
	// need: damage, decayTime, muzzleVelocity, hasBounce, image
	private double decayTime, muzzleVelocity;
	private boolean hasBounce, shouldRemove = false;
	 /*
	public Projectile(String id, String[] imageFileName, double xPos, double yPos, double angle) {
		super(id, imageFileName, 0, Math.cos(Math.toRadians(angle)), 0, Math.sin(Math.toRadians(angle)), 0, 0);
		this.setX(xPos);
		this.setY(yPos);
		clock = new GameClock();
	} */

	public Projectile(ProjectileType type, double xPos, double yPos, double angle) {
		// id, filename, mass, xvelcity, xacceleration, yvel, yaccel, nrg 
		super("projectile", type.getImage(), 0, Math.cos(Math.toRadians(angle))*type.getMuzzleVelocity(), 0, Math.sin(Math.toRadians(angle))*type.getMuzzleVelocity(), 0);
		/*remove this line after get real projectile ~
		 */
		this.setPivotPoint(this.getWidth()/2, this.getHeight()/2);
		this.setRotation(angle+90);
		this.setX(xPos - this.getWidth()/2);
		this.setY(yPos - this.getHeight()/2);
		
		damage = type.getDamage();
		decayTime = type.getDecayTime();
		muzzleVelocity = type.getMuzzleVelocity();
		hasBounce = type.hasBounce();
		clock = new GameClock();
	}
	
	public GameClock getClock() {
		return clock;
	}

	public int getDamage() {
		return damage;
	}

	public double getDecayTime() {
		return decayTime;
	}

	public double getMuzzleVelocity() {
		return muzzleVelocity;
	}

	public boolean isHasBounce() {
		return hasBounce;
	}

	public boolean shouldRemove() {
		return clock.getElapsedTime() > 4000 || shouldRemove;
	}
	
	public void setRemove(boolean b) {
		shouldRemove = b;
	}
}
