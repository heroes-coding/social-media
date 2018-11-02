package com.cooksys.ftd.socialmedia.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cooksys.ftd.socialmedia.entity.HashTag;
import com.cooksys.ftd.socialmedia.entity.Tweet;

public interface HashTagRepository extends JpaRepository<HashTag, Integer> {

	HashTag getHashTagByLabel(String lowerCase);

	@Query("SELECT h FROM HashTag h ORDER BY h.label")
	List<HashTag> findAll();

	@Query("SELECT h.tweets FROM HashTag h WHERE h.label = ?1")
	Set<Tweet> getTweetsByLabel(String label);
}