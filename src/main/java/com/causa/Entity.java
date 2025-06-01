package com.causa;

public class Entity {
	private String name = "Server"; 
	
	// constructor
	public Entity() {}
	
	public Entity(String name) {
		this.name = name;
	}
	
	// getter and setter for name
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
