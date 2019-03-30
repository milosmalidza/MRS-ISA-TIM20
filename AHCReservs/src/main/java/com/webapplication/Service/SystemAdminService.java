package com.webapplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.AdminToRegister;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.SystemAdmin;
import com.webapplication.Repository.SystemAdminRepository;

@Service
public class SystemAdminService {

	@Autowired
	SystemAdminRepository sysAdminRep;
	
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	
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
}
