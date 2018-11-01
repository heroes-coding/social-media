package com.cooksys.ftd.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.ftd.socialmedia.entity.Tweet;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

	@Query("SELECT t FROM Tweet t WHERE t.deleted = false ORDER BY t.posted")
	List<Tweet> getAllTweets();

	Tweet getTweetById(Integer id);
	/*
	 * User getUserByUsernameAndDeletedFalse(String username);
	 * 
	 * User getUserByUsername(String username);
	 * 
	 * @Query("SELECT u FROM User u WHERE u.deleted = false ORDER BY u.username")
	 * List<User> findAll();
	 */
}