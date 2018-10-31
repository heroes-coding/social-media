package com.cooksys.ftd.socialmedia.dto;

import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties.Credential;

import com.cooksys.ftd.socialmedia.entity.Profile;

public class CredentialProfileDto {
	private Credential credentials;
	private Profile profile;
	public Credential getCredentials() {
		return credentials;
	}
	public void setCredentials(Credential credentials) {
		this.credentials = credentials;
	}
	public Profile getProfile() {
		return profile;
	}
	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
}
