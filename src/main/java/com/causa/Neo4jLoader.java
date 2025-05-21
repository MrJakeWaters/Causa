package com.causa;

import org.neo4j.driver.Values;
import org.neo4j.driver.Driver; 
import org.neo4j.driver.AuthTokens; 
import org.neo4j.driver.GraphDatabase;

public class Neo4jLoader<Generic> {
	private final String dbUri = "neo4j+s://cf5a6658.databases.neo4j.io";
	private final String dbUser = "neo4j";
	private final String dbPassword = "2USA0UKeiuIhJvLzyXS6I9i4rJY8zjKtq6IfWAS40fg";	
	private final Driver driver;
	private Generic content;
	
	public Neo4jLoader(Generic content) {
		this.content = content;
		this.driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
	}
	
	// loads decision object to Neo4j database
	public void neo4jLoader(Driver driver, String[] attributes) {
		try {
			String stmtAttributes = "{";
			for (String attribute: attributes) {
				stmtAttributes = String.format("%s%s: $%s,", stmtAttributes, attribute, this.content.getAttributeName());
			} 
			// need to remove the final "," from the string and close the object bracket
			stmtAttributes = stmtAttributes.substring(0, stmtAttributes.length-2) + "}";
			String statement = String.format("CREATE (n:%s %s)", this.content.getClass().getSimpleName(), stmtAttributes);
			driver.session().run(statement, Values.parameters("proposition", this.proposition, "decisionTimestamp", this.decisionTimestamp.toString()));
			driver.session().close();
			System.out.println("Decision Successfully Loaded to Neo4j Database");
		} catch (Exception e) {
			System.out.println(e);
			driver.session().close();
		}
		
	}
}
