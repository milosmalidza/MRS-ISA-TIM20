package com.webapplication.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.HotelAdmin;

@Repository
public interface HotelAdminRepository extends JpaRepository<HotelAdmin, Long> {
	
	@Query("select admin from HotelAdmin as admin where admin.username = ?1")
	HotelAdmin findByUsername(String username);
	
	@Query("select admin from HotelAdmin as admin where admin.id = ?1")
	Optional<HotelAdmin> findOne(Long id);
	
	@Query("select admin from HotelAdmin as admin where admin.hotel is null and admin.isEnabled = true")
	List<HotelAdmin> getAvailableAdmins();
	
	@Transactional
	void deleteByUsername(String username);
	
	HotelAdmin findByEmailIdIgnoreCase(String emailId);
}
