package com.webapplication.JSONBeans;

import java.util.List;

public class CompanyInfo {
	
	private String name;
	private String type;
	private String adminUsername;
	private List<String> admins;
	
	public CompanyInfo() {
		
	}

	public CompanyInfo(String name, String type, String adminUsername) {
		
		this.name = name;
		this.type = type;
		this.adminUsername = adminUsername;
	}
	
	

	public CompanyInfo(String name, String type, String adminUsername, List<String> usernames) {
		
		this.name = name;
		this.type = type;
		this.adminUsername = adminUsername;
		this.admins = usernames;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAdminUsername() {
		return adminUsername;
	}

	public void setAdminUsername(String adminUsername) {
		this.adminUsername = adminUsername;
	}
	
	public List<String> getUsernames() {
		return admins;
	}

	public void setUsernames(List<String> usernames) {
		this.admins = usernames;
	}
	
	@Override
	public String toString() {
		return this.name + "; " + this.type + "; " + this.adminUsername + ";";
	}

}
