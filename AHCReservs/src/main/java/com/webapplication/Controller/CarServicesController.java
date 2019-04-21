package com.webapplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
}
