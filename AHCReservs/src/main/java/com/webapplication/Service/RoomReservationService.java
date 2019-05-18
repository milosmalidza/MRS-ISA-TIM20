package com.webapplication.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.RoomReservationBean;
import com.webapplication.Model.HAdditionalService;
import com.webapplication.Model.HotelServiceType;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.Room;
import com.webapplication.Model.RoomReservation;
import com.webapplication.Repository.RoomReservationRepository;

@Service
public class RoomReservationService {

	@Autowired
	RoomReservationRepository roomReservRep;
	
	@Autowired
	RegisteredUserService regUserSvc;
	
	@Autowired
	HAdditionalServiceSvc hAdditionalServiceSvc;
	
	@Autowired
	RoomService roomSvc;
	
	public String reserveRooms(RoomReservationBean reservationData) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");
		Date checkInDate = null;
		Date checkOutDate = null;
		
		//Parse strings to java.util.Date
		try {
			checkInDate = sdf.parse(reservationData.getStrCheckInDate());
			checkOutDate = sdf.parse(reservationData.getStrCheckOutDate());
		} catch (ParseException e) {
			e.printStackTrace();
			return "Invalid date format";
		}
		
		RegisteredUser user = regUserSvc.findByUsername(reservationData.getUsername());
		
		//if the user does not exist or the user is an admin
		if(user == null) {
			return "You are not allowed to make a reservation";
		}
		
		Set<HAdditionalService> additionalServices = new HashSet<HAdditionalService>();
		//Parsing additional services
		if(reservationData.getSelectedAdditionalServices() != null) {
			additionalServices = convertStringToAdditionalService(reservationData.getSelectedAdditionalServices(),
					reservationData.getHotelID());
		}
		
		
		if(additionalServices == null) {
			return "Something wen't wrong, please try again";
		}
		
		Room room;
		//for every room selected create a Room reservation
		for(Long roomID: reservationData.getSelectedRooms()) {
			
			room = roomSvc.findById(roomID).get();
			
			if(room == null) {
				return "Room not found";
			}
			
			RoomReservation reservation = new RoomReservation(checkInDate, checkOutDate, reservationData.getNumOfGuests(),
					room.getRoomPrice().getPrice(), room.getHotel(), user, room, additionalServices);
			
			
			//if the room hasn't been reserved in the mean time the reservation is successful
			if(!isRoomReserved(room.getId(), checkInDate, checkOutDate)) {
				
				room.setReservation(reservation);
				save(reservation);
				roomSvc.save(room);
				
			}
			
			
		}
		
		return "Reservation successful";
	}
	
	
	public Set<HAdditionalService> convertStringToAdditionalService(List<String> stringServices, Long hotelID) {
		
		//service string in format 'service: price currency', so I need to extract the 'service'
		int indexOfColon = 0;
		HotelServiceType serviceType;
		HAdditionalService additionalService;
		
		Set<HAdditionalService> servicesSet = new HashSet<HAdditionalService>();
		
		for(String service: stringServices) {
			
			indexOfColon = service.indexOf(":");
			
			if(indexOfColon == -1) {
				System.out.println(": not found in service string");
				return null;
			}
			
			//perform conversion
			serviceType = hAdditionalServiceSvc.convertStringToService(service.substring(0, indexOfColon));
			
			if(serviceType == null) {
				System.out.println("Exception during string to HotelServiceType conversion");
				return null; //exception during conversion
			}
			
			//find the HAdditionalService with the parsed serviceType for the hotelID
			additionalService = hAdditionalServiceSvc.findOneForHotel(hotelID, serviceType);
			
			if(additionalService == null) {
				System.out.println("Additional service not found");
				return null;
			}
			
			servicesSet.add(additionalService);
		}
		
		return servicesSet;
		
	}
	
	
	public boolean isRoomReserved(Long room_id, Date checkIn, Date checkOut) {
		
		//iterate through all reservations containing the room passed as parameter
		for(RoomReservation reservation: findAll()) {
			
			if(reservation.getRoom().getId() == room_id) {
				
				//compare the dates to determine whether the room is reserved
				if(!(checkOut.before(reservation.getCheckIn()) || checkIn.after(reservation.getCheckOut()))) {
					return true;
				}
				
			}
			
		}
		
		
		return false;
		
	}
	
	
	
	/************ Repository methods  *************/
	
	public Optional<RoomReservation> findOne(Long id) {
		return roomReservRep.findById(id);
	}
	
	public RoomReservation save(RoomReservation reservation) {	
		return roomReservRep.save(reservation);
	}
	
	
	public RoomReservation findByUser(String username) {
		
		//TODO: implement
		return null;
	}
	
	
	public List<RoomReservation> findAll() {
		return roomReservRep.findAll();
	}
	
}
