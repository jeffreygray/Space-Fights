package edu.virginia.lab1test;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;

/**
 * Example game that utilizes our engine. We can create a simple prototype game with just a couple lines of code
 * although, for now, it won't be a very fun game :)
 * */
public class LabOneGame extends Game implements MouseListener{
	int xPos = 0;
	int yPos = 0;
	static int speed = 30;
	static int timeLeft = 3600;
	static int health = 3;
	static boolean vpressedlastframe = false;
	boolean gameOver = false;
	String[] images = {"mario_0.png", "mario_1.png", "mario_2.png", "mario_3.png"};
	
	/* Create a sprite object for our game. We'll use mario */
	AnimatedSprite mario = new AnimatedSprite("Mario", images);
	int marioW, marioH;
	
	Path2D hitbox;
	Rectangle2D r;
	
	/**
	 * Constructor. See constructor in Game.java for details on the parameters given
	 * */
	public LabOneGame() {
		super("Lab One Test Game", 1200, 700);
		this.getMainFrame().addMouseListener(this);
		r = new Rectangle2D.Double(0,0,
				marioW, marioH);
	}
	
	/**
	 * Engine will automatically call this update method once per frame and pass to us
	 * the set of keys (as strings) that are currently being pressed down
	 * */
	@Override
	public void update(ArrayList<String> pressedKeys){
		super.update(pressedKeys);
		System.out.println(pressedKeys.toString());
		/* Make sure mario is not null. Sometimes Swing can auto cause an extra frame to go before everything is initialized */
		if(mario != null) {
			marioW = (int)(mario.getUnscaledWidth()*mario.getScaleX());
			marioH = (int)(mario.getUnscaledHeight()*mario.getScaleY());
			
			if(!mario.isVisible() || mario.getAlpha() <= 0.1) {
				speed = 2;
			}
			else {
				if(pressedKeys.contains("Shift")) {
					speed = (int) (Math.abs(60*Math.pow(mario.getScaleX(), 3)) + 10);
					mario.setSpeed(2);
				}
				else {
					speed = (int)(Math.abs(30*Math.pow(mario.getScaleX(), 3)) + 5);
					mario.setSpeed(5);
				}
			}
				
			
			
			/* move mario based on IJKL keys */
			Point p = mario.getPosition();
			if(pressedKeys.contains("Up")) {
				p.translate(0, -speed);
			}
			if(pressedKeys.contains("Left")) 
				p.translate(-speed, 0);
			if(pressedKeys.contains("Right")) 
				p.translate(speed, 0);
			if(pressedKeys.contains("Down")) 
				p.translate(0, speed);
			/* Keep mario on the screen */
			
			double rightEdge = this.getMainFrame().getWidth() - marioW;
			double botEdge = this.getMainFrame().getHeight() - marioH;
			
			if(p.getX() >= rightEdge) p.x = (int)rightEdge;
			else if(p.getX() < -15) p.x = -15;
			if(p.getY() >= botEdge) p.y = (int)botEdge;
			else if(p.getY() < 35) p.y = 35;
			mario.setPosition(p);
			
			/* Move pivotPoint */
			p = mario.getPivotPoint();
			if(pressedKeys.contains("I")) {
				p.translate(0, -speed);
			}
			if(pressedKeys.contains("J")) 
				p.translate(-speed, 0);
			if(pressedKeys.contains("L")) 
				p.translate(speed, 0);
			if(pressedKeys.contains("K")) 
				p.translate(0, speed);
			mario.setPivotPoint(p);
			
			/*scale mario */
			if(pressedKeys.contains("A")) {
				mario.setScaleX(Math.min(mario.getScaleX()+0.09, 3));
				mario.setScaleY(Math.min(mario.getScaleY()+0.09, 3));
			}
			if(pressedKeys.contains("S")){
				mario.setScaleX(Math.max(mario.getScaleX()-0.09, 0.2));
				mario.setScaleY(Math.max(mario.getScaleY()-0.09, 0.2));
			}
			
			/* rotate mario */
			if(pressedKeys.contains("Q")) {
				mario.setRotation(mario.getRotation()+5);
			}
			if(pressedKeys.contains("W")) {
				mario.setRotation(mario.getRotation()-5);
			}
			
			/* change mario's transparency*/
			if(pressedKeys.contains("Z")) {
				mario.setAlpha(Math.max(mario.getAlpha()-0.07f, 0));
			}
			if(pressedKeys.contains("X")) {
				mario.setAlpha(Math.min(mario.getAlpha()+0.07f, 1));
			}
			
			
			/* toggle mario visibility */
			if(pressedKeys.contains("V") && !vpressedlastframe) {
				mario.setVisible(!mario.isVisible());
				vpressedlastframe =true;
			} else if (!pressedKeys.contains("V")){
				vpressedlastframe = false;
			}
			
			if(timeLeft % mario.getSpeed() == 0)
				mario.update(pressedKeys);
			
			/* update the location of mario's hitbox rectangle, based on movement and rotation */
			p = mario.getPosition();
			AffineTransform transformer = new AffineTransform();
			transformer.translate(p.getX(), p.getY());
			transformer.rotate(Math.toRadians(mario.getRotation()), mario.getPivotPoint().getX(), mario.getPivotPoint().getY());
			java.awt.Shape s = transformer.createTransformedShape(r);
			r.setFrame(0, 0, marioW, marioH);
			hitbox = new Path2D.Double(s);
			
			
		}
		
		if(timeLeft>0)
			timeLeft -= 1;
		
		if(health == 0 || timeLeft == 0) {
			gameOver = true;
			
		}
		//System.out.println(pressedKeys.toString());
		
	}
	
