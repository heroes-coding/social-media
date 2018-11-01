package com.cooksys.ftd.socialmedia.advice.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserError extends Exception {
	
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