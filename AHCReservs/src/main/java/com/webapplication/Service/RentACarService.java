package com.webapplication.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.Vehicle;
import com.webapplication.Repository.RentACarRepository;
import com.webapplication.Repository.VehicleRepository;

@Service
public class RentACarService {

	
	@Autowired
	public RentACarRepository rentACarRep;
	
	@Autowired
	public VehicleRepository vehicleRep;
	
	
	public RentACar save(RentACar rentACar) {
		return rentACarRep.save(rentACar);
	}
	
	
	public RentACar findByName(String name) {
		return rentACarRep.findOneByName(name);
	}
	
	public List<RentACar> findAll() {
		return rentACarRep.findAll();
	}
	
	
	public String returnSearchResults(String json) throws IOException {
		
		List<Vehicle> allVehicles = vehicleRep.findAll();
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(json);
		
		
		String type = node.get("type").textValue();
		int doors = (int) node.get("doors").numberValue();
		int people = (int) node.get("people").numberValue();
		
		List<JsonNode> vehicles = new ArrayList<JsonNode>();
		
		for (Vehicle vehicle : allVehicles) {
			
			if (type.equals(vehicle.getVehicleType().toString()) &&
					doors == vehicle.getNumOfDoors() &&
					people == vehicle.getNumOfSeats()) {
				
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
	
	public String returnReservationResults(String json, String user) {
		
		System.out.println(user);
		System.out.println(json);
		
		
		return "ssss";
		
	}
	
	
	
}









