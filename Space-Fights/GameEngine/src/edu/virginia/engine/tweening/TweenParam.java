package edu.virginia.engine.tweening;

public class TweenParam {
	private TweenableParam parameter;
	private double startVal, endVal, time;
	private Function function;
	public TweenParam(TweenableParam paramToTween, double startVal, double endVal, double time, Function function) {
		parameter = paramToTween;
		this.startVal = startVal;
		this.endVal = endVal;
		this.time = time;
		this.function = function;
	}

	public TweenableParam getParameter() {
		return parameter;
	}

	public double getStartVal() {
		return startVal;
	}

	public double getEndVal() {
		return endVal;
	}

	public double getTime() {
		return time;
	}
	
	public Function getFunction() {
		return function;
	}
}
