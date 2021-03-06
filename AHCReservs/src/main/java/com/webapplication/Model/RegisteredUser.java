package com.webapplication.Model;


import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity
public class RegisteredUser extends AppUser{
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<VehicleReservation> reservations;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RoomReservation> hotelReservations;
	
	
	
	public RegisteredUser() {
		super();
		
	}

	public RegisteredUser(String username, String password, String firstName, String lastName, String email) {
		
		super(username, password, firstName, lastName, email);
		
	}

	
	public RegisteredUser(AppUser user) {
		
		super(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmailId());
	
	}
	
	

	@Override
	public String toString() {
		return super.toString();
	}
	
	public Set<RoomReservation> getHotelReservations() {
		return hotelReservations;
	}

	public void setHotelReservations(Set<RoomReservation> hotelReservations) {
		this.hotelReservations = hotelReservations;
	}
	
	@JsonIgnore
	public List<VehicleReservation> getReservations() {
		return reservations;
	}

	public void setReservations(List<VehicleReservation> reservations) {
		this.reservations = reservations;
	}
	
	
	
}
