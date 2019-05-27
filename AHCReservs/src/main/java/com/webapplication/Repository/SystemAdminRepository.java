package com.webapplication.Repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.SystemAdmin;

@Repository
public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {

	@Query("select admin from SystemAdmin as admin where admin.username = ?1")
	SystemAdmin findByUsername(String username);
	
	@Query("select admin from SystemAdmin as admin where admin.id = ?1")
	public Optional<SystemAdmin> findOne(Long id);
	
	@Transactional
	public void deleteByUsername(String username);
	
	public SystemAdmin findByEmailIdIgnoreCase(String emailid);
	
}
