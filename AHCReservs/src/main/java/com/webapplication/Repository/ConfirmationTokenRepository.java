package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapplication.Model.ConfirmationToken;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
	
	public ConfirmationToken findByConfirmationToken(String confirmationToken);
	
}
