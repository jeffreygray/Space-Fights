package edu.virginia.spacefights.classes;

import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.util.GameClock;

public class Projectile extends PhysicsSprite {
	static double muzzleVelocity = 15;
	GameClock clock;
	 /*
	public Projectile(String id, String[] imageFileName, double xPos, double yPos, double angle) {
		super(id, imageFileName, 0, Math.cos(Math.toRadians(angle)), 0, Math.sin(Math.toRadians(angle)), 0, 0);
		this.setX(xPos);
		this.setY(yPos);
		clock = new GameClock();
	} */

	public Projectile(String id, String imageFileName, double xPos, double yPos, double angle) {
		// id, filename, mass, xvelcity, xacceleration, yvel, yaccel, nrg 
		super(id, imageFileName, 0, Math.cos(Math.toRadians(angle))*muzzleVelocity, 0, Math.sin(Math.toRadians(angle))*muzzleVelocity, 0);
		/*remove this line after get real projectile ~
		 */
		this.setScaleY(0.15);
		this.setScaleX(0.3);
		this.setX(xPos - this.getWidth()/2);
		this.setY(yPos - this.getHeight()/2);
		clock = new GameClock();
	}
	
	public boolean shouldRemove() {
		return clock.getElapsedTime() > 4000;
	}
}
