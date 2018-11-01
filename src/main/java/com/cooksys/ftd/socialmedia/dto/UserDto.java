package com.cooksys.ftd.socialmedia.dto;

import java.sql.Timestamp;

public class UserDto {

	private String username;
	private ProfileDto profile;
	private Timestamp joined;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ProfileDto getProfile() {
		return profile;
	}

	public void setProfile(ProfileDto profile) {
		this.profile = profile;
	}

	public Timestamp getJoined() {
		return joined;
	}

	public void setJoined(Timestamp joined) {
		this.joined = joined;
	}
	
	
	
}
