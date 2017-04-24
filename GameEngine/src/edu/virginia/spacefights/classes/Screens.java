package edu.virginia.spacefights.classes;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import edu.virginia.engine.controller.GamePad;
import edu.virginia.engine.display.DisplayObjectContainer;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.CollisionEvent;
import edu.virginia.engine.events.CollisionManager;
import edu.virginia.engine.events.CombatEvent;
import edu.virginia.engine.events.DeathManager;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.tweening.Function;
import edu.virginia.engine.tweening.Tween;
import edu.virginia.engine.tweening.TweenJuggler;
import edu.virginia.engine.tweening.TweenableParam;
import edu.virginia.engine.util.SoundManager;

public class Screens implements IEventListener {
	public static final int GAME_SCENE = 0;
	public static final int SELECT_SCENE = 1;
	private int sceneToUpdate = SELECT_SCENE;

	/**
	 * @return the sceneToUpdate
	 */
	public int getSceneToUpdate() {
		return sceneToUpdate;
	}

	
	static double dampen = -0.65;
	int gameWidth, gameHeight;
	private DisplayObjectContainer gameScreen, shipSelectScreen;
	private Sprite scene, plat1, plat2, plat3, plat4, plat5, plat6, plat7, plat8, plat9, plat10, plat11, plat12, plat13, plat14, plat15, plat16;

	private ArrayList<Sprite> platforms = new ArrayList<Sprite>();
	private ArrayList<Ship> players = new ArrayList<Ship>();	
	public List<Ship> deadPlayers = new ArrayList<>();

	private ArrayList<ArrayList<Sprite>> playerAvailableShips = new ArrayList<ArrayList<Sprite>>(); // index is pNum, list contains images of the various ship types
	private CollisionManager collisionManager;
	private DeathManager deathManager;
	private double elasticity = 1.7;


	ArrayList<Sprite> selectorBoxes;
	int[] shipChoice = {0, 0};
	private boolean[] pressedButtonLastFrame = new boolean[4];
	private boolean[] playersReady = new boolean[4];

	/**A class to handle the updating of the screens 
	 * @param width the width of the game window
	 * @param height the height of the game window
	 * */
	public Screens(int width, int height) {
		gameWidth = width;
		gameHeight = height;

		scene = new Sprite("scene");
		gameScreen = new Sprite("gameScreen", "background.png");
		shipSelectScreen = new Sprite("shipSelecNode", "background.png");
		selectorBoxes = new ArrayList<Sprite>();

		collisionManager = new CollisionManager();
		deathManager = new DeathManager();

		makeShipSelectScreen();
		makeGameScreen();

		scene.addChild(shipSelectScreen);
		SoundManager.playMusic("sound.wav");
		TweenJuggler tj = new TweenJuggler();
	}

	public void makeShipSelectScreen() {
		for(int i = 0; i < 2; i++) {
			Sprite selector = new Sprite("p"+i+"SelectorBox", "player"+i+"Selector.png");

			playerAvailableShips.add(new ArrayList<Sprite>());
			for(ShipType st : ShipType.values()) {
				Sprite newShip = new Sprite(st.toString(), st.getImageName());
				selector.addChild(newShip);
				newShip.setPosition(newShip.getParent().getWidth()/5, newShip.getParent().getHeight()/5);
				newShip.setAlpha(0);
				playerAvailableShips.get(i).add(newShip);
			}
			playerAvailableShips.get(i).get(shipChoice[i]).setAlpha(1);
			selector.setPosition(gameWidth*(i+1)/5, gameHeight/5);

			shipSelectScreen.addChild(selector);
			selectorBoxes.add(selector);
		}
	}

