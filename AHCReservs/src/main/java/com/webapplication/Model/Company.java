package com.webapplication.Model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false)
	protected Long id;
	
	@Column(name="name", unique=false, nullable=false)
	protected String name;
	
	@Column(name="description", nullable=true)
	protected String description;
	
	@Column(name="address", nullable=true)
	protected String address;
	
	@Column(name="rating", nullable=true)
	protected double rating;
	
	public Company() {
		
	}
	
	public Company(String name) {
		this.name = name;
	}

	public Company(Long id, String name, String description, String address, double rating) {
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.rating = rating;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
