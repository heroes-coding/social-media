package com.cooksys.ftd.socialmedia.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;


@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true)
	private String username;
	
	@Column
	@CreationTimestamp
	private Timestamp joined;

	@JoinColumn
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Profile profile;
	
	@JoinColumn
	@OneToOne(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	private Credentials credentials;
	
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getTimestamp() {
		return joined;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.joined = timestamp;
	}
	
	
}
