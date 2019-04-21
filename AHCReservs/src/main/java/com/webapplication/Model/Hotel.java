package com.webapplication.Model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webapplication.JSONBeans.CompanyInfo;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //to ignore certain fields
public class Hotel extends Company {
	
	
	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<HotelAdmin> admins;
	
	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Room> rooms;
	
	public Hotel() {
		
		super();
		this.admins = new HashSet<HotelAdmin>();
		this.rooms = new HashSet<Room>();
		
	}

	public Hotel(String name) {
		super(name);
		this.admins = new HashSet<HotelAdmin>();
		this.rooms = new HashSet<Room>();
	}
	
	public Hotel(Long id, String name, String description, String address, double rating) {
		
		super(id, name, description, address, rating);
		this.admins = new HashSet<HotelAdmin>();
		this.rooms = new HashSet<Room>();
	}
	
	//used when registering a new company
	public Hotel(CompanyInfo companyInfo) {
		this.name = companyInfo.getName();
		this.admins = new HashSet<HotelAdmin>();
		this.rooms = new HashSet<Room>();
	}
	
	@JsonIgnore //ignoring to avoid infinite recursion when returning json
	public Set<HotelAdmin> getHotelAdmins() {
		return admins;
	}

	public void setHotelAdmins(Set<HotelAdmin> hotelAdmins) {
		this.admins = hotelAdmins;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	
	
}
