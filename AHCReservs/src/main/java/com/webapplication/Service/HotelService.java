package com.webapplication.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.webapplication.JSONBeans.DateBean;
import com.webapplication.JSONBeans.HotelData;
import com.webapplication.JSONBeans.HotelServiceData;
import com.webapplication.JSONBeans.MapPinData;
import com.webapplication.JSONBeans.RoomData;
import com.webapplication.Model.HAdditionalService;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelServiceType;
import com.webapplication.Model.Rating;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.Room;
import com.webapplication.Model.RoomReservation;
import com.webapplication.Model.RoomType;
import com.webapplication.Repository.HotelRepository;
import com.webapplication.Repository.RegisteredUserRepository;

import comparators.RoomNumComparator;


@Service
public class HotelService {

	@Autowired
	HotelRepository hotelRep;
	
	@Autowired
	MultipleService mulSvc;
	
	@Autowired
	RoomService roomSvc;
	
	@Autowired
	HAdditionalServiceSvc hAdditionalSvc;
	
	@Autowired
	RoomReservationService roomReservSvc;
	
	@Autowired
	RegisteredUserRepository rUserRep;
	
	public String setHotelRating(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		
		RegisteredUser ruser = rUserRep.findByUsername(userNode.get("username").asText());
		
		if (ruser == null) return "badRequest";
		if (!ruser.getPassword().equals(userNode.get("password").asText())) return "badRequest";
		
		
		JsonNode jsonNode = mapper.readTree(json);
		Hotel hotel = hotelRep.findOneById(jsonNode.get("hotelId").asLong());
		
		Date currentDate = new Date();
		
		List<RoomReservation> roomReservations = roomReservSvc.findAll();
		
		boolean canRate = false;
		
		for (RoomReservation r : roomReservations) {
			
			if (r.getUser() == null) {
				continue;
			}
			
			if (r.getHotel().getId() == hotel.getId() &&
					r.getUser().getId() == ruser.getId() &&
					currentDate.after(r.getCheckOut())) {
				canRate = true;
			}
		}
		
		if (!canRate) return "noReservation";
		
		
		
		Rating rating = null;
		for (Rating r : hotel.getRatings()) {
			if (r.getUserId() == ruser.getId()) {
				rating = r;
				break;
			}
		}
		
		if (rating != null) {
			rating.setRating(jsonNode.get("rating").asDouble());
		}
		else {
			rating = new Rating();
			rating.setUserId(ruser.getId());
			rating.setRating(jsonNode.get("rating").asDouble());
			hotel.getRatings().add(rating);
		}
		
		hotel.updateRating();
		
		hotelRep.save(hotel);
		
		return "success";
	}
	
	public String getHotelRating(String json, String user) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode userNode = mapper.readTree(user);
		JsonNode jsonNode = mapper.readTree(json);
		
		
		Hotel hotel = hotelRep.findOneById(jsonNode.get("hotelId").asLong());
		JsonNode data = mapper.createObjectNode();
		((ObjectNode)data).put("rating", 0);
		
		for(Rating r : hotel.getRatings()) {
			if (userNode.get("id").asLong() == r.getUserId()) {
				((ObjectNode)data).put("rating", r.getRating());
			}
		}
		
