package com.webapplication.Controller;

import java.io.IOException;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.Model.Vehicle;
import com.webapplication.Service.RentACarAdminService;

@RestController
@RequestMapping("/rentACarService")
public class RentACarController {
	
	@Autowired
	private RentACarAdminService rentService;
	
	
	@RequestMapping(value = "/AddCar", method = RequestMethod.POST)
	public String addCar(@RequestParam("json") String json) {
		
		try {
			return rentService.AddCar(json);
		} catch (NumberFormatException e) {
			return "badNumber";
		} catch (IllegalArgumentException e) {
			return "badType";
		} catch (IOException e) {
			return "badRequest";
		}
		
	}
	
	@RequestMapping(value = "/EditService", method = RequestMethod.POST)
	public String editInfo(@RequestParam("json") String json) {
		
		try {
			return rentService.ChangeCompanyInfo(json);
		} catch (IOException e) {
			return "badRequest";
		}
		
		
	}
	
	
	@RequestMapping(value = "/searchVehicles", method = RequestMethod.POST)
	public String ajaxtest(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("doors") int doors,
			@RequestParam("people") int people, @RequestParam("type") String type) throws JsonProcessingException {
		
		
		
		
		
		return "success";
	}
	
	
	
}
