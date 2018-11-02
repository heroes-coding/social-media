package com.cooksys.ftd.socialmedia.dto;

import java.util.ArrayList;
import java.util.List;

public class ContextDto {

	private TweetDto target;
	private List<TweetDto> before = new ArrayList<>();
	private List<TweetDto> after = new ArrayList<>();

	public TweetDto getTarget() {
		return target;
	}

	public void setTarget(TweetDto target) {
		this.target = target;
	}

	public List<TweetDto> getBefore() {
		return before;
	}

	public void setBefore(List<TweetDto> before) {
		this.before = before;
	}

	public List<TweetDto> getAfter() {
		return after;
	}

	public void setAfter(List<TweetDto> after) {
		this.after = after;
	}

	private void deleteNested(TweetDto tweetDto) {
		tweetDto.setInReplyTo(null);
		tweetDto.setRepostOf(null);
	}

	public ContextDto(TweetDto target, List<TweetDto> before, List<TweetDto> after) {
		super();
		deleteNested(target);
		this.target = target;
		for (TweetDto t : before) {
			deleteNested(t);
		}
		before.sort((TweetDto a, TweetDto b) -> a.getPosted().compareTo(b.getPosted()));
		this.before = before;
		for (TweetDto t : after) {
			deleteNested(t);
		}
		after.sort((TweetDto a, TweetDto b) -> a.getPosted().compareTo(b.getPosted()));
		this.after = after;
	}

}
