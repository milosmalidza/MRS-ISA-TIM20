package com.webapplication.Model;


import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
@Entity
public class RegisteredUser extends AppUser{

	//@OneToMany(mappedBy="registereduser", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
	//private List<Friendship> friendships;
	
	@OneToMany(mappedBy = "user")
	private List<VehicleReservation> reservations;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RoomReservation> hotelReservations;
	
	
	
	public RegisteredUser() {
		super();
		//this.friendships = new ArrayList<Friendship>();
	}

	public RegisteredUser(String username, String password, String firstName, String lastName, String email) {
		
		super(username, password, firstName, lastName, email);
		//this.friendships = new ArrayList<Friendship>();
	}

	
	public RegisteredUser(AppUser user) {
		
		super(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName(), user.getEmailId());
		//this.friendships = new ArrayList<Friendship>();
	}
	
	
	/*
	public List<Friendship> getFriendships() {
		return friendships;
	}

	public void setFriendships(List<Friendship> friendships) {
		this.friendships = friendships;
	}
	*/

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
	
}
