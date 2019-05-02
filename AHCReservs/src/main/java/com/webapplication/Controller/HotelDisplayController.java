package com.webapplication.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.webapplication.Model.Hotel;
import com.webapplication.Service.HotelService;

@Controller
public class HotelDisplayController {
	
	@Autowired
	HotelService hotelSvc;
	
	
	@GetMapping("/hotel-display.html")
	public String returnCarServices(Model model) {
		
		
		List<Hotel> hotels = hotelSvc.findAll();
		model.addAttribute("services", hotels);
		
		return "hotel-display.html";
	} 

}
