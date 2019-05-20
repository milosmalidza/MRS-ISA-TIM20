package com.webapplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapplication.Model.Hotel;
import com.webapplication.Service.HotelService;

@Controller
public class HotelDisplayController {
	
	@Autowired
	HotelService hotelSvc;
	
	
	@GetMapping("/hotels-display.html")
	public String returnHotelServices(Model model) {
		
		
		List<Hotel> hotels = hotelSvc.findAll();
		model.addAttribute("services", hotels);
		
		return "hotels-display.html";
	} 
	
	@GetMapping("/hotel-home.html")
	public String hotelSearch(@RequestParam("id") String id, Model model) {
		
		Hotel service = hotelSvc.findOne(Long.parseLong(id)).get();
		int availableRooms = service.getRooms().size();
				
		
		model.addAttribute("service", service);
		model.addAttribute("availableRooms", availableRooms);
		
		return "hotel-home.html";
	}

}
