package com.webapplication.Model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.webapplication.JSONBeans.CompanyInfo;

@Entity
public class Airline extends Company {

	@OneToMany(mappedBy = "airline", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<AirlineAdmin> admins;
	
	public Airline() {
		super();
		this.admins = new HashSet<AirlineAdmin>();
	}
	
	//used when registering a new company
	public Airline(CompanyInfo companyInfo) {
		this.name = companyInfo.getName();
		this.admins = new HashSet<AirlineAdmin>();
	}
	

	public Set<AirlineAdmin> getAdmins() {
		return admins;
	}

	public void setAdmins(Set<AirlineAdmin> admins) {
		this.admins = admins;
	}
	
}
