package com.webapplication.Model;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.webapplication.JSONBeans.UserBean;

@Entity
public class SystemAdmin extends AppUser {

	
	@Column(name="passwordChanged", nullable = false)
	private boolean passwordChanged;
	
	public SystemAdmin() {
		
	}
	
	public SystemAdmin(String username, String password, String firstName, String lastName,
			String email) {
		super(username, password, firstName, lastName, email);
	}
	
	public SystemAdmin(UserBean admin) {
	
		super(admin.getUsername(), admin.getPassword(), admin.getFirstName(),
				admin.getLastName(), admin.getEmail());
		
	}

	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
	
}