	public void shipSelectScreen(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		if(scene != null) {
			// if all 4 (or however many there are...) players ready, then call gameStart
			if(playersReady[0] && playersReady[1]) {
				gameStart();
			}
			for(int i = 0; i < controllers.size(); i++) {
				Sprite currently_selected_ship = playerAvailableShips.get(i).get(shipChoice[i]);
				Sprite selector = selectorBoxes.get(i);
				if(controllers.get(i).getLeftStickXAxis() == -1 && !playersReady[i]) {
					if(!pressedButtonLastFrame[i]){ 
						pressedButtonLastFrame[i] = true;
						Tween oldShipTween = new Tween(currently_selected_ship);
						// tween the old selected ship away
						oldShipTween.animate(TweenableParam.X, currently_selected_ship.getPosition().getX(), 
								-selector.getWidth(), 400, Function.LINEAR);
						oldShipTween.animate(TweenableParam.ALPHA, 1, 0, 400, Function.LINEAR);
						TweenJuggler.getInstance().add(oldShipTween);

						// Select the new ship as current, then tween in the newly selected ship
						shipChoice[i] = (shipChoice[i]-1) < 0 ? ShipType.values().length-1 : shipChoice[i]-1;
						currently_selected_ship = playerAvailableShips.get(i).get(shipChoice[i]);
						Tween nextShipTween = new Tween(currently_selected_ship);
						nextShipTween.animate(TweenableParam.X, selector.getWidth(), 
								selector.getWidth()/2-currently_selected_ship.getWidth()/2, 400, Function.LINEAR);
						nextShipTween.animate(TweenableParam.ALPHA, 0, 1, 400, Function.LINEAR);
						TweenJuggler.getInstance().add(nextShipTween);
					}
				}
				else if(controllers.get(i).getLeftStickXAxis() == 1  && !playersReady[i]) {
					if(!pressedButtonLastFrame[i]){ 
						pressedButtonLastFrame[i] = true;
						Tween oldShipTween = new Tween(currently_selected_ship);
						// tween the old selected ship away
						oldShipTween.animate(TweenableParam.X, currently_selected_ship.getPosition().getX(), 
								selector.getWidth(), 400, Function.LINEAR);
						oldShipTween.animate(TweenableParam.ALPHA, 1, 0, 400, Function.LINEAR);
						TweenJuggler.getInstance().add(oldShipTween);
						
						// Select the new ship as current, then tween in the newly selected ship
						shipChoice[i] = (shipChoice[i]-1) < 0 ? ShipType.values().length-1 : shipChoice[i]-1;
						currently_selected_ship = playerAvailableShips.get(i).get(shipChoice[i]);
						Tween nextShipTween = new Tween(currently_selected_ship);
						nextShipTween.animate(TweenableParam.X, -selector.getWidth(), 
								selector.getWidth()/2-currently_selected_ship.getWidth()/2, 400, Function.LINEAR);
						nextShipTween.animate(TweenableParam.ALPHA, 0, 1, 400, Function.LINEAR);
						TweenJuggler.getInstance().add(nextShipTween);
					}
				}
				else if((controllers.get(i).isButtonPressed(GamePad.BUTTON_A) || controllers.get(i).isButtonPressed(GamePad.BUTTON_START))
						&& !playersReady[i]) {
					if(!pressedButtonLastFrame[i]) {
						pressedButtonLastFrame[i] = true;
						playersReady[i] = true;
						// put a "READY" image over top of selector/ship box.
						selector.addChild(new Sprite("player"+i+"Ready", "ready.png"));
					}
				} else if(controllers.get(i).isButtonPressed(GamePad.BUTTON_B)) {
					if(!pressedButtonLastFrame[i]) {
						pressedButtonLastFrame[i] = true;
						playersReady[i] = false;
						selector.removeChildByID("player"+i+"Ready");
					}
				}	
				else pressedButtonLastFrame[i] = false;
			}
		}
	}



	private DisplayObjectContainer playerNode = new DisplayObjectContainer("playersNode");
	private DisplayObjectContainer levelNode = new DisplayObjectContainer("levelNode");
	private DisplayObjectContainer playersLivesBar = new DisplayObjectContainer("lifeBars");

