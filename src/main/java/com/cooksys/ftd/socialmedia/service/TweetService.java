package com.cooksys.ftd.socialmedia.service;

import java.util.ArrayList;
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
import com.cooksys.ftd.socialmedia.dto.HashTagDto;
import com.cooksys.ftd.socialmedia.dto.NewTweetDto;
import com.cooksys.ftd.socialmedia.dto.TweetDto;
import com.cooksys.ftd.socialmedia.dto.UserDto;
import com.cooksys.ftd.socialmedia.entity.HashTag;
import com.cooksys.ftd.socialmedia.entity.Tweet;
import com.cooksys.ftd.socialmedia.entity.User;
import com.cooksys.ftd.socialmedia.mapper.HashTagMapper;
import com.cooksys.ftd.socialmedia.mapper.TweetMapper;
import com.cooksys.ftd.socialmedia.mapper.UserMapper;
import com.cooksys.ftd.socialmedia.repository.TweetRepository;

@Service
public class TweetService {

	private UserService userService;
	private HashTagService hashTagService;
	private HashTagMapper hashTagMapper;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;
	private UserMapper userMapper;

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
	public TweetService(UserService userService, HashTagService hashTagService, HashTagMapper hashTagMapper,
			TweetRepository tweetRepository, TweetMapper tweetMapper, UserMapper userMapper) {
		super();
		this.userService = userService;
		this.hashTagService = hashTagService;
		this.hashTagMapper = hashTagMapper;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
		this.userMapper = userMapper;
	}

	private void addTagsAndSave(Tweet tweet) {
		String content = tweet.getContent();
		Set<String> hashLabels = getAttributes(HASH_TAG_PATTERN, content);
		Set<String> mentionsLabels = getAttributes(MENTION_PATTERN, content);
		Set<HashTag> hashTags = new HashSet<>();
		for (String h : hashLabels) {
			hashTags.add(hashTagService.getHashTagAndCreateIfNotExists(h));
		}
		tweet.setHashTags(hashTags);
		for (String m : mentionsLabels) {

			User user = userService.getUserOrNull(m);
			if (user == null) {
				continue;
			}
			tweet.getMentioned().add(user);
			user.getMentions().add(tweet);
			userService.saveUser(user);
		}
		tweetRepository.save(tweet);
	}

	public TweetDto addSimpleTweet(NewTweetDto newTweet) throws UserError {
		User user = userService.getUser(newTweet.getCredentials());
		Tweet tweet = tweetMapper.newTweetToEntity(newTweet);
		tweet.setAuthor(user);
		addTagsAndSave(tweet);
		return tweetMapper.entityToTweet(tweet);
	}

	public TweetDto replyToTweet(NewTweetDto newTweet, Integer id) throws UserError, TweetError {
		User user = userService.getUser(newTweet.getCredentials());
		Tweet tweet = tweetMapper.newTweetToEntity(newTweet);
		Tweet tweetRepliedTo = getTweet(id);
		tweet.setAuthor(user);
		tweet.setInReplyTo(tweetRepliedTo);
		addTagsAndSave(tweet);
		return tweetMapper.entityToTweet(tweet);
	}

	public TweetDto repostTweet(CredentialDto credentials, Integer id) throws UserError, TweetError {
		User user = userService.getUser(credentials);
		NewTweetDto newTweet = new NewTweetDto();
		newTweet.setCredentials(credentials);
		Tweet tweet = tweetMapper.newTweetToEntity(newTweet);
		Tweet tweetReposted = getTweet(id);
		tweet.setAuthor(user);
		tweet.setRepostOf(tweetReposted);
		tweetRepository.save(tweet);
		return tweetMapper.entityToTweet(tweet);
	}



	public void likeTweet(CredentialDto credentials, Integer id) throws UserError, TweetError {
		User user = userService.getUser(credentials);
		Tweet tweet = getTweet(id);
		tweet.getLikers().add(user);
		user.getLikes().add(tweet);
		tweetRepository.save(tweet);
		userService.saveUser(user);
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

	public Tweet getTweet(Integer id) throws TweetError {
		Tweet tweet = this.tweetRepository.getTweetById(id);
		if (tweet == null) {
			throw new TweetError(String.format("No tweet with the id %d", id));
		}
		if (tweet.isDeleted()) {
			throw new TweetError(String.format("Tweet %d was deleted", id));
		}
		return tweet;
	}

	public List<TweetDto> getTweets() {
		return tweetMapper.entitiesToDtos(this.tweetRepository.getAllTweets());
	}
	
	public List<UserDto> getLikers(Integer id) throws TweetError {
		Tweet tweet = getTweet(id);
		return userMapper.entitiesToDtos(new ArrayList<>(tweet.getLikers()));
	}

	public List<HashTagDto> getTweetHashTags(Integer id) throws TweetError {
		Tweet tweet = getTweet(id);
		return hashTagMapper.entitiesToDtos(new ArrayList<>(tweet.getHashTags()));
	}

	public List<TweetDto> getTweetReplies(Integer id) throws TweetError {
		Tweet tweet = getTweet(id);
		return tweetMapper.entitiesToDtos(new ArrayList<>(tweet.getReplies()));
	}

	public List<TweetDto> getTweetReposts(Integer id) throws TweetError {
		Tweet tweet = getTweet(id);
		return tweetMapper.entitiesToDtos(new ArrayList<>(tweet.getReposts()));
	}

	public List<UserDto> getTweetMentions(Integer id) throws TweetError {
		Tweet tweet = getTweet(id);
		return userMapper.entitiesToDtos(new ArrayList<>(tweet.getMentioned()));
	}

}