	/**
	 * Engine automatically invokes draw() every frame as well. If we want to make sure mario gets drawn to
	 * the screen, we need to make sure to override this method and call mario's draw method.
	 * */
	@Override
	public void draw(Graphics g){
		super.draw(g);
		/*
		Graphics2D g2d = (Graphics2D) g;
		g2d.draw(hitbox);
		*/
		/* Same, just check for null in case a frame gets thrown in before Mario is initialized */
		if(mario != null) mario.draw(g);
		
		g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
		g.drawString("Time: " + timeLeft/60, this.getMainFrame().getWidth()-80, 23);
		g.drawString("Health: ", 0, 23);
		
		g.setColor(Color.RED);
		for(int i = 0; i < health; i++) {
			g.fillRect(55+i*25, 3, 20, 30);	
		}
		
		g.setColor(Color.BLACK);
		g.drawLine(0, 35, this.getMainFrame().getWidth(), 35);
		
		if(gameOver){
			g.fillRect(0, 0, this.getMainFrame().getWidth(), this.getMainFrame().getHeight());
			g.setColor(Color.RED);
			g.setFont(new Font("TimesRoman", Font.BOLD, 32));
			g.drawString("GAME OVER", this.getMainFrame().getWidth()/2-50, this.getMainFrame().getHeight()*2/5);
			if(health > 0) {
				g.drawString("Mario Wins!", this.getMainFrame().getWidth()/2-50, this.getMainFrame().getHeight()/2);
			}else {
				g.drawString("Clicker Wins!", this.getMainFrame().getWidth()/2-50, this.getMainFrame().getHeight()/2);
			}
			this.stop();
		}
	}

	/**
	 * Quick main class that simply creates an instance of our game and starts the timer
	 * that calls update() and draw() every frame
	 * */
	public static void main(String[] args) {
		LabOneGame game = new LabOneGame();
		game.start();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/* Click is the down and up action. It does not seem to be as responsive and press, which is just the down action.
		 * For this reason I am moving the click check to mousePressed.
		 */
	}

	@Override
	public void mousePressed(MouseEvent e) {
		int clickX = e.getX();
		int clickY = e.getY();
/*		double marioL = mario.getPosition().getX();
		double marioR = mario.getPosition().getX() + mario.getUnscaledWidth()*mario.getScaleX();
		double marioT = mario.getPosition().getY();
		double marioB = mario.getPosition().getY() + 1.1*mario.getUnscaledHeight()*mario.getScaleY();
*/		
		System.out.println("CLICK");
	/*	if(clickX > marioL && clickX < marioR && clickY > marioT && clickY < marioB) {
			System.out.println("Clicked Mario! ");
			health -= 1;
		}
		*/
		if(hitbox.contains(clickX, clickY)) {
			System.out.println("Clicked Mario! ");
			health -= 1;	
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
