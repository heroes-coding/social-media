package com.cooksys.ftd.socialmedia.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cooksys.ftd.socialmedia.advice.exceptions.HashTagError;
import com.cooksys.ftd.socialmedia.dto.HashTagDto;
import com.cooksys.ftd.socialmedia.dto.TweetDto;
import com.cooksys.ftd.socialmedia.entity.HashTag;
import com.cooksys.ftd.socialmedia.entity.Tweet;
import com.cooksys.ftd.socialmedia.mapper.HashTagMapper;
import com.cooksys.ftd.socialmedia.mapper.TweetMapper;
import com.cooksys.ftd.socialmedia.repository.HashTagRepository;

@Service
public class HashTagService {
	private HashTagRepository hashTagRepository;
    private HashTagMapper hashTagMapper;
    private TweetMapper tweetMapper;

    @Autowired
    public HashTagService(HashTagRepository hashTagRepository, HashTagMapper hashTagMapper, TweetMapper tweetMapper) {
        this.hashTagRepository = hashTagRepository;
        this.hashTagMapper = hashTagMapper;
        this.tweetMapper = tweetMapper;
    }

    public List<HashTagDto> getHashTags() {
        return hashTagMapper.entitiesToDtos(this.hashTagRepository.findAll());
    }
    
    public List<TweetDto> getTweetsByTag(String label) throws HashTagError {
    	if (!hashTagExists(label)) {
    		throw new HashTagError(String.format("No hashtag #%s exists", label));
    	}
    	
    	Set<Tweet> tweets = this.hashTagRepository.getTweetsByLabel(label);
    	List<Tweet> activeTweets = new ArrayList<>();
    	for (Tweet t: tweets) {
    		if (!t.isDeleted()) {
    			activeTweets.add(t);
    		}
    	}
    	activeTweets.sort((Tweet a, Tweet b) -> b.getPosted().compareTo(a.getPosted()));
    	return tweetMapper.entitiesToDtos(activeTweets);
    }
    
    public HashTag getHashTagAndCreateIfNotExists(String label) {
    	label = label.toLowerCase();
    	HashTag tag = hashTagRepository.getHashTagByLabel(label);
    	if (tag == null) {
    		tag = new HashTag(label);
    	} else {
    		tag.setLastUsed(new Timestamp(System.currentTimeMillis()));
    	}
    	// new tags need to be saved to populate them.  Similarly, old tags must be saved for the last updated field
    	tag = hashTagRepository.save(tag);
    	return tag;
    }
    
    public boolean hashTagExists(String label) {
    	HashTag tag = hashTagRepository.getHashTagByLabel(label.toLowerCase());
    	return tag != null;
    }
}
