package com.webapplication.Repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.HAdditionalService;
import com.webapplication.Model.HotelServiceType;

@Repository
public interface HAdditionalServiceRepository extends JpaRepository<HAdditionalService, Long> {

	Optional<HAdditionalService> findById(Long id);
	
	@Query("select service from HAdditionalService as service where service.hotel.id = ?1")
	List<HAdditionalService> findAllForHotel(Long hotelID);
	
	
	@Query("select service from HAdditionalService as service where service.hotel.id = ?1 and service.service = ?2")
	HAdditionalService findOneForHotel(Long hotelID, HotelServiceType serviceType);
	
}
