package com.webapplication.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.webapplication.JSONBeans.AdminToRegister;

@Entity
public class RentACarAdmin extends AppUser {

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private RentACar rent_a_car;
	
	public RentACarAdmin() {
		super();
	}
	
	public RentACarAdmin(String username, String password, String firstName, String lastName,
			String email, RentACar rentACar) {
		
		super(username, password, firstName, lastName, email);
		this.rent_a_car = rentACar;
		
	}
	
	//used when registering a new admin
	public RentACarAdmin(AdminToRegister admin) {
		
		super(admin.getUsername(), admin.getPassword(), admin.getFirstName(),
				admin.getLastName(), admin.getEmail());
		
		this.rent_a_car = null; //the admin is jobless when being registered
		
	}

	public RentACar getRentACar() {
		return rent_a_car;
	}

	public void setRentACar(RentACar rentACar) {
		this.rent_a_car = rentACar;
	}

	
	
	
	
}
