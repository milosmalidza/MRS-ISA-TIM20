package com.webapplication.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import org.hibernate.LockMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.Vehicle;
import com.webapplication.Model.VehicleReservation;
import com.webapplication.Repository.RegisteredUserRepository;
import com.webapplication.Repository.VehicleRepository;
import com.webapplication.Repository.VehicleReservationRepository;

@Service
public class VehicleReservationService {
	
	@Autowired
	public RegisteredUserRepository userRepository;
	
	@Autowired
	public VehicleReservationRepository reservationRep;
	
	@Autowired
	public VehicleRepository vehicleRep;
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String quickVehicleReservation(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode userNode = mapper.readTree(user);
		
		RegisteredUser ruser = userRepository.findByUsername(userNode.get("username").asText());
		
		if (ruser == null) return "badRequest";
		if (!ruser.getPassword().equals(userNode.get("password").asText())) return "badRequest";
		
		JsonNode jsonNode = mapper.readTree(json);
		
		VehicleReservation reservation = reservationRep.findOneById(jsonNode.get("id").asLong());
		if (reservation.getUser() != null) return "reserved";
		reservation.setUser(ruser);
		
		reservationRep.save(reservation);
		
		return "success";
	}
	
	@Transactional
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
		
		
		Long id = Long.parseLong(jsonNode.get("id").asText());
		Vehicle vehicle = vehicleRep.findOneById(id);
		entityManager.lock(vehicle, LockModeType.PESSIMISTIC_WRITE);
		
		if (vehicle != null) {
			
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
			
			Date startDate = format.parse(jsonNode.get("startDate").asText());
			Date endDate = format.parse(jsonNode.get("endDate").asText());
			
			if (!RentACarService.isReservationOk(startDate, endDate, vehicle.getReservations())) {
				return "reserved";
			}
			
			
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
		
		return "fail";
		
	}
	
	
}
