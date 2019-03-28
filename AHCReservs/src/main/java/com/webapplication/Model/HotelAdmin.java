package com.webapplication.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
public class HotelAdmin extends AppUser {

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Hotel hotel;
	
	public HotelAdmin() {
		super();
	}
	
	public HotelAdmin(String username, String password, String firstName, String lastName,
			String email, Hotel hotel) {
		
		super(username, password, firstName, lastName, email);
		this.hotel = hotel;
		
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	
	
	
	
}
