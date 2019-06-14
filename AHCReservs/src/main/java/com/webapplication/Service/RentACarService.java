package com.webapplication.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.webapplication.JSONBeans.MapPinData;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.Rating;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.RentACarBranchOffice;
import com.webapplication.Model.Vehicle;
import com.webapplication.Model.VehicleReservation;
import com.webapplication.Repository.RegisteredUserRepository;
import com.webapplication.Repository.RentACarBranchOfficeRepository;
import com.webapplication.Repository.RentACarRepository;
import com.webapplication.Repository.VehicleRepository;
import com.webapplication.Repository.VehicleReservationRepository;

@Service
public class RentACarService {

	@Autowired
	public RegisteredUserRepository userRepository;
	
	@Autowired
	private RentACarAdminService rentAdminService;
	
	@Autowired
	public RentACarRepository rentACarRep;
	
	@Autowired
	public VehicleRepository vehicleRep;
	
	@Autowired
	public VehicleReservationRepository reservationRep;
	
	@Autowired
	public RentACarBranchOfficeRepository branchRep;
	
	
	public RentACar findOneById(Long id) {
		return rentACarRep.findOneById(id);
	}
	
	public RentACar save(RentACar rentACar) {
		return rentACarRep.save(rentACar);
	}
	
	
	public RentACar findByName(String name) {
		return rentACarRep.findOneByName(name);
	}
	
	public List<RentACar> findAll() {
		return rentACarRep.findAll();
	}
	
	public List<RentACarBranchOffice> findAllRentACarBranchOffices(RentACar rentACar) {
		
		return branchRep.findAllByRentACar(rentACar);
	}
	
	
	
	public String getBranchOffices(String json) throws IOException {
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode dataNode = mapper.readTree(json);
		
		
		RentACar rentACar = rentACarRep.findById(dataNode.get("serviceId").asLong()).get();
		
		List<RentACarBranchOffice> offices = findAllRentACarBranchOffices(rentACar);
		
		List<JsonNode> dataOutput = new ArrayList<JsonNode>();
		
		for (RentACarBranchOffice o : offices) {
			
			JsonNode n = mapper.createObjectNode();
			
			((ObjectNode)n).put("id", o.getId());
			((ObjectNode)n).put("name", o.getName());
			((ObjectNode)n).put("address", o.getAddress());
			
			dataOutput.add(n);
		}
		
		return mapper.writeValueAsString(dataOutput);
	}
	
	public String rateRentACar(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(json);
		JsonNode jsonUser = mapper.readTree(user);
		
		RegisteredUser ruser = userRepository.findByUsername(jsonUser.get("username").asText());
		
		if (ruser == null || !ruser.getPassword().equals(jsonUser.get("password").asText())) {
			return "notLoggedIn";
		}
		
		RentACar service = rentACarRep.findById(jsonNode.get("id").asLong()).get();
		
		VehicleReservation reservation = null;
		Date currentDate = new Date();
		
		for (Vehicle v : service.getVehicles()) {
			for (VehicleReservation r : v.getReservations()) {
				if (r.getUser().getId() == ruser.getId() && currentDate.after(r.getDueDate())) {
					
					reservation = r;
					break;
				}
			}
			
			if (reservation != null) break;
			
		}
		
		if (reservation == null) return "noReservation";
		
		
		
		Rating rating = null;
		
		for (Rating r : service.getRatings()) {
			if (r.getUserId() == ruser.getId()) {
				rating = r;
				break;
			}
		}
		
		if (rating == null) {
			rating = new Rating();
			rating.setUserId(ruser.getId());
			rating.setRating(jsonNode.get("rating").asDouble());
			service.getRatings().add(rating);
		}
		else {
			rating.setUserId(ruser.getId());
			rating.setRating(jsonNode.get("rating").asDouble());
		}
		
		
		service.updateRating();
		
		rentACarRep.save(service);
		
		return "success";
	}
	
	public double getUserRating(RentACar service, RegisteredUser user) {
		
		if (user == null) return 0.0;
		
		for (Rating r : service.getRatings()) {
			if (r.getUserId() == user.getId()) {
				return r.getRating();
			}
		}
		
		return 0.0;
	}
	
