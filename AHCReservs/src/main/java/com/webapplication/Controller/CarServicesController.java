package com.webapplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.RentACarBranchOffice;
import com.webapplication.Repository.RegisteredUserRepository;
import com.webapplication.Repository.RentACarAdminRepository;
import com.webapplication.Repository.RentACarBranchOfficeRepository;
import com.webapplication.Repository.RentACarRepository;
import com.webapplication.Service.RentACarService;

@Controller
public class CarServicesController {
	
	@Autowired
	RentACarService rentService;
	
	@Autowired
	RentACarAdminRepository rentAdminRep;
	
	@Autowired
	RentACarBranchOfficeRepository branchRep;
	
	@Autowired
	public RentACarRepository rentACarRep;
	
	@Autowired
	public RegisteredUserRepository userRepository;
	
	private static final String loginHtml = "login.html";
	
	@GetMapping("add-rent-a-car-vehicle.html")
	public String returnAdminAddVehicle(Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		RentACarAdmin admin = rentAdminRep.findByUsername(username);
		
		if (admin == null || !admin.getPassword().equals(password)) {
			return loginHtml;
		}
		
		RentACar service = rentService.rentACarRep.findOneById(admin.getRentACar().getId());
		
		model.addAttribute("service", service);
		
		return "add-rent-a-car-vehicle.html";
	}
	
	@GetMapping("view-vehicles.html")
	public String returnAdminVehicles(Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		RentACarAdmin admin = rentAdminRep.findByUsername(username);
		
		
		if (admin == null || !admin.getPassword().equals(password)) {
			return loginHtml;
		}
		
		RentACar service = rentService.rentACarRep.findOneById(admin.getRentACar().getId());
		
		model.addAttribute("service", service);
		model.addAttribute("vehicles", service.getVehicles());
		
		
		return "view-vehicles.html";
	}
	
	@GetMapping("/car-services.html")
	public String returnCarServices(Model model) {
		
		
		List<RentACar> services = rentService.findAll();
		model.addAttribute("services", services);
		
		return "car-services.html";
	}
	
	
	@GetMapping("/edit-rent-a-car-service.html")
	public String returnRentService(Model model,
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		
		RentACarAdmin admin = rentAdminRep.findByUsername(username);
		
		
		if (admin == null || !admin.getPassword().equals(password)) {
			return loginHtml;
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
	public String vehicleSearch(@RequestParam("id") String id,
								@RequestParam("u") String username,
								Model model) {
		RentACar service = rentACarRep.findOneById(Long.parseLong(id));
		RegisteredUser user = userRepository.findByUsername(username);
		List<RentACarBranchOffice> offices = branchRep.findAllByRentACar(service);
		
		double rating = rentService.getUserRating(service, user);
		
		
		model.addAttribute("service", service);
		model.addAttribute("offices", offices);
		model.addAttribute("rating", rating);
		return "vehicle-search.html";
	}
	
}
