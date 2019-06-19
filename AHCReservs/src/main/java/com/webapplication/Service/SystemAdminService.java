package com.webapplication.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.JSONBeans.UserBean;
import com.webapplication.Model.Airline;
import com.webapplication.Model.AirlineAdmin;
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
	
	@Autowired
	MultipleService mulSvc;
	
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
	
	
	public String registerAdmin(UserBean admin) {
		
		if(admin.getCompanyType() == null) {
			return "Company type undefined";
		}
		
		//TODO: Check whether email already exists
		
		
		if(mulSvc.usernameExist(admin.getUsername())) {
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
		
		if(mulSvc.companyExists(companyInfo.getName(), false)) {
			return "Company with the entered name already exists";
		}
		
		switch(companyInfo.getType().toLowerCase()) {
		
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
	
	
	
	
	
	public String addAdminsToCompany(CompanyInfo companyInfo) {
		
		//determine company type
		switch(companyInfo.getType().toLowerCase()) {
		
		case "hotel":
			
			Hotel hotel = hotelSvc.findOne(companyInfo.getCompanyId()).get(); //get hotel
			
			//iterate through admins and set their hotels
			for(String username: companyInfo.getUsernames()) {
				
				HotelAdmin hotelAdmin = hotelAdminSvc.findByUsername(username);
				
				//if the admin doesn't already have a hotel assigned, assign the hotel
				if(hotelAdmin.getHotel() == null) {
					hotelAdmin.setHotel(hotel);
					hotelAdminSvc.save(hotelAdmin);
				}
				
			}
			
			return "Admins and their companies successfully saved";
			
		case "airline":
			
			Airline airline = airlineSvc.findByName(companyInfo.getName()); //get airline
			
			//iterate through admins and set their airline
			for(String username: companyInfo.getUsernames()) {
				
				AirlineAdmin airlineAdmin = airlineAdminSvc.findByUsername(username);
				
				if(airlineAdmin.getAirline() == null) {
					airlineAdmin.setAirline(airline);
					airlineAdminSvc.save(airlineAdmin);
				}
				
			}
			
			return "Admins and their companies successfully saved";
			
		case "rent-a-car":
			
			RentACar rentACar = rentACarSvc.findOneById(companyInfo.getCompanyId());
			
			//iterate through admins and set their airline
			for(String username: companyInfo.getUsernames()) {
				
				RentACarAdmin rentACarAdmin = rentACarAdminSvc.findByUsername(username);
				
				if(rentACarAdmin.getRentACar() == null) {
					rentACarAdmin.setRentACar(rentACar);
					rentACarAdminSvc.save(rentACarAdmin);
				}
				
			}
			
			return "Admins and their companies successfully saved";
			
		}
		
		
		return "Admins not saved. Something wen't wrong.";
	}
	
	
	/* System admin repository methods */

	public SystemAdmin save(SystemAdmin admin) {
		
		return sysAdminRep.save(admin);
	}
	
	public SystemAdmin findByUsername(String username) {
		return sysAdminRep.findByUsername(username);
	}
	
	public Optional<SystemAdmin> findOne(Long id) {
		return sysAdminRep.findOne(id);
	}
	
	public void deleteByUsername(String username) {
		sysAdminRep.deleteByUsername(username);
	}
	
	
	public SystemAdmin findByEmailIdIgnoreCase(String emailid) {
		return sysAdminRep.findByEmailIdIgnoreCase(emailid);
	}
	
}
