package com.causa;

import java.lang.reflect.Field;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Neo4jAbstract<T> {
	private T object;	
	private String name;
	private String str;

	// constructor
	public Neo4jAbstract(T object) {
		this.object = object;
		this.name = object.getClass().getSimpleName();
		ObjectMapper mapper = new ObjectMapper();
		try {
			String json = mapper.writeValueAsString(object).replace("\"","\'");
			this.str = String.format("(%s: %s %s)", name.toLowerCase(), name, json);
			// get json string and set statement
			// removing "'" from json keys (but leave the value pairs), string conversion example:
			// 	CREATE (carCategory:Category {'name': 'Car'}) --> CREATE (carCategory:Category {name: 'Car'})
			for (Field field: object.getClass().getDeclaredFields()) {
				String jsonKey = String.format("'%s'", field.getName());
				this.str = this.str.replace(jsonKey, field.getName());
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	public String getStr() {
		return this.str;
	}

	public String getName() {
		return this.name;
	}
}
