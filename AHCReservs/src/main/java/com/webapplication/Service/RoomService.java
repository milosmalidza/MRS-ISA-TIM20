package com.webapplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.Room;
import com.webapplication.Repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	RoomRepository roomRep;
	
	public Room save(Room room) {
		return roomRep.save(room);
	}
	
	
	
}
