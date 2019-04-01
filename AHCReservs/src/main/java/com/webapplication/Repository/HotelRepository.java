package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

	Hotel findOneByName(String name);

}
