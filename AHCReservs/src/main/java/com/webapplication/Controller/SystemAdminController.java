package com.webapplication.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.Company;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Service.RegisteredUserService;


@RestController
@RequestMapping("/sysadmin")
public class SystemAdminController {
	
	
	//TODO: @Autowired service iz kog pozivam metode
	public static List<Company> companies = new ArrayList<Company>();
	
	@Autowired
	RegisteredUserService regUserSvc;
	
	
	@RequestMapping(
			value="/getRegisteredUsers",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<RegisteredUser> getRegisteredUsers() {
		
		return regUserSvc.findAll();
	}
	
	@RequestMapping(
			value="/getAvaiableAdmins",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<AppUser> getAvailableAdmins() {
		
		return null;
	}
	
	
	
	@RequestMapping(
			value="/registerCompany",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerCompany(@RequestBody CompanyInfo companyInfo) {
		
		if(companyExists(companyInfo.getName())) {
			return new ResponseEntity<String>("Company already exists", HttpStatus.CREATED);
		}
		
		
		return null;
	}
	
	
	public boolean companyExists(String companyName) {
		
		for(Company c: companies) {
			
			if(c.getName().equalsIgnoreCase(companyName)) {
				return true;
			}
		}
		
		return false;
	}

}
