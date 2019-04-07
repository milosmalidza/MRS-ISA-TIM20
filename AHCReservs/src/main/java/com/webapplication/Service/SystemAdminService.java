package com.webapplication.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.webapplication.JSONBeans.AdminToRegister;
import com.webapplication.JSONBeans.CompanyInfo;
import com.webapplication.Model.Airline;
import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Model.AppUser;
import com.webapplication.Model.Company;
import com.webapplication.Model.ConfirmationToken;
import com.webapplication.Model.Hotel;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.RentACar;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.SystemAdmin;
import com.webapplication.Repository.ConfirmationTokenRepository;
import com.webapplication.Repository.SystemAdminRepository;

@Service
public class SystemAdminService {

	@Autowired
	SystemAdminRepository sysAdminRep;
	
	/* Admin services */
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	@Autowired
	AirlineAdminService airlineAdminSvc;
	
	@Autowired
	RentACarAdminService rentACarAdminSvc;
	
	/* Company services */
	@Autowired
	HotelService hotelSvc;
	
	@Autowired
	AirlineService airlineSvc;
	
	@Autowired
	RentACarService rentACarSvc;
	
	/* Email notification services*/
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	
	public String registerAdmin(AdminToRegister admin) {
		
		if(admin.getCompanyType() == null) {
			return null;
		}
		
		//TODO: Check whether email already exists
		
		String response; //message to be returned to the user
		
		//check which type of admin I'm registering
		switch(admin.getCompanyType()) {
		
		case "hotel":
			HotelAdmin hotelAdmin = new HotelAdmin(admin);
			
			if(hotelAdminSvc.save(hotelAdmin) == null) {
				return "Username already exists";
			}
			
			response = sendConfirmationEmail(hotelAdmin);
			
			if(response.contains("invalid")) {
				hotelAdminSvc.deleteByUsername(hotelAdmin.getUsername());
			}
			
			return response;
			
		case "airline":
			AirlineAdmin airlineAdmin = new AirlineAdmin(admin);
			
			if(airlineAdminSvc.save(airlineAdmin) == null) {
				return "Username already exists";
			} 
			
			response = sendConfirmationEmail(airlineAdmin);
			
			if(response.contains("invalid")) {
				airlineAdminSvc.deleteByUsername(airlineAdmin.getUsername());
			}
			
			return response;
			
		case "rent-a-car":
			RentACarAdmin rent_a_car_admin = new RentACarAdmin(admin);
			
			if(rentACarAdminSvc.save(rent_a_car_admin) == null) {
				return "Username already exists";
			}
			
			response = sendConfirmationEmail(rent_a_car_admin);
			
			if(response.contains("invalid")) {
				rentACarAdminSvc.deleteByUsername(rent_a_car_admin.getUsername());
			}
			
			return response;
			
		case "system":
			SystemAdmin sysAdmin = new SystemAdmin(admin);
			
			if(save(sysAdmin) == null) {
				return "Username already exists";
			}
			
			response = sendConfirmationEmail(sysAdmin);
			
			if(response.contains("invalid")) {
				deleteByUsername(sysAdmin.getUsername());
			}
			
			return response;
			
		default:
			break;
			
		}
		
		return "Uknown company";
	}
	
	public String sendConfirmationEmail(AppUser admin) {
		
		ConfirmationToken token = new ConfirmationToken(admin);
		
		confirmationTokenRepository.save(token);
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		mail.setTo(admin.getEmailId());
		mail.setSubject("Complete AHC Registration");
		mail.setFrom("ahcreservation@gmail.com");
		mail.setText("To confirm your account, please click the following link: "
				+ " http://localhost:8080/user/confirmRegistration?token="+token.getConfirmationToken());
		try{
			emailSenderService.sendEmail(mail);
			return "A confirmation has been sent to your e-mail address";
		}
		catch(Exception e) {

			return "E-mail address is invalid";
		}
		
		
	}
	
	
	
	public Company registerCompany(CompanyInfo companyInfo) {
		
		if(companyInfo.getType() == null) {
			return null;
		}
		
		switch(companyInfo.getType()) {
		
		case "hotel":
			HotelAdmin hotelAdmin = hotelAdminSvc.findByUsername(companyInfo.getAdminUsername());
			System.out.println("Found admin: " + hotelAdmin.toString());
			
			Hotel hotel = new Hotel(companyInfo);
			hotelAdmin.setHotel(hotel);
			
			hotelAdminSvc.save(hotelAdmin);
			return hotelSvc.save(hotel);
			
		case "airline":
			AirlineAdmin airlineAdmin = airlineAdminSvc.findByUsername(companyInfo.getAdminUsername());
			System.out.println("Found admin: " + airlineAdmin.toString());
			
			Airline airline = new Airline(companyInfo);
			airlineAdmin.setAirline(airline);
			
			airlineAdminSvc.save(airlineAdmin);
			return airlineSvc.save(airline);
			
		case "rent-a-car":
			RentACarAdmin rentACarAdmin = rentACarAdminSvc.findByUsername(companyInfo.getAdminUsername());
			System.out.println("Found admin: " + rentACarAdmin.toString());
			
			RentACar rentACar = new RentACar(companyInfo);
			rentACarAdmin.setRentACar(rentACar);
			
			rentACarAdminSvc.save(rentACarAdmin);
			return rentACarSvc.save(rentACar);

		}
		
		return null;
	}


	public SystemAdmin save(SystemAdmin admin) {
		
		if(findByUsername(admin.getUsername()) != null) {
			return null;
		}
		
		return sysAdminRep.save(admin);
	}
	
	public SystemAdmin findByUsername(String username) {
		return sysAdminRep.findByUsername(username);
	}
	
	public void deleteByUsername(String username) {
		sysAdminRep.deleteByUsername(username);
	}
	
}
