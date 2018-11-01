package com.cooksys.ftd.socialmedia.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cooksys.ftd.socialmedia.advice.exceptions.UserError;

@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(UserError.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseMessage userNotFound(UserError e) {
		System.out.println(e.getMessage());
		return new ResponseMessage(e.getMessage());
	}

}
