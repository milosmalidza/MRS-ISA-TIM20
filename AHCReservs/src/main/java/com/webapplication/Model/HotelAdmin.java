package com.webapplication.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.webapplication.JSONBeans.UserBean;

@Entity
public class HotelAdmin extends AppUser {

	@Column(name="passwordChanged", nullable = false)
	private boolean passwordChanged;
	
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
	
	public HotelAdmin(UserBean admin) {
		
		super(admin.getUsername(), admin.getPassword(), admin.getFirstName(),
				admin.getLastName(), admin.getEmail());
		
		this.hotel = null; //prilikom registracije admin je besposlen
		
	}

	
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
	
	
}
