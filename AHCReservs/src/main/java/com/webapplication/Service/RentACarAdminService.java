package com.webapplication.Service;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.RentACarBranchOffice;
import com.webapplication.Model.Vehicle;
import com.webapplication.Model.VehicleReservation;
import com.webapplication.Model.VehicleType;
import com.webapplication.Repository.RentACarAdminRepository;
import com.webapplication.Repository.RentACarBranchOfficeRepository;
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
	
	@Autowired
	public RentACarBranchOfficeRepository branchRep;

	
	public String getServiceRating(String user) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(user);
		
		RentACar service = rentRepository.findById(jsonNode.get("serviceId").asLong()).get();
		
		JsonNode result = mapper.createObjectNode();
		
		double rating = service.getRating();
		rating = rating*100;
		rating = Math.round(rating);
		rating = rating/100;
		
		((ObjectNode)result).put("rating", rating);
		
		return mapper.writeValueAsString(result);
	}
	
	@SuppressWarnings("deprecation")
	public String getVehicleRatings(String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(user);
		
		RentACar service = rentRepository.findOneById(jsonNode.get("serviceId").asLong());
		
		List<Vehicle> vehicles = service.getVehicles();
		
		ArrayNode vehicleNames = mapper.createArrayNode();
		ArrayNode vehicleRatings = mapper.createArrayNode();
		
		for(Vehicle v : vehicles) {
			double rating = 0;
			int validRatings = 0;
			for (VehicleReservation r : v.getReservations()) {
				if (r.getRating() != 0) {
					rating += r.getRating();
					
					validRatings++;
				}
			}
			
			if (rating != 0) {
				
				double r = rating / validRatings;
				r = r*100;
				r = Math.round(r);
				r = r/100;
				
				vehicleRatings.add(r);
			}
			else {
				vehicleRatings.add(0);
			}
			vehicleNames.add(v.getName());
		}
		
		JsonNode data = mapper.createObjectNode();
		
		
		((ObjectNode)data).put("names", vehicleNames);
		((ObjectNode)data).put("ratings", vehicleRatings);
		
		return mapper.writeValueAsString(data);
	}
	
	@SuppressWarnings("deprecation")
	public String getReservationsReport(String user) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(user);
		
		RentACar service = rentRepository.findOneById(jsonNode.get("serviceId").asLong());
		List<Vehicle> vehicles = service.getVehicles();
		
		Date date = new Date();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.setTime(date);
		c1.add(Calendar.MONTH, -11);
		
		ArrayNode months = mapper.createArrayNode();
		ArrayNode reservations = mapper.createArrayNode();
		
		for(int i = 0; i < 12; i++) {
			int overral = 0;
			for(Vehicle v : vehicles) {
				for(VehicleReservation r : v.getReservations()) {
					c2.setTime(r.getReservationDate());
					if (c2.get(Calendar.MONTH) == c1.get(Calendar.MONTH)) {
						overral++;
					}
				}
			}
			
			months.add(this.getMonthForInt(c1.get(Calendar.MONTH)));
			reservations.add(overral);
			
			c1.add(Calendar.MONTH, 1);
		}
		
		JsonNode data = mapper.createObjectNode();
		
		((ObjectNode)data).put("months", months);
		((ObjectNode)data).put("reservations", reservations);
		
		
		return mapper.writeValueAsString(data);
	}
	
	@SuppressWarnings("deprecation")
	public String getReservationsByDay(String user, String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(user);
		JsonNode jsonData = mapper.readTree(json);
		
		RentACar service = rentRepository.findOneById(jsonNode.get("serviceId").asLong());
		List<Vehicle> vehicles = service.getVehicles();
		
		Date date = new Date();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		Calendar c3 = Calendar.getInstance();
		
		c1.setTime(date);
		c1.add(Calendar.MONTH, jsonData.get("month").asInt()-11);
		c1.set(Calendar.DAY_OF_MONTH, c1.getActualMinimum(Calendar.DAY_OF_MONTH));
		
		c2.setTime(date);
		c2.add(Calendar.MONTH, jsonData.get("month").asInt()-11);
		c2.set(Calendar.DAY_OF_MONTH, c1.getActualMaximum(Calendar.DAY_OF_MONTH));
		c2.add(Calendar.DATE, 1);
		
		
		ArrayNode days = mapper.createArrayNode();
		ArrayNode reservations = mapper.createArrayNode();
		
		int index = 0;
		while(c1.getTime().before(c2.getTime())) {
			int overral = 0;
			for(Vehicle v : vehicles) {
				for(VehicleReservation r : v.getReservations()) {
					c3.setTime(r.getReservationDate());
					if (c3.get(Calendar.DAY_OF_MONTH) == c1.get(Calendar.DAY_OF_MONTH) &&
							c3.get(Calendar.MONTH) == c1.get(Calendar.MONTH)) {
						overral++;
					}
				}
			}
			index++;
			days.add(index + ".");
			reservations.add(overral);
			
			c1.add(Calendar.DATE, 1);
		}
		
		JsonNode data = mapper.createObjectNode();
		
		((ObjectNode)data).put("days", days);
		((ObjectNode)data).put("reservations", reservations);
		
		
		return mapper.writeValueAsString(data);
	}
	
	
	
	
	@SuppressWarnings("deprecation")
	public String getProfitReport(String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode jsonNode = mapper.readTree(user);
		
		RentACar service = rentRepository.findOneById(jsonNode.get("serviceId").asLong());
		List<Vehicle> vehicles = service.getVehicles();
		
		Date date = new Date();
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		
		c1.setTime(date);
		c1.add(Calendar.MONTH, -11);
		
		ArrayNode months = mapper.createArrayNode();
		ArrayNode profits = mapper.createArrayNode();
		
		for(int i = 0; i < 12; i++) {
			double overral = 0;
			for(Vehicle v : vehicles) {
				for(VehicleReservation r : v.getReservations()) {
					c2.setTime(r.getReservationDate());
					if (c2.get(Calendar.MONTH) == c1.get(Calendar.MONTH)) {
						overral += calculateReservation(r);
					}
				}
			}
			
			months.add(this.getMonthForInt(c1.get(Calendar.MONTH)));
			profits.add(overral);
			
			c1.add(Calendar.MONTH, 1);
		}
		
		JsonNode data = mapper.createObjectNode();
		
		((ObjectNode)data).put("months", months);
		((ObjectNode)data).put("profits", profits);
		
		
		return mapper.writeValueAsString(data);
	}
	
	double calculateReservation(VehicleReservation res) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(res.getReservationDate());
		double overral = 0;
		while (c1.getTime().before(res.getDueDate())) {
			overral += res.getVehicle().getPricePerDay();
			c1.add(Calendar.DATE, 1);
			
		}
		
		return overral;
	}
	
	String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
	
	
	
	

	public RentACarAdmin save(RentACarAdmin admin) {
		return rentACarAdminRep.save(admin);
	}

	public List<RentACarAdmin> findAll() {
		return rentACarAdminRep.findAll();
	}


	public RentACarAdmin findByUsername(String username) {
		return rentACarAdminRep.findByUsername(username);
	}
	
	
	public List<RentACarAdmin> getAvailableAdmins() {
		
		return rentACarAdminRep.getAvailableAdmins();
	}


	public void deleteByUsername(String username) {
		rentACarAdminRep.deleteByUsername(username);
	}
	
	
	
	
	public String removeBranchOffice(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		
		RentACarAdmin admin = rentACarAdminRep.findByUsername(userNode.get("username").asText());
		
		if (admin == null) return "badRequest";
		if (!admin.getPassword().equals(userNode.get("password").asText())) return "badRequest";
		
		JsonNode dataNode = mapper.readTree(json);
		
		RentACar service = rentRepository.findById(userNode.get("serviceId").asLong()).get();
		
		
		List<RentACarBranchOffice> offices = branchRep.findAllByRentACar(service);
		
		for (RentACarBranchOffice office : offices) {
			if (office.getId() == dataNode.get("id").asLong()) {
				branchRep.delete(office);
				return "success";
			}
		}
		
		return "badRequest";
		
		
	}
	
	public String updateBranchOffice(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		
		RentACarAdmin admin = rentACarAdminRep.findByUsername(userNode.get("username").asText());
		
		if (admin == null) {
			return "badRequest";
		}
		
		if (!admin.getPassword().equals(userNode.get("password").asText())) return "badRequest";
		
		RentACar service = rentRepository.findById(userNode.get("serviceId").asLong()).get();
		
		List<RentACarBranchOffice> offices = branchRep.findAllByRentACar(service);
		
		JsonNode dataNode = mapper.readTree(json);
		RentACarBranchOffice o = null;
		for (RentACarBranchOffice office : offices) {
			if (dataNode.get("name").asText().equals(office.getName()) && dataNode.get("address").asText().equals(office.getAddress())) {
				return "exists";
			}
			if (office.getId() == dataNode.get("id").asLong()) {
				o = office;
			}
		}
		
		o.setName(dataNode.get("name").asText());
		o.setAddress(dataNode.get("address").asText());
		
		branchRep.save(o);
		
		return "success";
	}
	
	

	public String addOfficeBranch(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		
		RentACarAdmin admin = rentACarAdminRep.findByUsername(userNode.get("username").asText());
		
		if (admin == null) {
			return "badRequest";
		}
		
		if (!admin.getPassword().equals(userNode.get("password").asText())) return "badRequest";
		
		
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
	
	public String AddCar(String json, String user) throws IOException, NumberFormatException, IllegalArgumentException  {

		ObjectMapper mapper = new ObjectMapper();
		JsonNode userNode = mapper.readTree(user);
		
		RentACarAdmin admin = rentACarAdminRep.findByUsername(userNode.get("username").asText());
		
		if (admin == null) return "badRequest";
		if (!admin.getPassword().equals(userNode.get("password").asText())) return "badRequest";
		
		RentACar service = rentRepository.findById(userNode.get("serviceId").asLong()).get();
		
		
		JsonNode jsonNode = mapper.readTree(json);
		
		Vehicle vehicle = new Vehicle();
		
		vehicle.setArchived(false);
		vehicle.setDescription(jsonNode.get("description").asText());
		vehicle.setName(jsonNode.get("name").asText());
		
		vehicle.setNumOfDoors(Integer.parseInt(jsonNode.get("numberOfDoors").asText()));
		vehicle.setNumOfSeats(Integer.parseInt(jsonNode.get("numberOfSeats").asText()));
		vehicle.setVehicleType(VehicleType.valueOf(jsonNode.get("typeOfVehicle").asText()));
		vehicle.setPricePerDay(Integer.parseInt(jsonNode.get("pricePerDay").asText()));
		vehicle.setRentACar(service);
		
		
		
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

	public Optional<RentACarAdmin> findOne(Long id) {
		return rentACarAdminRep.findOne(id);
	}

	
}
