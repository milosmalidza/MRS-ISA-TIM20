package com.webapplication.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.webapplication.JSONBeans.KeyBean;
import com.webapplication.Model.HAdditionalService;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.RoomReservation;
import com.webapplication.Model.VehicleReservation;
import com.webapplication.Repository.RegisteredUserRepository;

@Service
public class RegisteredUserService {

	@Autowired
	private RegisteredUserRepository registeredUserRep;
	
	@Autowired
	RoomReservationService roomReservSvc;
	
	@Autowired
	private RentACarService rentACarService;
	
	
	public String cancelHotelReservation(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		
		RegisteredUser ruser = registeredUserRep.findByUsername(userNode.get("username").asText());
		
		if (ruser == null) return "badRequest";
		if (!ruser.getPassword().equals(userNode.get("password").asText())) return "badRequest";
		
		JsonNode jsonNode = mapper.readTree(json);
		
		RoomReservation reservation = roomReservSvc.roomReservRep.findById(jsonNode.get("id").asLong()).get();
		
		Date currentDate = new Date();
		long milis = reservation.getCheckIn().getTime() - currentDate.getTime();
		long days = TimeUnit.DAYS.convert(milis, TimeUnit.MILLISECONDS);
		if (days <= 2) {
			return "notAllowed";
		}
		
		roomReservSvc.roomReservRep.delete(reservation);
		
		
		return "success";
	}
	
	public String cancelVehicleReservation(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		
		RegisteredUser ruser = registeredUserRep.findByUsername(userNode.get("username").asText());
		
		if (ruser == null) return "badRequest";
		if (!ruser.getPassword().equals(userNode.get("password").asText())) return "badRequest";
		
		JsonNode jsonNode = mapper.readTree(json);
		
		VehicleReservation reservation = rentACarService.reservationRep.findOneById(jsonNode.get("id").asLong());
		
		Date currentDate = new Date();
		long milis = reservation.getReservationDate().getTime() - currentDate.getTime();
		long days = TimeUnit.DAYS.convert(milis, TimeUnit.MILLISECONDS);
		if (days <= 2) {
			return "notAllowed";
		}
		
		rentACarService.reservationRep.delete(reservation);
		
		
		return "success";
	}
	
	public String getUserHotelReservations(String user) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		
		RegisteredUser ruser = registeredUserRep.findByUsername(userNode.get("username").asText());
		
		Set<RoomReservation> reservations = ruser.getHotelReservations();
		List<JsonNode> jsonReservations = new ArrayList<JsonNode>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date currentDate = new Date();
		for (RoomReservation r : reservations) {
			JsonNode node = mapper.createObjectNode();
			
			((ObjectNode)node).put("id", r.getId());
			((ObjectNode)node).put("hotelName", r.getHotel().getName());
			((ObjectNode)node).put("room", r.getRoom().getNumber());
			((ObjectNode)node).put("startDate", dateFormat.format(r.getCheckIn()));
			((ObjectNode)node).put("endDate", dateFormat.format(r.getCheckOut()));
			
			long milis = r.getCheckIn().getTime() - currentDate.getTime();
			long days = TimeUnit.DAYS.convert(milis, TimeUnit.MILLISECONDS);
			if (days <= 2) {
				((ObjectNode)node).put("status", "notAllowed");
			}
			else {
				((ObjectNode)node).put("status", "allowed");
			}
			
			jsonReservations.add(node);
		}
		
		return mapper.writeValueAsString(jsonReservations);
	}
	
	public String getUserVehicleReservations(String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		
		RegisteredUser ruser = registeredUserRep.findByUsername(userNode.get("username").asText());
		
		List<VehicleReservation> reservations = ruser.getReservations();
		List<JsonNode> jsonReservations = new ArrayList<JsonNode>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date currentDate = new Date();
		for (VehicleReservation r : reservations) {
			JsonNode node = mapper.createObjectNode();
			
			((ObjectNode)node).put("id", r.getId());
			((ObjectNode)node).put("carName", r.getVehicle().getName());
			((ObjectNode)node).put("startDate", dateFormat.format(r.getReservationDate()));
			((ObjectNode)node).put("endDate", dateFormat.format(r.getDueDate()));
			((ObjectNode)node).put("startLocation", r.getStartLocation());
			((ObjectNode)node).put("endLocation", r.getEndLocation());
			
			long milis = r.getReservationDate().getTime() - currentDate.getTime();
			long days = TimeUnit.DAYS.convert(milis, TimeUnit.MILLISECONDS);
			if (days <= 2) {
				((ObjectNode)node).put("status", "notAllowed");
			}
			else {
				((ObjectNode)node).put("status", "allowed");
			}
			
			jsonReservations.add(node);
		}
		
		return mapper.writeValueAsString(jsonReservations);
	}
	
	public Collection<HAdditionalService> getAdditionalServices(KeyBean key) {
		
		RoomReservation reservation = roomReservSvc.findOne(key.getKey()).get();
		
		if(reservation != null) {
			return reservation.getAdditionalServices();
		}
		
		return null;
		
	}
	
	
	public List<RegisteredUser> findAll() {
		return registeredUserRep.findAll();
	}
	
	public RegisteredUser save(RegisteredUser user) {
		return registeredUserRep.save(user);
	}
	
	public RegisteredUser findByUsername(String username) {
		return registeredUserRep.findByUsername(username);
	}
	
}
