package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.SystemAdmin;

@Repository
public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {

	@Query("select admin from SystemAdmin as admin where admin.username = ?1")
	SystemAdmin findByUsername(String username);
	
	@Transactional
	public void deleteByUsername(String username);
	
	public SystemAdmin findByEmailIdIgnoreCase(String emailid);
	
}
