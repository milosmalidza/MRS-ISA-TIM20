package com.webapplication.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.AdminToRegister;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.HotelAdmin;

@Service
public class SystemAdminService {

	@Autowired
	HotelAdminService hotelAdminSvc;
	
	public AppUser registerAdmin(AdminToRegister admin) {
		
		if(admin.getCompanyType() == null) {
			return null;
		}
		
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
			
		default:
			break;
			
		}
		
		return null;
	}
}
