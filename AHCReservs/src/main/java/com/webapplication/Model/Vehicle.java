package com.webapplication.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="vehicle")
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name = "name", unique = false, nullable = false)
	private String name;
	
	@Column(name = "description", unique = false, nullable = false)
	private String description;
	
	@Column(name = "numberOfSeats", unique = false, nullable = false)
	private int numOfSeats;
	
	@Column(name = "numOfDoors", unique = false, nullable = false)
	private int numOfDoors;
	
	@Enumerated
	@Column(name = "vehicleType", unique = false, nullable = false)
	private VehicleType vehicleType;
	
	@Column(name = "reserved", unique = false, nullable = false)
	private boolean reserved;
	
	@Column(name = "pricePerDay", unique = false, nullable = false)
	private int pricePerDay;
	
	@Column(name = "archived", nullable = false)
	private boolean archived;
	
	
	public Vehicle() {}
	
	public Vehicle(Long id, String name, String description, int numOfSeats, int numOfDoors, VehicleType vehicleType,
			boolean reserved, int pricePerDay, boolean archived) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.numOfSeats = numOfSeats;
		this.numOfDoors = numOfDoors;
		this.vehicleType = vehicleType;
		this.reserved = reserved;
		this.pricePerDay = pricePerDay;
		this.archived = archived;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isReserved() {
		return reserved;
	}

	public void setReserved(boolean reserved) {
		this.reserved = reserved;
	}

	public int getPricePerDay() {
		return pricePerDay;
	}

	public void setPricePerDay(int pricePerDay) {
		this.pricePerDay = pricePerDay;
	}

	public boolean isArchived() {
		return archived;
	}

	public void setArchived(boolean archived) {
		this.archived = archived;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumOfSeats() {
		return numOfSeats;
	}

	public void setNumOfSeats(int numOfSeats) {
		this.numOfSeats = numOfSeats;
	}

	public int getNumOfDoors() {
		return numOfDoors;
	}

	public void setNumOfDoors(int numOfDoors) {
		this.numOfDoors = numOfDoors;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(VehicleType vehicleType) {
		this.vehicleType = vehicleType;
	}
	
	
	
	
}
