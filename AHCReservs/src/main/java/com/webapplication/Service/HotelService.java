package com.webapplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.HotelData;
import com.webapplication.Model.Hotel;
import com.webapplication.Repository.HotelRepository;


@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRep;
	
	@Autowired
	MultipleService mulSvc;
	
	
	public Hotel updateProfile(HotelData hotelData) {
		
		Hotel hotel = null;
		
		//check if the name already exists in other companies
		if(mulSvc.hotelNameExists(hotelData.getName())) {
			return null;
		}
		
		//get the hotel by id
		hotel = findOne(hotelData.getId()).get();
			
		if(hotel == null) {
			return null;
		}
			
		//if the admin changed the hotel name
		if(!hotelData.getName().equals(hotel.getName())) {
				
			//if the new name already exists in the hotels
			if (findByName(hotel.getName()) != null) {
				return null;
			}
		}
		
		
		//memorize changes
		hotel.setName(hotelData.getName());
		hotel.setDescription(hotelData.getDescription());
		hotel.setAddress(hotelData.getAddress());
		
		save(hotel);
		
		return hotel;
	}
	
	public Optional<Hotel> findOne(Long id) {
		return hotelRep.findById(id);
	}
	
	public Hotel save(Hotel hotel) {	
		return hotelRep.save(hotel);
	}
	
	
	public Hotel findByName(String name) {
		return hotelRep.findOneByName(name);
	}
	
	public List<Hotel> findAll() {
		return hotelRep.findAll();
	}
}
