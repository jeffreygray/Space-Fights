// updated to print out energy and decrement upon hitting L
// TODO: 4.7.2017 ships and ships bounce, fix bug hugging bottom of map, rotate hb

package edu.virginia.lab1test;

import java.awt.Graphics;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.display.Game;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.spacefights.classes.Screens;


public class SpaceFights extends Game {
	// jeff 1800x1000 - will use for game expo bc larger screen 
	public static int gameWidth = 1800;
	public static int gameHeight = 1000;
	Screens screen;
	


	public SpaceFights() {
		super("Space Fights", gameWidth, gameHeight);
		screen = new Screens(gameWidth, gameHeight);
	}

	@Override
	public void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		if(screen != null) {
			switch(screen.getSceneToUpdate()) {
			case Screens.SELECT_SCENE:
				screen.shipSelectScreen(pressedKeys, controllers);
				break;
			case Screens.GAME_SCENE:
				screen.gameScreenUpdate(pressedKeys, controllers);
				break;
			case Screens.GAME_OVER:
				screen.gameOverUpdate(pressedKeys, controllers);
			}	
			
			TweenJuggler.nextFrame();
		}
		
	}

	@Override
	public void draw(Graphics g){
		super.draw(g);
		if(screen !=null) {
			screen.getScene().draw(g);
		}
	}

	public static void main(String[] args) {
		SpaceFights game = new SpaceFights();
		game.start();
	}

}
