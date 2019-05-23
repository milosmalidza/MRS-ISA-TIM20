package com.webapplication.Repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.AirlineAdmin;

@Repository
public interface AirlineAdminRepository extends JpaRepository<AirlineAdmin, Long>{

	@Query("select admin from AirlineAdmin as admin where admin.username = ?1")
	AirlineAdmin findByUsername(String username);
	
	@Query("select admin from AirlineAdmin as admin where admin.id = ?1")
	public Optional<AirlineAdmin> findOne(Long id);
	
	@Transactional
	public void deleteByUsername(String username);
	
	public AirlineAdmin findByEmailIdIgnoreCase(String emailid);
}
