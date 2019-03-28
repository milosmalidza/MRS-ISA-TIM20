package com.webapplication.Model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.MappedSuperclass;

import javax.persistence.Id;

@MappedSuperclass
public class AppUser {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	protected Long id;
	
	@Column(name = "username", unique = false, nullable = false)
	protected String username;
	
	@Column(name = "password", nullable = false)
	protected String password;
	
	@Column(name = "firstname", nullable = false)
	protected String firstName;
	
	@Column(name = "lastname", nullable = false)
	protected String lastName;
	
	@Column(name = "email", nullable = false)
	protected String email;
	
	public AppUser() {
		
	}

	public AppUser(String username, String password, String firstName, String lastName, String email) {
		
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	@Override
	public String toString() {
		return this.username + "; " + this.password + "; " + this.firstName + "; " + this.lastName
				+  "; " + this.email;
	}
	
}
