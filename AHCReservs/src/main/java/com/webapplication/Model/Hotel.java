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
	
	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<HAdditionalService> pricelist;
	
	public Hotel() {
		
		super();
		this.admins = new HashSet<HotelAdmin>();
		this.rooms = new HashSet<Room>();
		this.pricelist = new HashSet<HAdditionalService>();
		
	}

	public Hotel(String name) {
		super(name);
		this.admins = new HashSet<HotelAdmin>();
		this.rooms = new HashSet<Room>();
		this.pricelist = new HashSet<HAdditionalService>();
	}
	
	public Hotel(Long id, String name, String description, String address, double rating) {
		
		super(id, name, description, address, rating);
		this.admins = new HashSet<HotelAdmin>();
		this.rooms = new HashSet<Room>();
		this.pricelist = new HashSet<HAdditionalService>();
	}
	
	//used when registering a new company
	public Hotel(CompanyInfo companyInfo) {
		this.name = companyInfo.getName();
		this.admins = new HashSet<HotelAdmin>();
		this.rooms = new HashSet<Room>();
		this.pricelist = new HashSet<HAdditionalService>();
	}
	
	@JsonIgnore //ignoring to avoid infinite recursion when returning json
	public Set<HotelAdmin> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<HotelAdmin> hotelAdmins) {
		this.admins = hotelAdmins;
	}

	public Set<Room> getRooms() {
		return rooms;
	}

	public void setRooms(Set<Room> rooms) {
		this.rooms = rooms;
	}

	
	public Set<HAdditionalService> getPricelist() {
		return pricelist;
	}

	public void setPricelist(Set<HAdditionalService> pricelist) {
		this.pricelist = pricelist;
	} 

	
	
}