	public void gameStart() {
		players.clear();
		scene.removeChild(shipSelectScreen);
		scene.addChild(gameScreen);
		scene.addChild(playersLivesBar);
		playerNode.removeAll();
		for(int i = 0; i<shipChoice.length; i++) {
			// make and add player to display tree
			Ship player = new Ship(ShipType.valueOf(playerAvailableShips.get(i).get(shipChoice[i]).getId()), i);
			player.addEventListener(collisionManager, CollisionEvent.PLATFORM);
			player.addEventListener(deathManager, CombatEvent.DEATH);
			player.addEventListener(this, CombatEvent.DEATH);
			player.setScaleX(0.8);
			player.setScaleY(0.65);
			player.setPivotPoint(player.getWidth()/2, player.getHeight()/2);
			playerNode.addChild(player);
			players.add(player);

			// make and add lives bar to display tree
			Sprite container = new Sprite("player"+i+"LifeContainer", "player"+i+"Selector.png");
			container.setScaleY(0.5);
			container.setPosition(gameWidth*(i+1)/(shipChoice.length+1), gameHeight-container.getHeight());
			for(int j = 1; j <= 3; j++) {
				Sprite heart = new Sprite("black_heart_"+j, "black_heart.png");
				container.addChild(heart);
				heart.setPosition(j/5.0*heart.getParent().getWidth(), heart.getParent().getHeight()/2 - heart.getHeight()/2);
				heart.addChild(new Sprite("heart_"+j, "heart.png"));
			}
			playersLivesBar.addChild(container);
		}
		sceneToUpdate = Screens.GAME_SCENE;
	}

	public void makeGameScreen() {
		gameScreen.addChild(playerNode);
		gameScreen.addChild(levelNode);

		// IDEA TODO: make a level builder class; this will also maybe help to make a level select screen
		plat1 = new Sprite("plat1", "platformSpaceVertical.png");
		plat2 = new Sprite("plat2", "moon.png");
		plat2.setScaleX(9);
		plat2.setScaleY(9);
		plat3 = new Sprite("plat3", "platformSpaceVertical.png");
		plat4 = new Sprite("plat4", "platformSpace.png");
		plat5 = new Sprite("plat5", "platformSpace.png");
		plat6 = new Sprite("plat6", "platformSpace.png");
		plat7 = new Sprite("plat7", "platformSpace.png");
		plat8 = new Sprite("plat8", "platformSpace.png");
		plat9 = new Sprite("plat9", "platformSpace.png");
		plat10 = new Sprite("plat10", "platformSpace.png");
		plat11 = new Sprite("plat11", "platformSpace.png");
		plat12 = new Sprite("plat12", "platformSpace.png");
		plat13 = new Sprite("plat13", "platformSpaceVertical.png");
		plat14 = new Sprite("plat14", "platformSpace.png");
		plat15 = new Sprite("plat15", "platformSpaceVertical.png");
		plat16 = new Sprite("plat15", "platformSpace.png");

		platforms.add(plat1);
		platforms.add(plat2);
		platforms.add(plat3);
		platforms.add(plat4);
		platforms.add(plat5);
		platforms.add(plat6);
		platforms.add(plat7);
		platforms.add(plat8);
		platforms.add(plat9);
		platforms.add(plat10);
		platforms.add(plat11);
		platforms.add(plat12);
		platforms.add(plat13);
		platforms.add(plat14);
		platforms.add(plat15);
		platforms.add(plat16);

		plat1.setPosition(400, 250);
		plat2.setPosition(900, 300);
		plat3.setPosition(1350, 470);
		plat4.setPosition(0, 920);
		plat5.setPosition(plat4.getWidth(), 925);
		plat6.setPosition(plat4.getWidth() * 2, 925);
		plat7.setPosition(plat4.getWidth() * 3, 925);
		plat8.setPosition(plat4.getWidth() * 4, 925);
		plat9.setPosition(plat4.getWidth() * 5, 925);
		plat10.setPosition(plat4.getWidth() * 6, 925);
		plat11.setPosition(plat4.getWidth() * 7, 925);
		plat12.setPosition(plat4.getWidth() * 8, 925);
		plat13.setPosition(0,0);
		plat13.setScaleY(10);
		plat14.setPosition(1,0);
		plat14.setScaleX(10);
		plat15.setPosition(gameWidth - plat15.getWidth(), 0);
		plat15.setScaleY(10);
		plat16.setPosition(0,630);
		plat16.setScaleX(10);

		for(int i = 0; i < platforms.size(); i++) {
			Sprite plat = platforms.get(i);
			levelNode.addChild(plat);
		}
	}


