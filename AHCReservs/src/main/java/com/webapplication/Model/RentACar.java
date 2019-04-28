package com.webapplication.Model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.webapplication.JSONBeans.CompanyInfo;

@Entity
public class RentACar extends Company {
	
	@OneToMany(mappedBy = "rent_a_car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RentACarAdmin> admins;
	
	@OneToMany(mappedBy = "rentACar", targetEntity = Vehicle.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Vehicle> vehicles;
	
	@OneToMany(mappedBy = "rentACar", targetEntity =  RentACarBranchOffice.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RentACarBranchOffice> branchOffices;
	

	public RentACar() {}
	
	public RentACar(Set<RentACarAdmin> admins, List<Vehicle> vehicles, List<RentACarBranchOffice> branchOffices) {
		super();
		this.admins = admins;
		this.vehicles = vehicles;
		this.branchOffices = branchOffices;
	}


	//used when registering a new company
	public RentACar(CompanyInfo companyInfo) {
		this.name = companyInfo.getName();
		this.admins = new HashSet<RentACarAdmin>();
		
	}
	

	public List<RentACarBranchOffice> getBranchOffice() {
		return branchOffices;
	}

	public void setBranchOffice(List<RentACarBranchOffice> branchOffices) {
		this.branchOffices = branchOffices;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	} 
	
	public Set<RentACarAdmin> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<RentACarAdmin> admins) {
		this.admins = admins;
	}
	
}
