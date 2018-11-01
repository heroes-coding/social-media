package com.cooksys.ftd.socialmedia.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.cooksys.ftd.socialmedia.dto.NewTweetDto;
import com.cooksys.ftd.socialmedia.dto.TweetDto;
import com.cooksys.ftd.socialmedia.entity.Tweet;

@Mapper(componentModel = "spring")
public interface TweetMapper {

	Tweet newTweetToEntity(NewTweetDto dto);

	TweetDto entityToTweet(Tweet entity);

	List<TweetDto> entitiesToDtos(List<Tweet> entities);

}