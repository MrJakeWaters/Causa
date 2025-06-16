package com.causa;

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;



public class Abstract<T> {
	// class that formats a string representation of any object to be inserted into Neo4j database
	// starts with converting obect to json string, removing object key "'"
	// Example --> json --> {'name': 'Car', 'type': 'Jeep'} for object name "Car"
	// Neo4j string format --> (car:Car {name: 'Car', type: 'Jeep'})
	// ([lowercase object name]:[object name] [formatter json string])
	private T object;
	private String name;
	private Map<String, Object> map = new HashMap<>();
	private final ObjectMapper mapper = new ObjectMapper();

	// constructor
	public Abstract(T object) {
		this.object = object;
		this.name = object.getClass().getSimpleName();
		setMap(); // json sets json and map, map sets map and str
	}

	// map
	public Map<String, Object> getMap() {
		return this.map;
	}
	public void setMap() {
		try {
			for (Field field: this.object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				String key = field.getName();
				String value = String.valueOf(field.get(this.object));
				this.map.put(key, value);
			}	
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	// name
	public String getName() {
		return this.name;
	}
}
