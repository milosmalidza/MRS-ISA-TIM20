package com.webapplication.Model;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class VehicleReservation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id", unique=true, nullable=false)
	private Long id;
	
	@Temporal(TemporalType.DATE)
	@Basic
	private Date reservationDate;
	
	@Temporal(TemporalType.DATE)
	@Basic
	private Date dueDate;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private RegisteredUser user;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Vehicle vehicle;

	public VehicleReservation() {}
	
	public VehicleReservation(Long id, Date reservationDate, Date dueDate, RegisteredUser user, Vehicle vehicle) {
		super();
		this.id = id;
		this.reservationDate = reservationDate;
		this.dueDate = dueDate;
		this.user = user;
		this.vehicle = vehicle;
	}




	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getReservationDate() {
		return reservationDate;
	}

	public void setReservationDate(Date reservationDate) {
		this.reservationDate = reservationDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public RegisteredUser getUser() {
		return user;
	}

	public void setUser(RegisteredUser user) {
		this.user = user;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
	
	
	
	
	
}