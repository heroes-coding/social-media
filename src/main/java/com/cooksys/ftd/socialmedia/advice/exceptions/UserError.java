package com.cooksys.ftd.socialmedia.advice.exceptions;

public class UserError extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6202228025233570390L;
	private String message;

	public UserError(String message) {
		this.message = String.format("User error: %s", message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}