		return mapper.writeValueAsString(data);
	}
	
	public Hotel updateProfile(HotelData hotelData) {
		
		Hotel hotel = null;
		
		//check if the name already exists in other companies
		if(mulSvc.hotelNameExists(hotelData.getName())) {
			return null;
		}
		
		//get the hotel by id
		hotel = findOne(hotelData.getId()).get();
			
		if(hotel == null) {
			return null;
		}
			
		//if the admin changed the hotel name
		if(!hotelData.getName().equals(hotel.getName())) {
				
			//if the new name already exists in the hotels
			if (findByName(hotel.getName()) != null) {
				return null;
			}
		}
		
		
		//memorize changes
		hotel.setName(hotelData.getName());
		hotel.setDescription(hotelData.getDescription());
		hotel.setAddress(hotelData.getAddress());
		
		save(hotel);
		
		return hotel;
	}
	
	
	public Room addRoom(RoomData roomData) {
		
		Hotel hotel = findOne(roomData.getHotelID()).get();
		
		if(hotel == null) {
			return null;
		}
		
		//check if the room number already exists in the hotel
		if(mulSvc.roomExistsInHotel(hotel, roomData, false)) {
			return null;
		}
		
		//convert strings to enums
		RoomType type = roomSvc.getRoomType(roomData.getRoomTypeString());
		
		if(type == null) {
			return null;
		}
		
		roomData.setRoomType(type);
		
		//create new room
		Room room = new Room(roomData);
		room.setHotel(hotel);
		
		//save changes
		roomSvc.save(room);
		return room;
		
	}
	
	
	public String removeRoom(Long roomID) {
		
		Room room = roomSvc.findById(roomID).get();
		
		if(room == null) {
			return "Room does not exist";
		}
		
		if(!roomReservSvc.reservationsPassed(roomID)) {
			return "You can't remove a room which is reserved";
		}
		
		roomSvc.removeById(roomID);
		
		return "Success";
		
	}
	
	public String editRoom(RoomData roomData) {
		
		//get the hotel of the room
		Hotel hotel = findOne(roomData.getHotelID()).get();
		
		if(hotel == null) {
			return "Hotel does not exists";
		}
		
		//check if the room number already exists in the hotel
		if(mulSvc.roomExistsInHotel(hotel, roomData, true)) {
			return "Room number already exists in the hotel";
		}
		
		Room room = roomSvc.findById(roomData.getRoomID()).get();
		
		if(room == null) {
			return "Room does not exist";
		}
		
		if(!roomReservSvc.reservationsPassed(room.getId())) {
			return "You can't edit a room which is reserved";
		}
		
		//change the data of the room
		room.setNumber(roomData.getNumber());
		room.setFloor(roomData.getFloor());
		room.setNumOfBeds(roomData.getNumOfBeds());
	
		RoomType type = roomSvc.getRoomType(roomData.getRoomTypeString());
		
		if(type == null) {
			return "Unknown currency or room type";
		}
		
		room.setRoomType(type);
		room.setRoomPrice(roomData.getPrice());
		
		roomSvc.save(room);
		
		return "Success";
	}
	
	
	public HAdditionalService addService(HotelServiceData serviceData) {
		
		
		//convert strings to enums
		HotelServiceType svcType = hAdditionalSvc.convertStringToService(serviceData.getServiceTypeString());
		
		if(svcType == null) {
			return null;
		}
		
		serviceData.setServiceType(svcType);
		
		Hotel hotel = findOne(serviceData.getHotelID()).get();
		
		if(hotel == null) {
			System.out.println("Can't find hotel");
			return null;
		}
		
		//check if service already exists in the hotel
		if(hAdditionalSvc.serviceExistsInHotel(serviceData.getServiceType(), hotel.getId())) {
			return null;
		}
		
		//create object and save data
		HAdditionalService service = new HAdditionalService(serviceData, hotel);
		
		return hAdditionalSvc.save(service);
		
	}
	
	public HAdditionalService editService(HotelServiceData serviceData) {
		
		HAdditionalService additionalSvc = hAdditionalSvc.findOne(serviceData.getServiceID()).get();
		
		if(additionalSvc == null) {
			return null;
		}
		
		additionalSvc.setServicePrice(serviceData.getPrice());
		
		return hAdditionalSvc.save(additionalSvc);
		
	}
	
	
	public Collection<Room> getAvailableRooms(DateBean dateBean) {
		
		Hotel hotel = findOne(dateBean.getCompanyID()).get();
		
		if(hotel == null) {
			return null;
		}
		
		ArrayList<Room> availableRooms = new ArrayList<Room>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
		
		//convert strings to date
		try {
			dateBean.setStartDate(sdf.parse(dateBean.getStrStartDate()));
			dateBean.setEndDate(sdf.parse(dateBean.getStrEndDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		//iterate through hotel rooms and find the available ones
		for(Room room: hotel.getRooms()) {
			
			//check whether the room is reserved
			if(!roomReservSvc.isRoomReserved(room.getId(), dateBean.getStartDate(), dateBean.getEndDate())) {
				System.out.println("Room available");
				availableRooms.add(room);
			}
						
			System.out.println("Room reserved");
			
		}
		
		availableRooms.sort(new RoomNumComparator());
		
		return availableRooms;
		
	}
	
	
	
	public Hotel saveHotelPin(MapPinData pinData) {
		
		Hotel hotel = findOne(pinData.getCompanyID()).get();
		
		if(hotel == null) {
			return null;
		}
		
		hotel.setLatitude(pinData.getLatitude());
		hotel.setLongitude(pinData.getLongitude());
		
		
		return save(hotel);
		
		
	}
	
	
	public Collection<HotelServiceType> getServiceTypes() {
		
		return Arrays.asList(HotelServiceType.values());
		
	}
	
	public Optional<Hotel> findOne(Long id) {
		return hotelRep.findById(id);
	}
	
	public Hotel save(Hotel hotel) {	
		return hotelRep.save(hotel);
	}
	
	
	public Hotel findByName(String name) {
		return hotelRep.findOneByName(name);
	}
	
	public List<Hotel> findAll() {
		return hotelRep.findAll();
	}

	
}
