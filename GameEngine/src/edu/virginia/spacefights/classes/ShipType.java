package edu.virginia.spacefights.classes;

public enum ShipType {
	Lion(1700, 300, 0.15, 240, 280, 700, 1000, 25, "lion"), 
	Vulture(1400, 350, 0.24, 270, 430, 1100, 0, 10, "vulture"), 
	Rhino(1800, 320, 0.13, 300, 500, 10, 0, 50, "rhino");	
	
	private final int nrgCap;
	private final int nrgRecharge;
	private final double thrust;
	private final int bulletCost;
	private final int cooldown;
	private final int specialCost;
	private final int specialCD;
	private final int mass;
	
	private final String imageName;
	
	/**
	 * @param nrgCap The maximum amount of energy this type of ship can have
	 * @param nrgRecharge How quickly energy is refilled, as measured in units/second
	 * @param thrust Determines the acceleration of the ship 
	 * @param firingCost The amount of energy each weapon use incurs
	 * @param cooldown The amount of time that must occur between consecutive shots, expressed in milliseconds
	 */
	private ShipType(int nrgCap, int nrgRecharge, double thrust, int firingCost, int cooldown, int specCost, int specialCD, int mass, String fileName) {
		this.nrgCap = nrgCap;
		this.nrgRecharge = nrgRecharge;
		this.thrust = thrust;
		this.bulletCost = firingCost;
		this.cooldown = cooldown;
		this.specialCost = specCost;
		this.specialCD = specialCD;
		this.mass = mass;
		this.imageName = fileName;
	}

	public int getNrgCap() {
		return nrgCap;
	}

	public int getNrgRecharge() {
		return nrgRecharge;
	}

	public double getThrust() {
		return thrust;
	}

	public int getFiringCost() {
		return bulletCost;
	}

	public int getCooldown() {
		return cooldown;
	}

	public String getImageName() {
		return imageName;
	}

	public int getBulletCost() {
		return bulletCost;
	}

	public int getSpecialCost() {
		return specialCost;
	}

	public int getSpecialCD() {
		return specialCD;
	}

	public int getMass() {
		return mass;
	}
	
}
