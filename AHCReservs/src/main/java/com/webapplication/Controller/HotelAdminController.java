package com.webapplication.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.JSONBeans.HotelData;
import com.webapplication.JSONBeans.HotelServiceData;
import com.webapplication.JSONBeans.KeyBean;
import com.webapplication.JSONBeans.RoomData;
import com.webapplication.JSONBeans.Username;
import com.webapplication.Model.HAdditionalService;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelServiceType;
import com.webapplication.Model.Room;
import com.webapplication.Model.RoomType;
import com.webapplication.Service.HotelAdminService;
import com.webapplication.Service.HotelService;
import com.webapplication.Service.RoomService;

@RestController
@RequestMapping("/hotelAdmin")
public class HotelAdminController {

	@Autowired
	HotelAdminService hotelAdminSvc;
	
	@Autowired
	HotelService hotelSvc;
	
	@Autowired
	RoomService roomSvc;
	
	@RequestMapping(
			value="/getHotel",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(@RequestBody Username username) {
		
		System.out.println(username.getUsername());
		return new ResponseEntity<>(hotelAdminSvc.getHotel(username.getUsername()), HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value="/updateProfile",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> updateProfile(@RequestBody HotelData hotelData) {
		
		return new ResponseEntity<>(hotelSvc.updateProfile(hotelData), HttpStatus.ACCEPTED);
	}
	
	
	@RequestMapping(
			value="/addRoom",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Room> addRoom(@RequestBody RoomData roomData) {
		
		return new ResponseEntity<>(hotelSvc.addRoom(roomData), HttpStatus.CREATED);
	}
	
	
	@RequestMapping(
			value="/removeRoom",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> removeRoom(@RequestBody KeyBean keyBean) {
		
		return new ResponseEntity<>(hotelSvc.removeRoom(keyBean.getKey()), HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value="/editRoom",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> editRoom(@RequestBody RoomData roomData) {
		
		return new ResponseEntity<>(hotelSvc.editRoom(roomData), HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(
			value="/getRoomTypes",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RoomType>> getRoomTypes() {
		
		return new ResponseEntity<>(roomSvc.getRoomTypes(), HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value="/getServiceTypes",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<HotelServiceType>> getServiceTypes() {
		
		return new ResponseEntity<>(hotelSvc.getServiceTypes(), HttpStatus.OK);
		
	}
	
	
	@RequestMapping(
			value="/addService",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HAdditionalService> addService(@RequestBody HotelServiceData serviceData) {
		
		return new ResponseEntity<>(hotelSvc.addService(serviceData), HttpStatus.ACCEPTED);
		
	}
	
	
}
