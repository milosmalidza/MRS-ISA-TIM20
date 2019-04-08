package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.ConfirmationTokenRACAdmin;

public interface ConfirmationTokenRACAdminRepository extends JpaRepository<ConfirmationTokenRACAdmin, String> {

	public ConfirmationTokenRACAdmin findByConfirmationToken(String confirmationToken);
	
	@Transactional
	public void deleteByConfirmationToken(String confirmationToken);
	
}
