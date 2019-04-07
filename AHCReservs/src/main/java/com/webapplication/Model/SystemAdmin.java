package com.webapplication.Model;

import javax.persistence.Entity;

import com.webapplication.JSONBeans.AdminToRegister;

@Entity
public class SystemAdmin extends AppUser {

	public SystemAdmin() {
		
	}
	
	public SystemAdmin(String username, String password, String firstName, String lastName,
			String email) {
		super(username, password, firstName, lastName, email);
	}
	
	public SystemAdmin(AdminToRegister admin) {
	
		super(admin.getUsername(), admin.getPassword(), admin.getFirstName(),
				admin.getLastName(), admin.getEmail());
		
	}
	
}