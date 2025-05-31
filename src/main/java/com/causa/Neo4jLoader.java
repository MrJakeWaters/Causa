package com.causa;

import java.lang.reflect.Field;
import org.neo4j.driver.Values;
import org.neo4j.driver.Driver; 
import org.neo4j.driver.AuthTokens; 
import org.neo4j.driver.GraphDatabase;

public class Neo4jLoader<Generic> {
	private final String dbUri = "neo4j+s://cf5a6658.databases.neo4j.io";
	private final String dbUser = "neo4j";
	private final String dbPassword = "2USA0UKeiuIhJvLzyXS6I9i4rJY8zjKtq6IfWAS40fg";	
	private final Driver driver;
	
	public Neo4jLoader() {
		this.driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
	}
	
	// loads decision object to Neo4j database
	public void saveObject(Decision content) {
		// create object Neo4j statement
		// CREATE (carCategory:Category {name: 'Car'})
		try {
			String stmtAttributes = "{";
			for (Field attribute: content.getClass().getDeclaredFields()) {
				attribute.setAccessible(true);
				stmtAttributes = String.format("%s%s: $%s,", stmtAttributes, attribute.getName(), attribute.getName());
			} 
			stmtAttributes = stmtAttributes.substring(0, stmtAttributes.length()-1) + "}";
			String statement = String.format("CREATE (n:%s %s)", content.getClass().getSimpleName(), stmtAttributes);
			System.out.println(statement);
		} catch (Exception e) {
			e.printStackTrace();	
		}
	}
}
