package com.webapplication.JSONBeans;

public class CompanyInfo {
	
	private String name;
	private String type;
	private String adminUsername;
	
	public CompanyInfo() {
		
	}

	public CompanyInfo(String name, String type, String adminUsername) {
		
		this.name = name;
		this.type = type;
		this.adminUsername = adminUsername;
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
	
	@Override
	public String toString() {
		return this.name + "; " + this.type + "; " + this.adminUsername + ";";
	}

}
