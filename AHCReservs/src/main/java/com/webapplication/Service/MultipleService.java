package com.webapplication.Service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.JSONBeans.RoomData;
import com.webapplication.JSONBeans.UserBean;
import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.Room;
import com.webapplication.Model.SystemAdmin;

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
	
	
	public AppUser updateProfile(UserBean user) {
		
		
		switch(user.getUserType()) {
		
		case "registeredUser":
			RegisteredUser regUser = regUserSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!regUser.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			regUser.changeUserData(user);
			return regUserSvc.save(regUser);
			
		case "sysAdmin":
			SystemAdmin sysAdmin = sysAdminSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!sysAdmin.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			sysAdmin.changeUserData(user);
			return sysAdminSvc.save(sysAdmin);
			
		case "airAdmin":
			AirlineAdmin airAdmin = airlineAdminSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!airAdmin.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			airAdmin.changeUserData(user);
			return airlineAdminSvc.save(airAdmin);
			
		case "hotelAdmin":
			HotelAdmin hotelAdmin = hotelAdminSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!hotelAdmin.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			hotelAdmin.changeUserData(user);
			return hotelAdminSvc.save(hotelAdmin);
			
		case "carAdmin":
			RentACarAdmin carAdmin = rentACarAdminSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!carAdmin.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			carAdmin.changeUserData(user);
			return rentACarAdminSvc.save(carAdmin);
			
		default:
			break;
		}
		
		return null;
		
	}
	
	
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
