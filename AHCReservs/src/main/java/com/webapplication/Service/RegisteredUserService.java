package com.webapplication.Service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.KeyBean;
import com.webapplication.Model.HAdditionalService;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.RoomReservation;
import com.webapplication.Repository.RegisteredUserRepository;

@Service
public class RegisteredUserService {

	@Autowired
	private RegisteredUserRepository registeredUserRep;
	
	@Autowired
	RoomReservationService roomReservSvc;
	
	
	public Collection<HAdditionalService> getAdditionalServices(KeyBean key) {
		
		RoomReservation reservation = roomReservSvc.findOne(key.getKey()).get();
		
		if(reservation != null) {
			return reservation.getAdditionalServices();
		}
		
		return null;
		
	}
	
	
	public List<RegisteredUser> findAll() {
		return registeredUserRep.findAll();
	}
	
	public RegisteredUser save(RegisteredUser user) {
		return registeredUserRep.save(user);
	}
	
	public RegisteredUser findByUsername(String username) {
		return registeredUserRep.findByUsername(username);
	}
	
}
