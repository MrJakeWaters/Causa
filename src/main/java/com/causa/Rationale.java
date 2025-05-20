package com.causa;

import java.time.Instant;

public class Rationale {
	private Instant lastModifiedTimestamp;
	private String justification;

	// constructor to be able to parse api put request
	// https://stackoverflow.com/questions/48448079/json-parse-error-can-not-construct-instance-of-io-starter-topic-topic
	public Rationale() {}

	// contstructor
	public Rationale(String justification) {
		this.justification = justification;
		setLastModifiedTimestamp();
	}
	
	public Instant getLastModifiedTimestamp() {
		return this.lastModifiedTimestamp;
	}

	public String getJustification() {
		return this.justification;
	}

	public void setLastModifiedTimestamp() {
		this.lastModifiedTimestamp = Instant.now();
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}
}

