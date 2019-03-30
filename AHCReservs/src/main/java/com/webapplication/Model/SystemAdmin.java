package com.webapplication.Model;

import javax.persistence.Entity;

@Entity
public class SystemAdmin extends AppUser {

	public SystemAdmin() {
		
	}
	
	public SystemAdmin(String username, String password, String firstName, String lastName,
			String email) {
		super(username, password, firstName, lastName, email);
	}
}
