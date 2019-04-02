package com.webapplication.Controller;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.JSONBeans.AdminToRegister;
import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.Company;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Service.AirlineAdminService;
import com.webapplication.Service.HotelAdminService;
import com.webapplication.Service.RentACarAdminService;
import com.webapplication.Service.SystemAdminService;

@RestController
@RequestMapping("/sysadmin")
public class SystemAdminController {
	
	@Autowired
	SystemAdminService sysAdminSvc;
	
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	@Autowired
	AirlineAdminService airlineAdminSvc;
	
	@Autowired
	RentACarAdminService rentACarAdminSvc;
	
	
	@RequestMapping(
			value="/registerCompany",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Company> registerCompany(@RequestBody CompanyInfo companyInfo) {
		
		System.out.println(companyInfo.toString());
		return new ResponseEntity<>(sysAdminSvc.registerCompany(companyInfo), HttpStatus.CREATED);
		//return null;
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
			value="/getAvailableHotelAdmins",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<HotelAdmin> getHotelAdmins() {
		
		return hotelAdminSvc.getAvailableAdmins();
		
	}
	
	
	@RequestMapping(
			value="/getAvailableAirlineAdmins",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<AirlineAdmin> getAirlineAdmins() {
		
		return airlineAdminSvc.getAvailableAdmins();
		
	}
	
	@RequestMapping(
			value="/getAvailableRentACarAdmins",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<RentACarAdmin> getRentACarAdmins() {
		
		return rentACarAdminSvc.getAvailableAdmins();
		
	}
		
	
	/** POSTMAN TEST METHODS */
	@RequestMapping(
			value="/getAdminByUsername",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public HotelAdmin getAdminByUsername(@RequestBody String username) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(username);
		System.out.println(username);
		return hotelAdminSvc.findByUsername(node.get("username").asText());
		
	}
	

}
	