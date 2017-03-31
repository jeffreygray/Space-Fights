package edu.virginia.engine.controller;

import net.java.games.input.*;

/**
 * Wrapper for the Component in JInput API. 
 * */
public class GamePadComponent {

	/* The component from the game pad */
	private Component comp;
	
	public GamePadComponent(Component component){
		this.comp = component;
	}
	
	public String getId(){
		return this.comp.getIdentifier().getName();
	}
	
	public String getName(){
		return this.comp.getName();
	}
	
	public double getData(){
		return this.comp.getPollData();
	}
	
	public boolean equals(String id){
		return this.getId().equals(id);
	}
}
