package com.webapplication.Model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

import javax.persistence.Id;

@MappedSuperclass
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false)
	protected Long id;
	
	@Column(name = "username", unique = true, nullable = false)
	protected String username;
	
	@Column(name = "password", nullable = false)
	protected String password;
	
	@Column(name = "firstname", nullable = false)
	protected String firstName;
	
	@Column(name = "lastname", nullable = false)
	protected String lastName;
	
	protected String emailId;
	
	protected boolean isEnabled;
	
	public AppUser() {
		
	}

	public AppUser(String username, String password, String firstName, String lastName, String emailId) {
		
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return this.username + "; " + this.password + "; " + this.firstName + "; " + this.lastName
				+  "; " + this.emailId;
	}
	
}
