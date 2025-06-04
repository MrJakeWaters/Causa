package com.causa;

import java.util.Map;
import java.util.HashMap;
import java.lang.reflect.Field;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;



public class Neo4jAbstract<T> {
	// class that formats a string representation of any object to be inserted into Neo4j database
	// starts with converting obect to json string, removing object key "'"
	// Example --> json --> {'name': 'Car', 'type': 'Jeep'} for object name "Car"
	// Neo4j string format --> (car:Car {name: 'Car', type: 'Jeep'})
	// ([lowercase object name]:[object name] [formatter json string])
	private T object;
	private int n;
	private String name;
	private String json;
	private String str;
	private Map<String, Object> map = new HashMap<>();
	private String alias;
	private final ObjectMapper mapper = new ObjectMapper();

	// constructor
	public Neo4jAbstract(T object, int n) {
		this.n = n;
		this.object = object;
		this.name = object.getClass().getSimpleName().replace("Neo4j", "");
		this.alias = String.valueOf(this.name.toLowerCase().charAt(0)) + Integer.toString(this.n);
		setMap(); // json sets json and map, map sets map and str
	}

	// alias
	public void setAlias(String alias) {
		this.alias = alias;
		setMap();
	}
	public String getAlias() {
		return this.alias;
	}
	
	// json
	public void setJson() {
		try {
			// sets json based off object
			this.json = mapper.writeValueAsString(this.map);
			setStr();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	public String getJson() {
		return this.json;
	}

	// map
	public Map<String, Object> getMap() {
		return this.map;
	}
	public void setMap() {
		try {
			for (Field field: this.object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				String key = String.format("%s%s", field.getName(), Integer.toString(this.n));
				String value = String.valueOf(field.get(this.object));
				this.map.put(key, value);
			}	
			setJson();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	// str
	public String getStr() {
		return this.str;
	}
	public void setStr() {
		this.str = String.format("CREATE (%s:%s %s)", this.alias, this.name, this.json.replace("\"","\'")); 
		for (Map.Entry<String, Object> entry : this.map.entrySet()) {
			String key = String.format("'%s'", entry.getKey());
			// remove integer from key string to keep all objects having the same keys 
			// only indexing the values for when it's replaced in the map
			String keyReplacement = String.format("%s", entry.getKey()).replace(Integer.toString(this.n), ""); 
			String value = String.format("'%s'", entry.getValue());
			String valueReplacement = String.format("$%s", entry.getKey());

			// update the string
			this.str = this.str.replace(key, keyReplacement); // replace key: 'name' --> name
			this.str = this.str.replace(value, valueReplacement); // replace value: 'Jake' --> $name2
		}
	}

	// name
	public String getName() {
		return this.name;
	}
}
