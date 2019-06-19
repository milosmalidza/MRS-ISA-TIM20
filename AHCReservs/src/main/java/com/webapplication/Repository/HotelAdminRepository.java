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
	public Optional<HotelAdmin> findOne(Long id);
	
	@Query("select admin from HotelAdmin as admin where admin.hotel is null and admin.isEnabled = true")
	public List<HotelAdmin> getAvailableAdmins();
	
	@Transactional
	public void deleteByUsername(String username);
	
	public HotelAdmin findByEmailIdIgnoreCase(String emailid);
}
