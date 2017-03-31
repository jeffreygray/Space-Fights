package edu.virginia.engine.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.events.EventDispatcher;

/**
 * A very basic display object for a java based gaming engine
 * 
 * */
public class DisplayObject extends EventDispatcher {

	/* All DisplayObject have a unique id */
	private String id;

	/* The image that is displayed by this object */
	private BufferedImage displayImage;
	
	private DisplayObject parent;

	
	private boolean visible;
	protected Point position;
	private Point pivotPoint;
	private double scaleX, scaleY;
	private double rotation;
	private float alpha;
	
	private Rectangle hitbox = new Rectangle();
	
	public void setDisplayImage(BufferedImage displayImage) {
		this.displayImage = displayImage;
	}

	/**
	 * Constructors: can pass in the id OR the id and image's file path and
	 * position OR the id and a buffered image and position
	 */
	public DisplayObject(String id) {
		this.setId(id);
		this.visible = true;
		this.position = new Point(0,0);
		this.pivotPoint = new Point(0,0);
		this.scaleX = 1;
		this.scaleY = 1;
		this.rotation = 0;
		this.alpha = 1;
	}

	public DisplayObject(String id, String fileName) {
		this.setId(id);
		this.setImage(fileName);
		this.visible = true;
		this.position = new Point(0,0);
		this.pivotPoint = new Point(0,0);
		this.scaleX = 1;
		this.scaleY = 1;
		this.rotation = 0;
		this.alpha = 1;
		this.hitbox = new Rectangle(0, 0, (int)(this.getDisplayImage().getWidth()*scaleX), (int)(this.getDisplayImage().getHeight()*scaleY));
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}


	/**
	 * Returns the unscaled width and height of this display object
	 * */
	public int getUnscaledWidth() {
		if(displayImage == null) return 0;
		return displayImage.getWidth();
	}
	
	public int getWidth() {
		if(displayImage == null) return 0;
		return (int) (displayImage.getWidth()*this.scaleX);
	}

	public int getUnscaledHeight() {
		if(displayImage == null) return 0;
		return displayImage.getHeight();
	}
	
	public int getHeight() {
		if(displayImage == null) return 0;
		return (int) (displayImage.getHeight()*this.scaleY);
	}

	public BufferedImage getDisplayImage() {
		return this.displayImage;
	}

	protected void setImage(String imageName) {
		if (imageName == null) {
			return;
		}
		displayImage = readImage(imageName);
		if (displayImage == null) {
			System.err.println("[DisplayObject.setImage] ERROR: " + imageName + " does not exist!");
		}
	}


	/**
	 * Helper function that simply reads an image from the given image name
	 * (looks in resources\\) and returns the bufferedimage for that filename
	 * */
	public BufferedImage readImage(String imageName) {
		BufferedImage image = null;
		try {
			String file = ("resources" + File.separator + imageName);
			image = ImageIO.read(new File(file));
		} catch (IOException e) {
			System.out.println("[Error in DisplayObject.java:readImage] Could not read image " + imageName);
			e.printStackTrace();
		}
		return image;
	}

	public void setImage(BufferedImage image) {
		if(image == null) return;
		displayImage = image;
	}


