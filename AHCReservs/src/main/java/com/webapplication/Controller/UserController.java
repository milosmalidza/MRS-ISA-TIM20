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
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.webapplication.Model.AirlineAdmin;
import com.webapplication.Model.ConfirmationToken;
import com.webapplication.Model.HotelAdmin;
import com.webapplication.Model.RegisteredUser;
import com.webapplication.Model.RentACarAdmin;
import com.webapplication.Model.SystemAdmin;
import com.webapplication.Repository.AirlineAdminRepository;
import com.webapplication.Repository.ConfirmationTokenRepository;
import com.webapplication.Repository.HotelAdminRepository;
import com.webapplication.Repository.RegisteredUserRepository;
import com.webapplication.Repository.RentACarAdminRepository;
import com.webapplication.Repository.SystemAdminRepository;
import com.webapplication.Service.EmailSenderService;


@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private SystemAdminRepository systemAdminRepository;
	
	@Autowired
	private RentACarAdminRepository rentacarAdminRepository;
	
	@Autowired
	private HotelAdminRepository hotelAdminRepository;
	
	@Autowired
	private AirlineAdminRepository airlineAdminRepository;
	
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
		
		if (userRepository.findByUsername(node.get("username").asText()) != null)
			return "usernameExists";
		else if (systemAdminRepository.findByUsername(node.get("username").asText()) != null)
			return "usernameExists";
		else if (rentacarAdminRepository.findByUsername(node.get("username").asText()) != null)
			return "usernameExists";
		else if (hotelAdminRepository.findByUsername(node.get("username").asText()) != null)
			return "usernameExists";
		else if (airlineAdminRepository.findByUsername(node.get("username").asText()) != null)
			return "usernameExists";
		
		
		
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
			try{
				emailSenderService.sendEmail(mail);
			}
			catch(Exception e) {
				userRepository.deleteByUsername(username);
				confirmationTokenRepository.deleteByConfirmationToken(token.getConfirmationToken());
				
				return "emailError";
			}
			
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
			return new ModelAndView("redirect:/index.html");
		}
		
	}
	
	
	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public String loginUser(@RequestParam("username") String username,
							@RequestParam("password") String password) throws IOException {
		
		RegisteredUser user = userRepository.findByUsername(username);
		SystemAdmin sysUser = systemAdminRepository.findByUsername(username);
		AirlineAdmin airAdmin = airlineAdminRepository.findByUsername(username);
		HotelAdmin hotelAdmin = hotelAdminRepository.findByUsername(username);
		RentACarAdmin carAdmin = rentacarAdminRepository.findByUsername(username);
		
		ObjectMapper mapper = new ObjectMapper();
		
		if (user != null) {
			
			if (password.equals(user.getPassword())) {
				
				if (!user.isEnabled()) {
					
					JsonNode n = mapper.createObjectNode();
					((ObjectNode) n).put("status", "notEnabled");
					
					return mapper.writeValueAsString(n);
				}
				
				
				String userJson = mapper.writeValueAsString(user);
				JsonNode jsonNode = mapper.readTree(userJson);
				((ObjectNode) jsonNode).put("status", "success");
				((ObjectNode) jsonNode).put("user", "registeredUser");
				System.out.println(mapper.writeValueAsString(jsonNode));
				
				
				
				return mapper.writeValueAsString(jsonNode);
			}
			else {
				
				JsonNode node = mapper.createObjectNode();
				((ObjectNode) node).put("status", "invalidPassword");
				
				return mapper.writeValueAsString(node);
			}
			
		}
		
		else if (sysUser != null) {
			
			if (password.equals(sysUser.getPassword())) {
				
				if (!sysUser.isEnabled()) {
					
					JsonNode n = mapper.createObjectNode();
					((ObjectNode) n).put("status", "notEnabled");
					
					return mapper.writeValueAsString(n);
				}
				
				
				String userJson = mapper.writeValueAsString(sysUser);
				JsonNode jsonNode = mapper.readTree(userJson);
				((ObjectNode) jsonNode).put("status", "success");
				((ObjectNode) jsonNode).put("user", "systemUser");
				System.out.println(mapper.writeValueAsString(jsonNode));
				
				
				
				return mapper.writeValueAsString(jsonNode);
			}
			else {
				
				JsonNode node = mapper.createObjectNode();
				((ObjectNode) node).put("status", "invalidPassword");
				
				return mapper.writeValueAsString(node);
			}
		}
		
		else if (airAdmin != null) {
			
			if (password.equals(airAdmin.getPassword())) {
				
				if (!airAdmin.isEnabled()) {
					
					JsonNode n = mapper.createObjectNode();
					((ObjectNode) n).put("status", "notEnabled");
					
					return mapper.writeValueAsString(n);
				}
				
				
				String userJson = mapper.writeValueAsString(airAdmin);
				JsonNode jsonNode = mapper.readTree(userJson);
				((ObjectNode) jsonNode).put("status", "success");
				((ObjectNode) jsonNode).put("user", "airAdmin");
				System.out.println(mapper.writeValueAsString(jsonNode));
				
				
				
				return mapper.writeValueAsString(jsonNode);
			}
			else {
				
				JsonNode node = mapper.createObjectNode();
				((ObjectNode) node).put("status", "invalidPassword");
				
				return mapper.writeValueAsString(node);
			}
		}
		
		else if (hotelAdmin != null) {
			
			if (password.equals(hotelAdmin.getPassword())) {
				
				if (!hotelAdmin.isEnabled()) {
					
					JsonNode n = mapper.createObjectNode();
					((ObjectNode) n).put("status", "notEnabled");
					
					return mapper.writeValueAsString(n);
				}
				
				
				String userJson = mapper.writeValueAsString(hotelAdmin);
				JsonNode jsonNode = mapper.readTree(userJson);
				((ObjectNode) jsonNode).put("status", "success");
				((ObjectNode) jsonNode).put("user", "hotelAdmin");
				System.out.println(mapper.writeValueAsString(jsonNode));
				
				
				
				return mapper.writeValueAsString(jsonNode);
			}
			else {
				
				JsonNode node = mapper.createObjectNode();
				((ObjectNode) node).put("status", "invalidPassword");
				
				return mapper.writeValueAsString(node);
			}
		}
		
		else if (carAdmin != null) {
			
			if (password.equals(carAdmin.getPassword())) {
				
				if (!carAdmin.isEnabled()) {
					
					JsonNode n = mapper.createObjectNode();
					((ObjectNode) n).put("status", "notEnabled");
					
					return mapper.writeValueAsString(n);
				}
				
				
				String userJson = mapper.writeValueAsString(carAdmin);
				JsonNode jsonNode = mapper.readTree(userJson);
				((ObjectNode) jsonNode).put("serviceId", carAdmin.getRentACar().getId());
				((ObjectNode) jsonNode).put("status", "success");
				((ObjectNode) jsonNode).put("user", "carAdmin");
				System.out.println(mapper.writeValueAsString(jsonNode));
				
				return mapper.writeValueAsString(jsonNode);
			}
			else {
				
				JsonNode node = mapper.createObjectNode();
				((ObjectNode) node).put("status", "invalidPassword");
				
				return mapper.writeValueAsString(node);
			}
		}
		
		
		else {
			
			JsonNode node = mapper.createObjectNode();
			((ObjectNode) node).put("status", "invalidUser");
			
			return mapper.writeValueAsString(node);
		}
		
		
	}
	
}
