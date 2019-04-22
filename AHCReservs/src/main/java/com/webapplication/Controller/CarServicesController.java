package com.webapplication.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapplication.Model.RentACar;
import com.webapplication.Service.RentACarService;

@Controller
public class CarServicesController {
	
	@Autowired
	RentACarService rentService;
	
	@GetMapping("/cars.html")
	public String returnCars(Model model) {
		
		model.addAttribute("services", rentService.findAll());
		return "cars.html";
	}
	
	
	@GetMapping("/vehicle-search.html")
	public String vehicleSearch(@RequestParam("id") String id, Model model) {
		RentACar service = rentService.rentACarRep.findOneById(Long.parseLong(id));
		
		
		model.addAttribute("service", service);
		return "vehicle-search.html";
	}
	
}
