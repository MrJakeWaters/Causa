package com.causa;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Rationale {
	private String rationaleTimestamp;
	private String justification;
	private String entity;
	private String proposition;

	// constructor to be able to parse api put request
	// https://stackoverflow.com/questions/48448079/json-parse-error-can-not-construct-instance-of-io-starter-topic-topic
	public Rationale() {}

	// contstructor
	public Rationale(String justification) {
		this.justification = justification;
		setRationaleTimestamp();
	}

	public void decisionAttacher(Decision decision) {
		this.entity = decision.getEntity();
		this.proposition = decision.getProposition();
	}

	// getter and setter of rationaleTimestamp
	public String getRationaleTimestamp() {
		return this.rationaleTimestamp;
	}

	public void setRationaleTimestamp() {
		this.rationaleTimestamp = Instant.now().toString();
	}

	// getter and setter of justification
	public String getJustification() {
		return this.justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	// getter and setter of entity
	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	// getter and setter of proposition
	public String getProposition() {
		return this.proposition;
	}

	public void setProposition(String proposition) {
		this.proposition = proposition;
	}
}

