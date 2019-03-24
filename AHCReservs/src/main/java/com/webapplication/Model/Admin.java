package com.webapplication.Model;

public class Admin extends User {

	private Company company;
	
	public Admin() {
		
	}
	
	public Admin(String username, String password, String firstName, String lastName, String email) {
		
		super(username, password, firstName, lastName, email);
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	
}
