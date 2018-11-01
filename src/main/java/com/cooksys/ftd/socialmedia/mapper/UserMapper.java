package com.cooksys.ftd.socialmedia.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.cooksys.ftd.socialmedia.dto.CredentialProfileDto;
import com.cooksys.ftd.socialmedia.dto.UserDto;
import com.cooksys.ftd.socialmedia.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

	UserDto entityToDto(User entity);

	User dtoToEntity(UserDto dto);

	List<UserDto> entitiesToDtos(List<User> entities);

	List<User> dtosToEntities(List<User> dtos);

	@Mappings({ 
		@Mapping(target = "username", source = "credentials.username"),
		@Mapping(target = "password", source = "credentials.password")
		})
	User CredentialProfileDtoToUser(CredentialProfileDto dto);
	
}