package com.causa;

import java.lang.reflect.Field;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Neo4jAbstract<T> {
	// class that formats a string representation of any object to be inserted into Neo4j database
	// starts with converting obect to json string, removing object key "'"
	// Example --> json --> {'name': 'Car', 'type': 'Jeep'} for object name "Car"
	// Neo4j string format --> (car:Car {name: 'Car', type: 'Jeep'})
	// ([lowercase object name]:[object name] [formatter json string])
	private T object;	
	private String name;
	private String str;
	private boolean loadStatus = false;

	// constructor
	public Neo4jAbstract(T object) {
		this.object = object;
		this.name = object.getClass().getSimpleName();
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(object).replace("\"","\'");
			this.str = String.format("(%s:%s %s)", this.name.toLowerCase(), this.name, json); 
			for (Field field: object.getClass().getDeclaredFields()) {
				String jsonKey = String.format("'%s'", field.getName());
				this.str = this.str.replace(jsonKey, field.getName());
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public String getJson() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(this.object).replace("\"","\'");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "Cannot Parse to Json";
		}
	}

	public boolean getLoadStatus() {
		return this.loadStatus;
	}
	
	public void setLoadStatus(boolean status) {
		this.loadStatus = status;
	}

	public String getStr() {
		return this.str;
	}

	public String getName() {
		return this.name;
	}
}
