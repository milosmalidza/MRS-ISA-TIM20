package com.webapplication.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Repository.AirlineAdminRepository;

@Service
public class AirlineAdminService {

	@Autowired
	AirlineAdminRepository airlineAdminRep;
	

	public AirlineAdmin save(AirlineAdmin admin) {
		return airlineAdminRep.save(admin);
	}
	
	public List<AirlineAdmin> findAll() {
		return airlineAdminRep.findAll();
	}
	
	
	public AirlineAdmin findByUsername(String username) {
		return airlineAdminRep.findByUsername(username);
	}
	
	public Optional<AirlineAdmin> findOne(Long id) {
		return airlineAdminRep.findOne(id);
	}
	
	
	public List<AirlineAdmin> getAvailableAdmins() {
		
		return airlineAdminRep.getAvailableAdmins();
	}
	
	
	public void deleteByUsername(String username) {
		airlineAdminRep.deleteByUsername(username);
	}
	
	
	public AirlineAdmin findByEmailIdIgnoreCase(String emailid) {
		return airlineAdminRep.findByEmailIdIgnoreCase(emailid);
	}
	
}
