package com.webapplication.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	public Vehicle findAllByName(String name);
	
	@Transactional
	public void deleteById(Long id);
	
	
}
