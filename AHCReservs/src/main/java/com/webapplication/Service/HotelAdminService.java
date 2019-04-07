package com.webapplication.Service;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.HotelAdmin;
import com.webapplication.Repository.HotelAdminRepository;

@Service
public class HotelAdminService {

	@Autowired
	HotelAdminRepository hotelAdminRep;
	
	
	public HotelAdmin save(HotelAdmin admin) {
		
		//the username must be unique in the table
		if(findByUsername(admin.getUsername()) != null) {
			return null;
		}
		
		return hotelAdminRep.save(admin);
	}
	
	public List<HotelAdmin> findAll() {
		return hotelAdminRep.findAll();
	}
	
	
	public HotelAdmin findByUsername(String username) {
		return hotelAdminRep.findByUsername(username);
	}
	
	
	//returns the hotel admins that aren't currenly assigned to any hotel
	public List<HotelAdmin> getAvailableAdmins() {
		
		List<HotelAdmin> availableAdmins = new ArrayList<HotelAdmin>();
		
		for(HotelAdmin hotelAdmin: findAll()) {
			if(hotelAdmin.getHotel() == null) {
				availableAdmins.add(hotelAdmin);
			}
		}
		
		return availableAdmins;
	}
	
	
	public void deleteByUsername(String username) {
		hotelAdminRep.deleteByUsername(username);
	}
	
}
