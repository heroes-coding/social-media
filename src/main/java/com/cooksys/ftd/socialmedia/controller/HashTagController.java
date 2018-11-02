package com.cooksys.ftd.socialmedia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.ftd.socialmedia.advice.exceptions.HashTagError;
import com.cooksys.ftd.socialmedia.dto.HashTagDto;
import com.cooksys.ftd.socialmedia.dto.TweetDto;
import com.cooksys.ftd.socialmedia.service.HashTagService;

@RestController
public class HashTagController {

	private HashTagService hashTagService;

	@Autowired
	public HashTagController(HashTagService hashTagService) {
		super();
		this.hashTagService = hashTagService;
	}

	@GetMapping("tags/{label}")
	public List<TweetDto> getTweetsByTag(@PathVariable("label") String label) throws HashTagError {
		return hashTagService.getTweetsByTag(label);
	}

	@GetMapping("tags")
	public List<HashTagDto> getHashTags() {
		return hashTagService.getHashTags();
	}

}
