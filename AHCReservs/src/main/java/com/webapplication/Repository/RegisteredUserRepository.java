package com.webapplication.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapplication.Model.RegisteredUser;


public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, String>{
	
	public List<RegisteredUser> findAll();
	
	public RegisteredUser findOne(String username);
}
