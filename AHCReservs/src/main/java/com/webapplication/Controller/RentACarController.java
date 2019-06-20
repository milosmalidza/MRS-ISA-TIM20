package com.webapplication.Controller;

import java.io.IOException;


import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.JSONBeans.KeyBean;
import com.webapplication.Service.RentACarAdminService;
import com.webapplication.Service.RentACarService;
import com.webapplication.Service.VehicleReservationService;

@RestController
@RequestMapping("/rentACarService")
public class RentACarController {
	
	@Autowired
	private RentACarAdminService rentAdminService;
	
	@Autowired
	private RentACarService rentService;
	
	@Autowired
	private VehicleReservationService vehResServ;
	
	private static final String BADREQUEST = "badRequest";
	
	
	@RequestMapping(value = "/getServiceRating", method = RequestMethod.POST)
	public String getServiceRating(@RequestParam("user") String user) {
		try {
			return rentAdminService.getServiceRating(user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
	}
	
	@RequestMapping(value = "/getVehicleRatings", method = RequestMethod.POST)
	public String getVehicleRatings(@RequestParam("user") String user) {
		try {
			return rentAdminService.getVehicleRatings(user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
	}
	
	
	@RequestMapping(value = "/getReservationsReport", method = RequestMethod.POST)
	public String getReservationsReport(@RequestParam("user") String user) {
		
		try {
			return rentAdminService.getReservationsReport(user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
	}
	
	@RequestMapping(value = "/getReservationsByDay", method = RequestMethod.POST)
	public String getReservationsByDay(@RequestParam("user") String user,
										@RequestParam("json") String json) {
		
		try {
			return rentAdminService.getReservationsByDay(user, json);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
	}
	
	
	@RequestMapping(value = "/getProfitReport", method = RequestMethod.POST)
	public String getProfitReport(@RequestParam("user") String user) {
		
		try {
			return rentAdminService.getProfitReport(user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
	}
	
	@RequestMapping(value = "/removeBranchOffice", method = RequestMethod.POST)
	public String removeBranchOffice(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		try {
			return rentAdminService.removeBranchOffice(json, user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
	}
	
	@RequestMapping(value = "/updateBranchOffice", method = RequestMethod.POST)
	public String updateBranchOffice(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		try {
			return rentAdminService.updateBranchOffice(json, user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
	}
	
	@RequestMapping(value = "/getBranchOffices", method = RequestMethod.POST)
	public String getBranchOffices(@RequestParam("json") String json) {
		
		try {
			return rentService.getBranchOffices(json);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
	}
	
	@RequestMapping(value = "/addOfficeBranch", method = RequestMethod.POST)
	public String addOfficeBranch(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		try {
			return rentAdminService.addOfficeBranch(json, user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
		
	}
	
	@RequestMapping(value = "/rateService", method = RequestMethod.POST)
	public String rateService(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		try {
			return rentService.rateRentACar(json, user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
		
	}
	
	@RequestMapping(value = "/saveEditedVehicle", method = RequestMethod.POST)
	public String saveEditedVehicle(@RequestParam("json") String json,
									@RequestParam("user") String user) {
		
		try {
			return rentAdminService.saveEditedVehicle(json, user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
		
	}
	
	@RequestMapping(value = "/GetVehicleInfo", method = RequestMethod.POST)
	public String getVehicleInfo(@RequestParam("json") String json) {
		
		try {
			return rentService.getVehicleInfo(json);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
		
	}
	
	@RequestMapping(value = "/CheckVehicle", method = RequestMethod.POST)
	public String checkVehicle (@RequestParam("json") String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode status = mapper.readTree(json);
			return rentService.checkVehicle(status.get("id").asText());
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
		
	}
	
	
	@RequestMapping(value = "/RemoveCar", method = RequestMethod.POST)
	public String removeCar(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		try {
			return rentService.removeVehicle(json, user);
		} catch (IOException e) {
			e.printStackTrace();
			return BADREQUEST;
		}
	}
	
	@RequestMapping(value = "/AddCar", method = RequestMethod.POST)
	public String addCar(@RequestParam("json") String json,
						@RequestParam("user") String user) {
		
		try {
			return rentAdminService.AddCar(json, user);
		} catch (NumberFormatException e) {
			return "badNumber";
		} catch (IllegalArgumentException e) {
			return "badType";
		} catch (IOException e) {
			return BADREQUEST;
		}
		
	}
	
	@RequestMapping(value = "/EditService", method = RequestMethod.POST)
	public String editInfo(@RequestParam("json") String json,
			@RequestParam("user") String user) {
		
		try {
			return rentAdminService.ChangeCompanyInfo(json, user);
		} catch (IOException e) {
			return BADREQUEST;
		}
		
		
	}
	
	
	@RequestMapping(value = "/searchVehicles", method = RequestMethod.POST)
	public String searchVehicles(@RequestParam("json") String json) throws JsonProcessingException {
		
		try {
			return rentService.returnSearchResults(json);
		} catch (IOException | ParseException e) {
			return BADREQUEST;
		}
		
		
	}
	
	@RequestMapping(value = "/reserveVehicle", method = RequestMethod.POST)
	public String reserveVehicle(@RequestParam("json") String json, @RequestParam("user") String user) {
		
		try {
			return vehResServ.returnReservationResults(json, user);
		} catch (IOException | ParseException e) {
			
			e.printStackTrace();
			return BADREQUEST;
		}
		
		
	}
	
	

	@RequestMapping(
			value="/getRentACar",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public String getRentACar(@RequestBody KeyBean keyBean) {
		
		return rentService.getRentACar(keyBean.getKey());
		
	}
	
}


















