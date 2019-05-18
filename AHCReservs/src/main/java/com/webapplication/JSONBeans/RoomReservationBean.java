package com.webapplication.JSONBeans;

import java.util.List;

public class RoomReservationBean {

	private Long hotelID;
	private String strCheckInDate;
	private String strCheckOutDate;
	private int numOfGuests;
	private String username;
	private List<Long> selectedRooms;
	private List<String> selectedAdditionalServices;
	
	public RoomReservationBean() {
		
	}

	

	public RoomReservationBean(Long hotelID, String strCheckInDate, String strCheckOutDate, int numOfGuests,
			String username, List<Long> selectedRooms, List<String> selectedAdditionalServices) {
		
		this.hotelID = hotelID;
		this.strCheckInDate = strCheckInDate;
		this.strCheckOutDate = strCheckOutDate;
		this.numOfGuests = numOfGuests;
		this.username = username;
		this.selectedRooms = selectedRooms;
		this.selectedAdditionalServices = selectedAdditionalServices;
	}



	public String getStrCheckInDate() {
		return strCheckInDate;
	}

	public void setStrCheckInDate(String strCheckInDate) {
		this.strCheckInDate = strCheckInDate;
	}

	public String getStrCheckOutDate() {
		return strCheckOutDate;
	}

	public void setStrCheckOutDate(String strCheckOutDate) {
		this.strCheckOutDate = strCheckOutDate;
	}

	public int getNumOfGuests() {
		return numOfGuests;
	}

	public void setNumOfGuests(int numOfGuests) {
		this.numOfGuests = numOfGuests;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Long> getSelectedRooms() {
		return selectedRooms;
	}

	public void setSelectedRooms(List<Long> selectedRooms) {
		this.selectedRooms = selectedRooms;
	}

	public List<String> getSelectedAdditionalServices() {
		return selectedAdditionalServices;
	}

	public void setSelectedAdditionalServices(List<String> selectedAdditionalServices) {
		this.selectedAdditionalServices = selectedAdditionalServices;
	}

	public Long getHotelID() {
		return hotelID;
	}

	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	
	
	
}