	public void gameScreenUpdate(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		if (scene != null) { // makes sure we loaded everything
			scene.update(pressedKeys, controllers);

			// check if only 1 player is left on the screen
			if(players.size() == 1) {
				// Display Game Over; players.get(0).getPlayerNum() wins
				sceneToUpdate = -1;
				Ship winner = players.get(0);
				Tween winnerDance = new Tween(winner);
				winnerDance.animate(TweenableParam.X, 50, gameWidth, 2000, Function.EASE_IN_OUT_QUAD);
				winnerDance.animate(TweenableParam.Y, 50, 150, 2000, Function.LINEAR);
				winnerDance.animate(TweenableParam.ROTATION, 90, 0, 2000, Function.LINEAR);
				TweenJuggler.getInstance().add(winnerDance);
				return;
			}
			// Keeps players within bounds of game window by rebounding them back in
			for(Ship player: players) {
				Point shipPos = player.getPosition();
				if (shipPos.x <= 0) {
					player.setXPosition(1);
					player.setXv(-player.getXv());
				} else if (shipPos.x > gameWidth - player.getWidth()) {
					player.setXPosition(gameWidth - player.getWidth()-1);
					player.setXv(dampen * player.getXv());
				}
				if (shipPos.y <= 0) {
					player.setYPosition(1);
					player.setYv(dampen * player.getYv());
				} else if (shipPos.y > gameHeight - player.getHeight()) {
					player.setYPosition(gameHeight - player.getHeight()-1);
					player.setYv(dampen * player.getYv());
				}
				// ship-to-ship collision
				ArrayList<Ship> alreadyCollided = new ArrayList<Ship>();
				for(Ship other: players) {
					if(player.collidesWith(other) && other.getPlayerNum() != player.getPlayerNum() /*&& !alreadyCollided.contains(other)*/) {
						System.out.println("COLLISION");
						alreadyCollided.add(player);
						alreadyCollided.add(other);
						Rectangle myHB = player.getHitbox();
						Rectangle otherHB = player.getHitbox();
						Rectangle overlap = myHB.intersection(otherHB);
						if (overlap.width < overlap.height) {
							player.setNrg((int) (player.getNrg()-Math.abs(2*other.getM()*other.getXv())));
							other.setNrg((int) (other.getNrg()-Math.abs(2*player.getM()*player.getXv())));
							//System.out.println(player.getNrg());
							// momentum formulae
							double oldV1 = player.getXv();
							player.setXv((elasticity*other.getM()*other.getXv() + player.getXv()*(player.getM()-elasticity*other.getM()))/(other.getM() + player.getM()));  
							other.setXv((elasticity *player.getM()*oldV1 + other.getXv()*(other.getM()-elasticity*player.getM()))/(other.getM() + player.getM()));
						} else {// coming from top or bottom
							player.setNrg((int) (player.getNrg()-Math.abs(2*other.getM()*other.getYv())));
							other.setNrg((int) (other.getNrg()-Math.abs(2*player.getM()*player.getYv())));
							double oldV2 = player.getYv();
							player.setYv((elasticity*other.getM()*other.getYv() + player.getYv()*(player.getM()-elasticity*other.getM()))/(other.getM() + player.getM()));  
							other.setYv((elasticity*player.getM()*oldV2 + other.getYv()*(other.getM()-elasticity*player.getM()))/(other.getM() + player.getM()));
						}
					}
				}
			}
			/*
			 * for players:
			 *   for each plat:
			 *   	check collide
			 *   	for each for each proj:
			 *     		check collide w/ plat
			 *   for each projectile
			 *   	for each ship
			 *     		check collide
			 */

			for(Ship player: players) {

				for(Sprite plat: platforms) {

					// player-platform collision
					if (player.collidesWith(plat)) {
						player.dispatchEvent(new Event(CollisionEvent.PLATFORM, player));

						Rectangle mHB = player.getHitbox();
						Rectangle pHB = plat.getHitbox();
						Rectangle overlap = mHB.intersection(pHB);

						if (overlap.width < overlap.height) {
							// coming from side; which side?
							if (mHB.x > pHB.x) { // collision with right side
								player.setPosition(pHB.getX() + pHB.getWidth(), player.getPosition().y);	
							} else // left side
								player.setPosition(pHB.getX() - mHB.getWidth(), player.getPosition().y); 
							// System.out.println("LR WALL HIT");
							player.setXv(dampen * player.getXv());  
							// Dampen is currently 0.8, can change later
						} else {// coming from top or bottom
							if (mHB.y < pHB.y) { // above
								player.setPosition(player.getPosition().x, pHB.getY() - mHB.getHeight()); 
							} else { // below
								player.setPosition(player.getPosition().x, pHB.getY() + pHB.getHeight()); 
							}
							player.setYv(dampen * player.getYv());
						}

					}
					// projectile-platform collision
					for (Projectile p : player.getProjectiles()) {
						if (p.collidesWith(plat)) {
							p.dispatchEvent(new Event(CollisionEvent.PLATFORM, p));
							//SoundManager.playMusic("bullet.wav");

							if (!p.isHasBounce()) {
								p.setRemove(true);
							}

							Rectangle mHB = p.getHitbox();
							Rectangle pHB = plat.getHitbox();
							Rectangle overlap = mHB.intersection(pHB);

							if (overlap.width < overlap.height) {
								// coming from side; which side?
								if (mHB.x > pHB.x) { // collision with right
									p.setPosition(pHB.getX() + pHB.getWidth(), p.getPosition().y);
								} else // left side
									p.setPosition(pHB.getX() - mHB.getWidth(), p.getPosition().y); 
								// System.out.println("LR WALL HIT");
								p.setXv(-p.getXv());
							} else { // either above or below platform
								if(p.isHasBounce())
									p.setYv(-p.getYv());
								// reset position to get it "out" of the platform (so hitboxes don't intersect)
								if (mHB.y < pHB.y) // above
									p.setPosition(p.getPosition().x, pHB.getY() - mHB.getHeight());
								else // below
									p.setPosition(p.getPosition().x, pHB.getY() + pHB.getHeight());

							}
						}
					}
				} // End platform collisions
				// player-projectile collision
				for (Projectile p : player.getProjectiles()) {
					for (Ship s : players) {
						// checks with collision with enemies
						if (p.collidesWith(s) && s.getPlayerNum() != player.getPlayerNum()) {
							s.setNrg(s.getNrg() - p.getDamage());
							p.setRemove(true);
						}
					}
				} // end player-projectile collision

			} // end for each player loop
			players.removeAll(deadPlayers);
		} // end not null check
	}

	public DisplayObjectContainer getScene() {
		return scene;
	}

	@Override
	public void handleEvent(Event event) {
		switch(event.getEventType()) {
		case CombatEvent.DEATH:
			Ship s = (Ship) (event.getSource());
			int sNum = s.getPlayerNum();
			((DisplayObjectContainer) ((DisplayObjectContainer) playersLivesBar.getChildren().get(sNum)).getChildren().get(s.getLives())).removeIndex(0);
			if(s.getLives() == 0) {
				System.out.println("RemoveIndex :"+sNum);
				playerNode.removeChild(s);
				deadPlayers.add(s);
			}
		}

	}
}
