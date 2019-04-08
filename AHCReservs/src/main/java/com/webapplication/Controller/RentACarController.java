package com.webapplication.Controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.Service.RentACarAdminService;

@RestController
@RequestMapping("/rentACarService")
public class RentACarController {
	
	@Autowired
	
	private RentACarAdminService rentService;
	
	
	@RequestMapping(value = "/AddCar", method = RequestMethod.POST)
	public String addCar(@RequestParam("json") String json) {
		
		try {
			rentService.AddCar(json);
		} catch (IOException e) {

			return "error parsing data";
		}
		return json;
	}
	
	
}
