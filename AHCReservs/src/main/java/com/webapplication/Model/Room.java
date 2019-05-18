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
import com.webapplication.JSONBeans.RoomData;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Column(name="number", unique=true, nullable=false)
	private int number;
	
	@Column(name="floor", unique=false, nullable=false)
	private int floor;
	
	@Column(name="numOfBeds", unique=false, nullable=false)
	private int numOfBeds;
	
	@Column(name="roomType", unique=false, nullable=false)
	private RoomType roomType;
	
	@Column(name="discount", unique=false, nullable=false)
	private boolean discount;
	
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Hotel hotel;
	
	@Column(name="roomPrice", unique=false, nullable=false)
	private double roomPrice;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private RoomReservation reservation;
	
	public Room() {
	
	}

	public Room(Long id, int number, int floor, int numOfBeds, RoomType roomType,
			boolean discount, Hotel hotel) {
		
		this.id = id;
		this.number = number;
		this.floor = floor;
		this.numOfBeds = numOfBeds;
		this.roomType = roomType;
		this.discount = discount;
		this.hotel = hotel;
		
	}
	
	
	public Room(RoomData roomData) {
		
		this.number = roomData.getNumber();
		this.floor = roomData.getFloor();
		this.numOfBeds = roomData.getNumOfBeds();
		this.roomType = roomData.getRoomType();
		this.discount = false;
		this.roomPrice = roomData.getPrice();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	
	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}

	@JsonIgnore //ignoring to avoid infinite recursion when returning json
	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}

	@JsonIgnore
	public RoomReservation getReservation() {
		return reservation;
	}

	public void setReservation(RoomReservation reservation) {
		this.reservation = reservation;
	}

	
}
