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

import com.webapplication.JSONBeans.DateBean;
import com.webapplication.JSONBeans.KeyBean;
import com.webapplication.JSONBeans.RoomReservationBean;
import com.webapplication.Model.HAdditionalService;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.Room;
import com.webapplication.Service.HAdditionalServiceSvc;
import com.webapplication.Service.HotelService;
import com.webapplication.Service.RoomReservationService;
import com.webapplication.Service.RoomService;

@RestController
@RequestMapping("/hotelHome")
public class HotelHomeController {
	
	@Autowired
	HotelService hotelSvc;
	
	@Autowired
	RoomService roomSvc;
	
	@Autowired
	HAdditionalServiceSvc additionalServiceSvc;
	
	@Autowired
	RoomReservationService roomReservSvc;
	
	@RequestMapping(value = "/setHotelRating", method = RequestMethod.POST)
	public ResponseEntity<String> setHotelRating(@RequestParam("json") String json,
												@RequestParam("user") String user) {
		
		try {
			return new ResponseEntity<>(hotelSvc.setHotelRating(json, user), HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/getHotelRating", method = RequestMethod.POST)
	public ResponseEntity<String> getHotelRating(@RequestParam("json") String json,
												@RequestParam("user") String user) {
		
		try {
			return new ResponseEntity<>(hotelSvc.getHotelRating(json, user), HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(
			value="/getHotel",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(@RequestBody KeyBean keyBean) {
		
		return new ResponseEntity<>(hotelSvc.findOne(keyBean.getKey()).get(), HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value="/getRoom",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Room> getRoom(@RequestBody KeyBean keyBean) {
		
		return new ResponseEntity<>(roomSvc.findById(keyBean.getKey()).get(), HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value="/getAvailableRooms",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Room>> getNumOfAvailableRooms(@RequestBody DateBean dateBean) {
		
		return new ResponseEntity<>(hotelSvc.getAvailableRooms(dateBean), HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value="/getHotelAdditionalServices",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<HAdditionalService>> getHotelAdditionalServices(@RequestBody KeyBean hotelKey) {
		
		return new ResponseEntity<>(additionalServiceSvc.findAllForHotel(hotelKey.getKey()), HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value="/reserveRooms",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> reserveRooms(@RequestBody RoomReservationBean reservationData) {
		
		try {
			return new ResponseEntity<>(roomReservSvc.reserveRooms(reservationData) , HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Someone already reserved the room.\nPlease refresh the page" , HttpStatus.CONFLICT);
		}
		
	}
	

}
