package com.cooksys.ftd.socialmedia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.socialmedia.service.HashTagService;
import com.cooksys.ftd.socialmedia.service.UserService;

@RestController
public class ValidationController {

	private UserService userService;
	private HashTagService hashTagService;

	@Autowired
	public ValidationController(UserService userService, HashTagService hashTagService) {
		super();
		this.userService = userService;
		this.hashTagService = hashTagService;
	}

	@GetMapping("validate/username/exists/@{username}")
	public boolean userExists(@PathVariable("username") String username) {
		return this.userService.userExists(username);

	}

	@GetMapping("validate/username/available/@{username}")
	public boolean userAvailable(@PathVariable("username") String username) {
		return !this.userService.userExists(username);
	}

	@GetMapping("validate/tag/exists/{label}")
	public boolean hashTagExists(@PathVariable("label") String label) {
		return this.hashTagService.hashTagExists(label);
	}

}
