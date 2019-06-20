package com.webapplication.JSONBeans;

import com.webapplication.Model.HotelServiceType;

public class HotelServiceData {
	
	private Long hotelID;
	private Long serviceID;
	private String serviceTypeString;
	private HotelServiceType serviceType;
	private double price;
	
	public HotelServiceData() {
		
	}


	public String getServiceTypeString() {
		return serviceTypeString;
	}

	public void setServiceTypeString(String serviceTypeString) {
		this.serviceTypeString = serviceTypeString;
	}

	public HotelServiceType getServiceType() {
		return serviceType;
	}

	public void setServiceType(HotelServiceType serviceType) {
		this.serviceType = serviceType;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getHotelID() {
		return hotelID;
	}

	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	
	public Long getServiceID() {
		return serviceID;
	}


	public void setServiceID(Long serviceID) {
		this.serviceID = serviceID;
	}


}
