package com.webapplication.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Repository.AirlineAdminRepository;

@Service
public class AirlineAdminService {

	@Autowired
	AirlineAdminRepository airlineAdminRep;
	

	public AirlineAdmin save(AirlineAdmin admin) {
		
		//the username must be unique in the table
		if(findByUsername(admin.getUsername()) != null) {
			return null;
		}
		
		return airlineAdminRep.save(admin);
	}
	
	public List<AirlineAdmin> findAll() {
		return airlineAdminRep.findAll();
	}
	
	
	public AirlineAdmin findByUsername(String username) {
		return airlineAdminRep.findByUsername(username);
	}
	
	
	//returns the hotel admins that aren't currenly assigned to any hotel
	public List<AirlineAdmin> getAvailableAdmins() {
		
		List<AirlineAdmin> availableAdmins = new ArrayList<AirlineAdmin>();
		
		for(AirlineAdmin airlineAdmin: findAll()) {
			if(airlineAdmin.getAirline() == null) {
				availableAdmins.add(airlineAdmin);
			}
		}
		
		return availableAdmins;
	}
	
}
