package edu.virginia.spacefights.classes;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.display.PhysicsSprite;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CombatEvent;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.util.GameClock;
import edu.virginia.engine.util.SoundManager;

public class Ship extends PhysicsSprite {
	public static final int MOMENTUM_DAMAGE_RATIO = 150;

	private int nrg;
	private int nrgCap;
	private int playerNum;
	private int lives;
	private double max_speed;
	private double rotate_speed;
	private double thrust;
	private GameClock lastShot, lastSpawned, lastFlashed;
	private ShipType type;
	private Sprite nrgFront, nrgBack;
	private ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	private boolean recentlySpawned = false;
	private Point spawn = new Point();
	
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
		super(""+playerNumber, type.getImageName()+playerNumber+".png");
		nrgCap = type.getNrgCap();
		nrg = nrgCap;
		playerNum = playerNumber;
		lives = 3;
		
		max_speed = 10;
		rotate_speed = 5;
		thrust = type.getThrust();
		this.type = type;
		this.setM(type.getMass());
		
		if(playerNumber == 0)
			spawn.setLocation(300, 150);
		else if(playerNumber == 1)
			spawn.setLocation(900, 150);
		else if(playerNumber == 2)
			spawn.setLocation(300, 650);
		else if(playerNumber == 3)
			spawn.setLocation(900, 650);
		setPosition(spawn.x, spawn.y);
		
		lastShot = new GameClock();
		lastSpawned = new GameClock();
		lastFlashed = new GameClock();
		
		nrgFront = new Sprite("nrgFront", "frontNRG.png");
		nrgBack = new Sprite("nrgBack", "rearNRG.png");
		
		nrgBack.setScaleY(-0.8);
		nrgBack.setScaleX(0.6);
		nrgBack.addChild(nrgFront);

		System.out.println("Ship #"+playerNum+ " is at position "+this.getPosition());
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		this.setXa(0);
		this.setYa(0);
		this.setXv(getXv()*0.995);
		this.setYv(getYv()*0.995);
		double rotationInRads = Math.toRadians(this.getRotation()-90);
		GamePad playerController = controllers.get(playerNum);

		if(playerController.getLeftStickYAxis() == -1 && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
				this.setXa(Math.cos(rotationInRads) * thrust);
				this.setYa(Math.sin(rotationInRads) * thrust);
		}
		if(playerController.getLeftStickYAxis() == 1 && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
			this.setXa(-Math.cos(rotationInRads) * thrust);
			this.setYa(-Math.sin(rotationInRads) * thrust);
		}
		if(playerController.getLeftStickXAxis() == -1) {
			this.setRotation(this.getRotation()-rotate_speed);
		}
		if(playerController.getLeftStickXAxis() == 1) {
			this.setRotation(this.getRotation()+rotate_speed);
		}
		
		if(playerController.isButtonPressed(GamePad.BUTTON_A) && lastShot.getElapsedTime() >= type.getCooldown() && nrg > type.getFiringCost()) {
			nrg = nrg - type.getFiringCost();
			SoundManager.playSoundEffect("bullet.wav");

			lastShot.resetGameClock();
			double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
			double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
			projectiles.add(new Projectile(ProjectileType.Bullet, x, y, this.getRotation()-90));
		}
		
