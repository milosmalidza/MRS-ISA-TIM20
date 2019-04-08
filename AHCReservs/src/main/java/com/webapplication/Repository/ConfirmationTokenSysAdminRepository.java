package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.ConfirmationTokenSysAdmin;

public interface ConfirmationTokenSysAdminRepository extends JpaRepository<ConfirmationTokenSysAdmin, String> {

	public ConfirmationTokenSysAdmin findByConfirmationToken(String confirmationToken);
	
	@Transactional
	public void deleteByConfirmationToken(String confirmationToken);
	
}
