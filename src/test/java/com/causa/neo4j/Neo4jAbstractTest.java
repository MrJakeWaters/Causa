package com.causa;

import java.util.List;
import java.util.UUID;
import java.util.Arrays;
import java.util.ArrayList;
import java.lang.StackWalker;

// unit testing imports
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Neo4jAbstractTest {
	@Test
	public void testDecisionAbstractMap() {
		String proposition = "I ran a unit test";
		String entity = "Unit Tester";
		List<String> rationale = Arrays.asList(
			"to validate the code runs properly", 
			"improve stability of changes to the codebase"
		);
		ApiDecision decision = new ApiDecision(proposition, entity);
		decision.setRationale(rationale);
		Neo4jAbstract na = new Neo4jAbstract(decision, 0);
	}
}
