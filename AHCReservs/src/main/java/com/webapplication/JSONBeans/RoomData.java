package com.webapplication.JSONBeans;

import com.webapplication.Model.RoomType;

public class RoomData {
	
	private Long hotelID;
	private Long roomID;
	private int number;
	private int floor;
	private int numOfBeds;
	
	private RoomType roomType;
	private String roomTypeString;
	
	private double price;
	
	public RoomData() {
		
	}

	public Long getHotelID() {
		return hotelID;
	}


	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}


	public Long getRoomID() {
		return roomID;
	}


	public void setRoomID(Long roomID) {
		this.roomID = roomID;
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


	public RoomType getRoomType() {
		return roomType;
	}


	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}


	public String getRoomTypeString() {
		return roomTypeString;
	}


	public void setRoomTypeString(String roomTypeString) {
		this.roomTypeString = roomTypeString;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	

	
}
