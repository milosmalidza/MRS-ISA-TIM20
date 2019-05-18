package com.webapplication.Controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.Model.Vehicle;
import com.webapplication.Service.RentACarAdminService;
import com.webapplication.Service.RentACarService;

@RestController
@RequestMapping("/rentACarService")
public class RentACarController {
	
	@Autowired
	private RentACarAdminService rentAdminService;
	
	@Autowired
	private RentACarService rentService;
	
	
	@RequestMapping(value = "/addOfficeBranch", method = RequestMethod.POST)
	public String addOfficeBranch(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		return "sdsad";
	}
	
	@RequestMapping(value = "/rateService", method = RequestMethod.POST)
	public String rateService(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		try {
			return rentService.rateRentACar(json, user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "badRequest";
		}
		
	}
	
	@RequestMapping(value = "/saveEditedVehicle", method = RequestMethod.POST)
	public String saveEditedVehicle(@RequestParam("json") String json,
									@RequestParam("user") String user) {
		
		try {
			return rentAdminService.saveEditedVehicle(json, user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "badRequest";
		}
		
	}
	
	@RequestMapping(value = "/GetVehicleInfo", method = RequestMethod.POST)
	public String getVehicleInfo(@RequestParam("json") String json) {
		
		try {
			return rentService.getVehicleInfo(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "badRequest";
		}
		
	}
	
	@RequestMapping(value = "/CheckVehicle", method = RequestMethod.POST)
	public String checkVehicle (@RequestParam("json") String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode status = mapper.readTree(json);
			return rentService.checkVehicle(status.get("id").asText());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "badRequest";
		}
		
	}
	
	
	@RequestMapping(value = "/RemoveCar", method = RequestMethod.POST)
	public String removeCar(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		try {
			return rentService.removeVehicle(json, user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "badRequest";
		}
	}
	
	@RequestMapping(value = "/AddCar", method = RequestMethod.POST)
	public String addCar(@RequestParam("json") String json) {
		
		try {
			return rentAdminService.AddCar(json);
		} catch (NumberFormatException e) {
			return "badNumber";
		} catch (IllegalArgumentException e) {
			return "badType";
		} catch (IOException e) {
			return "badRequest";
		}
		
	}
	
	@RequestMapping(value = "/EditService", method = RequestMethod.POST)
	public String editInfo(@RequestParam("json") String json,
			@RequestParam("user") String user) {
		
		try {
			return rentAdminService.ChangeCompanyInfo(json, user);
		} catch (IOException e) {
			return "badRequest";
		}
		
		
	}
	
	
	@RequestMapping(value = "/searchVehicles", method = RequestMethod.POST)
	public String searchVehicles(@RequestParam("json") String json) throws JsonProcessingException {
		
		try {
			return rentService.returnSearchResults(json);
		} catch (IOException | ParseException e) {
			return "badRequest";
		}
		
		
	}
	
	@RequestMapping(value = "/reserveVehicle", method = RequestMethod.POST)
	public String reserveVehicle(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		try {
			return rentService.returnReservationResults(json, user);
		} catch (IOException | ParseException e) {
			
			e.printStackTrace();
			return "badRequest";
		}
		
		
	}
	
}


















