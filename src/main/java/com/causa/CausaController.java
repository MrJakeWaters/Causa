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
	
	private final Neo4jLoader loader = new Neo4jLoader();
	
	@GetMapping("/hello")
	public String whatUp() {
		return "What Up from Causa API\n";
	}
	
	@GetMapping("/database_insert_test")
	public Neo4jAbstract[] createTestDecision() {
		// create decision
		Decision decision = new Decision("Today on 6/1/2025 I sent an API request");
		decision.setEntity("Jake");

		// create rationale
		Rationale rationale = new Rationale("I am testing out the code for the new project");
		rationale.decisionAttacher(decision);

		// save to Neo4j and return abstracts of saved objects
		Neo4jAbstract[] abstracts = new Neo4jAbstract[2];
		abstracts = loader.saveObject(decision, rationale);	
		return abstracts;
	}

	@PostMapping("/create_decision")
	public ApiDecision createDecision(@RequestBody ApiDecision decision) {
		// set timestamps
		decision.setDecisionTimestamp();
		for (ApiRationale r: decision.getRationale()) {
			r.setRationaleTimestamp();
		} 
		return decision;
	}
}
