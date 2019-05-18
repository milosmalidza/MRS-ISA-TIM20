package com.webapplication.Model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

@MappedSuperclass
public abstract class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false)
	protected Long id;
	
	@Column(name="name", unique=false, nullable=false)
	protected String name;
	
	@Lob
	@Column(name="description", length=1000, nullable=true)
	protected String description;
	
	@Column(name="address", nullable=true)
	protected String address;
	
	@Column(name="rating", nullable=true)
	protected double rating;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<Rating> ratings;
	
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

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
	public void updateRating() {
		double sum = 0;
		for (Rating r : ratings) {
			sum += r.getRating();
		}
		
		this.rating = Math.round((sum / ratings.size()) * 10) / 10;
		
	}
	
	
	
}
