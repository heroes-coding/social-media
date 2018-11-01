package com.cooksys.ftd.socialmedia.advice.exceptions;

public class TweetError extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1945514176365782519L;
	private String message;
	
	public TweetError(String message) {
		this.message = String.format("Tweet error: %s", message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}