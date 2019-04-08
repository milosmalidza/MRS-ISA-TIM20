package com.webapplication.Service;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Repository.RentACarAdminRepository;

@Service
public class RentACarAdminService {


	@Autowired
	RentACarAdminRepository rentACarAdminRep;


	public RentACarAdmin save(RentACarAdmin admin) {
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


	public String AddCar(String requestJson) throws IOException {

		ObjectMapper mapper = new ObjectMapper();




		return "test";
	}


	public RentACarAdmin findByEmailIdIgnoreCase(String emailid) {
		return rentACarAdminRep.findByEmailIdIgnoreCase(emailid);
	}

	
}
