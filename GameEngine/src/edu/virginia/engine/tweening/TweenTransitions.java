package edu.virginia.engine.tweening;

/** holds various complex tween transition functions */
public class TweenTransitions {
	
	public static double applyTransition(double percentComplete, Function f) {
		switch(f) {
		case EASE_IN_OUT_QUAD:
			return Math.pow(percentComplete, 2)/(Math.pow(percentComplete, 2) + Math.pow((1-percentComplete), 2));
		default:
			return percentComplete;
			
		}
	}
	
}
