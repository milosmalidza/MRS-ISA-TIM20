package com.webapplication.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.JSONBeans.DateBean;
import com.webapplication.JSONBeans.RoomData;
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
				|| sysAdminSvc.findByUsername(username) != null ) { 
			
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
	
	
	public DateBean parseDates(DateBean dateBean, SimpleDateFormat sdf) {
		
		if(sdf == null) {
			sdf = new SimpleDateFormat("dd.MM.yyyy.");
		}
		
		try {
			dateBean.setStartDate(sdf.parse(dateBean.getStrStartDate()));
			dateBean.setEndDate(sdf.parse(dateBean.getStrEndDate()));
		} catch (ParseException e) {
			return null;
		}
		
		return dateBean;	
		
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
	

}
