package com.webapplication.JSONBeans;


import com.webapplication.Model.RoomType;

public class RoomData {
	
	private Long hotelID;
	private int number;
	private int floor;
	private int numOfBeds;
	private RoomType roomType;
	private String roomTypeString;
	
	
	public RoomData() {
		
	}
	
	
	public RoomData(Long hotelID, int number, int floor, int numOfBeds, RoomType roomType, String roomTypeString) {
		
		this.hotelID = hotelID;
		this.number = number;
		this.floor = floor;
		this.numOfBeds = numOfBeds;
		this.roomType = roomType;
		this.roomTypeString = roomTypeString;
	}


	public Long getHotelID() {
		return hotelID;
	}


	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
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
	
	
	

	
}
