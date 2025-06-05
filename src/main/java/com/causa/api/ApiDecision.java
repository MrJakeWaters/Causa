package com.causa;

// external
import java.util.ArrayList;
import java.util.List;
import java.time.Instant;

public class ApiDecision {
	private String decisionTimestamp; 
	private String proposition;
	private String entity;
	private List<ApiRationale> rationale = new ArrayList<>();

	public ApiDecision() {}

	public ApiDecision(String proposition, String entity, List<String> justifications) {
		this.proposition = proposition;
		this.entity = entity;
		for (String justification: justifications) {
			ApiRationale rationale = new ApiRationale(justification);
			rationale.setRationaleTimestamp();
			this.rationale.add(rationale);
		}
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

	// rationale
	public List<ApiRationale> getRationale() {
		return this.rationale;
	}
	public void setRationale(List<ApiRationale> rationale) {
		this.rationale = rationale;
	}
}
