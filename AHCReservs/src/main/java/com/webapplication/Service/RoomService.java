package com.webapplication.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.Room;
import com.webapplication.Model.RoomType;
import com.webapplication.Repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	RoomRepository roomRep;
	
	public Room save(Room room) {
		return roomRep.save(room);
	}
	
	public void removeById(Long id) {
		roomRep.deleteById(id);
	}
	
	public Optional<Room> findById(Long id) {
		return roomRep.findById(id);
	}
	
	//convert string to room type
	public RoomType getRoomType(String roomTypeString) {
		
		switch(roomTypeString.toLowerCase()) {
		
		case "single":
			return RoomType.SINGLE;
			
		case "double":
			return RoomType.DOUBLE;
			
		case "triple":
			return RoomType.TRIPLE;
			
		case "quad":
			return RoomType.QUAD;
		
		default:
			return null;
		
		}
	}
	
	
}
