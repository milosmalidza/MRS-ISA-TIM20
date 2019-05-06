package com.webapplication.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.JSONBeans.KeyBean;
import com.webapplication.Service.HotelService;

@RestController
@RequestMapping("/hotelHome")
public class HotelHomeController {
	
	@Autowired
	HotelService hotelSvc;
	
	
	@RequestMapping(
			value="/getNumOfAvailableRooms",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> getNumOfAvailableRooms(@RequestBody KeyBean keyBean) {
		
		return new ResponseEntity<>(hotelSvc.getNumOfAvaiableRooms(keyBean.getKey()), HttpStatus.OK);
		
	}

}
