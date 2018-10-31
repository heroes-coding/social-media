package com.cooksys.ftd.socialmedia.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


	public UserDto addUser(CredentialProfileDto dto) {
		User newUser = userMapper.CredentialProfileDtoToUser(dto);
		userRepository.save(newUser);
		return userMapper.entityToDto(newUser);
	}
}