package com.webapplication.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.HotelData;
import com.webapplication.JSONBeans.HotelServiceData;
import com.webapplication.JSONBeans.KeyBean;
import com.webapplication.JSONBeans.RoomData;
import com.webapplication.Model.Currency;
import com.webapplication.Model.HAdditionalService;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelServiceType;
import com.webapplication.Model.Room;
import com.webapplication.Model.RoomType;
import com.webapplication.Repository.HotelRepository;


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
		Currency currency = mulSvc.convertStringToCurrency(roomData.getCurrencyString());
		
		if(type == null || currency == null) {
			return null;
		}
		
		roomData.setRoomType(type);
		roomData.setCurrency(currency);
		
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
		
		if(room.isReserved()) {
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
		
		if(room.isReserved()) {
			return "You can't edit a room which is reserved";
		}
		
		//change the data of the room
		room.setNumber(roomData.getNumber());
		room.setFloor(roomData.getFloor());
		room.setNumOfBeds(roomData.getNumOfBeds());
	
		RoomType type = roomSvc.getRoomType(roomData.getRoomTypeString());
		Currency currency = mulSvc.convertStringToCurrency(roomData.getCurrencyString());
		
		if(type == null || currency == null) {
			return "Unknown currency or room type";
		}
		
		room.setRoomType(type);
		room.getRoomPrice().setCurrency(currency);
		room.getRoomPrice().setPrice(roomData.getPrice());
		
		roomSvc.save(room);
		
		return "Success";
	}
	
	
	public HAdditionalService addService(HotelServiceData serviceData) {
		
		
		//convert strings to enums
		Currency currency = mulSvc.convertStringToCurrency(serviceData.getCurrencyString());
		HotelServiceType svcType = hAdditionalSvc.convertStringToService(serviceData.getServiceTypeString());
		
		if(currency == null || svcType == null) {
			return null;
		}
		
		serviceData.setCurrency(currency);
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
		
		//convert string to enum
		Currency currency = mulSvc.convertStringToCurrency(serviceData.getCurrencyString());
		
		if(currency == null) {
			return null;
		}
		
		additionalSvc.getServicePrice().setPrice(serviceData.getPrice());
		additionalSvc.getServicePrice().setCurrency(currency);
		
		return hAdditionalSvc.save(additionalSvc);
		
	}
	
	
	public int getNumOfAvaiableRooms(Long hotelID) {
		
		Hotel hotel = findOne(hotelID).get();
		
		if(hotel == null) {
			return -1;
		}
		
		int availableRooms = 0;
		
		for(Room room: hotel.getRooms()) {
			
			if(!room.isReserved()) {
				availableRooms++;
			}
			
		}
		
		return availableRooms;
		
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
