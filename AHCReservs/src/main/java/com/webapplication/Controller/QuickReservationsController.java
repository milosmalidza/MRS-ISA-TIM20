package com.webapplication.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.webapplication.Model.RoomReservation;
import com.webapplication.Model.VehicleReservation;
import com.webapplication.Service.RentACarService;
import com.webapplication.Service.RoomReservationService;

@Controller	
public class QuickReservationsController {
	
	@Autowired
	RoomReservationService roomReservSvc;
	
	@Autowired
	RentACarService rentService;
	
	
	@GetMapping("/quick-reservations-display.html")
	public String getReservations(Model model) {
		
		Collection<RoomReservation> roomReservs = roomReservSvc.getQuickReservations();
		Collection<VehicleReservation> vehicleReservs = rentService.getQuickVehicleReservations();
		
		
		model.addAttribute("roomReservations", roomReservs);
		model.addAttribute("vehicleReservations", vehicleReservs);
		
		return "quick-reservations-display.html";
	}

}
