package com.webapplication.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MultipleService {
	
	/** A service used by all other services */
	
	//user service
	@Autowired
	RegisteredUserService regUserSvc;
	
	/* Admin services */
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	@Autowired
	AirlineAdminService airlineAdminSvc;
	
	@Autowired
	RentACarAdminService rentACarAdminSvc;
	
	@Autowired
	SystemAdminService sysAdminSvc;
	
	/* Company services */
	@Autowired
	HotelService hotelSvc;
	
	@Autowired
	AirlineService airlineSvc;
	
	@Autowired
	RentACarService rentACarSvc;
	
	
	public boolean usernameExist(String username) {
		
		if(hotelAdminSvc.findByUsername(username) != null
				|| airlineAdminSvc.findByUsername(username) != null 
				|| rentACarAdminSvc.findByUsername(username) != null
				|| regUserSvc.findByUsername(username) != null
				|| sysAdminSvc.findByUsername(username) != null ) { //searching through system admins
			
			return true;
		}
		
		return false;
	}
	
	
	public boolean companyExists(String companyName, boolean isJson) {
		
		//parse json if neccessairy
		if(isJson) {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = null;
			try {
				node = mapper.readTree(companyName);
			} catch (IOException e) {
				e.printStackTrace();
			}
			companyName = node.get("companyName").asText();
		}
		
		if(airlineSvc.findByName(companyName) != null 
		     || hotelSvc.findByName(companyName) != null
		     || rentACarSvc.findByName(companyName) != null) {
			
			return true;
		} 
		
		return false;

	}
	
	
	public boolean hotelNameExists(String hotelName) {
		
		if(airlineSvc.findByName(hotelName) != null || rentACarSvc.findByName(hotelName) != null) {
				
			return true;
		}
		
		return false;
		
	}

}
