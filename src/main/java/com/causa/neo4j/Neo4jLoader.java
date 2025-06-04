package com.causa;

// external
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import org.neo4j.driver.Driver; 
import org.neo4j.driver.AuthTokens; 
import org.neo4j.driver.exceptions.*;
import org.neo4j.driver.GraphDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Neo4jLoader<Generic> {
	// static attributes
	private final String dbUri = "neo4j+s://cf5a6658.databases.neo4j.io";
	private final String dbUser = "neo4j";
	private final String dbPassword = "2USA0UKeiuIhJvLzyXS6I9i4rJY8zjKtq6IfWAS40fg";	
	private final Driver driver;
	
	// constructor
	public Neo4jLoader() {
		this.driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
		this.driver.verifyConnectivity();
		System.out.println("Neo4j Database Connection Working\n");
	}
	
	// load api decision with rationale
	public List<Neo4jAbstract> saveObject(ApiDecision apiDecision, boolean display) {
		// converts object to json string, generates Neo4j insert statement to load to Database
		// Neo4j SQL statement Example: CREATE (carCategory:Category {name: 'Car'})
		int n = 0;
		List<Neo4jAbstract> objects = new ArrayList<>();		
		Neo4jDecision decision = new Neo4jDecision(apiDecision);
		objects.add(new Neo4jAbstract(decision, n)); // add decision abstraction
		for (ApiRationale apiRationale: apiDecision.getRationale()) {
			Neo4jRationale rationale = new Neo4jRationale(
				apiRationale.getRationaleTimestamp(),
				apiRationale.getJustification()
			);
			objects.add(new Neo4jAbstract(rationale, n)); // add rationale abstraction	
			n++;
		}

		// create query to execute for decision object
		String query = "";
		String createObjects = "";
		Map<String, Object> map = new HashMap<>();
		for (Neo4jAbstract object: objects) {
			map.putAll(object.getMap());
			query = String.format("%s%s\n", query, object.getStr());
			if (objects.get(0) != object) {
				query = query + String.format("CREATE (%s)-[:BECAUSE]-> (%s)\n",  objects.get(0).getAlias(), object.getAlias());
			}
		}
		
		// show query if desired
		if (display == true) {
			System.out.println(query);
		}
		// execute query
		/*  Ex.
			CREATE (d0:Decision {proposition:$proposition0,entity:$entity0,decisionTimestamp:$decisionTimestamp0})
			CREATE (r0:Rationale {rationaleTimestamp:$rationaleTimestamp0,justification:$justification0})
			CREATE (d0)-[:BECAUSE]->(r0)
			CREATE (r1:Rationale {rationaleTimestamp:$rationaleTimestamp1,justification:$justification1})
			CREATE (d0)-[:BECAUSE]->(r1)
			CREATE (r2:Rationale {rationaleTimestamp:$rationaleTimestamp2,justification:$justification2})	
			CREATE (d0)-[:BECAUSE]->(r2)
		*/
		try {
			this.driver.executableQuery(query).withParameters(map).execute();
		} catch (Neo4jException e) {
			System.out.println(e.getMessage());
		}
		
		// close session
		this.driver.session().close();
		return objects;
	}
}
