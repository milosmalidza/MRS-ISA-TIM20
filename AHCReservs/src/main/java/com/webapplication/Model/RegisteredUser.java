package com.webapplication.Model;

import javax.persistence.Entity;

@Entity
public class RegisteredUser extends AppUser{

	
	public RegisteredUser() {
		
	}

	public RegisteredUser(String username, String password, String firstName, String lastName, String email) {
		
		super(username, password, firstName, lastName, email);
	}

	
	
}
