package com.webapplication.Model;


import javax.persistence.Entity;
@Entity
public class RegisteredUser extends AppUser{

	//@OneToMany(mappedBy="registereduser", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	//private List<Friendship> friendships;
	
	
	public RegisteredUser() {
		super();
		//this.friendships = new ArrayList<Friendship>();
	}

	public RegisteredUser(String username, String password, String firstName, String lastName, String email) {
		
		super(username, password, firstName, lastName, email);
		//this.friendships = new ArrayList<Friendship>();
	}

	
	public RegisteredUser(AppUser user) {
		
		super(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmailId());
		//this.friendships = new ArrayList<Friendship>();
	}
	
	
	/*
	public List<Friendship> getFriendships() {
		return friendships;
	}

	public void setFriendships(List<Friendship> friendships) {
		this.friendships = friendships;
	}
	*/

	@Override
	public String toString() {
		return super.toString();
	}
	
}
