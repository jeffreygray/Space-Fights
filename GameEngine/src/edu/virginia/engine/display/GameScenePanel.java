package edu.virginia.engine.display;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This class is essentially something I needed to do to get around Java's swing stuff. It is the JPanel we are drawing
 * all of our graphics on. You can ignore this class.
 * */
@SuppressWarnings("serial")
public class GameScenePanel extends JPanel {

	/* The game associated with this panel */
	private Game gameRef;

	/**
	 * Constructor
	 * */
	public GameScenePanel(Game gameRef) {
		super();
		this.setLayout(null);
		this.setGameRef(gameRef);
		this.setBounds(0,0,gameRef.getUnscaledWidth(), gameRef.getUnscaledHeight());
	}

	public Game getGameRef() {
		return gameRef;
	}

	public void setGameRef(Game sceneRef) {
		this.gameRef = sceneRef;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		gameRef.nextFrame(g);
	}

	@Override
	public String toString() {
		return gameRef.getId() + " (width = " + this.getWidth()
				+ ", height = " + this.getHeight();
	}
}
