package com.webapplication.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.webapplication.Model.RoomReservation;
import com.webapplication.Service.RoomReservationService;

@Controller	
public class QuickReservationsController {
	
	@Autowired
	RoomReservationService roomReservSvc;
	
	
	@GetMapping("/quick-reservations-display.html")
	public String getReservations(Model model) {
		
		Collection<RoomReservation> roomReservs = roomReservSvc.getQuickReservations();
		//TODO: dobaviti sve brze rezervacije za vozila
		
		model.addAttribute("roomReservations", roomReservs);
		//TODO: postaviti atribut za rezervacije vozila
		
		return "quick-reservations-display.html";
	}

}
