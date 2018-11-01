package com.cooksys.ftd.socialmedia.dto;

import java.sql.Timestamp;

public class TweetDto {

	private int id;
	private UserDto author;
	private Timestamp posted;
	private String content;
	private TweetDto inReplyTo;
	private TweetDto repostOf;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public UserDto getAuthor() {
		return author;
	}
	public void setAuthor(UserDto author) {
		this.author = author;
	}
	public Timestamp getPosted() {
		return posted;
	}
	public void setPosted(Timestamp posted) {
		this.posted = posted;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public TweetDto getInReplyTo() {
		return inReplyTo;
	}
	public void setInReplyTo(TweetDto inReplyTo) {
		this.inReplyTo = inReplyTo;
	}
	public TweetDto getRepostOf() {
		return repostOf;
	}
	public void setRepostOf(TweetDto repostOf) {
		this.repostOf = repostOf;
	}
	
	
	
}
