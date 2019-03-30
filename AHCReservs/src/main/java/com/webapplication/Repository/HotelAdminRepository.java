package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.HotelAdmin;

@Repository
public interface HotelAdminRepository extends JpaRepository<HotelAdmin, Long> {

}
