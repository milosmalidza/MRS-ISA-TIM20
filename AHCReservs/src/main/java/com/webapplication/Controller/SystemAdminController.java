package com.webapplication.Controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.ws.spi.http.HttpContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.Model.Admin;
import com.webapplication.Model.Airline;
import com.webapplication.Model.Company;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.RentACar;


@RestController
@RequestMapping("/sysadmin")
public class SystemAdminController {
	
	
	//TODO: @Autowired service iz kog pozivam metode
	
	public List<Admin> admins = new ArrayList<Admin>();
	public static List<Company> companies = new ArrayList<Company>();
	
	HttpContext context;
	
	@RequestMapping(
			value="/getAvaiableAdmins",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<Admin> getAvailableAdmins() {
		
		admins.clear();
		
		admins.add(new Admin("admin1", "", "name1", "surname1", ""));
		admins.add(new Admin("admin2", "", "name2", "surname2", ""));
		admins.add(new Admin("admin3", "", "name3", "surname3", ""));
		
		
		return admins;
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
		
		Company newCompany = null;
		boolean flag = false;
		
		//instantiate company reference to the selected admin
		for (Admin admin : admins) {
			if(admin.getUsername().equals(companyInfo.getAdminUsername())) {
				
				switch(companyInfo.getType()) {
				
				case "airline":
					newCompany = new Airline(companyInfo.getName());
					admin.setCompany(newCompany);
					flag = true;
					break;
					
				case "hotel":
					newCompany = new Hotel(companyInfo.getName());
					admin.setCompany(newCompany);
					flag = true;
					break;
					
				case "rent-a-car":
					newCompany = new RentACar(companyInfo.getName());
					admin.setCompany(newCompany);
					flag = true;
					break;
				}
				
			}
			
			if(flag) {
				break;
			}
			
	
		}
		
		if(newCompany != null) {
			companies.add(newCompany);
		}
		
		return new ResponseEntity<String>("Successfully registered", HttpStatus.CREATED);
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
