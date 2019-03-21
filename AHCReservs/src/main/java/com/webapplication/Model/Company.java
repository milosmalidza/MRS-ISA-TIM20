package com.webapplication.Model;

public abstract class Company {
	
	protected int id;
	protected String name;
	protected String description;
	protected String address;
	protected double rating;
	
	public Company() {
		
	}

	public Company(int id, String name, String description, String address, double rating) {
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.rating = rating;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}
	
	
	
	
	
}
