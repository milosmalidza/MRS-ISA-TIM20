package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapplication.Model.AirlineAdmin;

public interface AirlineAdminRepository extends JpaRepository<AirlineAdmin, Long>{

	@Query("select admin from AirlineAdmin as admin where admin.username = ?1")
	AirlineAdmin findByUsername(String username);
	
}