	public String saveRentACarPin(MapPinData pinData) {
		
		RentACar rentACar = findOneById(pinData.getCompanyID());
		
		if(rentACar == null) {
			return "";
		}
		
		rentACar.setLatitude(pinData.getLatitude());
		rentACar.setLongitude(pinData.getLongitude());
		
		
		save(rentACar);
		
		return "ok";
		
		
	}
	
	
	public String getRentACar(Long id) {
		
		RentACar rentACar = findOneById(id);
		
		if(rentACar == null) {
			return "";
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode rentACarNode = mapper.createObjectNode();
		
		((ObjectNode)rentACarNode).put("id", rentACar.getId());
		((ObjectNode)rentACarNode).put("name", rentACar.getName());
		((ObjectNode)rentACarNode).put("description", rentACar.getDescription());
		((ObjectNode)rentACarNode).put("address", rentACar.getAddress());
		((ObjectNode)rentACarNode).put("latitude", rentACar.getLatitude());
		((ObjectNode)rentACarNode).put("longitude", rentACar.getLongitude());
		
		
		String rentACarJson = null;
		
		try {
			rentACarJson = mapper.writeValueAsString(rentACarNode);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(rentACarJson);
		
		return rentACarJson;
		
		
	}
	
	public String getVehicleInfo(String json) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(json);
		Long id = Long.parseLong(node.get("id").asText());
		
		Vehicle vehicle = vehicleRep.findById(id).get();
		
		JsonNode vehicleNode = mapper.createObjectNode();
		
		((ObjectNode)vehicleNode).put("name", vehicle.getName());
		((ObjectNode)vehicleNode).put("description", vehicle.getDescription());
		((ObjectNode)vehicleNode).put("type", vehicle.getVehicleType().toString());
		((ObjectNode)vehicleNode).put("doors", vehicle.getNumOfDoors());
		((ObjectNode)vehicleNode).put("people", vehicle.getNumOfSeats());
		((ObjectNode)vehicleNode).put("price", vehicle.getPricePerDay());
		((ObjectNode)vehicleNode).put("status", "ok");
		
		String vehicleJson = mapper.writeValueAsString(vehicleNode);
		
		
		return vehicleJson;
	}
	
	public String checkVehicle(String id) {
		
		Long vid = Long.parseLong(id);
		
		Vehicle vehicle = vehicleRep.findById(vid).get();
		
		if (vehicle == null) return "badRequest";
		
		Date currentDate = new Date();
		List<VehicleReservation> reses = vehicle.getReservations();
		for (VehicleReservation res : reses) {
			if (currentDate.before(res.getDueDate()) || currentDate.before(res.getReservationDate())) {
				return "reserved";
			}
		}
		
		return "ok";
	}
	
	public String removeVehicle(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode dataNode = mapper.readTree(json);
		JsonNode userNode = mapper.readTree(user);
		
		RentACarAdmin admin = rentAdminService.findByUsername(userNode.get("username").asText());
		
		if (admin == null || !admin.getPassword().equals(userNode.get("password").asText())) {
			return "badRequest";
		}
		
		
		Long vehicleId = Long.parseLong(dataNode.get("vehicle").asText());
		Long serviceId = Long.parseLong(userNode.get("serviceId").asText());
		
		Vehicle vehicle = vehicleRep.findById(vehicleId).get();
		RentACar service = rentACarRep.findById(serviceId).get();
		
		System.out.println(service.getVehicles().size());
		service.getVehicles().remove(vehicle);
		System.out.println(service.getVehicles().size());
		//rentACarRep.save(service);
		
		Date currentDate = new Date();
		
		for (VehicleReservation res : vehicle.getReservations()) {
			if (currentDate.before(res.getDueDate())) {
				return "reserved";
			}
		}
		
		vehicleRep.delete(vehicle);
		return "success";
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
					people <= vehicle.getNumOfSeats() &&
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
				String startLocation = jsonNode.get("startLocation").asText();
				String endLocation = jsonNode.get("endLocation").asText();
				
				VehicleReservation reservation = new VehicleReservation();
				reservation.setReservationDate(startDate);
				reservation.setDueDate(endDate);
				reservation.setVehicle(vehicle);
				reservation.setUser(userObject);
				reservation.setStartLocation(startLocation);
				reservation.setEndLocation(endLocation);
				
				
				
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









