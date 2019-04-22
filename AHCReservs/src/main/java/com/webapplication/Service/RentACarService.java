package com.webapplication.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.Vehicle;
import com.webapplication.Model.VehicleReservation;
import com.webapplication.Repository.RegisteredUserRepository;
import com.webapplication.Repository.RentACarRepository;
import com.webapplication.Repository.VehicleRepository;
import com.webapplication.Repository.VehicleReservationRepository;

@Service
public class RentACarService {

	@Autowired
	public RegisteredUserRepository userRepository;
	
	@Autowired
	public RentACarRepository rentACarRep;
	
	@Autowired
	public VehicleRepository vehicleRep;
	
	@Autowired
	public VehicleReservationRepository reservationRep;
	
	
	public RentACar save(RentACar rentACar) {
		return rentACarRep.save(rentACar);
	}
	
	
	public RentACar findByName(String name) {
		return rentACarRep.findOneByName(name);
	}
	
	public List<RentACar> findAll() {
		return rentACarRep.findAll();
	}
	
	
	
	public String returnSearchResults(String json) throws IOException, ParseException {
		
		List<Vehicle> allVehicles = vehicleRep.findAll();
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(json);
		
		
		String type = node.get("type").textValue();
		int doors = (int) node.get("doors").numberValue();
		int people = (int) node.get("people").numberValue();
		Long id = Long.parseLong(node.get("companyid").asText());
		
		List<JsonNode> vehicles = new ArrayList<JsonNode>();
		
		for (Vehicle vehicle : allVehicles) {
			
			if (type.equals(vehicle.getVehicleType().toString()) &&
					doors == vehicle.getNumOfDoors() &&
					people == vehicle.getNumOfSeats() &&
					id == vehicle.getRentACar().getId()) {
				
				boolean isReserved = false;
				List<VehicleReservation> reservations = vehicle.getReservations();
				for (VehicleReservation reservation : reservations) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
					Date startDate = format.parse(node.get("startDate").asText());
					Date endDate = format.parse(node.get("endDate").asText());
					
					if (!(startDate.before(reservation.getReservationDate()) || startDate.after(reservation.getDueDate())) ||
							!(endDate.before(reservation.getReservationDate()) || endDate.after(reservation.getDueDate()))) {
						System.out.println("skip");
						isReserved = true;
						break;
					}
				}
				
				if (isReserved) {
					continue;
				}
					
				
				JsonNode n = mapper.createObjectNode();
				
				((ObjectNode)n).put("id", vehicle.getId());
				((ObjectNode)n).put("name", vehicle.getName());
				((ObjectNode)n).put("description", vehicle.getDescription());
				((ObjectNode)n).put("numOfSeats", vehicle.getNumOfSeats());
				((ObjectNode)n).put("numOfDoors", vehicle.getNumOfDoors());
				((ObjectNode)n).put("vehicleType", vehicle.getVehicleType().toString());
				((ObjectNode)n).put("pricePerDay", vehicle.getPricePerDay());
				
				vehicles.add(n);
				
			}
			
		}
		
		String returnJson = mapper.writeValueAsString(vehicles);
		
		return returnJson;
	}
	
	public String returnReservationResults(String json, String user) throws IOException, ParseException {
		
		if (user == null || user.equals("null")) {
			return "notLoggedIn";
		}
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		JsonNode jsonNode = mapper.readTree(json);
		
		RegisteredUser userObject = userRepository.findByUsername(userNode.get("username").asText());
		if (userObject == null) {
			return "notLoggedIn";
		}
		else if (!userObject.getPassword().equals(userNode.get("password").asText())) {
			return "badRequest";
		}
		
		List<Vehicle> vehicles = vehicleRep.findAll();
		
		Long id = Long.parseLong(jsonNode.get("id").asText());
		for (Vehicle vehicle : vehicles) {
			if (vehicle.getId() == id) {
				
				
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
				
				Date startDate = format.parse(jsonNode.get("startDate").asText());
				Date endDate = format.parse(jsonNode.get("endDate").asText());
				
				VehicleReservation reservation = new VehicleReservation();
				reservation.setReservationDate(startDate);
				reservation.setDueDate(endDate);
				reservation.setVehicle(vehicle);
				reservation.setUser(userObject);
				
				
				
				reservationRep.save(reservation);
				
				return "success";
			}
		}
		
		return "fail";
		
	}


	public String getServiceName(List<RentACar> findAll) {
		
		return null;
	}
	
	
	
}









