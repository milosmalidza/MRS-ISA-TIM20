package com.webapplication.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.RegisteredUser;
import com.webapplication.Repository.RegisteredUserRepository;

@Service
public class RegisteredUserService {

	@Autowired
	private RegisteredUserRepository registeredUserRep;
	
	
	public List<RegisteredUser> findAll() {
		return registeredUserRep.findAll();
	}
	
	public RegisteredUser save(RegisteredUser user) {
		return registeredUserRep.save(user);
	}
	
}
