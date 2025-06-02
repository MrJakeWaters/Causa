package com.causa;

import org.neo4j.driver.Driver; 
import org.neo4j.driver.AuthTokens; 
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.exceptions.*;

public class Neo4jLoader<Generic> {
	// static attributes
	private final String dbUri = "neo4j+s://cf5a6658.databases.neo4j.io";
	private final String dbUser = "neo4j";
	private final String dbPassword = "2USA0UKeiuIhJvLzyXS6I9i4rJY8zjKtq6IfWAS40fg";	
	private final Driver driver;
	private String statement;
	
	// constructor
	public Neo4jLoader() {
		this.driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
		this.driver.verifyConnectivity();
		System.out.println("Neo4j Database Connection Working\n");
	}
	
	public String getStatement() {
		return this.statement;
	}

	// loads decision object to Neo4j database
	public void saveObject(Decision decision) {
		// converts object to json string, generates Neo4j insert statement to load to Database
		// Neo4j SQL statement Example: CREATE (carCategory:Category {name: 'Car'})
		Neo4jAbstract neo4jAbstract = new Neo4jAbstract(decision);
		try {
			this.statement = String.format("CREATE %s", neo4jAbstract.getStr());
			System.out.println(this.statement);
			this.driver.session().run(this.statement);
			this.driver.session().close();
		} catch (Neo4jException e) {
			System.out.println(String.format("%s node already exists", neo4jAbstract.getName()));
			this.driver.session().close();
		}
	}

	// load decision with rationale
	public Neo4jAbstract[] saveObject(Decision decision, Rationale rationale) {
		// converts object to json string, generates Neo4j insert statement to load to Database
		// Neo4j SQL statement Example: CREATE (carCategory:Category {name: 'Car'})
		Neo4jAbstract decisionAbstract = new Neo4jAbstract(decision);
		Neo4jAbstract rationaleAbstract = new Neo4jAbstract(rationale);
		String relationship = "MATCH (d:Decision),(r:Rationale) where r.proposition = d.proposition and r.entity = d.entity create (d)-[:BECAUSE] ->(r)";
		Neo4jAbstract[] saveObjects = {decisionAbstract, rationaleAbstract};
		try {
			this.statement = String.format("CREATE %s,%s", decisionAbstract.getStr(), rationaleAbstract.getStr());
			System.out.println(this.statement);

			// execute statements to create object and relationship
			this.driver.session().run(this.statement);
			decisionAbstract.setLoadStatus(true);
			rationaleAbstract.setLoadStatus(true);
			this.driver.session().run(relationship);

			// close session
			this.driver.session().close();
		} catch (Neo4jException e) {
			e.getMessage();
			this.driver.session().close();
		}
		return saveObjects;
	}
}
