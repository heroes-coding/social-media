package com.cooksys.ftd.socialmedia.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.ftd.socialmedia.advice.exceptions.UserError;
import com.cooksys.ftd.socialmedia.dto.CredentialDto;
import com.cooksys.ftd.socialmedia.dto.CredentialProfileDto;
import com.cooksys.ftd.socialmedia.dto.TweetDto;
import com.cooksys.ftd.socialmedia.dto.UserDto;
import com.cooksys.ftd.socialmedia.entity.User;
import com.cooksys.ftd.socialmedia.mapper.TweetMapper;
import com.cooksys.ftd.socialmedia.mapper.UserMapper;
import com.cooksys.ftd.socialmedia.repository.TweetRepository;
import com.cooksys.ftd.socialmedia.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;
	private UserMapper userMapper;
	private TweetRepository tweetRepository;
	private TweetMapper tweetMapper;

	@Autowired
	public UserService(UserRepository userRepository, UserMapper userMapper, TweetRepository tweetRepository,
			TweetMapper tweetMapper) {
		super();
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.tweetRepository = tweetRepository;
		this.tweetMapper = tweetMapper;
	}

	public List<UserDto> getUsers() {
		return userMapper.entitiesToDtos(this.userRepository.findAll());
	}

	public boolean userExists(String username) {
		User user = userRepository.getUserByUsername(username);
		return user != null;
	}

	public User getUserOrNull(String username) {
		return userRepository.getUserByUsernameAndDeletedFalse(username);
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public User getUser(CredentialDto credentials) throws UserError {
		User user = getUser(credentials.getUsername());
		checkCredentials(user, credentials);
		return user;
	}

	public User getUser(String username) throws UserError {
		User oldUser = userRepository.getUserByUsernameAndDeletedFalse(username);
		if (oldUser == null) {
			throw new UserError(String.format("No user with username: %s", username));
		}
		return oldUser;
	}

	public UserDto getUserDto(String username) throws UserError {
		return userMapper.entityToDto(getUser(username));
	}

	private void checkCredentials(User user, CredentialDto credentials) throws UserError {
		// checks passed credentials against a passed user

		if (credentials.getUsername() == null) {
			throw new UserError("Missing username");
		} else if (credentials.getPassword() == null) {
			throw new UserError("Missing password");
		} else if (!credentials.getUsername().equals(user.getUsername())
				|| !credentials.getPassword().equals(user.getPassword())) {
			throw new UserError("Invalid Credentials");
		}
	}

	public UserDto deleteUser(CredentialProfileDto dto, String username) throws UserError {
		CredentialDto credentials = dto.getCredentials();
		User user = getUser(username);
		checkCredentials(user, credentials);
		user.setDeleted(true);
		userRepository.save(user);
		return userMapper.entityToDto(user);
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
			throw new UserError(String.format("No user with username: %s", username));
		} else if (!username.equals(updatedUser.getUsername())) {
			throw new UserError("Credentials and queried username do not match");
		} else if (!oldUser.getPassword().equals(updatedUser.getPassword())) {
			throw new UserError("Incorrect password");
		}

		oldUser.setProfile(updatedUser.getProfile());
		userRepository.save(oldUser);
		return userMapper.entityToDto(oldUser);
	}

	public void followUser(CredentialProfileDto dto, String username) throws UserError {
		User toFollow = getUser(username);
		User follower = getUser(dto.getCredentials());
		follower.getFollowing().add(toFollow);
		userRepository.save(follower);
		toFollow.getFollowers().add(follower);
		userRepository.save(toFollow);
	}

	public void unfollowUser(CredentialProfileDto dto, String username) throws UserError {
		User toFollow = getUser(username);
		User follower = getUser(dto.getCredentials());
		Set<User> following = follower.getFollowing();
		Set<User> followers = toFollow.getFollowers();
		if (following.contains(toFollow)) {
			following.remove(toFollow);
			userRepository.save(follower);
		}
		if (followers.contains(follower)) {
			followers.remove(follower);
			userRepository.save(toFollow);
		}
	}

	public List<TweetDto> getFeed(String username) throws UserError {
		User user = getUser(username);
		List<TweetDto> allTweets = tweetMapper.entitiesToDtos(tweetRepository.getAllTweets(user));
		for (User u : user.getFollowing()) {
			if (!u.isDeleted()) {
				allTweets.addAll(tweetMapper.entitiesToDtos(tweetRepository.getAllTweets(u)));
			}
		}
		allTweets.sort((TweetDto a, TweetDto b) -> b.getPosted().compareTo(a.getPosted()));
		return allTweets;
	}

	public List<TweetDto> getTweets(String username) throws UserError {
		User user = getUser(username);
		return tweetMapper.entitiesToDtos(tweetRepository.getAllTweets(user));
	}

	public List<TweetDto> getMentions(String username) throws UserError {
		User user = getUser(username);
		return tweetMapper.entitiesToDtos(new ArrayList<>(user.getMentions()));
	}

	public List<UserDto> getFollowers(String username) throws UserError {
		User user = getUser(username);
		return userMapper.entitiesToDtos(new ArrayList<>(user.getFollowers()));
	}

	public List<UserDto> getFollowing(String username) throws UserError {
		User user = getUser(username);
		return userMapper.entitiesToDtos(new ArrayList<>(user.getFollowing()));
	}
}