package com.cooksys.ftd.socialmedia.entity;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Tweet {
	public enum Type {
		SIMPLE, REPOST, REPLY, BAD
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JoinColumn
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User author;

	@Column
	@CreationTimestamp
	private Timestamp posted;

	@Column
	private String content;

	@JoinColumn
	@ManyToOne(cascade = CascadeType.ALL)
	private Tweet inReplyTo;

	@JoinColumn
	@ManyToOne(cascade = CascadeType.ALL)
	private Tweet repostOf;

	@Column
	private boolean deleted;

	@JoinColumn
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Set<HashTag> hashTags;
	
	@JoinColumn
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "likes")
	private Set<User> likers;
	
	@JoinColumn
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "mentions")
	private Set<User> mentioned;
	
	@JoinColumn
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "inReplyTo")
	private Set<Tweet> replies;
	
	@JoinColumn
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "repostOf")
	private Set<Tweet> reposts;
	
	public Set<HashTag> getHashTags() {
		return hashTags;
	}

	public void setHashTags(Set<HashTag> hashTags) {
		this.hashTags = hashTags;
	}
	
	public Set<User> getLikers() {
		return likers;
	}

	public void setLikers(Set<User> likers) {
		this.likers = likers;
	}

	public Set<User> getMentioned() {
		return mentioned;
	}

	public void setMentioned(Set<User> mentioned) {
		this.mentioned = mentioned;
	}

	public Set<Tweet> getReplies() {
		return replies;
	}

	public void setReplies(Set<Tweet> replies) {
		this.replies = replies;
	}

	public Set<Tweet> getReposts() {
		return reposts;
	}

	public void setReposts(Set<Tweet> reposts) {
		this.reposts = reposts;
	}

	public Type getType() {
		Type tweetType = Type.BAD;
		if (content != null && inReplyTo == null && this.repostOf == null) {
			tweetType = Type.SIMPLE;
		} else if (content == null && inReplyTo == null && this.repostOf != null) {
			tweetType = Type.REPOST;
		} else if (content != null && inReplyTo != null && this.repostOf == null) {
			tweetType = Type.REPLY;
		}
		return tweetType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
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

	public Tweet getInReplyTo() {
		return inReplyTo;
	}

	public void setInReplyTo(Tweet inReplyTo) {
		this.inReplyTo = inReplyTo;
	}

	public Tweet getRepostOf() {
		return repostOf;
	}

	public void setRepostOf(Tweet repostOf) {
		this.repostOf = repostOf;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