		 if(playerController.isButtonPressed(GamePad.BUTTON_B) && lastShot.getElapsedTime() >= type.getSpecialCD() && nrg > type.getSpecialCost()) {
		 SoundManager.playSoundEffect("laser.wav");
			nrg = nrg-type.getSpecialCost();
			lastShot.resetGameClock();
			double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
			double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
			
			// each projectile will be genrated and store in projectiles and associated with source ship.
			// this may allow us to avoid friendly fire, if desired
			projectiles.add(new Projectile(ProjectileType.Laser, x, y, this.getRotation()-90));
		}
		
//		if(playerNum == 1) {
//			if(pressedKeys.contains("W") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
//				this.setXa(Math.cos(rotationInRads) * thrust);
//				this.setYa(Math.sin(rotationInRads) * thrust);
//			}
//			if(pressedKeys.contains("S") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
//				this.setXa(-Math.cos(rotationInRads) * thrust);
//				this.setYa(-Math.sin(rotationInRads) * thrust);
//				//System.out.println("XV: " + xv +"\t YV: " + yv);
//			}
//			if(pressedKeys.contains("A")) {
//				this.setRotation(this.getRotation()-rotate_speed);
//			}
//			if(pressedKeys.contains("D")) {
//				this.setRotation(this.getRotation()+rotate_speed);
//			}
//			if(pressedKeys.contains("B") && lastShot.getElapsedTime() >= type.getCooldown() && nrg >= type.getFiringCost()) {
//				nrg = nrg - type.getFiringCost();
//				SoundManager.playSoundEffect("bullet.wav");
//
//				lastShot.resetGameClock();
//				double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
//				double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
//				
//				projectiles.add(new Projectile(ProjectileType.Bullet, x, y, this.getRotation()-90));
//			}
//			 if(pressedKeys.contains("V") && lastShot.getElapsedTime() >= type.getSpecialCD() && nrg >= type.getSpecialCost()) {
//				 SoundManager.playSoundEffect("laser.wav");
//					nrg = nrg-type.getSpecialCost();
//					lastShot.resetGameClock();
//					double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
//					double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
//					
//					// each projectile will be genrated and store in projectiles and associated with source ship.
//					// this may allow us to avoid friendly fire, if desired
//					projectiles.add(new Projectile(ProjectileType.Laser, x, y, this.getRotation()-90));
////				}
//	
//		 else if(playerNum == 2) {
//			if(pressedKeys.contains("I") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
//				this.setXa(Math.cos(rotationInRads) * thrust);
//				this.setYa(Math.sin(rotationInRads) * thrust);
//			}
//			if(pressedKeys.contains("K") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
//				this.setXa(-Math.cos(rotationInRads) * thrust);
//				this.setYa(-Math.sin(rotationInRads) * thrust);
//				//System.out.println("XV: " + xv +"\t YV: " + yv);
//			}
//			if(pressedKeys.contains("J")) {
//				this.setRotation(this.getRotation()-5);
//			}
//			if(pressedKeys.contains("L")) {
//				this.setRotation(this.getRotation()+5);
//			}
//			if(pressedKeys.contains("O") && lastShot.getElapsedTime() >= type.getCooldown() && nrg >= type.getFiringCost()) {
//				SoundManager.playSoundEffect("bullet.wav");
//				nrg = nrg-type.getFiringCost();
//				lastShot.resetGameClock();
//				double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
//				double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
//				
//				// each projectile will be genrated and store in projectiles and associated with source ship.
//				// this may allow us to avoid friendly fire, if desired
//				projectiles.add(new Projectile(ProjectileType.Bullet, x, y, this.getRotation()-90));
//			} if(pressedKeys.contains("P") && lastShot.getElapsedTime() >= type.getSpecialCD() && nrg >= type.getSpecialCost()) {
//				SoundManager.playSoundEffect("laser.wav");
//
//				nrg = nrg-type.getSpecialCost();
//				lastShot.resetGameClock();
//				double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
//				double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
//				
//				// each projectile will be genrated and store in projectiles and associated with source ship.
//				// this may allow us to avoid friendly fire, if desired
//				projectiles.add(new Projectile(ProjectileType.Laser, x, y, this.getRotation()-90));
//			}
//		}
//		else if(playerNum == 3) {
//			if(pressedKeys.contains("NumPad-5") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
//				this.setXa(Math.cos(rotationInRads) * thrust);
//				this.setYa(Math.sin(rotationInRads) * thrust);
//			}
//			if(pressedKeys.contains("NumPad-2") && Math.hypot(this.getXv(), this.getYv()) < max_speed) {
//				this.setXa(-Math.cos(rotationInRads) * thrust);
//				this.setYa(-Math.sin(rotationInRads) * thrust);
//				//System.out.println("XV: " + xv +"\t YV: " + yv);
//			}
//			if(pressedKeys.contains("NumPad-1")) {
//				this.setRotation(this.getRotation()-5);
//			}
//			if(pressedKeys.contains("NumPad-3")) {
//				this.setRotation(this.getRotation()+5);
//			}
//			if(pressedKeys.contains("Back Quote") && lastShot.getElapsedTime() >= type.getCooldown() && nrg >= type.getFiringCost()) {
//				nrg = nrg-type.getFiringCost();
//				lastShot.resetGameClock();
//				double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
//				double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
//				
//				// each projectile will be genrated and store in projectiles and associated with source ship.
//				// this may allow us to avoid friendly fire, if desired
//				projectiles.add(new Projectile(ProjectileType.Bullet, x, y, this.getRotation()-90));
//			} if(pressedKeys.contains("Q") && lastShot.getElapsedTime() >= type.getSpecialCD() && nrg >= type.getSpecialCost()) {
//				nrg = nrg-type.getSpecialCost();
//				lastShot.resetGameClock();
//				double x = this.getX() + this.getPivotPoint().x + Math.cos(rotationInRads)*this.getHeight()/2;
//				double y = this.getY() + this.getPivotPoint().y + Math.sin(rotationInRads)*this.getWidth()/2;
//				
//				// each projectile will be genrated and store in projectiles and associated with source ship.
//				// this may allow us to avoid friendly fire, if desired
//				projectiles.add(new Projectile(ProjectileType.Laser, x, y, this.getRotation()-90));
//			}
//		}
		
		
		// want to make players flash to indicate they are invincible
		if(recentlySpawned) {
			if(lastSpawned.getElapsedTime() > 2000) {
				recentlySpawned = false;
				this.setVisible(true);
			} else if(lastFlashed.getElapsedTime() > 200) {
					// flashes the player as long as he's invincible
					this.setVisible(!this.isVisible());
					lastFlashed.resetGameClock();
			}
		}
		
