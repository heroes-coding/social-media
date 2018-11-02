package com.cooksys.ftd.socialmedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.socialmedia.advice.exceptions.TweetError;
import com.cooksys.ftd.socialmedia.advice.exceptions.UserError;
import com.cooksys.ftd.socialmedia.dto.CredentialDto;
import com.cooksys.ftd.socialmedia.dto.HashTagDto;
import com.cooksys.ftd.socialmedia.dto.NewTweetDto;
import com.cooksys.ftd.socialmedia.dto.TweetDto;
import com.cooksys.ftd.socialmedia.dto.UserDto;
import com.cooksys.ftd.socialmedia.service.TweetService;

@RestController
public class TweetController {

	private TweetService tweetService;

	@Autowired
	public TweetController(TweetService tweetService) {
		super();
		this.tweetService = tweetService;
	}

	@PostMapping("tweets")
	public TweetDto newSimpleTweet(@RequestBody NewTweetDto newTweet) throws UserError {
		return tweetService.addSimpleTweet(newTweet);
	}

	@PostMapping("tweets/{id}/reply")
	public TweetDto replyToTweet(@RequestBody NewTweetDto newTweet, @PathVariable("id") Integer id)
			throws UserError, TweetError {
		return this.tweetService.replyToTweet(newTweet, id);
	}

	@PostMapping("tweets/{id}/repost")
	public TweetDto replyToTweet(@RequestBody CredentialDto credentials, @PathVariable("id") Integer id)
			throws UserError, TweetError {
		return this.tweetService.repostTweet(credentials, id);
	}

	@GetMapping("tweets")
	public List<TweetDto> getTweets() {
		return tweetService.getTweets();
	}

	@GetMapping("tweets/{id}/tags")
	public List<HashTagDto> getTags(@PathVariable("id") Integer id) throws TweetError {
		return this.tweetService.getTweetHashTags(id);
	}

	@GetMapping("tweets/{id}/likes")
	public List<UserDto> getLikers(@PathVariable("id") Integer id) throws TweetError {

		return this.tweetService.getLikers(id);
	}

	@GetMapping("tweets/{id}/replies")
	public List<TweetDto> getReplies(@PathVariable("id") Integer id) throws TweetError {
		return this.tweetService.getTweetReplies(id);
	}

	@GetMapping("tweets/{id}/reposts")
	public List<TweetDto> getReposts(@PathVariable("id") Integer id) throws TweetError {
		return this.tweetService.getTweetReposts(id);
	}

	@GetMapping("tweets/{id}/mentions")
	public List<UserDto> getMentions(@PathVariable("id") Integer id) throws TweetError {
		return this.tweetService.getTweetMentions(id);
	}

	@GetMapping("tweets/{id}")
	public TweetDto getTweet(@PathVariable("id") Integer id) throws TweetError {
		return this.tweetService.getTweetDto(id);
	}

	@PostMapping("tweets/{id}/like")
	public void likeTweet(@RequestBody CredentialDto credentials, @PathVariable("id") Integer id)
			throws UserError, TweetError {
		this.tweetService.likeTweet(credentials, id);
	}
}
