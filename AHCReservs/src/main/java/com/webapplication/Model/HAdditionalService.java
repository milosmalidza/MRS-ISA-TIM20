package com.webapplication.Model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.webapplication.JSONBeans.HotelServiceData;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class HAdditionalService {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="service", unique=false, nullable=false)
	private HotelServiceType service;
	
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Price servicePrice;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Hotel hotel;
	
	public HAdditionalService() {
		
	}

	public HAdditionalService(Long id, HotelServiceType service, Price price) {
		
		this.id = id;
		this.service = service;
		this.servicePrice = price;
	}
	
	public HAdditionalService(HotelServiceData data, Hotel hotel) {
		
		this.service = data.getServiceType();
		
		this.servicePrice = new Price();
		this.servicePrice.setCurrency(data.getCurrency());
		this.servicePrice.setPrice(data.getPrice());
		
		this.setHotel(hotel);
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HotelServiceType getService() {
		return service;
	}

	public void setService(HotelServiceType service) {
		this.service = service;
	}

	public Price getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(Price price) {
		this.servicePrice = price;
	}

	
	@JsonIgnore
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	} 
	
	
	
	
	
}
