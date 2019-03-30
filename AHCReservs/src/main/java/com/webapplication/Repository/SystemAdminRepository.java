package com.webapplication.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapplication.Model.SystemAdmin;

@Repository
public interface SystemAdminRepository extends JpaRepository<SystemAdmin, Long> {

}
