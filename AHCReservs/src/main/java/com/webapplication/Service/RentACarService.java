package com.webapplication.Service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapplication.Model.RentACar;
import com.webapplication.Repository.RentACarRepository;

@Service
public class RentACarService {

	
	@Autowired
	RentACarRepository rentACarRep;
	
	
	public RentACar save(RentACar rentACar) {
		return rentACarRep.save(rentACar);
	}
	
	
	public RentACar findByName(String name) {
		return rentACarRep.findOneByName(name);
	}
	
	public List<RentACar> findAll() {
		return rentACarRep.findAll();
	}
	
	
	
	
	
}









