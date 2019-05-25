package com.webapplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.UserBean;
import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.SystemAdmin;

@Service
public class UserService {

	@Autowired
	RegisteredUserService regUserSvc;
	
	/* Admin services */
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	@Autowired
	AirlineAdminService airlineAdminSvc;
	
	@Autowired
	RentACarAdminService rentACarAdminSvc;
	
	@Autowired
	SystemAdminService sysAdminSvc;
	
	@Autowired
	MultipleService multiSvc;
	
	
	public AppUser updateProfile(UserBean user) {
		
		switch(user.getUserType()) {
		
		case "registeredUser":
			RegisteredUser regUser = regUserSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!regUser.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(multiSvc.usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			regUser.changeUserData(user);
			return regUserSvc.save(regUser);
			
		case "sysAdmin":
			SystemAdmin sysAdmin = sysAdminSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!sysAdmin.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(multiSvc.usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			sysAdmin.changeUserData(user);
			return sysAdminSvc.save(sysAdmin);
			
		case "airAdmin":
			AirlineAdmin airAdmin = airlineAdminSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!airAdmin.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(multiSvc.usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			airAdmin.changeUserData(user);
			return airlineAdminSvc.save(airAdmin);
			
		case "hotelAdmin":
			HotelAdmin hotelAdmin = hotelAdminSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!hotelAdmin.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(multiSvc.usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			hotelAdmin.changeUserData(user);
			return hotelAdminSvc.save(hotelAdmin);
			
		case "carAdmin":
			RentACarAdmin carAdmin = rentACarAdminSvc.findOne(user.getId()).get();
			
			//if the user changed his username
			if(!carAdmin.getUsername().equals(user.getUsername())) {
				
				//check whether the new username already exits
				if(multiSvc.usernameExist(user.getUsername())) {
					return null;
				}
			}
			
			carAdmin.changeUserData(user);
			return rentACarAdminSvc.save(carAdmin);
			
		default:
			break;
		}
		
		return null;
		
	}
	
	
	
	public AppUser changePassword(UserBean user) {
		
		switch(user.getUserType()) {
		
		case "sysAdmin":
			
			SystemAdmin sysAdmin = sysAdminSvc.findOne(user.getId()).get();
			
			//if the user left the same password
			if(user.getPassword().equals(sysAdmin.getPassword())) {
				return null;
			}
			
			sysAdmin.setPasswordChanged(true);
			sysAdmin.setPassword(user.getPassword());
			return sysAdminSvc.save(sysAdmin);
			
		case "airAdmin":
			AirlineAdmin airAdmin = airlineAdminSvc.findOne(user.getId()).get();
			
			//if the user left the same password
			if(user.getPassword().equals(airAdmin.getPassword())) {
				return null;
			}
			
			airAdmin.setPasswordChanged(true);
			airAdmin.setPassword(user.getPassword());
			return airlineAdminSvc.save(airAdmin);
			
		case "hotelAdmin":
			HotelAdmin hotelAdmin = hotelAdminSvc.findOne(user.getId()).get();
			
			//if the user left the same password
			if(user.getPassword().equals(hotelAdmin.getPassword())) {
				return null;
			}
			
			hotelAdmin.setPasswordChanged(true);
			hotelAdmin.setPassword(user.getPassword());
			return hotelAdminSvc.save(hotelAdmin);
			
		case "carAdmin":
			RentACarAdmin carAdmin = rentACarAdminSvc.findOne(user.getId()).get();
			
			//if the user left the same password
			if(user.getPassword().equals(carAdmin.getPassword())) {
				return null;
			}
			
			carAdmin.setPasswordChanged(true);
			carAdmin.setPassword(user.getPassword());
			return rentACarAdminSvc.save(carAdmin);
			
		default:
			break;
		}
		
		return null;
		
		}
		
}
	
	

