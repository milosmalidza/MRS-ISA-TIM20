package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapplication.Model.RentACarAdmin;

public interface RentACarAdminRepository extends JpaRepository<RentACarAdmin, Long> {

	
	@Query("select admin from RentACarAdmin as admin where admin.username = ?1")
	RentACarAdmin findByUsername(String username);
	
}
