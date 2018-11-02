package com.cooksys.ftd.socialmedia.dto;

import java.sql.Timestamp;

public class HashTagDto {

	private String label;
	private Timestamp firstUsed;
	private Timestamp lastUsed;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label.toLowerCase();
	}

	public Timestamp getFirstUsed() {
		return firstUsed;
	}

	public void setFirstUsed(Timestamp firstUsed) {
		this.firstUsed = firstUsed;
	}

	public Timestamp getLastUsed() {
		return lastUsed;
	}

	public void setLastUsed(Timestamp lastUsed) {
		this.lastUsed = lastUsed;
	}

}
