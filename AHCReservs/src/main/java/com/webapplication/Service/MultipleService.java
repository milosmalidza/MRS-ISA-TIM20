package com.webapplication.Service;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.JSONBeans.RoomData;
import com.webapplication.Model.Currency;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.Room;

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
	
	
	public boolean roomExistsInHotel(Hotel hotel, RoomData roomData, boolean editing) {
		
		//check if the room number already exists in the hotel
		for (Room room : hotel.getRooms()) {
			
			if(room.getNumber() == roomData.getNumber()) {
				
				//if the room is being edited
				if(editing) {
					
					//if the user left the same room number when he was editing
					if(room.getId() == roomData.getRoomID()) {
						continue;
					}
						
					return true;
					
				} else {
					return true;
				}
				
			}
			
		}
		
		return false;
		
	}
	
	
	public Collection<Currency> getCurrencies() {
		
		return Arrays.asList(Currency.values());
		
	}
	
	
	public Currency convertStringToCurrency(String currencyString) {
		
		try {
			
			return Currency.valueOf(currencyString.toUpperCase());
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return null;
		}
		
	}

}
