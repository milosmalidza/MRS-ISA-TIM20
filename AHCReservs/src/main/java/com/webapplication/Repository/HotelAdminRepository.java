package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.HotelAdmin;

@Repository
public interface HotelAdminRepository extends JpaRepository<HotelAdmin, Long> {
	
	@Query("select admin from HotelAdmin as admin where admin.username = ?1")
	HotelAdmin findByUsername(String username);
	
	@Transactional
	public void deleteByUsername(String username);
}
