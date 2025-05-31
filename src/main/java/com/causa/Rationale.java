package com.causa;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class Rationale {
	private String rationaleTimestamp;
	private String justification;

	// constructor to be able to parse api put request
	// https://stackoverflow.com/questions/48448079/json-parse-error-can-not-construct-instance-of-io-starter-topic-topic
	public Rationale() {}

	// contstructor
	public Rationale(String justification) {
		this.justification = justification;
		setRationaleTimestamp();
	}
	
	public String getRationaleTimestamp() {
		return this.rationaleTimestamp;
	}

	public String getJustification() {
		return this.justification;
	}

	public void setRationaleTimestamp() {
		this.rationaleTimestamp = Instant.now().toString();
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}
}

