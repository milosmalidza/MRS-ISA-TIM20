package com.webapplication.JSONBeans;

import com.webapplication.Model.Currency;
import com.webapplication.Model.HotelServiceType;

public class HotelServiceData {
	
	private Long hotelID;
	private String serviceTypeString;
	private HotelServiceType serviceType;
	private double price;
	private String currencyString;
	private Currency currency;
	
	public HotelServiceData() {
		
	}

	
	public HotelServiceData(Long hotelID, String serviceTypeString, HotelServiceType serviceType, double price,
			String currencyString, Currency currency) {
		super();
		this.hotelID = hotelID;
		this.serviceTypeString = serviceTypeString;
		this.serviceType = serviceType;
		this.price = price;
		this.currencyString = currencyString;
		this.currency = currency;
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

	public String getCurrencyString() {
		return currencyString;
	}

	public void setCurrencyString(String currencyString) {
		this.currencyString = currencyString;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Long getHotelID() {
		return hotelID;
	}

	public void setHotelID(Long hotelID) {
		this.hotelID = hotelID;
	}
	
	
}
