package com.cooksys.ftd.socialmedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.socialmedia.advice.exceptions.UserError;
import com.cooksys.ftd.socialmedia.dto.CredentialProfileDto;
import com.cooksys.ftd.socialmedia.dto.UserDto;
import com.cooksys.ftd.socialmedia.service.UserService;

@RestController
public class UserController {

	private UserService userService;

	@Autowired
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PatchMapping("users/{username}")
	public UserDto updateUser(@RequestBody CredentialProfileDto dto, @PathVariable("username") String username) throws UserError {
		return this.userService.updateUser(username, dto);
	}
	
	@GetMapping("users/{username}")
	public UserDto getUser(@PathVariable("username") String username) throws UserError {
		return this.userService.getUser(username);
	}

	@GetMapping("users")
	public List<UserDto> getUsers() {
		return userService.getUsers();
	}

	@PostMapping("users")
	public UserDto addUser(@RequestBody CredentialProfileDto dto) throws UserError {
		return this.userService.addUser(dto);
	}

}
