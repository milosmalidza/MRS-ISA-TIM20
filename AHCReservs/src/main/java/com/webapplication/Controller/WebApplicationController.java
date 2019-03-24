package com.webapplication.Controller;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.Model.Admin;
import com.webapplication.Model.Airline;
import com.webapplication.Model.Company;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.Vehicle;
import com.webapplication.Model.VehicleType;



@RestController
public class WebApplicationController {
	
	
	public List<Vehicle> vehicles = new ArrayList<Vehicle>();
	public List<Admin> admins = new ArrayList<Admin>();
	public List<Company> companies = new ArrayList<Company>();
	
	@RequestMapping(value = "/searchVehicles", method = RequestMethod.POST)
	public String ajaxtest(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("doors") int doors,
			@RequestParam("people") int people, @RequestParam("type") String type) throws JsonProcessingException {
		
		Vehicle v1 = new Vehicle("1", "Mazda 3 2019", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 5, 5, VehicleType.Hatchback, false, 19, false);
		Vehicle v2 = new Vehicle("2", "Volkswagen Golf GTI 2019", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 5, 5, VehicleType.Hatchback, false, 14, false);
		Vehicle v3 = new Vehicle("3", "Toyota CHR ", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 5, 5, VehicleType.Sedan, false, 18, false);
		Vehicle v4 = new Vehicle("4", "Dodge Charger 2019", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 5, 5, VehicleType.Coupe, false, 22, false);
		Vehicle v5 = new Vehicle("5", "Acura MDX 2019", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 5, 5, VehicleType.Sedan, false, 21, false);
		Vehicle v6 = new Vehicle("6", "Jeep Grand Cherokee 2019", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 5, 5, VehicleType.SUV, false, 26, false);
		Vehicle v7 = new Vehicle("7", "Ford Mustang 2019", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.", 5, 5, VehicleType.Sedan, false, 30, false);
		
		vehicles.clear();
		vehicles.add(v1);
		vehicles.add(v2);
		vehicles.add(v3);
		vehicles.add(v4);
		vehicles.add(v5);
		vehicles.add(v6);
		vehicles.add(v7);
		
		List<Vehicle> temp = new ArrayList<Vehicle>();
		
		
		for (Vehicle v : vehicles) {
			if (!v.isArchived() && v.getNumOfDoors() == doors && v.getNumOfSeats() == people && v.getVehicleType().toString().equals(type)) {
				temp.add(v);
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(temp);
		
		System.out.println(json);
		
		
		return json;
	}
	
	
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
