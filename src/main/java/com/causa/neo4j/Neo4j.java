package com.causa;

// external
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import org.neo4j.driver.*;
import java.util.ArrayList;
import org.neo4j.driver.Driver; 
import java.lang.reflect.Method;
import org.neo4j.driver.AuthTokens; 
import org.neo4j.driver.exceptions.*;
import org.neo4j.driver.GraphDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

public class Neo4j<Generic> {
	// static attributes
	private final String dbUri = "neo4j+s://cf5a6658.databases.neo4j.io";
	private final String dbUser = "neo4j";
	private final String dbPassword = "jGiDtd1DvPnCL47dSE3LHIymIcLm0JITdNTT6Fun8qU";	
	private final Driver driver;
	
	// constructor
	public Neo4j() {
		this.driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
		this.driver.verifyConnectivity();
		System.out.println("Neo4j Database Connection Working\n");
	}
	
	public void getDecisions() {
		String query = "match (decision:Decision) optional match (decion:Decision)-[relationship:BECAUSE]->(rationale:Rationale) return decision, rationale, relationship";
		var results = this.driver.executableQuery(query).execute();
		for (var record: results.records()) {
			var decision = record.get("decision").asNode();
			var rationale = record.get("rationale").asNode();
			var relationship = record.get("relationship");

			// display results
			System.out.println(decision.asMap());
			System.out.println(rationale.asMap());
			System.out.println(relationship.asRelationship().getClass());
			System.out.println(relationship.asRelationship().startNodeId());
			System.out.println(relationship.asRelationship().startNodeElementId());
		}
	}

	// load api decision with rationale
	public String saveObject(ApiDecision apiDecision, boolean display) {
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
		Map<String, Object> map = new HashMap<>();
		for (Neo4jAbstract object: objects) {
			map.putAll(object.getMap());
			query = String.format("%s%s\n", query, object.getStr());
			
			// generate relationship statement
			if (objects.get(0) != object) {
				// this adds an extra character to the end of the string
				query = query + String.format("CREATE (%s)-[:BECAUSE]-> (%s)\n",  objects.get(0).getAlias(), object.getAlias());
			}
		}
		query = query.trim();
		
		// show query if desired
		if (display == true) {
			System.out.println(query);
		}
		// execute query
		try {
			this.driver.executableQuery(query).withParameters(map).execute();
		} catch (Neo4jException e) {
			System.out.println(e.getMessage());
		}
		
		// close session
		this.driver.session().close();
		return query;
	}
}
