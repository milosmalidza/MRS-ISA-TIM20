package com.webapplication.Controller;

import java.io.IOException;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.JSONBeans.KeyAndValueBean;
import com.webapplication.JSONBeans.KeyBean;
import com.webapplication.Model.HAdditionalService;
import com.webapplication.Service.RegisteredUserService;
import com.webapplication.Service.RentACarService;
import com.webapplication.Service.RoomReservationService;
import com.webapplication.Service.VehicleReservationService;

@RestController
@RequestMapping("/regUser")
public class RegisteredUserController {
	
	@Autowired
	RoomReservationService roomReservSvc;
	
	@Autowired
	RentACarService rentService;
	
	@Autowired
	RegisteredUserService regUserSvc;
	
	@Autowired
	VehicleReservationService vehResServ;
	
	
	@RequestMapping(value = "/quickVehicleReservation", method = RequestMethod.POST)
	public ResponseEntity<String> quickVehicleReservation(@RequestParam("json") String json,
											@RequestParam("user") String user) {
		
		try {
			return new ResponseEntity<>(vehResServ.quickVehicleReservation(json, user), HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(
			value="/quickRoomReservation",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> quickRoomReservation(@RequestBody KeyAndValueBean data) {
		
		try {
			return new ResponseEntity<>(roomReservSvc.quickReservation(data), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Oops, someone beat you to the reservation.\nPlease refresh the page.", HttpStatus.CONFLICT);
		}
		
	}
	
	
	@RequestMapping(
			value="/getAdditionalServices",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<HAdditionalService>> getAdditionalServices(@RequestBody KeyBean reservationKey) {
		
		return new ResponseEntity<>(regUserSvc.getAdditionalServices(reservationKey), HttpStatus.OK);
		
	}

}
