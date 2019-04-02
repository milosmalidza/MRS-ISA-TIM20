package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.Airline;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Long> {

	Airline findOneByName(String name);
	
}
