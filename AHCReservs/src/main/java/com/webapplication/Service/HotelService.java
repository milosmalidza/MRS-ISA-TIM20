package com.webapplication.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.HotelData;
import com.webapplication.JSONBeans.RoomData;
import com.webapplication.Model.Hotel;
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
		for (Room room : hotel.getRooms()) {
			
			if(room.getNumber() == roomData.getNumber()) {
				return null;
			}
			
		}
		
		//convert string to enum
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
