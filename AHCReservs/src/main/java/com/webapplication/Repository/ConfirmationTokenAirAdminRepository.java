package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.ConfirmationTokenAirAdmin;

public interface ConfirmationTokenAirAdminRepository extends JpaRepository<ConfirmationTokenAirAdmin, String> {

	public ConfirmationTokenAirAdmin findByConfirmationToken(String confirmationToken);
	
	@Transactional
	public void deleteByConfirmationToken(String confirmationToken);
	
}
