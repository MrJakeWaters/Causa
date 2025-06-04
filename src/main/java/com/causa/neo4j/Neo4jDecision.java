package com.causa;

public class Neo4jDecision {
	private String decisionTimestamp;
	private String entity; 
	private String proposition;
	
	public Neo4jDecision(ApiDecision decision) {
		this.decisionTimestamp = decision.getDecisionTimestamp();	
		this.entity = decision.getEntity();
		this.proposition = decision.getProposition();	
	}

	// decisionTimestamp
	public String getDecisionTimestamp() {
		return this.decisionTimestamp;
	}
	public void setDecisionTimestamp(String decisionTimestamp) {
		this.decisionTimestamp = decisionTimestamp;
	}

	// entity
	public String getEntity() {
		return this.entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}

	// proposition
	public String getProposition() {
		return this.proposition;
	}
	public void setProposition(String proposition) {
		this.proposition = proposition;
	}
}
