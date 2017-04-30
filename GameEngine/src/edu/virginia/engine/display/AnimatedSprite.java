package edu.virginia.engine.display;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;

public class AnimatedSprite extends Sprite {

	private ArrayList<Sprite> spriteSheet;
	private ArrayList<String> spriteNames = new ArrayList<String>();
	private int currFrame, startInd, endInd, speed, count;
	private boolean playing;
	
	public AnimatedSprite(String id, String imageFileName) {
		super(id, imageFileName);
		spriteNames.add(imageFileName);
		currFrame = startInd = endInd = count = 0;
		speed = 5;
		playing = false;
		this.setImage(spriteNames.get(0));
	}

	public AnimatedSprite(String id, String[] imageFileName) {
		super(id);
		spriteNames = new ArrayList<String>();
		for(int i = 0; i < imageFileName.length; i++) {
			spriteNames.add(imageFileName[i]);
		}
		currFrame = startInd = 0;
		endInd = spriteNames.size()-1;
		speed = 10;
		count = 0;
		playing = false;
		this.setImage(spriteNames.get(startInd));
	}
	
	/**
	 * set the animation type. 
	 * Accepts "walk", "run", and "jump"
	 * @param animation
	 */
	public void animate(String animation) {
		/* TODO: change the start and end indices based on the animation type selected */
		if(animation == "run") {
			startInd = 0;
			endInd = 3;
		} else if(animation == "stand") {
			startInd = 0;
			endInd = 0;
		}
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		count++;
		if(count%speed == 0) {
			currFrame++;
			if(currFrame > endInd)
				currFrame = startInd;
			this.setImage(spriteNames.get(currFrame));
		}
		super.update(pressedKeys, controllers);
	}

	public int getCurrFrame() {
		return currFrame;
	}

	public void setCurrFrame(int currFrame) {
		this.currFrame = currFrame;
	}

	public int getSpeed() {
		return speed;
	}

	/**
	 * The number of frames that should pass before the next step in the current animation.
	 * Thus, higher speeds equal lower animation rates.
	 * @param speed
	 */
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void setPlaying(boolean playing) {
		this.playing = playing;
	}
	

}
