package com.webapplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.JSONBeans.KeyAndValueBean;
import com.webapplication.Service.RoomReservationService;

@RestController
@RequestMapping("/regUser")
public class RegisteredUserController {
	
	@Autowired
	RoomReservationService roomReservSvc;
	
	@RequestMapping(
			value="/quickRoomReservation",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> quickRoomReservation(@RequestBody KeyAndValueBean data) {
		
		return new ResponseEntity<>(roomReservSvc.quickReservation(data), HttpStatus.OK);
		
	}

}
