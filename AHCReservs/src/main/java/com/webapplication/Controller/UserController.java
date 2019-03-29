package com.webapplication.Controller;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.Model.ConfirmationToken;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Repository.ConfirmationTokenRepository;
import com.webapplication.Repository.RegisteredUserRepository;
import com.webapplication.Service.EmailSenderService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	
	@Autowired
	private RegisteredUserRepository userRepository;
	
	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	
	
	
	@RequestMapping(value = "/registerUser", method = RequestMethod.POST)
	public String registerUser(@RequestParam("json") String json) throws IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		JsonNode node = mapper.readTree(json);
		RegisteredUser user = userRepository.findByEmailIdIgnoreCase(node.get("email").asText());
		
		
		if (user != null) {
			
			return "emailExists";
		}
		else {
			String username = node.get("username").asText();
			String password = node.get("password").asText();
			String firstName = node.get("firstName").asText();
			String lastName = node.get("lastName").asText();
			String email = node.get("email").asText();
			RegisteredUser ru = new RegisteredUser(username, password, firstName, lastName, email);
			userRepository.save(ru);
			
			ConfirmationToken token = new ConfirmationToken(ru);
			
			confirmationTokenRepository.save(token);
			
			SimpleMailMessage mail = new SimpleMailMessage();
			
			mail.setTo(ru.getEmailId());
			mail.setSubject("Complete AHC Registration");
			mail.setFrom("ahcreservation@gmail.com");
			mail.setText("To confirm your account, please click the following link: "
					+ " http://localhost:8080/user/confirmRegistration?token="+token.getConfirmationToken());
			
			emailSenderService.sendEmail(mail);
			return "success";
		}
		
		
	}
	
	@RequestMapping(value = "/confirmRegistration", method = RequestMethod.GET)
	public ModelAndView confirmRegistration(@RequestParam("token") String token) {
		
		ConfirmationToken confirmationToken = confirmationTokenRepository.findByConfirmationToken(token);
		
		if (confirmationToken != null) {
			
			RegisteredUser user = userRepository.findByEmailIdIgnoreCase(confirmationToken.getUser().getEmailId());
			user.setEnabled(true);
			userRepository.save(user);
			
			
			return new ModelAndView("redirect:/login.html");
		}
		
		else {
			return new ModelAndView("redirect:");
		}
		
	}
	
	
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public String loginUser(@RequestParam("username") String username,
							@RequestParam("password") String password) {
		
		
		
		return username;
	}
	
}
