package com.webapplication.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.Hotel;
import com.webapplication.Repository.HotelRepository;


@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRep;
	
	
	public Hotel save(Hotel hotel) {
		
		//the hotel name must be unique in the table
		if(findByName(hotel.getName()) != null) {
			return null;
		}
		
		return hotelRep.save(hotel);
	}
	
	
	public Hotel findByName(String name) {
		return hotelRep.findOneByName(name);
	}
	
	public List<Hotel> findAll() {
		return hotelRep.findAll();
	}
}
