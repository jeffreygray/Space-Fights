package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import edu.virginia.engine.display.*;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.QuestEvent;
import edu.virginia.engine.events.QuestManager;
import sun.net.www.content.audio.x_aiff;

public class LabFourGame extends Game {
	DisplayObjectContainer scene, coin;
	AnimatedSprite mario;
	int speed = 15;
	QuestManager questManager;
	ArrayList<String> activeQuests;
	
	public LabFourGame() {
		super("The Main Event!", 1200, 700);
		String[] images = {"mario_0.png", "mario_1.png", "mario_2.png", "mario_3.png"};
		mario = new AnimatedSprite("mario", images);
		coin = new Sprite("planet", "planet_6.png");
		scene = new DisplayObjectContainer("scene");
		questManager = new QuestManager();
		activeQuests = questManager.getActiveQuests();
		
		scene.addChild(coin);
		scene.addChild(mario);
		
		mario.setParent(scene);
		coin.setParent(scene);
		
		coin.setPosition(1000, 300);
		
		coin.addEventListener(questManager, QuestEvent.COIN_PICKED_UP);
		mario.addEventListener(questManager, QuestEvent.MOVED_BACKWARDS);
	}
	
	@Override
	public void update(ArrayList<String> pressedKeys){
		if(mario != null) {
			Point p = new Point(mario.getPosition());
			if(pressedKeys.contains("Up"))
				p.translate(0, -speed);
			if(pressedKeys.contains("Left")) {
				p.translate(-speed, 0);
				mario.dispatchEvent(new Event(QuestEvent.MOVED_BACKWARDS, mario));
			}
			if(pressedKeys.contains("Right")) 
				p.translate(speed, 0);
			if(pressedKeys.contains("Down")) 
				p.translate(0, speed);
			if(!(p.equals(mario.getPosition()))) 
				mario.animate("run");
			else mario.animate("stand");
			mario.setPosition(p);
			mario.update(pressedKeys);
			
			Rectangle marioHB = new Rectangle((int)mario.getPosition().getX(), (int)mario.getPosition().getY(), 
					mario.getUnscaledWidth(), mario.getUnscaledHeight());
			Rectangle coinHB = new Rectangle(coin.getPosition().x, coin.getPosition().y, 
					coin.getUnscaledWidth(), coin.getUnscaledHeight());
			if(marioHB.intersects(coinHB)) {
				coin.dispatchEvent(new Event(QuestEvent.COIN_PICKED_UP, coin));
				coin.setVisible(false);
			}
			
			activeQuests = questManager.getActiveQuests();
			
		}
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		if(scene != null){
			scene.draw(g);
		}
		g.drawString("Active Quests", 50, 500);
		for(int i = 0; i < activeQuests.size(); i++)
			g.drawString(activeQuests.get(i), 50, 515+15*i);
	}

	public static void main(String[] args) {
		LabFourGame game = new LabFourGame();
		game.start();
	}

}
