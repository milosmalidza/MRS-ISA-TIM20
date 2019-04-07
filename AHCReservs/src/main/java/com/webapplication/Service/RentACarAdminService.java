package com.webapplication.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Repository.RentACarAdminRepository;

@Service
public class RentACarAdminService {

	
	@Autowired
	RentACarAdminRepository rentACarAdminRep;
	

	public RentACarAdmin save(RentACarAdmin admin) {
		
		//the username must be unique in the table
		if(findByUsername(admin.getUsername()) != null) {
			return null;
		}
		
		return rentACarAdminRep.save(admin);
	}
	
	public List<RentACarAdmin> findAll() {
		return rentACarAdminRep.findAll();
	}
	
	
	public RentACarAdmin findByUsername(String username) {
		return rentACarAdminRep.findByUsername(username);
	}
	
	
	//returns the hotel admins that aren't currenly assigned to any hotel
	public List<RentACarAdmin> getAvailableAdmins() {
		
		List<RentACarAdmin> availableAdmins = new ArrayList<RentACarAdmin>();
		
		for(RentACarAdmin rentACarAdmin: findAll()) {
			if(rentACarAdmin.getRentACar() == null) {
				availableAdmins.add(rentACarAdmin);
			}
		}
		
		return availableAdmins;
	}
	
	
	public void deleteByUsername(String username) {
		rentACarAdminRep.deleteByUsername(username);
	}
	
	
}
