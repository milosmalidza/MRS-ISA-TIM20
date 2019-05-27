package com.webapplication.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Repository.HotelAdminRepository;

@Service
public class HotelAdminService {

	@Autowired
	HotelAdminRepository hotelAdminRep;
	
	
	public Hotel getHotel(String username) {
		
		HotelAdmin admin = hotelAdminRep.findByUsername(username);
		
		if(admin != null) {
			return admin.getHotel();
		}
		
		return null;
	}
	
	
	public HotelAdmin save(HotelAdmin admin) {
		return hotelAdminRep.save(admin);
	}
	
	public List<HotelAdmin> findAll() {
		return hotelAdminRep.findAll();
	}
	
	
	public HotelAdmin findByUsername(String username) {
		return hotelAdminRep.findByUsername(username);
	}
	
	public Optional<HotelAdmin> findOne(Long id) {
		return hotelAdminRep.findOne(id);
	}
	
	
	//returns the hotel admins that aren't currenly assigned to any hotel
	public List<HotelAdmin> getAvailableAdmins() {
		
		List<HotelAdmin> availableAdmins = new ArrayList<HotelAdmin>();
		
		for(HotelAdmin hotelAdmin: findAll()) {
			if(hotelAdmin.getHotel() == null && hotelAdmin.isEnabled()) {
				availableAdmins.add(hotelAdmin);
			}
		}
		
		return availableAdmins;
	}
	
	
	public void deleteByUsername(String username) {
		hotelAdminRep.deleteByUsername(username);
	}
	
	
	public HotelAdmin findByEmailIdIgnoreCase(String emailid) {
		return hotelAdminRep.findByEmailIdIgnoreCase(emailid);
	}
	
}
