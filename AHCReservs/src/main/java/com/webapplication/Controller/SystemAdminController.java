package com.webapplication.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.JSONBeans.AdminToRegister;
import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Service.HotelAdminService;
import com.webapplication.Service.SystemAdminService;

@RestController
@RequestMapping("/sysadmin")
public class SystemAdminController {
	
	@Autowired
	SystemAdminService sysAdminSvc;
	
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	//@Autowired
	//HotelAdminService 
	
	@RequestMapping(
			value="/registerCompany",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerCompany(@RequestBody CompanyInfo companyInfo) {
		
		//TODO: pozvati metodu iz servisa
		return null;
	}
	
	@RequestMapping(
			value="/registerAdmin",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AppUser> registerAdmin(@RequestBody AdminToRegister admin) {
		
		return new ResponseEntity<>(sysAdminSvc.registerAdmin(admin), HttpStatus.CREATED);

	}
	
	@RequestMapping(
			value="/getHotelAdmins",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<HotelAdmin> getHotelAdmins() {
		
		return hotelAdminSvc.findAll();
		
	}
	
	

}
	