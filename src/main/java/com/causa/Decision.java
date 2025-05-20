package com.causa;

import java.util.Arrays;
import java.time.Instant;

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
}
