package com.webapplication.Model;

import java.util.Date;

import javax.persistence.Basic;
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
public class Reservation {
	
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
	
	@ManyToOne
	private RegisteredUser user;
	
	@OneToOne
	private Vehicle vehicle;
	
	
	
}
