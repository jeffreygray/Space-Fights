package edu.virginia.spacefights.classes;

public enum ProjectileType {
	Bullet(15, 3000, 430, true, "bullet.png"), Laser(10, 1500, 10000, false, "laser.png");
	private double muzzleVelocity, decayTime;
	private int damage;
	private boolean hasBounce;
	private String image;
	/**
	 * @param muzzleVelocity
	 * @param decayTime
	 * @param damage
	 * @param hasBounce
	 * @param image
	 */
	private ProjectileType(double muzzleVelocity, double decayTime, int damage, boolean hasBounce, String image) {
		this.muzzleVelocity = muzzleVelocity;
		this.decayTime = decayTime;
		this.damage = damage;
		this.hasBounce = hasBounce;
		this.image = image;
	}
	public double getMuzzleVelocity() {
		return muzzleVelocity;
	}
	public double getDecayTime() {
		return decayTime;
	}
	public int getDamage() {
		return damage;
	}
	public boolean hasBounce() {
		return hasBounce;
	}
	public String getImage() {
		return image;
	}
}
