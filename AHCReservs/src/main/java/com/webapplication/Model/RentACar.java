package com.webapplication.Model;

import java.util.List;

public class RentACar extends Company {
	
	private List<String> branchOffice;
	private List<Vehicle> vehicles;
	
	public RentACar() {
		super();
	}
	
	public RentACar(String name) {
		super(name);
	}

	public RentACar(List<String> branchOffice, List<Vehicle> vehicles) {
		super();
		this.branchOffice = branchOffice;
		this.vehicles = vehicles;
	}

	public List<String> getBranchOffice() {
		return branchOffice;
	}

	public void setBranchOffice(List<String> branchOffice) {
		this.branchOffice = branchOffice;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}
	
	
	
}
