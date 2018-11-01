package com.cooksys.ftd.socialmedia.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.socialmedia.advice.exceptions.TweetError;
import com.cooksys.ftd.socialmedia.advice.exceptions.UserError;
import com.cooksys.ftd.socialmedia.dto.CredentialDto;
import com.cooksys.ftd.socialmedia.dto.NewTweetDto;
import com.cooksys.ftd.socialmedia.dto.TweetDto;
import com.cooksys.ftd.socialmedia.dto.UserDto;
import com.cooksys.ftd.socialmedia.entity.Tweet;
import com.cooksys.ftd.socialmedia.entity.User;
import com.cooksys.ftd.socialmedia.service.HashTagService;
import com.cooksys.ftd.socialmedia.service.TweetService;
import com.cooksys.ftd.socialmedia.service.UserService;

@RestController
public class TweetController {

	private TweetService tweetService;

	public TweetController(TweetService tweetService) {
		super();
		this.tweetService = tweetService;
	}

	
	@PostMapping("tweets")
	public TweetDto newSimpleTweet(@RequestBody NewTweetDto newTweet) throws UserError {
		return tweetService.addSimpleTweet(newTweet);
	}

	@GetMapping("tweets")
	public List<TweetDto> getTweets() {
		return tweetService.getTweets();
	}
	
	@GetMapping("tweets/{id}")
	public TweetDto getTweet(@PathVariable("id") Integer id) throws UserError, TweetError {
		return this.tweetService.getTweetDto(id);
	}

	@DeleteMapping("tweets/{id}")
	public TweetDto deleteTweet(@RequestBody CredentialDto credentials, @PathVariable("id") Integer id) throws UserError, TweetError {
		return this.tweetService.deleteTweet(credentials, id);
	}
}
