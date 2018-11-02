package com.cooksys.ftd.socialmedia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.ftd.socialmedia.entity.Tweet;
import com.cooksys.ftd.socialmedia.entity.User;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {

	@Query("SELECT t FROM Tweet t WHERE t.deleted = false ORDER BY t.posted")
	List<Tweet> getAllTweets();

	@Query("SELECT t FROM Tweet t WHERE t.deleted = false AND t.author = ?1 ORDER BY t.posted DESC")
	List<Tweet> getAllTweets(User u);

	Tweet getTweetById(Integer id);

}