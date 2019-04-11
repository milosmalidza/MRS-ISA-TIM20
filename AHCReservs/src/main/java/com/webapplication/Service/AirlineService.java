package com.webapplication.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapplication.Model.Airline;
import com.webapplication.Repository.AirlineRepository;

@Service
public class AirlineService {

	@Autowired
	AirlineRepository airlineRep;
	
	public Airline save(Airline airline) {
		return airlineRep.save(airline);
	}
	
	
	public Airline findByName(String name) {
		return airlineRep.findOneByName(name);
	}
	
	public List<Airline> findAll() {
		return airlineRep.findAll();
	}
}
