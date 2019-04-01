package com.webapplication.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.AdminToRegister;
import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.Company;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.SystemAdmin;
import com.webapplication.Repository.SystemAdminRepository;

@Service
public class SystemAdminService {

	@Autowired
	SystemAdminRepository sysAdminRep;
	
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	@Autowired
	HotelService hotelSvc;
	
	
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
			//TODO: registrovati
			break;
			
		case "rent-a-car":
			//TODO: registrovati
			break;
			
		case "system":
			SystemAdmin sysAdmin = new SystemAdmin(admin);
			return sysAdminRep.save(sysAdmin);
			
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
			Hotel hotel = new Hotel(companyInfo, hotelAdmin);
			hotelAdmin.setHotel(hotel);
			
			hotelAdminSvc.save(hotelAdmin);
			return hotelSvc.save(hotel);
			
		case "airline":
			//TODO: registrovati
			break;
			
		case "rent-a-car":
			//TODO: registrovati
			break;
		}
		
		return null;
	}



	
}
