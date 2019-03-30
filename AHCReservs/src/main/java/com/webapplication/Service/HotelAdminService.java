package com.webapplication.Service;

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
		return hotelAdminRep.save(admin);
	}
	
	public List<HotelAdmin> findAll() {
		return hotelAdminRep.findAll();
	}
	
	
	
	
	
}
