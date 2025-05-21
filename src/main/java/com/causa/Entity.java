package com.causa;

public class Entity {
	private String name; 
	private Decision[] decisions;
	
	// constructor
	public Entity() {}
	
	public Entity(String name) {
		this.name = name;
		this.decisions = new Decision[1];
	}

	public String getName() {
		return this.name;
	}
	
	public Decision[] getDecisions() {
		return this.decisions;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDecisions(Decision[] decisions) {
		this.decisions = decisions;
	}
}
