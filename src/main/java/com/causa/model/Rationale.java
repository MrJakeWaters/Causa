package com.causa;

import java.time.Instant;

public class Rationale {
	private String justification;
	private String rationaleTimestamp;
	
	public Rationale() {}
	public Rationale(String justification) {
		this.justification = justification;
		setRationaleTimestamp();
	}

	// justification
	public String getJustification() {
		return this.justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}

	// rationaleTimestamp
	public String getRationaleTimestamp() {
		return this.rationaleTimestamp;
	}
	public void setRationaleTimestamp() {
		this.rationaleTimestamp = Instant.now().toString().replace("T", " ").replace("Z","");
	}
}
