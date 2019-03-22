package com.webapplication.Model;

public class Vehicle {
	
	private String id;
	private String name;
	private String description;
	private int numOfSeats;
	private int numOfDoors;
	private VehicleType vehicleType;
	private boolean reserved;
	private int pricePerDay;
	private boolean archived;
	
	
	public Vehicle() {}
	
	public Vehicle(String id, String name, String description, int numOfSeats, int numOfDoors, VehicleType vehicleType,
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
