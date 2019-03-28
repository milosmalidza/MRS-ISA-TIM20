package com.webapplication.Controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.webapplication.Model.AppUser;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Service.RegisteredUserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	RegisteredUserService regUserSvc;
	
	
	/** Metoda za registraciju korisnika. Podaci o korisniku se salju u JSON formatu.
	 *  @param httpEntity - kako bi postman mogao da namapira json moram ostaviti ovaj oblik parametra
	 *  @return json sa podacima o registrovanom korisniku
	 *  */
	
	@RequestMapping(
			value="/registerUser",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public RegisteredUser registerUser(HttpEntity<AppUser> httpEntity) { 
		
		RegisteredUser ru = new RegisteredUser(httpEntity.getBody()); //getBody() - izvlaci json iz parametra
		System.out.println("Registering user: " + ru.toString());
		
		regUserSvc.save(ru);
		return ru;
	}
	
	
	@RequestMapping(
			value="/getRegisteredUsers",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Collection<RegisteredUser> getRegisteredUsers() {
		
		return regUserSvc.findAll();
	}
	
}
