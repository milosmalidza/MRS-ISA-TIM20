package com.webapplication.Controller;

import java.io.IOException;
import java.text.ParseException;
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
import org.springframework.web.servlet.ModelAndView;

import com.webapplication.JSONBeans.UserBean;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Service.AirlineAdminService;
import com.webapplication.Service.ConfirmationTokenService;
import com.webapplication.Service.HotelAdminService;
import com.webapplication.Service.HotelService;
import com.webapplication.Service.MultipleService;
import com.webapplication.Service.RentACarAdminService;
import com.webapplication.Service.RentACarService;
import com.webapplication.Service.SystemAdminService;

@RestController
@RequestMapping("/sysadmin")
public class SystemAdminController {
	
	@Autowired
	SystemAdminService sysAdminSvc;
	
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	@Autowired
	AirlineAdminService airlineAdminSvc;
	
	@Autowired
	RentACarAdminService rentACarAdminSvc;
	
	@Autowired
	ConfirmationTokenService confirmTokenSvc;
	
	@Autowired
	MultipleService mulSvc;
	
	@Autowired
	HotelService hotelSvc;
	
	@Autowired
	RentACarService racSvc;
	
	
	@RequestMapping(
			value="/registerCompany",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerCompany(@RequestBody CompanyInfo companyInfo) {
		
		return new ResponseEntity<>(sysAdminSvc.registerCompany(companyInfo), HttpStatus.CREATED);
		
	}
	
	@RequestMapping(
			value="/registerAdmin",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> registerAdmin(@RequestBody UserBean admin) {
		
		return new ResponseEntity<>(sysAdminSvc.registerAdmin(admin), HttpStatus.CREATED);

	}
	
	
	/* GET methods for getting available admins */
	
	
	@RequestMapping(
			value="/getAvailableHotelAdmins",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<HotelAdmin> getHotelAdmins() {
		
		return hotelAdminSvc.getAvailableAdmins();
		
	}
	
	
	@RequestMapping(
			value="/getAvailableAirlineAdmins",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<AirlineAdmin> getAirlineAdmins() {
		
		return airlineAdminSvc.getAvailableAdmins();
		
	}
	
	@RequestMapping(
			value="/getAvailableRentACarAdmins",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<RentACarAdmin> getRentACarAdmins() {
		
		return rentACarAdminSvc.getAvailableAdmins();
		
	}
	
	
	/* Methods for admin registration confirmations */
	
	@RequestMapping(value = "/confirmHotelAdminRegistration", method = RequestMethod.GET)
	public ModelAndView confirmHotelAdminRegistration(@RequestParam("token") String token) {
		
		return new ModelAndView(confirmTokenSvc.confirmHotelAdminRegistration(token));
		
	}
	
	@RequestMapping(value = "/confirmAirlineAdminRegistration", method = RequestMethod.GET)
	public ModelAndView confirmHotelAirlineRegistration(@RequestParam("token") String token) {
		
		return new ModelAndView(confirmTokenSvc.confirmAirlineAdminRegistration(token));
		
	}
	
	@RequestMapping(value = "/confirmRentACarAdminRegistration", method = RequestMethod.GET)
	public ModelAndView confirmRentACarAdminRegistration(@RequestParam("token") String token) {
		
		return new ModelAndView(confirmTokenSvc.confirmRentACarAdminRegistration(token));
		
	}
	
	
	@RequestMapping(value = "/confirmSysAdminRegistration", method = RequestMethod.GET)
	public ModelAndView confirmSysAdminRegistration(@RequestParam("token") String token) {
		
		return new ModelAndView(confirmTokenSvc.confirmSysAdminRegistration(token));
		
	}
	
	
	@RequestMapping(
			value="/companyExists",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public boolean companyExists(@RequestBody String companyName) throws IOException {
		
		return mulSvc.companyExists(companyName, true);
	}
	
	
	@RequestMapping(
			value="/addAdminsToCompany",
			method=RequestMethod.POST,
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addAdminsToCompany(@RequestBody CompanyInfo companyInfo) {
		
		return new ResponseEntity<>(sysAdminSvc.addAdminsToCompany(companyInfo), HttpStatus.ACCEPTED);
		
	}
		
	
	@RequestMapping(
			value="/getHotels",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Hotel>> getHotels() {
		
		return new ResponseEntity<>(hotelSvc.findAll(), HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value="/getRentACarServices",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayNode> getRentACarServices() {
		
		return new ResponseEntity<>(racSvc.getRentACarNameAndId(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/getRentACarServiceBranches", method = RequestMethod.POST)
	public ResponseEntity<String> getRentACarServiceBranches(@RequestParam("json") String json) {
		
		try {
			return new ResponseEntity<>(racSvc.getBranchOffices(json), HttpStatus.OK);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/returnServiceCars", method = RequestMethod.POST)
	public ResponseEntity<String> returnServiceCars(@RequestParam("json") String json) {
		
			try {
				return new ResponseEntity<>(racSvc.returnServiceCars(json), HttpStatus.OK);
			} catch (IOException | ParseException e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		
	}
	
	@RequestMapping(value = "/makeVehicleReservations", method = RequestMethod.POST)
	public ResponseEntity<String> makeVehicleReservations(@RequestParam("json") String json,
			@RequestParam("user") String user) {
		try {
			return new ResponseEntity<>(racSvc.makeVehicleReservations(json, user), HttpStatus.OK);
		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	

}
	