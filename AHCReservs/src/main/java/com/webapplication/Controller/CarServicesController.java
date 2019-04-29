package com.webapplication.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapplication.Model.RentACar;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Repository.RentACarAdminRepository;
import com.webapplication.Service.RentACarService;

@Controller
public class CarServicesController {
	
	@Autowired
	RentACarService rentService;
	
	@Autowired
	RentACarAdminRepository rentAdminRep;
	
	@GetMapping("view-vehicles.html")
	public String returnAdminVehicles(Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		RentACarAdmin admin = rentAdminRep.findByUsername(username);
		
		
		if (admin == null || !admin.getPassword().equals(password)) {
			return "login.html";
		}
		
		RentACar service = rentService.rentACarRep.findOneById(admin.getRentACar().getId());
		
		model.addAttribute("service", service);
		model.addAttribute("vehicles", service.getVehicles());
		
		
		return "view-vehicles.html";
	}
	
	@GetMapping("/edit-rent-a-car-service.html")
	public String returnRentService(Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		RentACarAdmin admin = rentAdminRep.findByUsername(username);
		
		
		if (admin == null || !admin.getPassword().equals(password)) {
			return "login.html";
		}
		
		
		RentACar service = rentService.rentACarRep.findOneById(admin.getRentACar().getId());
		
		model.addAttribute("service", service);
		
		return "edit-rent-a-car-service.html";
		
	}
	
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
