package com.webapplication.Model;

public class HotelAdmin extends AppUser {

	
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
