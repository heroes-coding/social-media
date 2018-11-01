package com.cooksys.ftd.socialmedia.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.ftd.socialmedia.advice.exceptions.UserError;
import com.cooksys.ftd.socialmedia.dto.CredentialProfileDto;
import com.cooksys.ftd.socialmedia.dto.UserDto;
import com.cooksys.ftd.socialmedia.entity.User;
import com.cooksys.ftd.socialmedia.mapper.UserMapper;
import com.cooksys.ftd.socialmedia.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    
    public List<UserDto> getUsers() {
        return userMapper.entitiesToDtos(this.userRepository.findAll());
    }
    
    public UserDto getUser(String username) throws UserError {
    	User oldUser = userRepository.getUserByUsername(username);
    	if (oldUser == null) {
    		throw new UserError(String.format("No user with username: %s",username));
    	}
    	return userMapper.entityToDto(oldUser);
    }
    
	public UserDto addUser(CredentialProfileDto dto) throws UserError {
		User newUser = userMapper.CredentialProfileDtoToUser(dto);
		if (newUser.getUsername() == null) {
			throw new UserError("Missing username");
		} else if (newUser.getPassword() == null) {
			throw new UserError("Missing password");
		} else if (newUser.getProfile().getEmail() == null) {
			throw new UserError("Missing email");
		}
		User oldUser = userRepository.getUserByUsername(newUser.getUsername());
		if (oldUser == null) {
			userRepository.save(newUser);
		} else if (oldUser.getPassword().equals(newUser.getPassword())) {
			if (oldUser.isDeleted()) {
				oldUser.setDeleted(false);
				oldUser.setProfile(newUser.getProfile());
				userRepository.save(oldUser);
			} else {
				throw new UserError("User already exists");
			}
		} else {
			throw new UserError("User already exists");
		}

		return userMapper.entityToDto(newUser);
	}


	public UserDto updateUser(String username, CredentialProfileDto dto) throws UserError {
    	User oldUser = userRepository.getUserByUsername(username);
    	User updatedUser = userMapper.CredentialProfileDtoToUser(dto);
    	
    	// My equals method omits ids and joined timestamp
    	if (oldUser.equals(updatedUser)) {
    		throw new UserError(String.format("No changes to make to user: %s", username));
    	} else if (oldUser == null || oldUser.isDeleted()) {
    		throw new UserError(String.format("No user with username: %s",username));
    	} else if (!username.equals(updatedUser.getUsername())) {
    		throw new UserError("Credentials and queried username do not match");
    	} else if (!oldUser.getPassword().equals(updatedUser.getPassword())) {
    		throw new UserError("Incorrect password");
    	}
    	
    	oldUser.setProfile(updatedUser.getProfile());
    	userRepository.save(oldUser);
    	return userMapper.entityToDto(oldUser);
	}
}