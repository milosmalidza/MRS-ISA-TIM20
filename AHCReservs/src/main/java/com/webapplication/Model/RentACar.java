package com.webapplication.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.webapplication.JSONBeans.CompanyInfo;

@Entity
public class RentACar extends Company {
	
	//private List<String> branchOffice;
	
	//@OneToMany(mappedBy = "rentacar", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//private Set<Vehicle> vehicles;
	
	@OneToMany(mappedBy = "rent_a_car", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<RentACarAdmin> admins;
	

	public RentACar() {
		super();
		//this.vehicles = new HashSet<Vehicle>();
		this.admins = new HashSet<RentACarAdmin>();
		
	}
	
	public RentACar(String name) {
		super(name);
		//this.vehicles = new HashSet<Vehicle>();
		this.admins = new HashSet<RentACarAdmin>();
		
	}

	public RentACar(Set<Vehicle> vehicles, Set<RentACarAdmin> admins) {
		super();
		//this.branchOffice = branchOffice;
		//this.vehicles = vehicles;
		this.admins = admins;
		
	}
	
	//used when registering a new company
	public RentACar(CompanyInfo companyInfo) {
		this.name = companyInfo.getName();
		this.admins = new HashSet<RentACarAdmin>();
		
	}
	

	/*
	public List<String> getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(List<String> branchOffice) {
		this.branchOffice = branchOffice;
	}

	public Set<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(Set<Vehicle> vehicles) {
		this.vehicles = vehicles;
	} */
	
	public Set<RentACarAdmin> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<RentACarAdmin> admins) {
		this.admins = admins;
	}
	
}
