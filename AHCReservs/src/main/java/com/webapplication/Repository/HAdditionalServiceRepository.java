package com.webapplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapplication.Model.HAdditionalService;

public interface HAdditionalServiceRepository extends JpaRepository<HAdditionalService, Long> {

	Optional<HAdditionalService> findById(Long id);
	
}
