package com.cooksys.ftd.socialmedia.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.ftd.socialmedia.advice.exceptions.TweetError;
import com.cooksys.ftd.socialmedia.advice.exceptions.UserError;
import com.cooksys.ftd.socialmedia.dto.CredentialDto;
import com.cooksys.ftd.socialmedia.dto.NewTweetDto;
import com.cooksys.ftd.socialmedia.dto.TweetDto;
import com.cooksys.ftd.socialmedia.entity.HashTag;
import com.cooksys.ftd.socialmedia.entity.Tweet;
import com.cooksys.ftd.socialmedia.entity.User;
import com.cooksys.ftd.socialmedia.mapper.TweetMapper;
import com.cooksys.ftd.socialmedia.repository.TweetRepository;

@Service
public class TweetService {

	private UserService userService;
	private HashTagService hashTagService;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;

	private static final Pattern HASH_TAG_PATTERN = Pattern.compile("#(\\S+)");
	private static final Pattern MENTION_PATTERN = Pattern.compile("@(\\S+)");

	private Set<String> getAttributes(Pattern pattern, String content) {
		Matcher m = pattern.matcher(content);
		Set<String> attributes = new HashSet<>();
		while (m.find()) {
			attributes.add(m.group(1));
		}
		return attributes;
	}

	@Autowired
	public TweetService(UserService userService, HashTagService hashTagService, TweetRepository tweetRepository,
			TweetMapper tweetMapper) {
		super();
		this.userService = userService;
		this.hashTagService = hashTagService;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}

	private void addTags(Tweet tweet) {
		String content = tweet.getContent();
		Set<String> hashLabels = getAttributes(HASH_TAG_PATTERN, content);
		Set<String> mentionsLabels = getAttributes(HASH_TAG_PATTERN, content);
		Set<HashTag> hashTags = new HashSet<>();
		for (String h: hashLabels) {
			hashTags.add(hashTagService.getHashTagAndCreateIfNotExists(h));
		}
		tweet.setHashTags(hashTags);
	}

	public TweetDto addSimpleTweet(NewTweetDto newTweet) throws UserError {
		User user = userService.getUser(newTweet.getCredentials());
		Tweet tweet = tweetMapper.newTweetToEntity(newTweet);
		tweet.setAuthor(user);
		addTags(tweet);
		tweetRepository.save(tweet);
		return tweetMapper.entityToTweet(tweet);
	}

	public List<TweetDto> getTweets() {
		return tweetMapper.entitiesToDtos(this.tweetRepository.getAllTweets());
	}

	private Tweet getTweet(Integer id) throws TweetError {
		Tweet tweet = this.tweetRepository.getTweetById(id);
		if (tweet == null) {
			throw new TweetError(String.format("No tweet with the id %d", id));
		}
		return tweet;
	}

	public TweetDto deleteTweet(CredentialDto credentials, Integer id) throws UserError, TweetError {
		User user = userService.getUser(credentials);
		Tweet tweet = getTweet(id);
		if (!user.equals(tweet.getAuthor())) {
			throw new UserError(String.format("Tweet %d does not belong to %s", id, user.getUsername()));
		}
		tweet.setDeleted(true);
		tweetRepository.save(tweet);
		return tweetMapper.entityToTweet(tweet);
	}

	public TweetDto getTweetDto(Integer id) throws TweetError {
		Tweet tweet = getTweet(id);
		return tweetMapper.entityToTweet(tweet);
	}
}
