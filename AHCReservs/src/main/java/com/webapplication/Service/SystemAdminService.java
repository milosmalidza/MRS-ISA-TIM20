package com.webapplication.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	
	public AppUser registerAdmin(AdminToRegister admin) {
		
		if(admin.getCompanyType() == null) {
			return null;
		}
		
		//check which type of admin I'm registering
		switch(admin.getCompanyType()) {
		
		case "hotel":
			HotelAdmin hotelAdmin = new HotelAdmin(admin);
			return hotelAdminSvc.save(hotelAdmin);
			
		case "airline":
			AirlineAdmin airlineAdmin = new AirlineAdmin(admin);
			return airlineAdminSvc.save(airlineAdmin);
			
		case "rent-a-car":
			RentACarAdmin rent_a_car_admin = new RentACarAdmin(admin);
			return rentACarAdminSvc.save(rent_a_car_admin);
			
		case "system":
			SystemAdmin sysAdmin = new SystemAdmin(admin);
			return save(sysAdmin);
			
		default:
			break;
			
		}
		
		return null;
	}
	
	
	
	public Company registerCompany(CompanyInfo companyInfo) {
		
		if(companyInfo.getType() == null) {
			return null;
		}
		
		switch(companyInfo.getType()) {
		
		case "hotel":
			HotelAdmin hotelAdmin = hotelAdminSvc.findByUsername(companyInfo.getAdminUsername());
			System.out.println("Found admin: " + hotelAdmin.toString());
			
			Hotel hotel = new Hotel(companyInfo);
			hotelAdmin.setHotel(hotel);
			
			hotelAdminSvc.save(hotelAdmin);
			return hotelSvc.save(hotel);
			
		case "airline":
			AirlineAdmin airlineAdmin = airlineAdminSvc.findByUsername(companyInfo.getAdminUsername());
			System.out.println("Found admin: " + airlineAdmin.toString());
			
			Airline airline = new Airline(companyInfo);
			airlineAdmin.setAirline(airline);
			
			airlineAdminSvc.save(airlineAdmin);
			return airlineSvc.save(airline);
			
		case "rent-a-car":
			RentACarAdmin rentACarAdmin = rentACarAdminSvc.findByUsername(companyInfo.getAdminUsername());
			System.out.println("Found admin: " + rentACarAdmin.toString());
			
			RentACar rentACar = new RentACar(companyInfo);
			rentACarAdmin.setRentACar(rentACar);
			
			rentACarAdminSvc.save(rentACarAdmin);
			return rentACarSvc.save(rentACar);

		}
		
		return null;
	}


	public SystemAdmin save(SystemAdmin admin) {
		
		if(findByUsername(admin.getUsername()) != null) {
			return null;
		}
		
		return sysAdminRep.save(admin);
	}
	
	public SystemAdmin findByUsername(String username) {
		return sysAdminRep.findByUsername(username);
	}
	
}
