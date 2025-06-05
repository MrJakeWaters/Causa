package com.causa;

import java.util.List;
import java.util.UUID;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.StackWalker;

// unit testing imports
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Neo4jLoaderTest {
	@Test
	public void testSaveObjectQueryString() {
		Neo4jLoader loader = new Neo4jLoader();
		String methodName = StackWalker.getInstance()
			.walk(frames -> frames.findFirst()
			.map(StackWalker.StackFrame::getMethodName)
			.orElse("unknown"));
		String proposition = String.format("Executed Unit Test %s", UUID.randomUUID().toString());
		String entity = String.format("Unit Test: %s", methodName);
		List<String> rationale = Arrays.asList(
			"to validate the code runs properly", 
			"improve stability of changes to the codebase"
		);
		String result = "CREATE (d0:Decision {proposition:$proposition0,entity:$entity0,decisionTimestamp:$decisionTimestamp0})\nCREATE (r0:Rationale {rationaleTimestamp:$rationaleTimestamp0,justification:$justification0})\nCREATE (d0)-[:BECAUSE]-> (r0)\nCREATE (r1:Rationale {rationaleTimestamp:$rationaleTimestamp1,justification:$justification1})\nCREATE (d0)-[:BECAUSE]-> (r1)";

		// create decision object and attempt to save to DB
		// all units tests will be saved to DB if successfull
		ApiDecision decision = new ApiDecision(proposition, entity, rationale);
		String query = loader.saveObject(decision, false);
		assertEquals(result, query);
	}
	public void testSaveObjectLoadedToDatabase() {
		Neo4jLoader loader = new Neo4jLoader();
		String methodName = StackWalker.getInstance()
			.walk(frames -> frames.findFirst()
			.map(StackWalker.StackFrame::getMethodName)
			.orElse("unknown"));
		String proposition = String.format("Executed Unit Test %s", UUID.randomUUID().toString());
		String entity = String.format("Unit Test: %s", methodName);
		List<String> rationale = Arrays.asList(
			"ensuring data is being loaded to neo4j"
		);
		ApiDecision decision = new ApiDecision(proposition, entity, rationale);
		String query = loader.saveObject(decision, false);
	}
}
