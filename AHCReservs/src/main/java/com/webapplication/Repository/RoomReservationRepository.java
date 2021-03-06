package com.webapplication.Repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.RoomReservation;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

	public RoomReservation findOneById(Long id);
	
	@Query("select reservation from RoomReservation as reservation where reservation.user is null")
	public Collection<RoomReservation> findQuickReservations();
	
	
	
	@Transactional
	public void deleteById(Long id);
  
}
