package com.webapplication.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.Vehicle;
import com.webapplication.Model.VehicleReservation;

@Repository
public interface VehicleReservationRepository extends JpaRepository<VehicleReservation, Long> {
	
	public VehicleReservation findOneById(Long name);
	
	
}