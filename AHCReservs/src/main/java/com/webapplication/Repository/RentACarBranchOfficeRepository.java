package com.webapplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.RentACar;
import com.webapplication.Model.RentACarBranchOffice;


@Repository
public interface RentACarBranchOfficeRepository extends JpaRepository<RentACarBranchOffice, Long> {

	
	List<RentACarBranchOffice> findAllByRentACar(RentACar rentACar);
	
	
}