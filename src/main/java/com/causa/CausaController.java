package com.causa;

// external
import java.util.List;
import java.util.ArrayList;
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
	
	@PostMapping("/create_decision")
	public List<Neo4jAbstract> createDecision(@RequestBody ApiDecision apiDecision) {
		apiDecision.setDecisionTimestamp();
		for (ApiRationale r: apiDecision.getRationale()) {
			r.setRationaleTimestamp();
		} 
		List<Neo4jAbstract> objects = loader.saveObject(apiDecision, true);
		return objects;
	}
}
