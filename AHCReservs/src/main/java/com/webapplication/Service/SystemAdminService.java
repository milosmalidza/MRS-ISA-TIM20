package com.webapplication.Service;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.JSONBeans.AdminToRegister;
import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.Model.Airline;
import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.Company;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.SystemAdmin;
import com.webapplication.Repository.SystemAdminRepository;

@Service
public class SystemAdminService {

	@Autowired
	SystemAdminRepository sysAdminRep;
	
	@Autowired
	RegisteredUserService regUserSvc;
	
	/* Admin services */
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	@Autowired
	AirlineAdminService airlineAdminSvc;
	
	@Autowired
	RentACarAdminService rentACarAdminSvc;
	
	/* Company services */
	@Autowired
	HotelService hotelSvc;
	
	@Autowired
	AirlineService airlineSvc;
	
	@Autowired
	RentACarService rentACarSvc;
	
	/* Confirmation mail service */
	@Autowired
	ConfirmationTokenService confirmService;
	
	
	public String registerAdmin(AdminToRegister admin) {
		
		if(admin.getCompanyType() == null) {
			return "Company type undefined";
		}
		
		//TODO: Check whether email already exists
		
		
		if(usernameExist(admin.getUsername())) {
			return "Username already exists";
		}
		
		String response; //message to be returned to the user
		
		//check which type of admin I'm registering
		switch(admin.getCompanyType()) {
		
		case "hotel":
			HotelAdmin hotelAdmin = new HotelAdmin(admin);
			hotelAdminSvc.save(hotelAdmin);
			
			response = confirmService.sendConfirmationMail(hotelAdmin);
			if(response.contains("invalid")) {
				hotelAdminSvc.deleteByUsername(hotelAdmin.getUsername());
			}
			
			return response;
			
		case "airline":
			AirlineAdmin airlineAdmin = new AirlineAdmin(admin);
			airlineAdminSvc.save(airlineAdmin);
			
			response = confirmService.sendConfirmationMail(airlineAdmin);
			if(response.contains("invalid")) {
				airlineAdminSvc.deleteByUsername(airlineAdmin.getUsername());
			}
			
			return response;
			
		case "rent-a-car":
			RentACarAdmin rent_a_car_admin = new RentACarAdmin(admin);
			rentACarAdminSvc.save(rent_a_car_admin);
			
			response = confirmService.sendConfirmationMail(rent_a_car_admin);
			if(response.contains("invalid")) {
				rentACarAdminSvc.deleteByUsername(rent_a_car_admin.getUsername());
			}
			
			return response;
			
		case "system":
			SystemAdmin sysAdmin = new SystemAdmin(admin);
			save(sysAdmin);
			
			response = confirmService.sendConfirmationMail(sysAdmin);
			if(response.contains("invalid")) {
				deleteByUsername(sysAdmin.getUsername());
			}
			
			return response;
			
		default:
			break;
			
		}
		
		return "Uknown company";
	}
	
	
	public String registerCompany(CompanyInfo companyInfo) {
		
		if(companyInfo.getType() == null) {
			return "Unknown company type";
		}
		
		if(companyExists(companyInfo.getName(), false)) {
			return "Company with the entered name already exists";
		}
		
		switch(companyInfo.getType()) {
		
		case "hotel":
			
			HotelAdmin hotelAdmin = hotelAdminSvc.findByUsername(companyInfo.getAdminUsername());
			System.out.println("Found admin: " + hotelAdmin.toString());
			
			Hotel hotel = new Hotel(companyInfo);
			hotelAdmin.setHotel(hotel);
			
			hotelAdminSvc.save(hotelAdmin);
			
			return "Hotel successfully registered";
			
		case "airline":
			
			AirlineAdmin airlineAdmin = airlineAdminSvc.findByUsername(companyInfo.getAdminUsername());
			System.out.println("Found admin: " + airlineAdmin.toString());
			
			Airline airline = new Airline(companyInfo);
			airlineAdmin.setAirline(airline);
			
			airlineAdminSvc.save(airlineAdmin);
			
			return "Airline successfully registered";
			
		case "rent-a-car":
			
			RentACarAdmin rentACarAdmin = rentACarAdminSvc.findByUsername(companyInfo.getAdminUsername());
			System.out.println("Found admin: " + rentACarAdmin.toString());
			
			RentACar rentACar = new RentACar(companyInfo);
			rentACarAdmin.setRentACar(rentACar);
			
			rentACarAdminSvc.save(rentACarAdmin);
			
			return "Rent a car service successfully registered";

		}
		
		return null;
	}
	
	
	public boolean usernameExist(String username) {
		
		if(hotelAdminSvc.findByUsername(username) != null
				|| airlineAdminSvc.findByUsername(username) != null 
				|| rentACarAdminSvc.findByUsername(username) != null
				|| regUserSvc.findByUsername(username) != null
				|| findByUsername(username) != null ) { //searching through system admins
			
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
	
	
	/* System admin repository methods */

	public SystemAdmin save(SystemAdmin admin) {
		
		return sysAdminRep.save(admin);
	}
	
	public SystemAdmin findByUsername(String username) {
		return sysAdminRep.findByUsername(username);
	}
	
	public void deleteByUsername(String username) {
		sysAdminRep.deleteByUsername(username);
	}
	
	
	public SystemAdmin findByEmailIdIgnoreCase(String emailid) {
		return sysAdminRep.findByEmailIdIgnoreCase(emailid);
	}
	
}
