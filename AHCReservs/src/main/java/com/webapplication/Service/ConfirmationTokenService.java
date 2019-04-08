package com.webapplication.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Model.ConfirmationTokenAirAdmin;
import com.webapplication.Model.ConfirmationTokenHotelAdmin;
import com.webapplication.Model.ConfirmationTokenRACAdmin;
import com.webapplication.Model.ConfirmationTokenSysAdmin;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.SystemAdmin;
import com.webapplication.Repository.ConfirmationTokenAirAdminRepository;
import com.webapplication.Repository.ConfirmationTokenHotelAdminRepository;
import com.webapplication.Repository.ConfirmationTokenRACAdminRepository;
import com.webapplication.Repository.ConfirmationTokenSysAdminRepository;

@Service
public class ConfirmationTokenService {
	
	/* Email notification services*/
	@Autowired
	private ConfirmationTokenHotelAdminRepository confirmationTokenHotelAdminRepository;
	
	@Autowired
	private ConfirmationTokenAirAdminRepository confirmationTokenAirAdminRepository;
	
	@Autowired
	private ConfirmationTokenRACAdminRepository confirmationTokenRACAdminRepository;
	
	@Autowired
	private ConfirmationTokenSysAdminRepository confirmationTokenSysAdminRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	
	@Autowired
	HotelAdminService hotelAdminSvc;
	
	@Autowired
	AirlineAdminService airlineAdminSvc;
	
	@Autowired
	RentACarAdminService racAdminSvc;
	
	@Autowired
	SystemAdminService sysAdminSvc;
	
	
	/* Mail nofitication senders */

	public String sendConfirmationMail(HotelAdmin admin) {
	
		SimpleMailMessage mail = new SimpleMailMessage();
		
		ConfirmationTokenHotelAdmin hotelToken = new ConfirmationTokenHotelAdmin(admin);
		confirmationTokenHotelAdminRepository.save(hotelToken);
	
		mail.setTo(admin.getEmailId());
		mail.setSubject("Complete AHC Registration");
		mail.setFrom("ahcreservation@gmail.com");
		mail.setText("To confirm your account, please click the following link: "
				+ " http://localhost:8080/sysadmin/confirmHotelAdminRegistration?token="+hotelToken.getConfirmationToken());
		
		
		try {
			emailSenderService.sendEmail(mail);
			return "A confirmation has been sent to your e-mail address";
		}
		catch(Exception e) {

			return "E-mail address is invalid";
		}
		
	}
	
	
	public String sendConfirmationMail(AirlineAdmin admin) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		ConfirmationTokenAirAdmin airToken = new ConfirmationTokenAirAdmin(admin);
		confirmationTokenAirAdminRepository.save(airToken);
	
		mail.setTo(admin.getEmailId());
		mail.setSubject("Complete AHC Registration");
		mail.setFrom("ahcreservation@gmail.com");
		mail.setText("To confirm your account, please click the following link: "
				+ " http://localhost:8080/sysadmin/confirmAirlineAdminRegistration?token="+airToken.getConfirmationToken());
		
		
		try{
			emailSenderService.sendEmail(mail);
			return "A confirmation has been sent to your e-mail address";
		}
		catch(Exception e) {

			return "E-mail address is invalid";
		}
		
	}
	
	
	public String sendConfirmationMail(RentACarAdmin admin) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		ConfirmationTokenRACAdmin racToken = new ConfirmationTokenRACAdmin(admin);
		confirmationTokenRACAdminRepository.save(racToken);
	
		mail.setTo(admin.getEmailId());
		mail.setSubject("Complete AHC Registration");
		mail.setFrom("ahcreservation@gmail.com");
		mail.setText("To confirm your account, please click the following link: "
				+ " http://localhost:8080/sysadmin/confirmRentACarAdminRegistration?token="+racToken.getConfirmationToken());
		
		
		try{
			emailSenderService.sendEmail(mail);
			return "A confirmation has been sent to your e-mail address";
		}
		catch(Exception e) {

			return "E-mail address is invalid";
		}
		
	}
	
	
	public String sendConfirmationMail(SystemAdmin admin) {
		
		SimpleMailMessage mail = new SimpleMailMessage();
		
		ConfirmationTokenSysAdmin sysToken = new ConfirmationTokenSysAdmin(admin);
		confirmationTokenSysAdminRepository.save(sysToken);
	
		mail.setTo(admin.getEmailId());
		mail.setSubject("Complete AHC Registration");
		mail.setFrom("ahcreservation@gmail.com");
		mail.setText("To confirm your account, please click the following link: "
				+ " http://localhost:8080/sysadmin/confirmSysAdminRegistration?token="+sysToken.getConfirmationToken());
		
		
		try{
			emailSenderService.sendEmail(mail);
			return "A confirmation has been sent to your e-mail address";
		}
		catch(Exception e) {

			return "E-mail address is invalid";
		}
		
	}
	
	
	/* Confirmation token functions */
	public String confirmHotelAdminRegistration(String token) {
		
		ConfirmationTokenHotelAdmin confirmationToken = confirmationTokenHotelAdminRepository.findByConfirmationToken(token);
		
		if (confirmationToken != null) {
			
			HotelAdmin hotelAdmin = hotelAdminSvc.findByEmailIdIgnoreCase(confirmationToken.getUser().getEmailId());
			hotelAdmin.setEnabled(true);
			hotelAdminSvc.save(hotelAdmin);
			
			
			return "redirect:/login.html";
		}
		
		else {
			return "redirect:/index.html";
		}
		
	}
	
	
	public String confirmAirlineAdminRegistration(String token) {
		
		ConfirmationTokenAirAdmin confirmationToken = confirmationTokenAirAdminRepository.findByConfirmationToken(token);
		
		if (confirmationToken != null) {
			
			AirlineAdmin airAdmin = airlineAdminSvc.findByEmailIdIgnoreCase(confirmationToken.getUser().getEmailId());
			airAdmin.setEnabled(true);
			airlineAdminSvc.save(airAdmin);
			
			return "redirect:/login.html";
		}
		
		else {
			return "redirect:/index.html";
		}
		
	}
	
	
	public String confirmRentACarAdminRegistration(String token) {
		
		ConfirmationTokenRACAdmin confirmationToken = confirmationTokenRACAdminRepository.findByConfirmationToken(token);
		
		if (confirmationToken != null) {
			
			RentACarAdmin racAdmin = racAdminSvc.findByEmailIdIgnoreCase(confirmationToken.getUser().getEmailId());
			racAdmin.setEnabled(true);
			racAdminSvc.save(racAdmin);
			
			return "redirect:/login.html";
		}
		
		else {
			return "redirect:/index.html";
		}
		
	}
	
	public String confirmSysAdminRegistration(String token) {
		
		ConfirmationTokenSysAdmin confirmationToken = confirmationTokenSysAdminRepository.findByConfirmationToken(token);
		
		if (confirmationToken != null) {
			
			SystemAdmin sysAdmin = sysAdminSvc.findByEmailIdIgnoreCase(confirmationToken.getUser().getEmailId());
			sysAdmin.setEnabled(true);
			sysAdminSvc.save(sysAdmin);
			
			return "redirect:/login.html";
		}
		
		else {
			return "redirect:/index.html";
		}
		
	}

	
}
