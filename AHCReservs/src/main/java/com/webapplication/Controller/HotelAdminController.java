package com.webapplication.Controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.Model.Company;
import com.webapplication.Model.Hotel;

@RestController
@RequestMapping("/hotelAdmin")
public class HotelAdminController {

	
	// ** DUMMY METHOD **//
	@RequestMapping(
			value="/getCompany",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Company getCompany() {
		
		if(SystemAdminController.companies.size() > 0) {
			
			return getHotel();
		}
		
		return null;
	}
	
	
	public Company getHotel() {
		
		for(Company c: SystemAdminController.companies) {
			
			if(c instanceof Hotel) {
				return c;
			}
			
		}
		
		return null;
		
	}
	
}
