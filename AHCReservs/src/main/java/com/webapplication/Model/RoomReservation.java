package com.webapplication.Model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //to ignore certain fields
public class RoomReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="checkIn", unique=false, nullable=false)
	private Date checkIn;
	
	@Column(name="checkOut", unique=false, nullable=false)
	private Date checkOut;
	
	@Column(name="numOfGuests", unique=false, nullable=false)
	private int numOfGuests;
	
	@Column(name="reservedPrice", unique=false, nullable=false)
	private double reservedPrice; //the price of the room at the moment of reservation
	
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Hotel hotel;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private RegisteredUser user;
	
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Room room;
	
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Set<HAdditionalService> additionalServices;
	
	
	public RoomReservation() {
		
	}


	public RoomReservation(Date checkIn, Date checkOut, int numOfGuests, double reservedPrice, Hotel hotel,
			RegisteredUser user, Room room, Set<HAdditionalService> additionalServices) {
		
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.numOfGuests = numOfGuests;
		this.reservedPrice = reservedPrice;
		this.hotel = hotel;
		this.user = user;
		
		this.room = room;
		this.additionalServices = additionalServices;
	}




	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getCheckIn() {
		return checkIn;
	}


	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}


	public Date getCheckOut() {
		return checkOut;
	}


	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}


	public int getNumOfGuests() {
		return numOfGuests;
	}


	public void setNumOfGuests(int numOfGuests) {
		this.numOfGuests = numOfGuests;
	}


	public Hotel getHotel() {
		return hotel;
	}


	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	
	@JsonIgnore
	public RegisteredUser getUser() {
		return user;
	}


	public void setUser(RegisteredUser user) {
		this.user = user;
	}


	public double getReservedPrice() {
		return reservedPrice;
	}


	public void setReservedPrice(double reservedPrice) {
		this.reservedPrice = reservedPrice;
	}


	public Room getRoom() {
		return room;
	}


	public void setRoom(Room room) {
		this.room = room;
	}


	public Set<HAdditionalService> getAdditionalServices() {
		return additionalServices;
	}


	public void setAdditionalServices(Set<HAdditionalService> additionalServices) {
		this.additionalServices = additionalServices;
	}

	
	
}
