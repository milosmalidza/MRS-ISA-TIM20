package com.webapplication.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.HAdditionalService;

@Repository
public interface HAdditionalServiceRepository extends JpaRepository<HAdditionalService, Long> {

	Optional<HAdditionalService> findById(Long id);
	
}
