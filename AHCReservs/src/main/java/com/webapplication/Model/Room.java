package com.webapplication.Model;


public class Room {

	private int number;
	private int floor;
	private int numOfBeds;
	private double rating;
	private RoomType roomType;
	
	public Room() {
		
	}

	
	public Room(int number, int floor, int numOfBeds, double rating, RoomType roomType) {
		
		this.number = number;
		this.floor = floor;
		this.numOfBeds = numOfBeds;
		this.rating = rating;
		this.roomType = roomType;
	}
	

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public int getNumOfBeds() {
		return numOfBeds;
	}

	public void setNumOfBeds(int numOfBeds) {
		this.numOfBeds = numOfBeds;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}
	
	
	
	
}
