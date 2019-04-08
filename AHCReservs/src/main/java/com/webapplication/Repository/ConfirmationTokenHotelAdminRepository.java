package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.ConfirmationTokenHotelAdmin;

public interface ConfirmationTokenHotelAdminRepository extends JpaRepository<ConfirmationTokenHotelAdmin, String> {

	public ConfirmationTokenHotelAdmin findByConfirmationToken(String confirmationToken);
	
	@Transactional
	public void deleteByConfirmationToken(String confirmationToken);
	
}
