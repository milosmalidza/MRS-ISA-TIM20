package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
	
	public Vehicle findAllByName(String name);
	
	
}
