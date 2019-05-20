package com.webapplication.Repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.RoomReservation;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {

	@Query("select reservation from RoomReservation as reservation where reservation.user = null")
	public Collection<RoomReservation> findQuickReservations();
}
