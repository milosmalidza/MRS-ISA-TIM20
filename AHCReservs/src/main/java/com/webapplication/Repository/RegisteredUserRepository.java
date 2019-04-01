package com.webapplication.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.webapplication.Model.RegisteredUser;

@Repository
public interface RegisteredUserRepository extends JpaRepository<RegisteredUser, String>{
	
	public List<RegisteredUser> findAll();
	
	public RegisteredUser findByEmailIdIgnoreCase(String emailid);
	
	public RegisteredUser findByUsername(String username);
	
	@Transactional
	public void deleteByUsername(String username);
	
}
