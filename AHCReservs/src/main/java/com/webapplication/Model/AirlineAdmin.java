package com.webapplication.Model;

public class AirlineAdmin extends User {
	
	private Airline airline;
	
	public AirlineAdmin() {
		super();
	}
	
	public AirlineAdmin(String username, String password, String firstName, String lastName,
			String email, Airline airline) {
		
		super(username, password, firstName, lastName, email);
		this.airline = airline;
		
	}

	public Airline getAirline() {
		return airline;
	}

	public void setAirline(Airline airline) {
		this.airline = airline;
	}
	
	

}
