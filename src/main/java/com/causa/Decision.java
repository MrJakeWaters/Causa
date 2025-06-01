package com.causa;

import java.util.Arrays;
import java.time.Instant;
import org.neo4j.driver.Values;
import org.neo4j.driver.Driver; 
import org.neo4j.driver.AuthTokens; 
import org.neo4j.driver.GraphDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


public class Decision {
	private String decisionTimestamp;
	private String entity = "Server"; 
	private String proposition;

	// constructor to be able to parse api put request
	// https://stackoverflow.com/questions/48448079/json-parse-error-can-not-construct-instance-of-io-starter-topic-topic
	public Decision() {}

	// constructor
	public Decision(String proposition) {
		this.proposition = proposition;
		setDecisionTimestamp();
	}
	
	// getter and setter of decisionTimestamp
	public String getDecisionTimestamp() {
		return this.decisionTimestamp;
	}
	
	public void setDecisionTimestamp() {
		this.decisionTimestamp = Instant.now().toString();
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
