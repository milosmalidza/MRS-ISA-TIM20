package com.webapplication.Service;

import java.io.IOException;
import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.Vehicle;
import com.webapplication.Model.VehicleType;
import com.webapplication.Repository.RentACarAdminRepository;
import com.webapplication.Repository.VehicleRepository;

@Service
public class RentACarAdminService {


	@Autowired
	private RentACarAdminRepository rentACarAdminRep;
	
	@Autowired
	private VehicleRepository vehicleRepository;


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

	
	public String ChangeCompanyInfo(String requestJson) {
		
		
		return "success";
	}
	

	public RentACarAdmin findByEmailIdIgnoreCase(String emailid) {
		return rentACarAdminRep.findByEmailIdIgnoreCase(emailid);
	}

	
}
