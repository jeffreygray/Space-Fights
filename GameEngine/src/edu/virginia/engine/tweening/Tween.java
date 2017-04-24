package edu.virginia.engine.tweening;

import java.util.ArrayList;
import java.util.Date;

import edu.virginia.engine.display.*;
import edu.virginia.engine.events.EventDispatcher;

/**Object   representing   one   Sprite   being   tweened   in   some   way.   Can   have   multiple 
TweenParam objects */
public class Tween extends EventDispatcher {
	private DisplayObject obj;
	private boolean complete;
	private ArrayList<TweenParam> fields = new ArrayList<TweenParam>();
	private long startTime;
	
	public Tween(DisplayObject obj) {
		this.obj = obj;
		this.complete = false;
	}
	
	public Tween(DisplayObject obj, TweenTransitions transition) {
		this.obj = obj;
		this.complete = false;
	}
	
	public void animate(TweenableParam fieldToAnimate, double startVal, double endVal, double time, Function function) {
		fields.add(new TweenParam(fieldToAnimate, startVal, endVal, time, function));
	}
	
	public void setValue(TweenableParam param, double val) {
		switch(param) {
		case X:
			obj.setXPosition(val);
			break;
		case Y:
			obj.setYPosition(val);
			break;
		case SCALE_X:
			obj.setScaleX(val);
			break;
		case SCALE_Y:
			obj.setScaleY(val);
			break;
		case ROTATION:
			obj.setRotation(val);
			break;
		case ALPHA:
			obj.setAlpha((float) val);
		}
	}
	
	public boolean isComplete() {
		return complete;
	}
	
	public void beginStartTime() {
		startTime = System.nanoTime()/1000;
		//System.out.println("START: " + startTime);
	}

	public void update() {
		for(int i = 0; i < fields.size(); i++) {
			long currTime = System.nanoTime()/1000;
			TweenParam tp = fields.get(i);
			double percentComplete = (currTime - startTime)/tp.getTime();
			//System.out.println(percentComplete);
			setValue(tp.getParameter(), tp.getStartVal()+(tp.getEndVal() - tp.getStartVal())*TweenTransitions.applyTransition(percentComplete, tp.getFunction()));
			if(percentComplete >= 1)
				this.complete = true;
		}
	}

	public DisplayObject getObj() {
		return obj;
	}
}
