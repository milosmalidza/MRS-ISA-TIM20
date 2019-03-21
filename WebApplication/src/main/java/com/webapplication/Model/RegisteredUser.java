package com.webapplication.Model;

import java.util.ArrayList;
import java.util.List;

public class RegisteredUser extends User {

	private List<RegisteredUser> friends;
	
	public RegisteredUser() {
		
		super();
		this.friends = new ArrayList<RegisteredUser>();
		
	}
	
	public RegisteredUser(String username, String password, String firstName, String lastName, String email) {
		
		super(username, password, firstName, lastName, email);
		this.friends = new ArrayList<RegisteredUser>();
	}
	

	public List<RegisteredUser> getFriends() {
		return friends;
	}

	public void setFriends(List<RegisteredUser> friends) {
		this.friends = friends;
	}
	
	
}
