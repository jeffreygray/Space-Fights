package edu.virginia.engine.util;

/**
 * This is a simple class that gives you the ability to easily track time between frames or other events in
 * your game. This class is essentially a stopwatch.
 * */
public class GameClock {

	private long startTime;
	
	public GameClock(){
		resetGameClock();
	}
	
	/** 
	 * 
	 * @return returns milliseconds passed between since clock creation or clock reset
	 */
	public double getElapsedTime() {
		return (System.nanoTime() - startTime) / 1000000.0;
	}

	// resets both times to current time
	public void resetGameClock() {
		startTime = System.nanoTime();
	}

}