		/* adjust this player's energy meter based on current energy level, and apply recharge
		 */
		// Regen the nrg over time
		if(nrg < type.getNrgCap() && nrg > 0) 
			nrg += type.getNrgRecharge()/60; 
		nrgBack.setPosition(getPosition().x - getWidth() / 2, getPosition().y - getHeight() / 3);
		nrgFront.setScaleX((double) nrg / type.getNrgCap());
		
		
		super.update(pressedKeys, controllers);
		// need to update each projectile by looping through this ship's list of active projectiles
		for(int i = 0; i < projectiles.size(); i++) {
			Projectile p = projectiles.get(i);
			p.update(pressedKeys, controllers);
			// remove projectiles that have travelled too far or have collided with something
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
		nrgBack.draw(g);
		
		
	}
	
	public int getNrg() {
		return nrg;
	}
	
	public void setNrg(int energy) {
		//System.out.println("Setting nrg");
		// if the player did recently spawn, want them to not take any damage
		if(!recentlySpawned) {
			if(energy < 0) {
				// player dies
				//System.out.println("NRG < 0");
				lives--;
				this.dispatchEvent(new Event(CombatEvent.DEATH, this));
			}
			else 
				this.nrg = energy;
		}
	}

	public ShipType getShipType() {
		return type;
	}
	
	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	public int getPlayerNum() {
		return playerNum;
	}

	public void respawn() {
		//lives--;
		if(lives > 0) {
			setPosition(spawn.x, spawn.y);
			setNrg(type.getNrgCap());
			setRotation(0);
			setXv(0);
			setYv(0);
			recentlySpawned = true;
			lastSpawned.resetGameClock();
			lastShot.resetGameClock();
			lastFlashed.resetGameClock();
		} else {
			this.setVisible(false);
			nrgBack.setVisible(false);
			nrgFront.setVisible(false);
		}
	}

	public int getLives() {
		return lives;
	}
}
