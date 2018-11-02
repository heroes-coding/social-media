package com.cooksys.ftd.socialmedia.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.ftd.socialmedia.dto.HashTagDto;
import com.cooksys.ftd.socialmedia.entity.HashTag;

@Mapper(componentModel = "spring")
public interface HashTagMapper {

	HashTagDto entityToDto(HashTag entity);

	HashTag dtoToEntity(HashTagDto dto);

	List<HashTagDto> entitiesToDtos(List<HashTag> entities);

	List<HashTag> dtosToEntities(List<HashTagDto> dtos);

}