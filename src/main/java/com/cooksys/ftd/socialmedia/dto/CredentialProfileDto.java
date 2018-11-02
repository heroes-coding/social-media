package com.cooksys.ftd.socialmedia.dto;

import com.cooksys.ftd.socialmedia.entity.Profile;

public class CredentialProfileDto {
	private CredentialDto credentials;
	private Profile profile;
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CredentialDto getCredentials() {
		return credentials;
	}

	public void setCredentials(CredentialDto credentials) {
		this.credentials = credentials;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

}
