package com.webapplication.Model;

public class RentACarAdmin extends AppUser {

	private RentACar rentACar;
	
	public RentACarAdmin() {
		super();
	}
	
	public RentACarAdmin(String username, String password, String firstName, String lastName,
			String email, RentACar rentACar) {
		
		super(username, password, firstName, lastName, email);
		this.rentACar = rentACar;
		
	}

	public RentACar getRentACar() {
		return rentACar;
	}

	public void setRentACar(RentACar rentACar) {
		this.rentACar = rentACar;
	}

	
	
	
	
}
