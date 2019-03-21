package com.webapplication.Model;

public class Vehicle {
	
	private String id;
	private String name;
	private int numOfSeats;
	private int numOfDoors;
	private VehicleType tipVozila;
	
	
	public Vehicle() {}
	
	public Vehicle(String id, String name, int numOfSeats, int numOfDoors, VehicleType vehicleType) {
		
		super();
		this.id = id;
		this.name = name;
		this.numOfSeats = numOfSeats;
		this.numOfDoors = numOfDoors;
		this.tipVozila = vehicleType;
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

	public VehicleType getTipVozila() {
		return tipVozila;
	}

	public void setTipVozila(VehicleType tipVozila) {
		this.tipVozila = tipVozila;
	}
	
	
	
	
}
