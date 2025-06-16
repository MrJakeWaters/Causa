package com.causa;

// external
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

public class Decision {
	private String decisionTimestamp; 
	private String proposition;
	private String entity;

	public Decision() {}

	public Decision(String proposition, String entity) {
		this.proposition = proposition;
		this.entity = entity;
		setDecisionTimestamp();
	}

	// decisionTimestamp
	public String getDecisionTimestamp() {
		return this.decisionTimestamp;
	}
	public void setDecisionTimestamp() {
		this.decisionTimestamp = Instant.now().toString().replace("T", " ").replace("Z","");
	}

	// proposition
	public String getProposition() {
		return this.proposition;
	}
	public void setProposition(String proposition) {
		this.proposition = proposition;
	}

	// entity
	public String getEntity() {
		return this.entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
}
