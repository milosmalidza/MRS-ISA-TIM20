package com.webapplication.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.RegisteredUser;
import com.webapplication.Repository.RegisteredUserRepository;

@Service
public class RegisteredUserService {

	@Autowired
	private RegisteredUserRepository registeredUser;
	
	public RegisteredUser findOne(String username) {
		return registeredUser.findOne(username);
	}
	
	public List<RegisteredUser> findAll() {
		return registeredUser.findAll();
	}
	
	public RegisteredUser save(RegisteredUser user) {
		return registeredUser.save(user);
	}
	
}
