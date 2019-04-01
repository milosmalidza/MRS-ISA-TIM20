package com.webapplication.Model;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.webapplication.JSONBeans.CompanyInfo;

@Entity
public class Hotel extends Company {
	
	@OneToMany(mappedBy = "hotel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<HotelAdmin> hotelAdmins;
	
	public Hotel() {
		
		super();
		this.hotelAdmins = new HashSet<HotelAdmin>();
		
	}

	public Hotel(String name) {
		super(name);
		this.hotelAdmins = new HashSet<HotelAdmin>();
	}
	
	public Hotel(Long id, String name, String description, String address, double rating) {
		
		super(id, name, description, address, rating);
		this.hotelAdmins = new HashSet<HotelAdmin>();
	}
	
	//used when registering a new company
	public Hotel(CompanyInfo companyInfo, HotelAdmin hotelAdmin) {
		this.name = companyInfo.getName();
		//this.hotelAdmins.add(hotelAdmin);
	}

	public Set<HotelAdmin> getHotelAdmins() {
		return hotelAdmins;
	}

	public void setHotelAdmins(Set<HotelAdmin> hotelAdmins) {
		this.hotelAdmins = hotelAdmins;
	}

	
	
}
