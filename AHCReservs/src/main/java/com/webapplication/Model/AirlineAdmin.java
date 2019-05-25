package com.webapplication.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.webapplication.JSONBeans.UserBean;

@Entity
public class AirlineAdmin extends AppUser {
	
	@Column(name="passwordChanged", nullable = false)
	private boolean passwordChanged;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Airline airline;
	
	public AirlineAdmin() {
		super();
	}
	
	public AirlineAdmin(String username, String password, String firstName, String lastName,
			String email, Airline airline) {
		
		super(username, password, firstName, lastName, email);
		this.airline = airline;
		
	}
	
	//used when registering a new admin
	public AirlineAdmin(UserBean admin) {
		
		super(admin.getUsername(), admin.getPassword(), admin.getFirstName(),
				admin.getLastName(), admin.getEmail());
		
		this.airline = null; //the admin is jobless when being registered
		
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}

	public boolean isPasswordChanged() {
		return passwordChanged;
	}

	public void setPasswordChanged(boolean passwordChanged) {
		this.passwordChanged = passwordChanged;
	}
	
	

}