	/**
	 * Invoked on every frame before drawing. Used to update this display
	 * objects state before the draw occurs. Should be overridden if necessary
	 * to update objects appropriately.
	 * @param controllers 
	 * */
	protected void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		if(this.getDisplayImage() != null) {
			if(scaleX > 0 && scaleY > 0)
				hitbox.setBounds(position.x, position.y, (int) Math.abs(this.getDisplayImage().getWidth()*scaleX), (int) Math.abs(this.getDisplayImage().getHeight()*scaleY));
			else if(scaleX < 0 && scaleY > 0)
				hitbox.setBounds(position.x - this.getDisplayImage().getWidth(), position.y, (int) Math.abs(this.getDisplayImage().getWidth()*scaleX), (int) Math.abs(this.getDisplayImage().getHeight()*scaleY));
			else if(scaleX > 0 && scaleY < 0)
				hitbox.setBounds(position.x, position.y - this.getDisplayImage().getHeight(), (int) Math.abs(this.getDisplayImage().getWidth()*scaleX), (int) Math.abs(this.getDisplayImage().getHeight()*scaleY));
			else
				hitbox.setBounds(position.x - this.getDisplayImage().getWidth(), position.y - this.getDisplayImage().getHeight(), (int) Math.abs(this.getDisplayImage().getWidth()*scaleX), (int) Math.abs(this.getDisplayImage().getHeight()*scaleY));
		}
	}

	/**
	 * Draws this image. This should be overloaded if a display object should
	 * draw to the screen differently. This method is automatically invoked on
	 * every frame.
	 * */
	public void draw(Graphics g) {
		
		if (displayImage != null) {
			
			/*
			 * Get the graphics and apply this objects transformations
			 * (rotation, etc.)
			 */
			Graphics2D g2d = (Graphics2D) g;
			//g2d.draw(hitbox);

			//g2d.drawRect(this.getPosition().x-2, this.getPosition().y-2, 4, 4);
			applyTransformations(g2d);

			/* Actually draw the image, perform the pivot point translation here */
			if(visible)
				g2d.drawImage(displayImage, 0, 0,
					(int) (getUnscaledWidth()),
					(int) (getUnscaledHeight()), null);
			
			/*
			 * undo the transformations so this doesn't affect other display
			 * objects
			 */
			
			reverseTransformations(g2d);
			//g2d.drawOval(this.getPivotPoint().x+this.getPosition().x-4, this.getPivotPoint().y+this.getPosition().y-4, 8, 8);

		}
	}

	/**
	 * Applies transformations for this display object to the given graphics
	 * object
	 * */
	protected void applyTransformations(Graphics2D g2d) {
		g2d.translate(getPosition().getX(), getPosition().getY());
		g2d.rotate(Math.toRadians(getRotation()), getPivotPoint().getX(), getPivotPoint().getY());
		g2d.scale(getScaleX(), getScaleY());
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, getAlpha()));
	}

	/**
	 * Reverses transformations for this display object to the given graphics
	 * object
	 * */
	protected void reverseTransformations(Graphics2D g2d) {
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		g2d.scale(1/getScaleX(), 1/getScaleY());
		g2d.rotate(-Math.toRadians(getRotation()), getPivotPoint().getX(), getPivotPoint().getY());
		g2d.translate(-getPosition().getX(), -getPosition().getY());
	}
	
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}
	
	public void setPosition(double x, double y) {
		this.position.x = (int)x;
		this.position.y = (int)y;
	}

	public Point getPivotPoint() {
		return pivotPoint;
	}

	public void setPivotPoint(Point pivotPoint) {
		this.pivotPoint = pivotPoint;
	}

	public void setPivotPoint(int x, int y) {
		this.pivotPoint.x = x;
		this.pivotPoint.y = y;
	}
	
	public double getScaleX() {
		return scaleX;
	}

	public void setScaleX(double scaleX) {
		if(scaleX < 0 && this.scaleX > 0)
			this.position.x += this.getDisplayImage().getWidth();
		else if(scaleX > 0 && this.scaleX < 0)
			this.position.x -= this.getDisplayImage().getWidth(); 
		this.scaleX = scaleX;
	}

	public double getScaleY() {
		return scaleY;
	}

	public void setScaleY(double scaleY) {
		this.scaleY = scaleY;
	}

	public double getRotation() {
		return rotation;
	}

	/**
	 * Sets the rotation of the object in degrees
	 * @param rotation the number of degrees to rotate the image by
	 */
	public void setRotation(double rotation) {
		this.rotation = rotation%360;
	}
	
	public void setParent(DisplayObject d) {
		this.parent = d;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = Math.max(0, Math.min(1, alpha));
	}
	
	public Rectangle getHitbox() {
		return hitbox;
	}
	
	public boolean collidesWith(DisplayObject other) {
		return hitbox.intersects(other.getHitbox());
	}

	public void setYPosition(double val) {
		this.position.y = (int) val;
	}

	public void setXPosition(double val) {
		this.position.x = (int) val;
	}

}