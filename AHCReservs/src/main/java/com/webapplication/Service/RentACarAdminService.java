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
import com.webapplication.Model.RentACarBranchOffice;
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

	public String addOfficeBranch(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		
		RentACarAdmin admin = rentACarAdminRep.findByUsername(userNode.get("username").asText());
		
		if (admin == null || !admin.getPassword().equals(userNode.get("password").asText())) {
			return "badRequest";
		}
		
		
		RentACar carService = rentRepository.findById(userNode.get("serviceId").asLong()).get();
		
		JsonNode dataNode = mapper.readTree(json);
		
		RentACarBranchOffice office = new RentACarBranchOffice();
		office.setAddress(dataNode.get("location").asText());
		office.setName(dataNode.get("name").asText());
		office.setRentACar(carService);
		
		for (RentACarBranchOffice bo : carService.getBranchOffice()) {
			if (bo.getName().equals(office.getName()) && bo.getAddress().equals(office.getAddress())) {
				return "exists";
			}
		}
		
		carService.getBranchOffice().add(office);
		
		rentRepository.save(carService);
		
		return "success";
	}
	
	public String saveEditedVehicle(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode jsonData = mapper.readTree(json);
		JsonNode jsonUser = mapper.readTree(user);
		
		
		RentACarAdmin admin = rentACarAdminRep.findByUsername(jsonUser.get("username").asText());
		
		if (admin == null) {
			return "badRequest";
		}
		
		if (!admin.getPassword().equals(jsonUser.get("password").asText())) return "badRequest";
		
		Long id = jsonData.get("id").asLong();
		String name = jsonData.get("name").asText();
		String description = jsonData.get("description").asText();
		VehicleType type = VehicleType.valueOf(jsonData.get("type").asText());
		int doors = jsonData.get("doors").asInt();
		int people = jsonData.get("people").asInt();
		int price = jsonData.get("price").asInt();
		
		Vehicle vehicle = vehicleRepository.findById(id).get();
		
		vehicle.setName(name);
		vehicle.setDescription(description);
		vehicle.setNumOfDoors(doors);
		vehicle.setNumOfSeats(people);
		vehicle.setVehicleType(type);
		vehicle.setPricePerDay(price);
		
		vehicleRepository.save(vehicle);
		
		
		
		
		
		return "success";
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
	public String ChangeCompanyInfo(String requestJson, String user) throws IOException {
		
		
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(requestJson);
		JsonNode userNode = mapper.readTree(user);
		
		RentACar company = rentRepository.findById(userNode.get("serviceId").asLong()).get();
		if (company == null) {
			return "notLoggedIn";
		}
		
		
		String newName = node.get("newName").asText();
		if (!company.getName().equals(newName)) {
			RentACar checkout = rentRepository.findOneByName(newName);
			if (checkout != null) {
				return "exists";
			}
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
