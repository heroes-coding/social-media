package com.cooksys.ftd.socialmedia.advice.exceptions;

public class HashTagError extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4816761787629170347L;
	private String message;
	
	public HashTagError(String message) {
		this.message = String.format("Hash tag error: %s", message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}