package com.causa;

import java.util.Arrays;
import java.time.Instant;
import org.neo4j.driver.Values;
import org.neo4j.driver.Driver; 
import org.neo4j.driver.AuthTokens; 
import org.neo4j.driver.GraphDatabase;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;


public class Decision {
	private Instant lastModifiedTimestamp;
	private String proposition;
	private Rationale[] rationale;

	// constructor to be able to parse api put request
	// https://stackoverflow.com/questions/48448079/json-parse-error-can-not-construct-instance-of-io-starter-topic-topic
	public Decision() {}

	// contstructor
	public Decision(String proposition) {
		this.proposition = proposition;
		this.rationale = new Rationale[1];
		setLastModifiedTimestamp();
	}
	
	// loads decision object to Neo4j database
	public void neo4jLoader(Driver driver) {
		try {
			ObjectMapper decision = new ObjectMapper().registerModule(new JavaTimeModule());
			decision.writeValueAsString(this);
			String statement = String.format("CREATE (n:%s {data: $data})", this.getClass().getSimpleName());
			driver.session().run(statement, Values.parameters("data", decision));
			System.out.println("Decision Successfully Loaded to Neo4j Database");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void addRationale(String justification) {
		if (this.rationale.length == 0) {
			this.rationale[0] = new Rationale(justification);	
		} else {
			Rationale[] newRationale = Arrays.copyOf(this.rationale, this.rationale.length);
			newRationale[newRationale.length-1] = new Rationale(justification);
			this.rationale = newRationale;
			System.out.println("New Rationale Added to your decision");
		}
		System.out.println(this.rationale.length);
	}

	public Instant getLastModifiedTimestamp() {
		return this.lastModifiedTimestamp;
	}

	public String getProposition() {
		return this.proposition;
	}

	public Rationale[] getRationale() {
		return this.rationale;
	}

	public void setLastModifiedTimestamp() {
		this.lastModifiedTimestamp = Instant.now();
	}

	public void setProposition(String proposition) {
		this.proposition = proposition;
	}

	public void setRationale(Rationale[] rationale) {
		this.rationale = rationale;
	}

}
