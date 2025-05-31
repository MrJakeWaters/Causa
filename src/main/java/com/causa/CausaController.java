package com.causa;

import org.neo4j.driver.Driver; 
import org.neo4j.driver.AuthTokens; 
import org.neo4j.driver.GraphDatabase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class CausaController {
	
	private final String dbUri = "neo4j+s://cf5a6658.databases.neo4j.io";
	private final String dbUser = "neo4j";
	private final String dbPassword = "2USA0UKeiuIhJvLzyXS6I9i4rJY8zjKtq6IfWAS40fg";	
	private final Driver driver = GraphDatabase.driver(dbUri, AuthTokens.basic(dbUser, dbPassword));
	
	@GetMapping("/hello")
	public String whatUp() {
		return "What Up from Causa API\n";
	}
	
	@GetMapping("/connection")
	public String verifyDatabaseConnection() {
		driver.verifyConnectivity();
		return "Database Connection Working\n";
	}
	
	@GetMapping("/test_decision")
	public Decision createTestDecision() {
		Decision decision = new Decision("I sent an API request");
		//decision.addRationale("It is the best way to test my API");
		Neo4jLoader loader = new Neo4jLoader();
		loader.saveObject(decision);
		return decision;
	}

	@PostMapping("/decision")
	public Decision createDecision(@RequestBody Decision decision) {
		// insert decision object into neo4j database
		return decision;
	}
}
