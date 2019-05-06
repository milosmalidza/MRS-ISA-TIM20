package com.webapplication.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rating {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="user_id", nullable=false)
	private Long userId;
	
	@Column(name="rating", nullable=false)
	private double rating;
	
	
	public Rating() {}
	
	public Rating(Long id, Long userId, double rating) {
		super();
		this.id = id;
		this.userId = userId;
		this.rating = rating;
		
	}





	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public double getRating() {
		return rating;
	}


	public void setRating(double rating) {
		this.rating = rating;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	
	
}
