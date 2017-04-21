// updated to print out energy and decrement upon hitting L
// TODO: 4.7.2017 ships and ships bounce, fix bug hugging bottom of map, rotate hb

package edu.virginia.lab1test;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.events.CollisionManager;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.spacefights.classes.Projectile;
import edu.virginia.spacefights.classes.Screens;
import edu.virginia.spacefights.classes.Ship;
import edu.virginia.spacefights.classes.ShipType;
import edu.virginia.engine.util.SoundManager;


public class SpaceFights extends Game {
	static int gameWidth = 1200;
	static int gameHeight = 700;
	Screens screen;
	


	public SpaceFights() {
		super("Space Fights", gameWidth, gameHeight);
		screen = new Screens(gameWidth, gameHeight);
	}

	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		switch(screen.getSceneToUpdate()) {
		case Screens.SELECT_SCENE:
			screen.shipSelectScreen(pressedKeys, controllers);
		break;
		case Screens.GAME_SCENE:
			screen.gameScreenUpdate(pressedKeys, controllers);
		break;
		}		
		TweenJuggler.nextFrame();
	}

	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(screen.getScene() !=null) {
			screen.getScene().draw(g);
		}
	}

	public static void main(String[] args) {
		SpaceFights game = new SpaceFights();
		game.start();
	}

}
