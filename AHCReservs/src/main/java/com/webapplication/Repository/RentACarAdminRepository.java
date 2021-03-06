package com.webapplication.Repository;

import java.util.List;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.RentACarAdmin;

@Repository
public interface RentACarAdminRepository extends JpaRepository<RentACarAdmin, Long> {
	
	@Query("select admin from RentACarAdmin as admin where admin.username = ?1")
	RentACarAdmin findByUsername(String username);
	
	@Query("select admin from RentACarAdmin as admin where admin.id = ?1")
	public Optional<RentACarAdmin> findOne(Long id);
	
	@Query("select admin from RentACarAdmin as admin where admin.rent_a_car is null and admin.isEnabled = true")
	public List<RentACarAdmin> getAvailableAdmins();
	
	@Transactional
	public void deleteByUsername(String username);
	
	public RentACarAdmin findByEmailIdIgnoreCase(String emailid);
	
}
