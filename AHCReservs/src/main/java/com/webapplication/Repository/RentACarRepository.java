package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.RentACar;

@Repository
public interface RentACarRepository extends JpaRepository<RentACar, Long> {

	RentACar findOneByName(String name);
	
}
