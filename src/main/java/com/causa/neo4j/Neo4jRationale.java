package com.causa;

public class Neo4jRationale {
	private String rationaleTimestamp;
	private String justification;
	
	public Neo4jRationale(String rationaleTimestamp, String justification) {
		this.rationaleTimestamp = rationaleTimestamp;
		this.justification = justification;
	}

	// rationaleTimestamp
	public String getRationaleTimestamp() {
		return this.rationaleTimestamp;
	}
	public void setRationaleTimestamp(String rationaleTimestamp) {
		this.rationaleTimestamp = rationaleTimestamp;
	}

	// justification
	public String getJustification() {
		return this.justification;
	}
	public void setJustification(String justification) {
		this.justification = justification;
	}
}
