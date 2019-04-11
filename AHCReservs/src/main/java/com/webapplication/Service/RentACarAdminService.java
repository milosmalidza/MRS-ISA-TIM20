package com.webapplication.Service;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.Vehicle;
import com.webapplication.Model.VehicleType;
import com.webapplication.Repository.RentACarAdminRepository;
import com.webapplication.Repository.RentACarRepository;
import com.webapplication.Repository.VehicleRepository;

@Service
public class RentACarAdminService {


	@Autowired
	private RentACarAdminRepository rentACarAdminRep;
	
	@Autowired
	private VehicleRepository vehicleRepository;
	
	@Autowired
	private RentACarRepository rentRepository;


	public RentACarAdmin save(RentACarAdmin admin) {
		return rentACarAdminRep.save(admin);
	}

	public List<RentACarAdmin> findAll() {
		return rentACarAdminRep.findAll();
	}


	public RentACarAdmin findByUsername(String username) {
		return rentACarAdminRep.findByUsername(username);
	}


	//returns the hotel admins that aren't currenly assigned to any hotel
	public List<RentACarAdmin> getAvailableAdmins() {

		List<RentACarAdmin> availableAdmins = new ArrayList<RentACarAdmin>();

		for(RentACarAdmin rentACarAdmin: findAll()) {
			if(rentACarAdmin.getRentACar() == null && rentACarAdmin.isEnabled()) {
				availableAdmins.add(rentACarAdmin);
			}
		}

		return availableAdmins;
	}


	public void deleteByUsername(String username) {
		rentACarAdminRep.deleteByUsername(username);
	}


	public String AddCar(String requestJson) throws IOException, NumberFormatException, IllegalArgumentException  {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(requestJson);
		
		Vehicle vehicle = new Vehicle();
		
		vehicle.setArchived(false);
		vehicle.setDescription(node.get("description").asText());
		vehicle.setName(node.get("name").asText());
		
		vehicle.setNumOfDoors(Integer.parseInt(node.get("numberOfDoors").asText()));
		vehicle.setNumOfSeats(Integer.parseInt(node.get("numberOfSeats").asText()));
		vehicle.setVehicleType(VehicleType.valueOf(node.get("typeOfVehicle").asText()));
		vehicle.setPricePerDay(Integer.parseInt(node.get("pricePerDay").asText()));
		
		
		
		vehicleRepository.save(vehicle);
		
		return "success";
	}

	//Change company info with new data
	public String ChangeCompanyInfo(String requestJson) throws IOException {
		
		RentACar company = rentRepository.findOneByName("");
		if (company == null) {
			return "notLoggedIn";
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(requestJson);
		
		
		
		String newName = node.get("newName").asText();
		RentACar checkout = rentRepository.findOneByName(newName);
		if (checkout != null) {
			return "exists";
		}
		
		company.setName(newName);
		company.setDescription(node.get("newDescription").asText());
		company.setAddress(node.get("newAddress").asText());
		
		rentRepository.save(company);
		
		return "success";
	}
	

	public RentACarAdmin findByEmailIdIgnoreCase(String emailid) {
		return rentACarAdminRep.findByEmailIdIgnoreCase(emailid);
	}

	
}
