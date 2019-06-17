package com.webapplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.JSONBeans.MapPinData;
import com.webapplication.Model.Hotel;
import com.webapplication.Service.HotelService;
import com.webapplication.Service.RentACarService;

@RestController
@RequestMapping("/map")
public class MapController {

	@Autowired
	HotelService hotelSvc;
	
	@Autowired
	RentACarService rentCarSvc;
	
	@RequestMapping(value = "/saveHotelPin",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> saveHotelPin(@RequestBody MapPinData pinData) {
		
		return new ResponseEntity<>(hotelSvc.saveHotelPin(pinData), HttpStatus.ACCEPTED);
		
	}
	
	@RequestMapping(value = "/saveRentACarPin",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveRentACarPin(@RequestBody MapPinData pinData) {
		
		return rentCarSvc.saveRentACarPin(pinData);
		
	}
	
}
