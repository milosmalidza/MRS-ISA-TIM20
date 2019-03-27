package com.webapplication.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import org.springframework.data.annotation.Id;

public class Friendship {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private FriendRequest friendRequest;
	
	public Friendship() {}
	
	public Friendship(String username, FriendRequest friendRequest) {
		super();
		this.username = username;
		this.friendRequest = friendRequest;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public FriendRequest getFriendRequest() {
		return friendRequest;
	}

	public void setFriendRequest(FriendRequest friendRequest) {
		this.friendRequest = friendRequest;
	}
	
	
	
	
	
}
